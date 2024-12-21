package admin.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import admin.domain.AdminDTO;

public class AdminDAO_imple implements AdminDAO {

	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	
	private Connection conn; // Database 연결 객체
	
	private PreparedStatement pstmt; // SQL문 실행 객체, 미리 컴파일을 통해 SQL Injection을 방지
	
	private ResultSet rs; // SQL 쿼리 실행 결과를 담는 객체

	public AdminDAO_imple() {
		// 데이터베이스 연결 설정 값을 불러온 후 Connection Pool 연결 
		try {
			Context initContext = new InitialContext();
			
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			
			ds = (DataSource) envContext.lookup("jdbc/semioracle");
		} catch (NamingException e) {
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
	}

	/*
	 * 관리자 로그인 메소드
	 */
	@Override
	public AdminDTO selectAdmin(String adminId, String encryptPw) throws SQLException {
		AdminDTO adminDTO = new AdminDTO();
		
		try {
			conn = ds.getConnection();

			String sql 	= " select pk_admin_id, admin_password, admin_name "
						+ " from tbl_admin "
						+ " where pk_admin_id = ? and admin_password = ? ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, adminId);
			pstmt.setString(2, encryptPw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				adminDTO.setAdminId(rs.getString("pk_admin_id")); // 관리자 아이디
				adminDTO.setPassword(rs.getString("admin_password")); // 암호화된 관리자 비밀번호
				adminDTO.setAdminName(rs.getString("admin_name")); // 관리자 명
			}
			else {
				adminDTO = null;
			}

		} finally {
			close();
		}
		
		return adminDTO;
	}

	/*
	 * 세션의 adminId가 유효한 값인지 확인하는 메소드
	 * adminId를 통해 관리자를 조회하여 계정이 존재하면 1이 반환된다.
	 */
	@Override
	public int selectCountAdmin(String adminId) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select count(*) as count from tbl_admin where pk_admin_id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, adminId);
			
			rs = pstmt.executeQuery();	
			
			if(rs.next()) {
				result = rs.getInt("count");
			}
			
		} finally {
			close();
		}
		
		return result;
	}
	
}
