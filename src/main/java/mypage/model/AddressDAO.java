package mypage.model;

import java.sql.SQLException;

import org.json.JSONArray;

import mypage.domain.AddressDTO;

public interface AddressDAO {

	JSONArray selectAddrs(int pk_member_no) throws SQLException;

	int insertAddress(AddressDTO addrDto) throws SQLException;
	
}
