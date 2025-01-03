package payment.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import common.Constants;
import util.security.AES256;

public class Payment_DAO_imple implements Payment_DAO {

	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private AES256 aes;

	String sql = "";

	// 생성자
	public Payment_DAO_imple() {
		
		try {
			Context initContext = new InitialContext();
		    Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    ds = (DataSource)envContext.lookup("jdbc/semioracle");
		    
		    aes = new AES256(Constants.KEY);
		    // SecretMyKey.KEY 은 우리가 만든 암호화/복호화 키이다.
		    
		} catch(NamingException e) {
			e.printStackTrace();
		} catch(UnsupportedEncodingException e) {
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

	@Override
	public int insertOrderDate(Map<String, String> orderData, Map<String, String> orderDetailData) throws SQLException {

		int result_order_no = 0;
		CallableStatement cstmt = null;
		conn = ds.getConnection();

		sql = " {call insertOrderNo(?, ?, ?, ?, ?)} ";
		try {
		cstmt = conn.prepareCall(sql);
		cstmt.setString(1, orderDetailData.get("orderDetailArr"));
		cstmt.setInt(2, Integer.parseInt(orderData.get("selected_address_no")));
		cstmt.setInt(3, Integer.parseInt(orderData.get("total_price")));
		cstmt.setInt(4, Integer.parseInt(orderData.get("member_No")));
		cstmt.registerOutParameter(5, Types.NUMERIC);
		
		cstmt.execute();
		
		result_order_no = cstmt.getInt(5);
		

		} finally {
			close();
		}

		return result_order_no;
	}

}
