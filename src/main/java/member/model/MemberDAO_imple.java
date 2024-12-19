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

import common.Constants;
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

			aes = new AES256(Constants.KEY);
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

	// 로그인 처리
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

}
