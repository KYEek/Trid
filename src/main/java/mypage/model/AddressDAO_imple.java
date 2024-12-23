package mypage.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import common.Constants;
import mypage.domain.AddressDTO;
import util.security.AES256;

public class AddressDAO_imple implements AddressDAO {

	private DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;
	
	String sql ="";
	
	// 생성자
	public AddressDAO_imple() {
		
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
	
	
	
	//주소를 모두 조회하는 메서드 입니다.
	@Override
	public JSONArray selectAddrs(int pk_member_no) throws SQLException {
		
		//리턴할 json객체를 생성합니다
		JSONArray parentJson = new JSONArray();
		JSONObject json = null;
		conn = ds.getConnection();
		
		
		sql = "select member_name, addr_address, addr_detail, addr_extraaddr, addr_post_no, member_mobile, addr_isdefault,pk_addr_no "
				+ " from tbl_addr a "
				+ " join tbl_member m "
				+ " on a.fk_member_no = m.pk_member_no "
				+ " where fk_member_no = ? ";
		
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, pk_member_no);
		try {
			
			rs = pstmt.executeQuery();
			//json 객체를 생성하고 json에 값을 넣습니다.
			while (rs.next()) {
				json = new JSONObject();
				json.put("member_name", rs.getString(1));
				json.put("addr_address", rs.getString(2));
				json.put("addr_detail", rs.getString(3));
				json.put("addr_extraaddr", rs.getString(4));
				json.put("addr_post_no", rs.getInt(5));
				json.put("member_mobile", aes.decrypt(rs.getString(6)));
				json.put("addr_isdefault", rs.getInt(7));
				json.put("pk_addr_no", rs.getInt(8));
				parentJson.put(json);
			}

		} catch (JSONException | UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		if(json == null) {
			return null;
		}
		else {
			return parentJson;
		}
	}

	
	
	//주소 입력하는 sql문 입니다.
	@Override
	public int insertAddress(AddressDTO addrDto) throws SQLException {
		
		int result = 0;
		conn = ds.getConnection();
		
		sql = " insert into tbl_addr(PK_ADDR_NO, FK_MEMBER_NO, ADDR_POST_NO, ADDR_ADDRESS, ADDR_DETAIL, ADDR_EXTRAADDR, addr_isdefault) "
				+ "values (PK_ADDR_NO_SEQ.nextval, ?, ?,?, ?, ?, 0) ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, addrDto.getMember_no());
		pstmt.setInt(2, addrDto.getPost_no());
		pstmt.setString(3, addrDto.getAddress());
		pstmt.setString(4, addrDto.getDetail());
		pstmt.setString(5, addrDto.getExtraaddr());
		
		result = pstmt.executeUpdate();

		close();
		
		return result;
	}

	//입력받은 주소 번호를 기본주소로 설정합니다.
	@Override
	public int setDefault(int addrNo, int pk_member_no) throws SQLException {
		
		conn = ds.getConnection();
		
		int result = 0;
		
		//로그인 회원의 모든 주소의 기본주소 값을 0으로 초기화 합니다.
		sql = " update TBL_ADDR set ADDR_ISDEFAULT = 0 where FK_MEMBER_NO = ? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, pk_member_no);
		
		pstmt.executeUpdate();
		
		//설정한 주소를 기본주소로 만듭니다.
		sql = " update TBL_ADDR set ADDR_ISDEFAULT = 1 where PK_ADDR_NO = ? and FK_MEMBER_NO = ? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, addrNo);
		pstmt.setInt(2, pk_member_no);
		
		result = pstmt.executeUpdate();
		
		return result;
	}

	//주소를 삭제합니다
	@Override
	public int deleteAddr(int addrNo, int pk_member_no) throws SQLException {
		
		conn = ds.getConnection();
		
		int result = 0;
		
		sql = " delete from tbl_addr where PK_ADDR_NO = ? and FK_MEMBER_NO = ? ";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, addrNo);
		pstmt.setInt(2, pk_member_no);
		
		result = pstmt.executeUpdate();
		
		
		return result;
	}


	@Override
	public JSONObject selectOneAddr(int addrNo, int pk_member_no) throws SQLException {
		
		conn = ds.getConnection();
		JSONObject json = null;
		
		sql = " select member_name, addr_address, addr_detail, addr_extraaddr, addr_post_no, member_mobile, addr_isdefault,pk_addr_no "
				+ " from tbl_addr a "
				+ " join tbl_member m "
				+ " on a.fk_member_no = m.pk_member_no "
				+ " where fk_member_no = ? and pk_addr_no = ? ";
		
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, pk_member_no);
		pstmt.setInt(2, addrNo);
		
		rs = pstmt.executeQuery();
		try {
		if(rs.next()) {
			json = new JSONObject();
			json.put("member_name", rs.getString(1));
			json.put("addr_address", rs.getString(2));
			json.put("addr_detail", rs.getString(3));
			json.put("addr_extraaddr", rs.getString(4));
			json.put("addr_post_no", rs.getInt(5));
			json.put("member_mobile", aes.decrypt(rs.getString(6)));
			json.put("addr_isdefault", rs.getInt(7));
			json.put("pk_addr_no", rs.getInt(8));
		}
		
		} catch (UnsupportedEncodingException | GeneralSecurityException | SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return json;
	}


	@Override
	public int updateAddress(AddressDTO addrDto) throws SQLException {
		
		int result = 0;
		
		conn = ds.getConnection();
		
		
		sql = " update tbl_addr set ADDR_POST_NO = ?, ADDR_ADDRESS = ?, ADDR_DETAIL  = ?, ADDR_EXTRAADDR  = ?"
				+ "where PK_ADDR_NO = ? and FK_MEMBER_NO = ? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, addrDto.getPost_no());
		pstmt.setString(2, addrDto.getAddress());
		pstmt.setString(3, addrDto.getDetail());
		pstmt.setString(4, addrDto.getExtraaddr());
		pstmt.setInt(5, addrDto.getAddr_no());
		pstmt.setInt(6, addrDto.getMember_no());
		
		result = pstmt.executeUpdate();
		
		return result;
	}

}
