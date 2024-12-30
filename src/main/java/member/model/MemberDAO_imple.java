package member.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import member.domain.MemberDTO;
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
			// SecretMyKey.KEY 은 우리가 만든 암호화/복호화 키이다.

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
						+ " values(pk_member_no_seq.nextval , ?, ?, ?, ?, ?, sysdate, sysdate , sysdate, sysdate) ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, aes.encrypt(member.getMember_email()));
			pstmt.setString(2, Sha256.encrypt(member.getMember_password())); // 암호를 SHA256 알고리즘으로 단방향 암호화 시킨다.
			pstmt.setString(3, member.getMember_name());
			pstmt.setString(4, aes.encrypt(member.getMember_mobile())); // 휴대폰을 AES256 알고리즘으로 양방향 암호화 시킨다.
			pstmt.setInt(5, 1);
			//pstmt.setString(6, "sysdate");

			result = pstmt.executeUpdate();

		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}

//		System.out.println(result);
		return result;
	}// end of public int registerMember(MemberDTO member) throws SQLException
		// -----------------

	// 로그인 처리를 해주는 메소드
	@Override
	public MemberDTO login(Map<String, String> paraMap) throws SQLException {
		
		MemberDTO member = null;

		try {
			conn = ds.getConnection();

			String sql 	= " select pk_member_no, member_email, member_name, member_mobile, member_gender, member_birthday, member_idle, to_char(member_registerday, 'yyyy-mm-dd') as member_registerday, "
						+ " member_pwdchangeday, to_char(member_updateday, 'yyyy-mm-dd') as member_updateday " 
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

				/*
				 * if( rs.getInt("lastlogingap") >= 12 ) { // 마지막으로 로그인 한 날짜시간이 현재시각으로 부터 1년이
				 * 지났으면 휴면으로 지정 member.setIdle(1);
				 * 
				 * if(rs.getInt("idle") == 0) { // === tbl_member 테이블의 idle 컬럼의 값을 1로 변경하기 ===
				 * // sql = " update tbl_member set idle = 1 " + " where userid = ? ";
				 * 
				 * pstmt = conn.prepareStatement(sql); pstmt.setString(1,
				 * paraMap.get("userid"));
				 * 
				 * pstmt.executeUpdate(); }
				 * 
				 * }// end of if( rs.getInt("lastlogingap") >= 12 )------
				 */

				// === 휴면이 아닌 회원만 tbl_loginhistory(로그인기록) 테이블에 insert 하기 시작 === //
				/*
				 * if( rs.getInt("lastlogingap") < 12 ) { sql =
				 * " insert into tbl_loginhistory(historyno, fk_userid, clientip) " +
				 * " values(seq_historyno.nextval, ?, ?) ";
				 * 
				 * pstmt = conn.prepareStatement(sql); pstmt.setString(1,
				 * paraMap.get("userid")); pstmt.setString(2, paraMap.get("clientip"));
				 * 
				 * pstmt.executeUpdate(); // === 휴면이 아닌 회원만 tbl_loginhistory(로그인기록) 테이블에 insert
				 * 하기 끝 === //
				 * 
				 * if( rs.getInt("pwdchangegap") >= 3 ) { // 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이
				 * 지났으면 true // 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지나지 않았으면 false
				 * 
				 * member.setRequirePwdChange(true); // 로그인시 암호를 변경해라는 alert 를 띄우도록 할때 사용한다. }
				 * 
				 * }
				 */

			} // end of if(rs.next())--------------------

		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return member;
	}// end of public MemberVO login(Map<String, String> paraMap) throws
		// SQLException---------

	
	
	

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
	public int updateEmail(MemberDTO member) throws SQLException {
		
		int result = 0; // 업데이트가 성공되어졌는지 알기위한 용도
		
		try {
			conn = ds.getConnection();
			
			String sql = " update tbl_member set member_email = ? "
					   + " where pk_member_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
	        
	        pstmt.setString(1, aes.encrypt(member.getMember_email()) );  // 이메일을 AES256 알고리즘으로 양방향 암호화 시킨다. 
	        pstmt.setInt(2, member.getPk_member_no() ); 
	        
	        result = pstmt.executeUpdate();
	        
		}catch(GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		      
		} finally {
			close();
		}
		
		return result;// 업데이트가 성공되어졌다면 result 값은 1이 나온다.
	}

	// email 중복검사(현재 해당 사용자가 사용중인 email 이라면 true, 새로운 비밀번호이라면 false)
	@Override
	public boolean emailDuplicateCheck2(Map<String, String> paraMap) throws SQLException{

		boolean isExists = false;
	      
	      try {// 이메일은 다른 사용자와 중복되면 안된다.(현재 사용자가 해당 이메일을 사용하고 있는지와 다른 사용자가 해당 이메일을 사용하고 있는지 모두 확인해야한다.)
	         conn = ds.getConnection();
	         
	         String sql = " select pk_member_no "
	         			+ " from tbl_member "
	         			+ " where pk_member_no = ? and member_email = ? ";
	         
	         pstmt = conn.prepareStatement(sql); 
	         pstmt.setString(1, paraMap.get("pkNum"));
	         pstmt.setString(2, aes.encrypt(paraMap.get("newEmail")));
	         
	         rs = pstmt.executeQuery();
	         
	         isExists = rs.next(); // 행이 있으면(사용자가 현재 사용중인 email) true,
	                               // 행이 없으면(사용자가 사용하고 있지 않은 email) false
	         
	      } catch(GeneralSecurityException | UnsupportedEncodingException e) {
				e.printStackTrace();
		  } finally {
	         close();
	      }
	      
	      return isExists;
	}

	// email 중복검사 (tbl_member 테이블에서 email 이 존재하면 true 를 리턴해주고, email 이 존재하지 않으면 false 를 리턴한다)
	@Override
	public boolean emailDuplicateCheck(String newEmail) throws SQLException {

		boolean isExists = false;
		
		try {
			  conn = ds.getConnection();
			  
			  String sql = " select member_email "
			  		     + " from tbl_member "
			  		     + " where member_email = ? ";
			  
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

	// 현재 비밀번호를 알아와서 웹에 들어온 비밀번호 값과 같은지 다른지 비교해주는 페이지
	@Override
	public boolean currentPwdCheck(MemberDTO member) throws SQLException{
		
		boolean isExists = false;
		
		try {
			  conn = ds.getConnection();
			  
			  String sql = " select pk_member_no , member_password "
			  			 + " from tbl_member "
			  			 + " where pk_member_no = ? and member_password = ? ";
			  
			  pstmt = conn.prepareStatement(sql);
			  pstmt.setInt(1, member.getPk_member_no());
			  pstmt.setString(2, Sha256.encrypt(member.getMember_password()));
			  
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
		
		int result = 0; // 업데이트가 성공되어졌는지 알기위한 용도
		
		try {
			conn = ds.getConnection();
			
			String sql = " update tbl_member set member_mobile = ? "
					   + " where pk_member_no = ? ";
			
			pstmt = conn.prepareStatement(sql);// 우편배달부
	        
	        pstmt.setString(1, aes.encrypt(member.getMember_mobile()) );  // 전화번호를 AES256 알고리즘으로 양방향 암호화 시킨다. 
	        pstmt.setInt(2, member.getPk_member_no() ); 
	        
	        result = pstmt.executeUpdate();
	        
		}catch(GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		      
		} finally {
			close();
		}
	//	System.out.println(result);
		
		return result;// 업데이트가 성공되어졌다면 result 값은 1이 나온다.
	}

	// 새로운 전화번호가 tbl_member 에서 사용중인 전화번호인지 체크해주는 메소드
	@Override
	public boolean MobileDuplicateCheck(Map<String, String> paraMap) throws SQLException {
		
		 boolean isExists = false;
		    
		    try {
		        conn = ds.getConnection();
		        
		        // 두 컬럼 모두 조회
		        String sql = " SELECT pk_member_no, member_mobile "
		                   + " FROM tbl_member "
		                   + " WHERE member_mobile = ? ";  
		        
		        pstmt = conn.prepareStatement(sql);
		        pstmt.setString(1, aes.encrypt(paraMap.get("newMobile")));
		        
		        
		        rs = pstmt.executeQuery();
		        
		        // 결과가 있고, 그 결과의 pk_member_no가 현재 사용자와 다르다면(false) 
		        // 다른 사용자가 그 번호를 사용하고 있다는 의미.
		        if(rs.next()) {
		            String isPkNum = rs.getString("pk_member_no");
		            isExists = !isPkNum.equals(paraMap.get("pkNum"));
		        }
		        else {// 다른 사용자가 사용하고 있지 않다면(사용가능한 전화번호라면 true)
		        	isExists = true;
		        }
		        
		    } catch(GeneralSecurityException | UnsupportedEncodingException e) {
		        e.printStackTrace();
		    } finally {
		        close();
		    }
		    
		    return isExists;
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
	public int updatePwdEnd(MemberDTO member) throws SQLException {
		
		int result = 0; // 업데이트가 성공되어졌는지 알기위한 용도
		
		try {
			conn = ds.getConnection();
			
			String sql = " update tbl_member set member_password = ? "
					   + " where pk_member_no = ? ";
			
			pstmt = conn.prepareStatement(sql);// 우편배달부
	        
			pstmt.setString(1, Sha256.encrypt(member.getMember_password()));
	        pstmt.setInt(2, member.getPk_member_no() ); 
	        
	        result = pstmt.executeUpdate();
	           
		} finally {
			close();
		}
//		System.out.println(result);
		
		return result;// 업데이트가 성공되어졌다면 result 값은 1이 나온다.
		
	}// end of public boolean updatePwdEnd(MemberDTO member) throws SQLException-------------

}
