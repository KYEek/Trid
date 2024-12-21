package admin.model;

import java.sql.SQLException;

import admin.domain.AdminDTO;

public interface AdminDAO {

	//  관리자 인증을 수행하는 메소드
	AdminDTO selectAdmin(String adminId, String encryptPw) throws SQLException;
	
	// 세션의 adminId가 유효한 값인지 확인하는 메소드
	int selectCountAdmin(String adminId) throws SQLException;

}
