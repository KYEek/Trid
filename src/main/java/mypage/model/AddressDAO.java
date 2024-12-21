package mypage.model;

import java.sql.SQLException;

import org.json.JSONObject;

public interface AddressDAO {

	JSONObject selectAddrs(int pk_member_no) throws SQLException;
	
}
