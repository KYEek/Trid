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
	
	
	
	/*
	
	프로시저 내용
	create or replace procedure insertOrderNo (json_data in clob, selected_address_no in number, total_price in number, member_no in number, sequenceNum out number)
	is
	    json_str clob := json_data;
	    --반복문을 실행할 숫자
	    countNum number := 1;
	    --json배열의 각 객체
	    data_str varchar2(300);
	    --""가 사라진 순수한 값
	    extracted_str number;
	    --추출한 값을 담을 변수들
	    product_quantity number;
	    product_detail_num number;
	    product_price number;
	    --재고를 담을 변수
	    product_inventory number;
	    --예외를 실행시킬 변수
	    empty_inventory exception;
	begin
	    --json에서 [] 삭제
	    json_str := substr(json_str, 2, (length(json_str)-2));
	    --주문테이블 시퀀스 번호 생성
	    sequenceNum := PK_ORDER_NO_SEQ.nextval;
	    --주문내역 생성
	    insert into tbl_order (PK_ORDER_NO, FK_MEMBER_NO, FK_ADDR_NO, ORDER_STATUS, ORDER_TOTAL_PRICE) values(sequenceNum, member_no, selected_address_no, 0 , total_price);
	    loop
	        --각각의 객체별로 나누기
	        data_str := REGEXP_SUBSTR(json_str, '\{.*?\}',1,countNum);
	        --값이 없으면 반복문 취소
	        exit when data_str is null;
	        --각 객체의 값을 추출
	        for i in 1..3 loop 
	            extracted_str := to_number(REGEXP_REPLACE(REGEXP_SUBSTR(data_str, '".*?"',1,(i*2)),'"',''));
	            if i = 1 then
	                product_quantity := extracted_str;
	            elsif i = 2 then
	                product_detail_num := extracted_str;
	            elsif i = 3 then
	                product_price := extracted_str;
	            end if;
	            
	            --재고를 불러온다
	            select product_inventory into product_inventory
	            from tbl_product_detail
	            where pk_product_detail_no = 270;
	            
	            --재고가 없으면 예외 실행
	            if product_inventory <= 0 then
	                raise empty_inventory;
	            end if;
	            
	        end loop;
	        --주문 상세 테이블에 삽입
	        insert into TBL_ORDER_DETAIL(pk_order_detail_no, fk_order_no, fk_product_detail_no, order_detail_price, order_detail_quantity) values (PK_ORDER_DETAIL_NO_SEQ.nextval, sequenceNum, product_detail_num, product_price, product_quantity);
	        delete from tbl_basket where FK_MEMBER_NO = member_no and FK_PRODUCT_DETAIL_NO = product_detail_num;
	        --다음 객체를 위한 번호 증가
	        countNum := countNum + 1;
	    end loop;
	exception
	    --재고가 비었을 때 반환값을 -1로 설정 후 롤백
	    when empty_inventory then
	        sequenceNum := -1;
	        rollback;
	    
	end;
	  
	  
	 */
	
	
	
	//주문을 입력하는 메서드
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

	
	
	//바로 결제에서 상품 정보를 가져올 함수
	@Override
	public JSONObject selectProductInfo(int productDetailNo) throws SQLException {
		//장바구니의 목록을 조회하는 메서드
		
		JSONObject json = null;
		conn = ds.getConnection();

		try {

			sql = " select PRODUCT_DETAIL_NO, PRODUCT_NO, PRODUCT_SIZE, PRODUCT_INVENTORY, PRODUCT_NAME, PRODUCT_PRICE, COLOR_NAME, PRODUCT_IMAGE_PATH, PRODUCT_IMAGE_NAME "
				+ " from "
				+ " (select row_number() over(partition by PRODUCT_DETAIL_NO order by product_image_name) as rowNumber, PRODUCT_DETAIL_NO, PRODUCT_NO, PRODUCT_SIZE, PRODUCT_INVENTORY, PRODUCT_NAME, PRODUCT_PRICE, COLOR_NAME, PRODUCT_IMAGE_PATH, PRODUCT_IMAGE_NAME "
				+ " from select_basket_product_info "
				+ " order by product_no) "
				+ " where rownumber = 1 and product_detail_no = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productDetailNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				json = new JSONObject();
				json.put("PRODUCT_DETAIL_NO", rs.getInt("PRODUCT_DETAIL_NO"));
				json.put("PRODUCT_NO", rs.getInt("PRODUCT_NO"));
				json.put("PRODUCT_SIZE", rs.getString("PRODUCT_SIZE"));
				json.put("PRODUCT_INVENTORY", rs.getInt("PRODUCT_INVENTORY"));
				json.put("PRODUCT_NAME", rs.getString("PRODUCT_NAME"));
				json.put("PRODUCT_PRICE", rs.getInt("PRODUCT_PRICE"));
				json.put("COLOR_NAME", rs.getString("COLOR_NAME"));
				json.put("PRODUCT_IMAGE_PATH", rs.getString("PRODUCT_IMAGE_PATH"));
				json.put("PRODUCT_IMAGE_NAME", rs.getString("PRODUCT_IMAGE_NAME"));

			}

		} finally {
			close();
		}

		return json;
	}

}
