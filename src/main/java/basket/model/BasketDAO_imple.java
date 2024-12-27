package basket.model;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;

import basket.domain.BasketDTO;
import common.Constants;
import util.security.AES256;

public class BasketDAO_imple implements BasketDAO {

	
	
	private DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;
	
	String sql ="";
	
	// 생성자
	public BasketDAO_imple() {
		
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
			if(rs    != null) {rs.close();	  rs=null;}
			if(pstmt != null) {pstmt.close(); pstmt=null;}
			if(conn  != null) {conn.close();  conn=null;}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}// end of private void close()---------------
	
	
	
	
	@Override
	public JSONArray selectBasketList(int pk_member_no) throws SQLException {
		
		JSONArray basketArray = new JSONArray();
		conn = ds.getConnection();
		
		try {
			
			sql = " select PK_BASKET_NO, FK_MEMBER_NO, FK_PRODUCT_DETAIL_NO, BASKET_QUANTITY, PRODUCT_NO, PRODUCT_SIZE, PRODUCT_INVENTORY, PRODUCT_NAME, PRODUCT_PRICE, COLOR_NAME, PRODUCT_IMAGE_PATH, PRODUCT_IMAGE_NAME "
					+ " from tbl_basket "
					+ " join select_basket_product_info "
					+ " on FK_PRODUCT_DETAIL_NO = PRODUCT_DETAIL_NO "
					+ " where fk_member_no = ? "
					+ " order by pk_basket_no desc ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pk_member_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("PK_BASKET_NO", rs.getInt("PK_BASKET_NO"));
				json.put("FK_MEMBER_NO", rs.getInt("FK_MEMBER_NO"));
				json.put("FK_PRODUCT_DETAIL_NO", rs.getInt("FK_PRODUCT_DETAIL_NO"));
				json.put("BASKET_QUANTITY", rs.getInt("BASKET_QUANTITY"));
				json.put("PRODUCT_NO", rs.getString("PRODUCT_NO"));
				json.put("PRODUCT_SIZE", rs.getString("PRODUCT_SIZE"));
				json.put("PRODUCT_INVENTORY", rs.getInt("PRODUCT_INVENTORY"));
				json.put("PRODUCT_NAME", rs.getString("PRODUCT_NAME"));
				json.put("PRODUCT_PRICE", rs.getInt("PRODUCT_PRICE"));
				json.put("COLOR_NAME", rs.getString("COLOR_NAME"));
				json.put("PRODUCT_IMAGE_PATH", rs.getString("PRODUCT_IMAGE_PATH"));
				json.put("PRODUCT_IMAGE_NAME", rs.getString("PRODUCT_IMAGE_NAME"));
				
				basketArray.put(json);
			}
			
		}
		finally {
			close();
		}
		
		
		return basketArray;
	}//end of selectList------------------------------------------------------------


	@Override
	public String incrementBasketQuantity(int basketNo, int memberNum) throws SQLException {
		conn = ds.getConnection();
		String result = "fail";
		
		
		try {

			sql = " update tbl_basket set BASKET_QUANTITY = (BASKET_QUANTITY + 1) where pk_basket_no = ? and fk_member_no = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, basketNo);
			pstmt.setInt(2, memberNum);
			int resultNum = pstmt.executeUpdate();
			if(resultNum==1) {
				result = "success";
			}

		} finally {
			close();
		}

		return result;
	}//end of increment-------------------------------------------------


	@Override
	public String decrementBasketQuantity(int basketNo, int memberNum) throws SQLException {
		
		conn = ds.getConnection();
		
		String result = "fail";
		
		
		try {

			sql = " update tbl_basket set BASKET_QUANTITY = (BASKET_QUANTITY - 1) where pk_basket_no = ? and fk_member_no = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, basketNo);
			pstmt.setInt(2, memberNum);
			int resultNum = pstmt.executeUpdate();
			if(resultNum==1) {
				result = "success";
			}

		} finally {
			close();
		}

		return result;
	}//end of decrement----------------------------------------------------------------------


	@Override
	public String delectBasketProduct(int basketNo, int memberNum) throws SQLException {
		
		conn = ds.getConnection();
		
		String result = "fail";
		
		
		try {

			sql = "delete from tbl_basket where pk_basket_no = ? and fk_member_no = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, basketNo);
			pstmt.setInt(2, memberNum);
			int resultNum = pstmt.executeUpdate();
			if(resultNum==1) {
				result = "success";
			}

		} finally {
			close();
		}

		return result;
	}


	@Override
	public int insertBasket(int productDetailNum, int pk_member_no) throws SQLException {

		conn = ds.getConnection();

		int result = 0;

		try {
			
			System.out.println(productDetailNum);
			
			System.out.println(pk_member_no);
			
			sql = " insert into tbl_basket (pk_basket_no, fk_member_no, fk_product_detail_no, basket_quantity) values (PK_BASKET_NO_SEQ.nextval, ?, ?, 1) ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pk_member_no);
			pstmt.setInt(2, productDetailNum);
			result = pstmt.executeUpdate();

		} finally {
			close();
		}

		return result;
	}

}
