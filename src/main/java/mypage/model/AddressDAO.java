package mypage.model;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import mypage.domain.AddressDTO;

public interface AddressDAO {
	//주소 목록을 조회하는 SQL문
	JSONArray selectAddrs(int pk_member_no) throws SQLException;

	//주소를 입력하는 SQL문
	int insertAddress(AddressDTO addrDto) throws SQLException;

	//기본 주소를 설정하는 sql문
	boolean setDefault(int addrNo, int pk_member_no) throws SQLException;

	// 주소를 삭제하기
	int deleteAddr(int addrNo, int pk_member_no) throws SQLException;
	
	//주소 하나를 조회하는 메서드
	JSONObject selectOneAddr(int addrNo, int pk_member_no) throws SQLException;

	//주소를 업데이트 하는 메서드 입니다.
	int updateAddress(AddressDTO addrDto) throws SQLException;
	
}
