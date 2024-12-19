package product.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import common.Constants;
import common.domain.PagingDTO;
import product.domain.ColorDTO;
import product.domain.ProductDTO;

public class ProductDAO_imple implements ProductDAO{
	
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public ProductDAO_imple() {
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

	@Override
	public Map<String, Object>  selectProductList(int currentPage) throws SQLException {
		List<ProductDTO> productList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		
		try {
			conn = ds.getConnection();

			String sql 	= " WITH T AS"
						+ " ( "
							+ " SELECT ROWNUM AS RN, A.* " 
							+ " FROM ( "
								+ " select count(*) over() total, "
								+ " p.pk_product_no , p.product_name, p.product_price, p.product_explanation, p.product_registerday, "
								+ " p.product_updateday, p.product_status_code, "
								+ " d.pk_product_detail_no, d.product_detail_size, d.product_inventory, "
								+ " c.pk_category_no, c.category_name "
								+ " from tbl_product p join tbl_product_detail d on p.pk_product_no = d.fk_product_no "
								+ " join tbl_category c on c.pk_category_no = p.fk_category_no "
								+ " order by p.product_registerday desc "
								+ " ) A "
						+ " ) "
						+ " SELECT * FROM T "
						+ " WHERE T.RN >= ? and T.RN < ? ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, 0);
			pstmt.setInt(2, 10);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ProductDTO productDTO = new ProductDTO();
				
				productDTO.setProductNo(rs.getInt("pk_product_no"));
				productDTO.setProductDetailNo(rs.getInt("pk_product_detail_no"));
				productDTO.setCategoryNo(rs.getInt("pk_category_no"));
				productDTO.setCategoryName(rs.getString("category_name"));
				productDTO.setProductName(rs.getString("product_name"));
				productDTO.setSize(rs.getInt("product_detail_size"));
				productDTO.setExplanation(rs.getString("product_explanation"));
				productDTO.setPrice(rs.getInt("product_price"));
				productDTO.setInventory(rs.getInt("product_inventory"));
				productDTO.setRegisterday(rs.getString("product_registerday"));
				productDTO.setUpdateday(rs.getString("product_updateday"));
				productDTO.setStatus(rs.getInt("product_status_code"));
				
				productList.add(productDTO);
		
			}
			
			map.put("productList", productList);
	
		} finally {
			close();
		}
		
		return map;
	}

	/*
	 * 관리자 상품 상세 조회
	 */
	@Override
	public ProductDTO selectProduct(String productDetailNo) throws SQLException {
		ProductDTO productDTO = new ProductDTO();
		
		try {
			conn = ds.getConnection();
			
			String sql 	= " WITH COLOR "
						+ " AS "
						+ " ( "
							+ " select fk_product_detail_no, "
							+ "	LISTAGG(color_name, ',') WITHIN GROUP(ORDER BY color_name) as color_name, "
							+ "	LISTAGG(color_code, ',') WITHIN GROUP(ORDER BY color_code) as color_code "
							+ " from tbl_color "
							+ " group by fk_product_detail_no "
						+ " ) "
						+ " select p.pk_product_no, p.product_name, p.product_price, p.product_explanation, p.product_registerday, "
						+ " p.product_updateday, p.product_status_code, "
						+ " d.pk_product_detail_no, d.product_detail_size, d.product_inventory, "
						+ " c.pk_category_no, c.category_name, "
						+ " COLOR.color_name, COLOR.color_code "
						+ " from tbl_product p join tbl_product_detail d on p.pk_product_no = d.fk_product_no "
						+ " join tbl_category c on c.pk_category_no = p.fk_category_no "
						+ " join COLOR on COLOR.fk_product_detail_no = d.pk_product_detail_no " 
						+ " where d.pk_product_detail_no = ? ";
						
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, productDetailNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				ColorDTO colorDTO = new ColorDTO();
				
				productDTO.setProductNo(rs.getInt("pk_product_no"));
				productDTO.setProductDetailNo(rs.getInt("pk_product_detail_no"));
				productDTO.setCategoryNo(rs.getInt("pk_category_no"));
				productDTO.setCategoryName(rs.getString("category_name"));
				productDTO.setProductName(rs.getString("product_name"));
				productDTO.setSize(rs.getInt("product_detail_size"));
				productDTO.setExplanation(rs.getString("product_explanation"));
				productDTO.setPrice(rs.getInt("product_price"));
				productDTO.setInventory(rs.getInt("product_inventory"));
				productDTO.setRegisterday(rs.getString("product_registerday"));
				productDTO.setUpdateday(rs.getString("product_updateday"));
				productDTO.setStatus(rs.getInt("product_status_code"));

				colorDTO.setColorName(rs.getString("color_name"));
				colorDTO.setColor_code(rs.getString("color_code"));
				
				productDTO.setColorDTO(colorDTO);
			}
			else {
				productDTO = null;
			}
			
		} finally {
			close();
		}
		
		return productDTO;
	}

}
