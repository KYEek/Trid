package member.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import common.domain.PagingDTO;
import member.domain.MemberDTO;
import util.StringUtil;
import util.security.AES256;
import util.security.Sha256;

public class MemberDAO_imple implements MemberDAO {

	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private AES256 aes;
 
	// 생성자
	public MemberDAO_imple() {

		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/semioracle");

			aes = new AES256("trid3333#gclass$");

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	// 사용한 자원을 반납하는 close() 메소드 생성하기
	private void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// end of private void close()---------------

	
	// 회원가입을 해주는 메소드(tbl_member 테이블에 insert)
	@Override
	public int registerMember(MemberDTO member) throws SQLException {
		
		int result = 0;

		try {
			conn = ds.getConnection();

			String sql 	= " insert into tbl_member(pk_member_no, member_email, member_password, "
						+ " member_name, member_mobile, member_gender, member_birthday, member_pwdchangeday, "
						+ " member_updateday, member_registerday) "
						+ " values(pk_member_no_seq.nextval , ?, ?, ?, ?, ?, ?, sysdate , sysdate, sysdate) ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, aes.encrypt(member.getMember_email()));
			pstmt.setString(2, Sha256.encrypt(member.getMember_password())); // 암호를 SHA256 알고리즘으로 단방향 암호화 시킨다.
			pstmt.setString(3, member.getMember_name());
			pstmt.setString(4, aes.encrypt(member.getMember_mobile())); // 휴대폰을 AES256 알고리즘으로 양방향 암호화 시킨다.
			pstmt.setInt(5, member.getMember_gender());
			pstmt.setString(6, member.getMember_birthday());

			result = pstmt.executeUpdate();

		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return result;
	}// end of public int registerMember(MemberDTO member) throws SQLException--------------------------------
	

	// 로그인 처리를 해주는 메소드
	@Override
	public MemberDTO login(Map<String, String> paraMap) throws SQLException {
		
		MemberDTO member = null;

		try {
			conn = ds.getConnection();

			String sql 	= " select pk_member_no, member_email, member_name, member_mobile, member_gender, member_birthday, member_idle, to_char(member_registerday, 'yyyy-mm-dd') as member_registerday, "
						+ " member_pwdchangeday, to_char(member_updateday, 'yyyy-mm-dd') as member_updateday,"
						+ " (select NVL(ROUND(MONTHS_BETWEEN(sysdate, max(login_date))), 0) from tbl_login where fk_member_no = pk_member_no) as lastlogingap " 
						+ " from tbl_member "
						+ " where member_email = ? and member_password = ? "
						+ " and member_status = 1 ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, aes.encrypt(paraMap.get("member_email")));
			pstmt.setString(2, Sha256.encrypt(paraMap.get("member_password")));
			
			rs = pstmt.executeQuery();

			if (rs.next()) {

				member = new MemberDTO();

				member.setPk_member_no(rs.getInt("pk_member_no"));
				member.setMember_email(aes.decrypt(rs.getString("Member_email")));
				
				member.setMember_name(rs.getString("member_name"));
				member.setMember_mobile(aes.decrypt(rs.getString("member_mobile")));
				
				member.setMember_gender((rs.getInt("member_gender")));
				member.setMember_birthday((rs.getString("member_birthday")));
				
				member.setMember_idle((rs.getInt("member_idle")));
				member.setMember_registerday((rs.getString("member_registerday")));
				member.setMember_pwdchangeday((rs.getString("member_pwdchangeday")));
				member.setMember_updateday((rs.getString("member_updateday")));

				// 휴면 계정 확인
				// 마지막 로그인으로부터 6개월 이상 지난 경우 idle를 0로 변경
				if(rs.getInt("lastlogingap") >= 6) {
					member.setMember_idle(0);
					
					sql = " update tbl_member set member_idle = 0 where pk_member_no = ? ";
					
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setInt(1, member.getPk_member_no());
					
					// 회원 휴면 상태 갱신 실패 시 로그인 실패 처리
					if(pstmt.executeUpdate() != 1) {
						System.out.println("[ERROR] tbl_member update failed ");
						return null;
					}
					
				}
				// 휴면 상태가 아닌 경우 로그인 기록 추가
				else {
					sql = " insert into tbl_login(pk_login_no, fk_member_no, login_member_email, login_date, login_client_ip) "
						+ " values(pk_login_no_seq.nextval, ?, ?, sysdate, ? )";
					
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setInt(1, member.getPk_member_no()); // 회원 일련번호
					pstmt.setString(2, rs.getString("Member_email")); // 암호화된 이메일
					pstmt.setString(3, paraMap.get("clientIp")); // 접속 IP
					
					// 회원 로그인 기록 삽입 실패 시 로그인 실패 처리
					if(pstmt.executeUpdate() != 1) {
						System.out.println("[ERROR] tbl_login insert failed ");
						return null;
					}
				}

			} // end of if(rs.next())--------------------

		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return member;
	}// end of public MemberVO login(Map<String, String> paraMap) throws SQLException---------


	// 회원 탈퇴를 처리해주는 메소드
	@Override
	public int memberDelete(Map<String, String> paraMap) throws SQLException {

		int result = 0;
		
		conn = ds.getConnection();
		
		String sql = " update tbl_member set member_status = 0 "
				   + " where member_status = 1 and member_password = ? and pk_member_no = ? ";
		
		pstmt = conn.prepareStatement(sql);
		try {
			pstmt.setString(1, Sha256.encrypt(paraMap.get("member_password")));
		
			pstmt.setInt(2, Integer.parseInt(paraMap.get("pk_member_no")) );
			
			result = pstmt.executeUpdate();
		
		} finally {
			close();
		}
		
		return result;
		
	}

	
	// 이메일을 수정 해주는 메소드
	@Override
	public int updateEmail(Map<String , String> paraMap) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " update tbl_member set member_email = ? "
					   + " where pk_member_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
	        
	        pstmt.setString(1, aes.encrypt(paraMap.get("newEmail")) );  // 이메일을 AES256 알고리즘으로 양방향 암호화 시킨다. 
	        pstmt.setString(2, paraMap.get("memberNo") ); 
	        
	        result = pstmt.executeUpdate();
	        
		}catch(GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		      
		} finally {
			close();
		}
		
		return result; 
	}
	

	//email 수정시 자신이 변경하고자 하는 이메일이 tbl_member 테이블에서 사용중인지 아닌지 여부 알아오기 위한 메소드
	@Override
	public boolean emailDuplicateCheck(String newEmail) throws SQLException {

		boolean isExists = false;
		
		try {
			  conn = ds.getConnection();
			  
			  String sql = " select member_email "
			  		     + " from tbl_member "
			  		     + " where member_email = ? and member_status = 1 ";
			  
			  pstmt = conn.prepareStatement(sql);
			  pstmt.setString(1, aes.encrypt(newEmail));
			  
			  rs = pstmt.executeQuery();
			  
			  isExists = rs.next(); // 행이 있으면(중복된 email) true,
			                        // 행이 없으면(사용가능한 email) false
			  
		} catch(GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return isExists;
		
	}


	// 현재 비밀번호로 인증해주는 절차를 위해 사용자의 현재 비밀번호를 알아오는 메소드
	@Override
	public boolean currentPwdCheck(Map<String, String> paraMap) throws SQLException{
		
		boolean isExists = false;
		
		try {
			  conn = ds.getConnection();
			  
			  String sql = " select pk_member_no , member_password "
			  			 + " from tbl_member "
			  			 + " where pk_member_no = ? and member_password = ? ";
			  
			  pstmt = conn.prepareStatement(sql);
			  pstmt.setString(1, paraMap.get("memberNo"));
			  pstmt.setString(2, Sha256.encrypt(paraMap.get("currentPwd")));
			  
			  System.out.println(paraMap.get("memberNo") + paraMap.get("currentPwd") );
			  
			  rs = pstmt.executeQuery();
			  
			  isExists = rs.next(); // 행이 있으면(존재하는 사용자) true,
              						// 행이 없으면(존재하지 않는 사용자) false
			  
		} finally {
			close();
		}
		
		return isExists;
	}

	// 전화번호를 수정해주는 메소드
	@Override
	public int updateMobile(MemberDTO member) throws SQLException {
		
		int result = 0; 
		
		try {
			conn = ds.getConnection();
			
			String sql = " update tbl_member set member_mobile = ? "
					   + " where pk_member_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
	        
	        pstmt.setString(1, aes.encrypt(member.getMember_mobile()) );  // 전화번호를 AES256 알고리즘으로 양방향 암호화 시킨다. 
	        pstmt.setInt(2, member.getPk_member_no() ); 
	        
	        result = pstmt.executeUpdate();
	        
		}catch(GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		      
		} finally {
			close();
		}
		
		return result;
	}

	
	// 비밀번호 찾기(이메일을 입력 받아서 해당 사용자가 존재하는지 유무를 알려준다)
	@Override
	public boolean isUserExist(Map<String, String> paraMap) throws SQLException {
		
		boolean isUserExist = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select member_email "
					   + " from tbl_member "
					   + " where member_status = 1 and member_email = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, aes.encrypt(paraMap.get("email")));
			rs = pstmt.executeQuery();
			
			isUserExist = rs.next();
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		System.out.println(isUserExist);
		return isUserExist;
	}// end of public boolean isUserExist(Map<String, String> paraMap) throws SQLException-------------------------

	
	// 비밀번호 변경을 처리해주는 메소드
	@Override
	public int updatePwdEnd(Map<String , String> paraMap) throws SQLException {
		
		int result = 0; // 업데이트가 성공되어졌는지 알기위한 용도
		
		try {
			conn = ds.getConnection();
			
			String sql = " update tbl_member set member_password = ? "
					   + " where pk_member_no = ? ";
			
			pstmt = conn.prepareStatement(sql);// 우편배달부
	        
			pstmt.setString(1, Sha256.encrypt(paraMap.get("newPwd")));
	        pstmt.setString(2, paraMap.get("memberNo") ); 
	        
	        result = pstmt.executeUpdate();
	           
		} finally {
			close();
		}
//		System.out.println(result);
		
		return result;// 업데이트가 성공되어졌다면 result 값은 1이 나온다.
		
	}// end of public boolean updatePwdEnd(MemberDTO member) throws SQLException-------------

	
	// 관리자 회원 리스트 조회 시 전체 회원 수를 구하는 메소드
	@Override
	public int selectTotalRowCountByAdmin(Map<String, Object> paraMap) throws SQLException {
		int totalRowCount = 0; // 전체 행 개수를 저장하는 변수
		
		String searchType = (String)paraMap.get("searchType"); // 검색 타입 0: 회원명, 1: 이메일
		
		String searchWord = (String)paraMap.get("searchWord"); // 검색어
		
		String memberGender = (String)paraMap.get("memberGender"); // 회원 성별
		
		String memberIdle = (String)paraMap.get("memberIdle"); // 회원 휴면 상태 1 : 비휴면, 0 :휴면 (6개월 기준)
		
		String memberStatus = (String)paraMap.get("memberStatus"); // 회원 상태 1 : 활성,  0: 탈퇴,  2: 정지
		
		String dateMin = (String)paraMap.get("dateMin"); // 가입일자 최소값
		
		String dateMax = (String)paraMap.get("dateMax"); // 가입일자 최대값
		
		int count = 0; // 위치홀더 개수를 저장하는 변수
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " select count(*) as total "
						+ " from tbl_member "
						+ " where 1=1 ";
			
			if(!StringUtil.isBlank(searchType) && !StringUtil.isBlank(searchWord)) {
				switch(searchType) {
					case "0" : {
						sql += " and member_name like '%' || ? || '%' ";
						break;
					}
					case "1" : {
						sql += " and member_email = ? ";
						break;
					}
				}
			}
			
			if(!StringUtil.isBlank(memberGender)) {
				sql += " and member_gender = ? ";
			}
			
			if(!StringUtil.isBlank(memberIdle)) {
				sql += " and member_idle = ? ";
			}
			
			if(!StringUtil.isBlank(memberStatus)) {
				sql += " and member_status = ? ";
			}
			
			if(!StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				sql += " and member_registerday between to_date(?, 'yyyy-mm-dd') and to_date(?, 'yyyy-mm-dd hh24:mi:ss') ";
			}
			
			if(StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				sql += " and member_registerday <= to_date(?, 'yyyy-mm-dd hh24:mi:ss') ";
			}
			
			if(!StringUtil.isBlank(dateMin) && StringUtil.isBlank(dateMax)) {
				sql += " and member_registerday >= to_date(?, 'yyyy-mm-dd') ";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			if(!StringUtil.isBlank(searchType) && !StringUtil.isBlank(searchWord)) {
				if("1".equals(searchType)) {
					pstmt.setString(++count, aes.encrypt(searchWord));
				}
				else {
					pstmt.setString(++count, searchWord);	
				}
			}
			
			if(!StringUtil.isBlank(memberGender)) {
				pstmt.setString(++count, memberGender);
			}
			
			if(!StringUtil.isBlank(memberIdle)) {
				pstmt.setString(++count, memberIdle);
			}
			
			if(!StringUtil.isBlank(memberStatus)) {
				pstmt.setString(++count, memberStatus);
			}
			
			if(!StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMin);
				pstmt.setString(++count, dateMax + " 23:59:59");
			}
			
			if(StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMax + " 23:59:59");
			}
			
			if(!StringUtil.isBlank(dateMin) && StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMin);
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totalRowCount = rs.getInt("total");
			}
			
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return totalRowCount;
		
	} // selectTotalRowCountByAdmin(Map<String, Object> paraMap) throws SQLException

	
	// 관리자 회원 리스트 조회 메소드
	@Override
	public List<MemberDTO> selectMemberListByAdmin(Map<String, Object> paraMap) throws SQLException {
		List<MemberDTO> memberList = new ArrayList<>(); // MemberDTO 리스트
		
		PagingDTO pagingDTO = (PagingDTO)paraMap.get("pagingDTO");
		
		String searchType = (String)paraMap.get("searchType"); // 검색 타입 0: 회원명, 1: 이메일
		
		String searchWord = (String)paraMap.get("searchWord"); // 검색어
		
		// 정렬 타입 0: 회원명 오름차순, 1: 회원명 내림차순, 2:가입일 오름차순, 3: 가입일 내림차순
		String sortCategory = (String)paraMap.get("sortCategory") == null ? "" : (String)paraMap.get("sortCategory"); 
		
		String memberGender = (String)paraMap.get("memberGender"); // 회원 성별
		
		String memberIdle = (String)paraMap.get("memberIdle"); // 회원 휴면 상태 1 : 비휴면, 0 :휴면 (6개월 기준)
		
		String memberStatus = (String)paraMap.get("memberStatus"); // 회원 상태 1 : 활성,  0: 탈퇴,  2: 정지
		
		String dateMin = (String)paraMap.get("dateMin"); // 가입일자 최소값
		
		String dateMax = (String)paraMap.get("dateMax"); // 가입일자 최대값
		
		int count = 0; // 위치홀더 개수를 저장하는 변수
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT A.* "
						+ " FROM "
						+ " ( "
						+ " 	select ROWNUM as rnum, pk_member_no, member_email, member_name, member_mobile, member_gender, "
						+ " 	member_birthday, member_status, member_idle, member_registerday, member_pwdchangeday, member_updateday "
						+ " 	from tbl_member "
						+ " 	where 1=1 ";
	
			
			if(!StringUtil.isBlank(searchType) && !StringUtil.isBlank(searchWord)) {
				switch(searchType) {
					case "0" : {
						sql += " and member_name like '%' || ? || '%' ";
						break;
					}
					case "1" : {
						sql += " and member_email = ? ";
						break;
					}
				}
			}
			
			if(!StringUtil.isBlank(memberGender)) {
				sql += " and member_gender = ? ";
			}
			
			if(!StringUtil.isBlank(memberIdle)) {
				sql += " and member_idle = ? ";
			}
			
			if(!StringUtil.isBlank(memberStatus)) {
				sql += " and member_status = ? ";
			}
			
			if(!StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				sql += " and member_registerday between to_date(?, 'yyyy-mm-dd') and to_date(?, 'yyyy-mm-dd hh24:mi:ss') ";
			}
			
			if(StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				sql += " and member_registerday <= to_date(?, 'yyyy-mm-dd hh24:mi:ss') ";
			}
			
			if(!StringUtil.isBlank(dateMin) && StringUtil.isBlank(dateMax)) {
				sql += " and member_registerday >= to_date(?, 'yyyy-mm-dd') ";
			}
			
			switch(sortCategory) {
				case "0" : {
					sql += " order by member_name "; 
					break;	
				}
				case "1" : {
					sql += " order by member_name desc "; 
					break;
				}
				case "2" : {
					sql += " order by member_registerday ";
					break;
				}
				case "3" : {
					sql += " order by member_registerday desc ";
					break;
				}
				default : {
					sql += " order by member_registerday desc ";
					break;
				}
			}
			
			sql += " ) A "
					+ " WHERE rnum between ? and ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			if(!StringUtil.isBlank(searchType) && !StringUtil.isBlank(searchWord)) {
				if("1".equals(searchType)) {
					pstmt.setString(++count, aes.encrypt(searchWord));
				}
				else {
					pstmt.setString(++count, searchWord);	
				}
			}
			
