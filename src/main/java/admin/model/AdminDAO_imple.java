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
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public AdminDAO_imple() {
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
	 * 관리자 인증을 수행하는 메소드
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
				adminDTO.setAdminId(rs.getString("pk_admin_id"));
				adminDTO.setPassword(rs.getString("admin_password"));
				adminDTO.setAdminName(rs.getString("admin_name"));
			}
			else {
				adminDTO = null;
			}

		} finally {
			close();
		}
		
		return adminDTO;
	}
	
}