			if(!StringUtil.isBlank(memberGender)) {
				pstmt.setString(++count, memberGender);
			}
			
			if(!StringUtil.isBlank(memberIdle)) {
				pstmt.setString(++count, memberIdle);
			}
			
			if(!StringUtil.isBlank(memberStatus)) {
				pstmt.setString(++count, memberStatus);
			}
			
			if(!StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMin);
				pstmt.setString(++count, dateMax + " 23:59:59");
			}
			
			if(StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMax + " 23:59:59");
			}
			
			if(!StringUtil.isBlank(dateMin) && StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMin);
			}
			
			pstmt.setInt(++count, pagingDTO.getFirstRow()); // 현재 페이지의 첫 레코드의 번호
			pstmt.setInt(++count, pagingDTO.getLastRow()); // 현재 페이지의 마지막 레코드의 번호
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberDTO memberDTO = new MemberDTO();
				
				memberDTO.setPk_member_no(rs.getInt("pk_member_no"));
				memberDTO.setMember_email(aes.decrypt(rs.getString("member_email")));
				memberDTO.setMember_name(rs.getString("member_name"));
				memberDTO.setMember_mobile(rs.getString("member_mobile"));
				memberDTO.setMember_gender(rs.getInt("member_gender"));
				memberDTO.setMember_birthday(rs.getString("member_birthday"));
				memberDTO.setMember_status(rs.getInt("member_status"));
				memberDTO.setMember_idle(rs.getInt("member_idle"));
				memberDTO.setMember_registerday(rs.getString("member_registerday"));
				memberDTO.setMember_pwdchangeday(rs.getString("member_pwdchangeday"));
				memberDTO.setMember_updateday(rs.getString("member_updateday"));
				
				memberList.add(memberDTO);
			}
			
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return memberList;
	}

	// 관리자 회원 상세조회 메소드
	@Override
	public MemberDTO selectMemberByAdmin(String memberNo) throws SQLException {
		MemberDTO memberDTO = new MemberDTO();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " select pk_member_no, member_email, member_name, member_mobile, member_gender, "
						+ " member_birthday, member_status, member_idle, member_registerday, member_pwdchangeday, member_updateday "
						+ " from tbl_member "
						+ " where pk_member_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				memberDTO.setPk_member_no(rs.getInt("pk_member_no"));
				memberDTO.setMember_email(aes.decrypt(rs.getString("member_email")));
				memberDTO.setMember_name(rs.getString("member_name"));
				memberDTO.setMember_mobile(aes.decrypt(rs.getString("member_mobile")));
				memberDTO.setMember_gender(rs.getInt("member_gender"));
				memberDTO.setMember_birthday(rs.getString("member_birthday"));
				memberDTO.setMember_status(rs.getInt("member_status"));
				memberDTO.setMember_idle(rs.getInt("member_idle"));
				memberDTO.setMember_registerday(rs.getString("member_registerday"));
				memberDTO.setMember_pwdchangeday(rs.getString("member_pwdchangeday"));
				memberDTO.setMember_updateday(rs.getString("member_updateday"));
			}
			else {
				memberDTO = null;
			}
			
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return memberDTO;
	}
	
	// 관리자 회원 로그인 기록 리스트 조회 메소드
	@Override
	public int selectLoginHistoryTotalRowCount(String memberNo) throws SQLException {
		int totalRowCount = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select count(*) as total from tbl_login where fk_member_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totalRowCount = rs.getInt("total");
			}
			
		} finally {
			close();
		}
		
		return totalRowCount;
	}

	// 관리자 회원 로그인 기록 리스트 조회 메소드
	@Override
	public List<Map<String, String>> selectLoginHistoryByAdmin(Map<String, Object> paraMap) throws SQLException {
		List<Map<String, String>> historyList = new ArrayList<>();
		
		PagingDTO pagingDTO = (PagingDTO)paraMap.get("pagingDTO"); // PagingDTO
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT *"
						+ " FROM "
						+ " ( "
						+ "		select ROWNUM AS RN, pk_login_no, fk_member_no, login_member_email, login_date, login_client_ip "
						+ " 	from tbl_login "
						+ " 	where fk_member_no = ? "
						+ " ) "
						+ " WHERE RN BETWEEN ? AND ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, (String)paraMap.get("memberNo"));
			pstmt.setInt(2, pagingDTO.getFirstRow());
			pstmt.setInt(3, pagingDTO.getLastRow());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, String> map = new HashMap<>();
				
				map.put("loginNo", rs.getString("pk_login_no"));
				map.put("memberNo", rs.getString("fk_member_no"));
				map.put("email", aes.decrypt(rs.getString("login_member_email")));
				map.put("loginDate", rs.getString("login_date"));
				map.put("ip", rs.getString("login_client_ip"));
				
				historyList.add(map);
			}
			
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return historyList;
	}
	
	// 휴면 상태를 해제해주는 메소드
	@Override
	public int UpdateMemberIdle(String memberNo, String clientip) throws SQLException {

	      String email = "";
	      
	      try {
		      conn = ds.getConnection();
		      
		      conn.setAutoCommit(false);
		      
		      String sql = " update tbl_member set member_idle = 1 "
		                 + " where pk_member_no = ? ";
		      
		      pstmt = conn.prepareStatement(sql);
	      
		      pstmt.setString(1, memberNo);
	         
		      if(pstmt.executeUpdate() != 1) {
		    	  System.out.println("[ERROR] tbl_member update failed");
		    	  conn.rollback();
		    	  return 0;
		      }
		      
		      sql = " select member_email "
		      	  + " from tbl_member "
		      	  + " where pk_member_no = ? ";
		      
		      pstmt = conn.prepareStatement(sql);
		      
		      pstmt.setString(1, memberNo);
		      
		      rs = pstmt.executeQuery();
		      
		      if(rs.next()) {
		    	  email = rs.getString("member_email");
		      }
		      else {
		    	  conn.rollback();
		    	  System.out.println("[ERROR] tbl_member select failed");
		    	  return 0;
		      }
		      
		      sql = " insert into tbl_login"
		      	  + " ( "
		      	  + " pk_login_no, "
		      	  + " fk_member_no, "
		      	  + " login_member_email, "
		      	  + " login_date, "
		      	  + " login_client_ip "
		      	  + " ) "
		      	  + " values(pk_login_no_seq.nextval, ?, ?, sysdate, ? ) " ;
		      
		      pstmt = conn.prepareStatement(sql);
		      
		      pstmt.setString(1, memberNo);
		      pstmt.setString(2, email);
		      pstmt.setString(3, clientip);
		      
		      pstmt.executeUpdate();
		      
		      if(pstmt.executeUpdate() != 1) {
		    	  conn.rollback();
		    	  System.out.println("[ERROR] tbl_login insert failed");
		    	  return 0;
		      }
		      
		      conn.commit();
		      
	      } finally {
	         close();
	      }
	      
	      return 1;
	}

	// 회원가입시 전화번호 중복검사 (tbl_member 테이블에서 mobile 이 존재하면 true 를 리턴해주고, mobile 이 존재하지 않으면 false 를 리턴한다)
	@Override
	public boolean mobileDuplicateCheck(String mobile) throws SQLException {
		
		boolean isExists = false;
		
		try {
			  conn = ds.getConnection();
			  
			  String sql = " select pk_member_no "
	         			 + " from tbl_member "
	         			 + " where member_mobile = ? and member_status = 1 ";
			  
			  pstmt = conn.prepareStatement(sql);
			  pstmt.setString(1, aes.encrypt(mobile));
			  
			  rs = pstmt.executeQuery();
			  
			  isExists = rs.next(); // 행이 있으면(중복된 mobile) true,
			                        // 행이 없으면(사용가능한 mobile) false
			  
		} catch(GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return isExists;
		
	}// end of public boolean mobileDuplicateCheck(String mobile) throws SQLException-----------------	

}
