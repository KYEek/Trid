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

import board.domain.BoardDTO;
import common.domain.PagingDTO;
import product.domain.CategoryDTO;

import product.domain.ColorDTO;
import product.domain.ImageDTO;
import product.domain.ProductDTO;
import product.domain.ProductDetailDTO;
import util.StringUtil;

public class ProductDAO_imple implements ProductDAO {
	
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	
	private Connection conn; // Database 연결 객체
	
	private PreparedStatement pstmt; // SQL문 실행 객체, 미리 컴파일을 통해 SQL Injection을 방지
	
	private ResultSet rs; // SQL 쿼리 실행 결과를 담는 객체

	public ProductDAO_imple() {
		// 데이터베이스 연결 설정 값을 불러온 후 Connection Pool 연결 
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
	 * 관리자 상품 등록
	 */
	@Override
	public int insertProduct(ProductDTO productDTO) throws SQLException {
		int result = 0;
		Integer pkProductNo = null; // tbl_product에 상품 추가 시 지정될 상품 일련번호
		
		try {
			conn = ds.getConnection();
			
			conn.setAutoCommit(false); // 수동커밋 전환
			
			
			////// 상품 테이블 PK를 미리 추출 //////
			String sql = " select pk_product_no_seq.nextval as pk_product_no from dual ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			// 시퀀스 조회 성공 시 상품 일련번호 저장
			if(rs.next()) {
				pkProductNo = rs.getInt("pk_product_no");
			}
			// 시퀀스 조회 실패 시 상품 등록 실패 처리
			else {
				System.out.println("[error] sequence pk_category_no_seq failed");
				return 0;
			}
			
			
			////// 상품 테이블에 상품 추가 //////
			sql 	= " insert into tbl_product( pk_product_no, fk_category_no, product_name, "
					+ " product_price, product_registerday, product_explanation ,product_status_code ) "
					+ " values( ?, ?, ?, ?, sysdate, ?, 1 )";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, pkProductNo); // 상품 일련번호
			pstmt.setInt(2, productDTO.getCategoryDTO().getPkCategoryNo()); // 카테고리 일련번호
			pstmt.setString(3, productDTO.getProductName()); // 상품명
			pstmt.setInt(4, productDTO.getPrice()); // 가격
			pstmt.setString(5, productDTO.getExplanation()); // 상품 설명
			
			// tbl_product의 insert 실패 시
			if(pstmt.executeUpdate() != 1) {
				System.out.println("tbl_product insert failed");
				conn.rollback(); // 트랜잭션 롤백
				return 0;
			}
			
			
			////// 상품 상세 테이블에 사이즈 및 재고 추가 //////
			List<ProductDetailDTO> productDetailList = productDTO.getProductDetailList();
			
			// 상품 사이즈 S,M,L,XL 별 재고 정보 입력
			for(ProductDetailDTO productDetailDTO : productDetailList) {
				sql = " insert into tbl_product_detail( pk_product_detail_no, "
					+ " fk_product_no, product_detail_size, product_inventory ) "
					+ " values( pk_product_detail_no_seq.nextval, ?, ?, ? )";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, pkProductNo); // 상품 일련번호
				pstmt.setInt(2, productDetailDTO.getSize()); // 사이즈 0:S, 1:M, 2:L, 3:XL
				pstmt.setInt(3, productDetailDTO.getInventory()); // 재고
				
				// tbl_product_detail의 insert 실패 시
				if(pstmt.executeUpdate() != 1) {
					System.out.println("tbl_product_detail insert failed");
					conn.rollback(); // 트랜잭션 롤백
					return 0;
				}
				
			}
			
			
			////// 상품 색상 테이블에 색상 추가 //////
			List<ColorDTO> colorList = productDTO.getColorList();
			
			// 상품에 지정된 모든 색상 정보들 입력
			for(ColorDTO colorDTO : colorList) {
				sql = " insert into tbl_color( pk_color_no, fk_product_no, color_name, color_code ) "
					+ " values( pk_color_no_seq.nextval, ?, ?, ? )";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, pkProductNo); // 상품 일련번호
				pstmt.setString(2, colorDTO.getColorName()); // 색상명
				pstmt.setString(3, colorDTO.getColorCode()); // 색상 코드
				
				// tbl_color의 insert 실패 시
				if(pstmt.executeUpdate() != 1) {
					System.out.println("tbl_color insert failed");
					conn.rollback(); // 트랜잭션 롤백
					return 0;
				}
				
			}
			
			
			////// 이미지 테이블에 이미지 정보 추가 //////
			List<ImageDTO> imageList = productDTO.getImageList();
			
			// 상품의 모든 이미지 정보를 저장
			for(ImageDTO imageDTO : imageList) {
				sql = " insert into tbl_product_image( pk_product_image_no, fk_product_no, product_image_path, "
					+ " product_image_name, product_image_registerday ) "
					+ " values( pk_product_image_no_seq.nextval, ?, ?, ?, sysdate ) ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, pkProductNo); // 상품 일련번호
				
				pstmt.setString(2, imageDTO.getImagePath()); // 이미지 경로 
				pstmt.setString(3, imageDTO.getImageName()); // 이미지 명
				
				// tbl_product_image의 insert 실패 시
				if(pstmt.executeUpdate() != 1) {
					System.out.println("tbl_product_image insert failed");
					conn.rollback(); // 트랜잭션 롤백
					return 0;
				}
				
			}
			
			// 4개의 테이블 삽입 쿼리 성공 시
			conn.commit(); // 커밋 완료
			
			result = 1; // 결과적으로 상품 테이블에 행이 1개 추가되었으므로 1이 반환되도록 지정

		} finally {
			close();
		}
		
		return result;	
	}

	/*
	 * 카테고리 전체 목록 조회
	 */
	@Override
	public List<CategoryDTO> selectCategoryList() throws SQLException {
		List<CategoryDTO> categoryList = new ArrayList<>(); // 카테고리 DTO 리스트 초기화
		
		try {
			conn = ds.getConnection();
			
			String sql 	= " select pk_category_no, category_name, category_gender, category_type "
						+ " from tbl_category ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CategoryDTO categoryDTO = new CategoryDTO();
				
				categoryDTO.setPkCategoryNo(rs.getInt("pk_category_no")); // 카테고리 일련번호
				categoryDTO.setCategoryName(rs.getString("category_name")); // 카테고리 명
				categoryDTO.setGender(rs.getInt("category_gender")); // 카테고리 성별 0:남자 1:여자
				categoryDTO.setType(rs.getInt("category_type")); // 카테고리 상의/하의 0:상의, 1:하의
				
				categoryList.add(categoryDTO);
			}
			
		} finally {
			close();
		}
		
		return categoryList;
	}
	
	/*
	 * 상품 리스트 전체 행 개수 조회
	 */
	@Override
	public int selectTotalRowCount(Map<String, Object> paraMap) throws SQLException {
		int totalRowCount = 0;
		
		String searchWord = (String)paraMap.get("searchWord");
		
		String categoryNo = (String)paraMap.get("categoryNo");
		
		String priceMin = (String)paraMap.get("priceMin");
		
		String priceMax = (String)paraMap.get("priceMax");
		
		String dateMin = (String)paraMap.get("dateMin");
		
		String dateMax = (String)paraMap.get("dateMax");
		
		int count = 0;
		
		try {
			conn = ds.getConnection();

			String sql 	= " select count(*) as total "
						+ " from tbl_product p join tbl_category c on p.fk_category_no = c.pk_category_no "
						+ " where 1=1 ";
			
			if(!StringUtil.isBlank(searchWord)) {
				sql += " and product_name like '%' || ? || '%' ";
			}
			
			if(!StringUtil.isBlank(categoryNo)) {
				sql += " and pk_category_no = ? ";
			}
			
			if(!StringUtil.isBlank(priceMin) && !StringUtil.isBlank(priceMax)) {
				sql += " and product_price between ? and ? ";
			}
			
			if(StringUtil.isBlank(priceMin) && !StringUtil.isBlank(priceMax)) {
				sql += " and product_price <= ? ";
			}
			
			if(!StringUtil.isBlank(priceMin) && StringUtil.isBlank(priceMax)) {
				sql += " and product_price >= ? ";
			}
			
			if(!StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				sql += " and product_registerday between to_date(?, 'yyyy-mm-dd') and to_date(?, 'yyyy-mm-dd hh24:mi:ss') ";
			}
			
			if(StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				sql += " and product_registerday <= to_date(?, 'yyyy-mm-dd hh24:mi:ss') ";
			}
			
			if(!StringUtil.isBlank(dateMin) && StringUtil.isBlank(dateMax)) {
				sql += " and product_registerday >= to_date(?, 'yyyy-mm-dd') ";
			}

			pstmt = conn.prepareStatement(sql);
			
			if(!StringUtil.isBlank(searchWord)) {
				pstmt.setString(++count, searchWord);
			}
			
			if(!StringUtil.isBlank(categoryNo)) {
				pstmt.setString(++count, categoryNo);
			}
			
			if(!StringUtil.isBlank(priceMin) && !StringUtil.isBlank(priceMax)) {
				pstmt.setString(++count, priceMin);
				pstmt.setString(++count, priceMax);
			}
			
			if(StringUtil.isBlank(priceMin) && !StringUtil.isBlank(priceMax)) {
				pstmt.setString(++count, priceMax);
			}
			
			if(!StringUtil.isBlank(priceMin) && StringUtil.isBlank(priceMax)) {
				pstmt.setString(++count, priceMin);
			}
			
			if(!StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMin);
				pstmt.setString(++count, dateMax + " 23:59:59");
			}
			
			if(StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMax + " 23:59:59");
			}
			
			if(!StringUtil.isBlank(dateMin) && StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMin);
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totalRowCount = rs.getInt("total");
			}
	
		} finally {
			close();
		}
		
		return totalRowCount;
	}

	/*
	 * 관리자 상품 리스트 조회
	 * PagingDTO를 통해 페이지 당 정해진 개수만큼의 상품을 조회
	 */
	@Override
	public List<ProductDTO> selectProductList(Map<String, Object> paraMap) throws SQLException {
		List<ProductDTO> productList = new ArrayList<>();
		
		PagingDTO pagingDTO = (PagingDTO)paraMap.get("pagingDTO");
		
		String searchWord = (String)paraMap.get("searchWord");
		
		String categoryNo = (String)paraMap.get("categoryNo");
		
		String sortCategory = (String)paraMap.get("sortCategory") == null ? "" : (String)paraMap.get("sortCategory");
		
		String priceMin = (String)paraMap.get("priceMin");
		
		String priceMax = (String)paraMap.get("priceMax");
		
		String dateMin = (String)paraMap.get("dateMin");
		
		String dateMax = (String)paraMap.get("dateMax");
		
		try {
			conn = ds.getConnection();

			String sql 	= " WITH T AS"
						+ " ( "
							+ " SELECT ROWNUM AS RN, A.pk_product_no, A.product_name, A.product_price, A.product_explanation, "
							+ " to_char(A.product_registerday, 'yyyy-mm-dd') as product_registerday, "
							+ " to_char(A.product_updateday, 'yyyy-mm-dd') as product_updateday, "
							+ " A.product_status_code, A.pk_category_no, A.category_name, A.category_type, A.category_gender " 
							+ " FROM ( "
								+ " select "
								+ " p.pk_product_no , p.product_name, p.product_price, p.product_explanation, p.product_registerday, "
								+ " p.product_updateday, p.product_status_code, "
								+ " c.pk_category_no, c.category_name, c.category_type, c.category_gender "
								+ " from tbl_product p join tbl_category c on c.pk_category_no = p.fk_category_no ";
			
			if(!StringUtil.isBlank(searchWord)) {
				sql += " and product_name like '%' || ? || '%' ";
			}
			
			if(!StringUtil.isBlank(categoryNo)) {
				sql += " and pk_category_no = ? ";
			}
			
			if(!StringUtil.isBlank(priceMin) && !StringUtil.isBlank(priceMax)) {
				sql += " and product_price between ? and ? ";
			}
			
			if(StringUtil.isBlank(priceMin) && !StringUtil.isBlank(priceMax)) {
				sql += " and product_price <= ? ";
			}
			
			if(!StringUtil.isBlank(priceMin) && StringUtil.isBlank(priceMax)) {
				sql += " and product_price >= ? ";
			}
			
			if(!StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				sql += " and product_registerday between to_date(?, 'yyyy-mm-dd') and to_date(?, 'yyyy-mm-dd hh24:mi:ss') ";
			}
			
			if(StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				System.out.println(dateMin);
				sql += " and product_registerday <= to_date(?, 'yyyy-mm-dd hh24:mi:ss') ";
			}
			
			if(!StringUtil.isBlank(dateMin) && StringUtil.isBlank(dateMax)) {
				sql += " and product_registerday >= to_date(?, 'yyyy-mm-dd') ";
			}
			
			switch(sortCategory) {
				case "0" : {sql += " order by p.product_registerday desc "; break;}
				case "1" : {sql += " order by p.product_registerday "; break;}
				case "2" : {sql += " order by p.product_price desc "; break;}
				case "3" : {sql += " order by p.product_price "; break;}
				default : {sql += " order by p.product_registerday desc "; break;}
			}
				
			sql += " ) A "
					+ " ) "
					+ " SELECT * FROM T  WHERE T.RN between ? and ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			int count = 0;
			
			if(!StringUtil.isBlank(searchWord)) {
				pstmt.setString(++count, searchWord);
			}
			
			if(!StringUtil.isBlank(categoryNo)) {
				pstmt.setString(++count, categoryNo);
			}
			
			if(!StringUtil.isBlank(priceMin) && !StringUtil.isBlank(priceMax)) {
				pstmt.setString(++count, priceMin);
				pstmt.setString(++count, priceMax);
			}
			
			if(StringUtil.isBlank(priceMin) && !StringUtil.isBlank(priceMax)) {
				pstmt.setString(++count, priceMax);
			}
			
			if(!StringUtil.isBlank(priceMin) && StringUtil.isBlank(priceMax)) {
				pstmt.setString(++count, priceMin);
			}
			
			if(!StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMin);
				pstmt.setString(++count, dateMax + " 23:59:59");
			}
			
			if(StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMax + " 23:59:59");
			}
			
			if(!StringUtil.isBlank(dateMin) && StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMin);
			}
			
			pstmt.setInt(++count, pagingDTO.getFirstRow()); // 현재 페이지의 첫 레코드의 번호
			pstmt.setInt(++count, pagingDTO.getLastRow()); // 현재 페이지의 마지막 레코드의 번호
		
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ProductDTO productDTO = new ProductDTO();
				CategoryDTO categoryDTO = new CategoryDTO();
				
				// CategoryDTO 저장
				categoryDTO.setPkCategoryNo(rs.getInt("pk_category_no"));
				categoryDTO.setGender(rs.getInt("category_gender"));
				categoryDTO.setType(rs.getInt("category_type"));
				categoryDTO.setCategoryName(rs.getString("category_name"));
				
				// ProductDTO 저장
				productDTO.setProductNo(rs.getInt("pk_product_no"));
				productDTO.setProductName(rs.getString("product_name"));
				productDTO.setExplanation(rs.getString("product_explanation"));
				productDTO.setPrice(rs.getInt("product_price"));
				productDTO.setRegisterday(rs.getString("product_registerday"));
				productDTO.setUpdateday(rs.getString("product_updateday"));
				productDTO.setStatus(rs.getInt("product_status_code"));
				
				// ProductDTO에 CategoryDTO 저장
				productDTO.setCategoryDTO(categoryDTO);
				
				productList.add(productDTO);
		
			}
	
		} finally {
			close();
		}
		
		return productList;
	}

	/*
	 * 관리자 상품 상세 조회
	 */
	@Override
	public ProductDTO selectProduct(String productNo) throws SQLException {
		ProductDTO productDTO = new ProductDTO(); // ProductDTO 초기화
		
		CategoryDTO categoryDTO = new CategoryDTO(); // CategoryDTO 초기화
		
		List<ColorDTO> colorList = new ArrayList<>(); // ColorDTO 리스트 초기화
		
		List<ProductDetailDTO> productDetailList = new ArrayList<>(); // ProductDetailDTO 리스트 초기화
		
		List<ImageDTO> imageList = new ArrayList<>(); // ImageDTO 리스트 초기화
		
		try {
			conn = ds.getConnection();
			
			String sql 	= " WITH COLOR "
						+ " AS "
						+ " ( "
							+ " select fk_product_no, "
							+ "	LISTAGG(pk_color_no, ',') WITHIN GROUP(ORDER BY color_name) as pk_color_no, "
							+ "	LISTAGG(color_name, ',') WITHIN GROUP(ORDER BY color_name) as color_name, "
							+ "	LISTAGG(color_code, ',') WITHIN GROUP(ORDER BY color_name) as color_code "
							+ " from tbl_color "
							+ " group by fk_product_no "
						+ " ), "
						+ " D AS "
						+ " ( "
							+ " select fk_product_no, "
							+ " LISTAGG(pk_product_detail_no, ',') WITHIN GROUP(ORDER BY product_detail_size) as pk_product_detail_no, "
							+ "	LISTAGG(product_detail_size, ',') WITHIN GROUP(ORDER BY product_detail_size) as product_detail_size, "
							+ " LISTAGG(product_inventory, ',') WITHIN GROUP(ORDER BY product_detail_size) as product_inventory "
							+ " from tbl_product_detail "
							+ " group by fk_product_no "
						+ " ),"
						+ " IMAGES AS ( "
						+ "    select "
						+ "        fk_product_no, "
						+ "        LISTAGG(pk_product_image_no, ',') WITHIN GROUP(ORDER BY pk_product_image_no) AS pk_product_image_no, "
						+ "        LISTAGG(product_image_path, ',') WITHIN GROUP(ORDER BY pk_product_image_no) AS product_image_path, "
						+ "        LISTAGG(product_image_name, ',') WITHIN GROUP(ORDER BY pk_product_image_no) AS product_image_name, "
						+ "        LISTAGG(to_char(product_image_registerday, 'yyyy-mm-dd'), ',') WITHIN GROUP(ORDER BY pk_product_image_no) AS product_image_registerday "
						+ "    from tbl_product_image "
						+ "    group BY fk_product_no "
						+ " ) "
						+ " select p.pk_product_no, p.product_name, p.product_price, p.product_explanation, to_char(p.product_registerday, 'yyyy-mm-dd') as product_registerday, "
						+ " to_char(p.product_updateday, 'yyyy-mm-dd') as product_updateday, p.product_status_code, "
						+ " d.pk_product_detail_no, d.product_detail_size, d.product_inventory, "
						+ " c.pk_category_no, c.category_name, c.category_type, c.category_gender, "
						+ " COLOR.pk_color_no, COLOR.color_name, COLOR.color_code,"
						+ " IMAGES.pk_product_image_no, "
						+ " IMAGES.product_image_path, "
						+ " IMAGES.product_image_name,"
						+ " IMAGES.product_image_registerday "
						+ " from tbl_product p join d on p.pk_product_no = d.fk_product_no "
						+ " join tbl_category c on c.pk_category_no = p.fk_category_no "
						+ " join COLOR on COLOR.fk_product_no = p.pk_product_no "
						+ " join IMAGES on IMAGES.fk_product_no = p.pk_product_no "
						+ " where p.pk_product_no = ? ";
						
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, productNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				// CategoryDTO 저장
				categoryDTO.setPkCategoryNo(rs.getInt("pk_category_no"));
				categoryDTO.setGender(rs.getInt("category_gender"));
				categoryDTO.setType(rs.getInt("category_type"));
				categoryDTO.setCategoryName(rs.getString("category_name"));
				
				// ProductDTO 저장
				productDTO.setProductNo(rs.getInt("pk_product_no"));
				productDTO.setProductName(rs.getString("product_name"));
				productDTO.setExplanation(rs.getString("product_explanation"));
				productDTO.setPrice(rs.getInt("product_price"));
				productDTO.setRegisterday(rs.getString("product_registerday"));
				productDTO.setUpdateday(rs.getString("product_updateday"));
				productDTO.setStatus(rs.getInt("product_status_code"));
				
				// 상품 상세 일련번호 배열
				String[] pkProductDetailArr = rs.getString("pk_product_detail_no").split(",");

				// 사이즈 배열
				String[] sizeArr = rs.getString("product_detail_size").split(",");
				
				// 사이즈 별 상품의 재고
				String[] inventoryArr = rs.getString("product_inventory").split(",");
				
				// 상품의 색상 일련번호 배열
				String[] pkColorNoArr= rs.getString("pk_color_no").split(",");
				
				// 상품의 색상명 배열
				String[] colorNameArr = rs.getString("color_name").split(",");
				
				// 상품의 색상 코드 배열
				String[] colorCodeArr = rs.getString("color_code").split(",");
				
				// 상품 이미지 일련번호 배열
				String[] pkProductImageNoArr = rs.getString("pk_product_image_no").split(",");
				
				// 상품 이미지 주소 배열
				String[] productImagePathArr = rs.getString("product_image_path").split(",");
				
				// 상품 이미지명 배열
				String[] productImageNameArr = rs.getString("product_image_name").split(",");
				
				// 상품 이미지명 배열
				String[] productImageReigsterdayArr = rs.getString("product_image_registerday").split(",");
				
				// ColorDTO 리스트에 색상 정보 저장
				for(int i = 0; i < pkColorNoArr.length; i++) {
					ColorDTO colorDTO = new ColorDTO();
					
					colorDTO.setPkColorNo(Integer.parseInt(pkColorNoArr[i]));
					colorDTO.setColorName(colorNameArr[i]);
					colorDTO.setColorCode(colorCodeArr[i]);
					
					colorList.add(colorDTO);
				}
				
				// ProductDetailDTO 리스트에 상품 상세 정보 저장
				for(int i = 0; i < pkProductDetailArr.length; i++) {
					ProductDetailDTO productDetailDTO = new ProductDetailDTO();
					
					productDetailDTO.setPkProductDetailNo(Integer.parseInt(pkProductDetailArr[i]));
					productDetailDTO.setSize(Integer.parseInt(sizeArr[i]));
					productDetailDTO.setInventory(Integer.parseInt(inventoryArr[i]));
					
					productDetailList.add(productDetailDTO);
				}
				
				// ImageDTO 리스트에 이미지 정보 저장
				for(int i = 0; i < pkProductImageNoArr.length; i++) {
					ImageDTO imageDTO = new ImageDTO();
					
					imageDTO.setPkProductImageNo(Integer.parseInt(pkProductImageNoArr[i]));
					imageDTO.setImagePath(productImagePathArr[i]);
					imageDTO.setImageName(productImageNameArr[i]);
					imageDTO.setRegisterday(productImageReigsterdayArr[i]);
					
					imageList.add(imageDTO);
				}
				
				// ProductDTO에 CategoryDTO 저장
				productDTO.setCategoryDTO(categoryDTO);
				
				// ProductDTO에 ColorDTO 리스트 저장
				productDTO.setColorList(colorList);
				
				// ProductDTO에 ProductDetailDTO 리스트 저장
				productDTO.setProductDetailList(productDetailList);
				
				// ProductDTO에 imageDTO 리스트 저장
				productDTO.setImageList(imageList);
				
				
			}
			else {
				productDTO = null;
			}
			
		} catch(NumberFormatException e) {
			e.printStackTrace();
			productDTO = null;
		} finally {
			close();
		}
		
		return productDTO;
	}

	/*
	 * 관리자 상품 삭제 메소드
	 */
	@Override
	public int deleteProduct(String productNo) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql 	= " update tbl_product set product_status_code = 0, product_updateday = sysdate "
						+ " where product_status_code = 1 and pk_product_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, productNo);
			
			result = pstmt.executeUpdate();

		} finally {
			close();
		}
		
		return result;
	}


	/*
	 * 관리자 상품 수정
	 */
	@Override
	public int updateProduct(ProductDTO productDTO) throws SQLException {
		int result = 0;
		
		try {
			
			conn = ds.getConnection();
			
			conn.setAutoCommit(false);
			
			String sql 	= " update tbl_product set product_name = ?, product_explanation = ?, product_price = ? "
						+ " where pk_product_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, productDTO.getProductName());
			pstmt.setString(2, productDTO.getExplanation());
			pstmt.setInt(3, productDTO.getPrice());
			pstmt.setInt(4, productDTO.getProductNo());
			
			if(pstmt.executeUpdate() != 1) {
				System.out.println("tbl_product update failed");
				conn.rollback();
				return 0;
			}
			
			for(ProductDetailDTO productDetailDTO : productDTO.getProductDetailList()) {
				sql = " update tbl_product_detail set product_inventory = ? "
					+ " where pk_product_detail_no = ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, productDetailDTO.getInventory());
				pstmt.setInt(2, productDetailDTO.getPkProductDetailNo());
				
				if(pstmt.executeUpdate() != 1) {
					System.out.println("tbl_product_detail update failed");
					conn.rollback();
					return 0;
				}
				
			}
			
			for (ImageDTO imageDTO : productDTO.getImageList()) {
				sql		= " insert into tbl_product_image( pk_product_image_no, fk_product_no, product_image_path, " 
						+ " product_image_name, product_image_registerday ) "
						+ " values( pk_product_image_no_seq.nextval, ?, ?, ?, sysdate ) ";

				pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, productDTO.getProductNo()); // 상품 일련번호

				pstmt.setString(2, imageDTO.getImagePath()); // 이미지 경로
				pstmt.setString(3, imageDTO.getImageName()); // 이미지 명

				// tbl_product_image의 insert 실패 시
				if (pstmt.executeUpdate() != 1) {
					System.out.println("tbl_product_image insert failed");
					conn.rollback(); // 트랜잭션 롤백
					return 0;
				}

			}
			
			conn.commit();
			
			result = 1;
			
		} finally {
			close();
		}

		return result;
	}

	@Override
	public int deleteProductImage(String pkProductImageNo) throws SQLException {
		int result = 0;
		
		try {

			conn = ds.getConnection();

				String sql 	= " delete from tbl_product_image where pk_product_image_no = ? ";

				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, pkProductImageNo); // 상품 이미지 일련번호

				result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
	}

	@Override
	public int insertProductImage(List<ImageDTO> imageList, int productNo) throws SQLException {
		String sql = "";

		try {

			conn = ds.getConnection();

			conn.setAutoCommit(false);

			// 상품의 모든 이미지 정보를 저장
			for (ImageDTO imageDTO : imageList) {
				sql		= " insert into tbl_product_image( pk_product_image_no, fk_product_no, product_image_path, " 
						+ " product_image_name, product_image_registerday ) "
						+ " values( pk_product_image_no_seq.nextval, ?, ?, ?, sysdate ) ";

				pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, productNo); // 상품 일련번호

				pstmt.setString(2, imageDTO.getImagePath()); // 이미지 경로
				pstmt.setString(3, imageDTO.getImageName()); // 이미지 명

				// tbl_product_image의 insert 실패 시
				if (pstmt.executeUpdate() != 1) {
					System.out.println("tbl_product_image insert failed");
					conn.rollback(); // 트랜잭션 롤백
					return 0;
				}

			}

			conn.commit();
			
			return 1;
			
		} finally {
			close();
		}

	}

	
	// 사용자 상품 상세 조회
	@Override
	public ProductDTO selectProductByMember(String productNo) throws SQLException {
		ProductDTO productDTO = new ProductDTO(); // ProductDTO 초기화
		
		CategoryDTO categoryDTO = new CategoryDTO(); // CategoryDTO 초기화
		
		List<ColorDTO> colorList = new ArrayList<>(); // ColorDTO 리스트 초기화
		
		List<ImageDTO> imageList = new ArrayList<>();	// ImageDTO 초기화
		
		List<ProductDetailDTO> productDetailList = new ArrayList<>(); // ProductDetailDTO 리스트 초기화
		
		try {
			conn = ds.getConnection();
			
			// 관리자코드에서 등록날짜. 업데이트날짜 제거 및 조건절에 상품상태코드 추가(활성화된 것)
			String sql 	= " WITH COLOR AS ( "
						+ "    SELECT "
						+ "        fk_product_no, "
						+ "        LISTAGG(pk_color_no, ',') WITHIN GROUP(ORDER BY color_name) AS pk_color_no, "
						+ "        LISTAGG(color_name, ',') WITHIN GROUP(ORDER BY color_name) AS color_name, "
						+ "        LISTAGG(color_code, ',') WITHIN GROUP(ORDER BY color_name) AS color_code "
						+ "    FROM tbl_color "
						+ "    GROUP BY fk_product_no "
						+ " ), "
						+ " D AS ("
						+ "    SELECT "
						+ "        fk_product_no, "
						+ "        LISTAGG(pk_product_detail_no, ',') WITHIN GROUP(ORDER BY product_detail_size) AS pk_product_detail_no, "
						+ "        LISTAGG(product_detail_size, ',') WITHIN GROUP(ORDER BY product_detail_size) AS product_detail_size, "
						+ "        LISTAGG(product_inventory, ',') WITHIN GROUP(ORDER BY product_detail_size) AS product_inventory "
						+ "    FROM tbl_product_detail "
						+ "    GROUP BY fk_product_no "
						+ " ), "
						+ " IMAGES AS ("
						+ "    SELECT "
						+ "        fk_product_no, "
						+ "        LISTAGG(pk_product_image_no, ',') WITHIN GROUP(ORDER BY pk_product_image_no) AS pk_product_image_no, "
						+ "        LISTAGG(product_image_path, ',') WITHIN GROUP(ORDER BY pk_product_image_no) AS product_image_path, "
						+ "        LISTAGG(product_image_name, ',') WITHIN GROUP(ORDER BY pk_product_image_no) AS product_image_name "
						+ "    FROM tbl_product_image "
						+ "    GROUP BY fk_product_no "
						+ " ) "
						+ " SELECT "
						+ "    p.pk_product_no, "
						+ "    p.product_name, "
						+ "    p.product_price, "
						+ "    p.product_explanation, "
						+ "    p.product_status_code, "
						+ "    d.pk_product_detail_no, "
						+ "    d.product_detail_size, "
						+ "    d.product_inventory, "
						+ "    c.pk_category_no, "
						+ "    c.category_name, "
						+ "    c.category_type, "
						+ "    c.category_gender, "
						+ "    COLOR.pk_color_no, "
						+ "    COLOR.color_name, "
						+ "    COLOR.color_code, "
						+ "    IMAGES.pk_product_image_no, "
						+ "    IMAGES.product_image_path, "
						+ "    IMAGES.product_image_name "
						+ " FROM "
						+ "    tbl_product p "
						+ "    JOIN D ON p.pk_product_no = D.fk_product_no "
						+ "    JOIN tbl_category c ON c.pk_category_no = p.fk_category_no "
						+ "    JOIN COLOR ON COLOR.fk_product_no = p.pk_product_no "
						+ "    LEFT JOIN IMAGES ON IMAGES.fk_product_no = p.pk_product_no "
						+ " WHERE "
						+ "    p.pk_product_no = ? "
						+ "    AND p.product_status_code = 1 ";
						
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, productNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				// CategoryDTO 저장
				categoryDTO.setPkCategoryNo(rs.getInt("pk_category_no"));
				categoryDTO.setGender(rs.getInt("category_gender"));
				categoryDTO.setType(rs.getInt("category_type"));
				categoryDTO.setCategoryName(rs.getString("category_name"));
				
				// ProductDTO 저장
				productDTO.setProductNo(rs.getInt("pk_product_no"));
				productDTO.setProductName(rs.getString("product_name"));
				productDTO.setExplanation(rs.getString("product_explanation"));
				productDTO.setPrice(rs.getInt("product_price"));
				productDTO.setStatus(rs.getInt("product_status_code"));
	
				
				// 상품 상세 일련번호 배열
				String[] pkProductDetailArr = rs.getString("pk_product_detail_no").split(",");

				// 사이즈 배열
				String[] sizeArr = rs.getString("product_detail_size").split(",");
				
				// 사이즈 별 상품의 재고
				String[] inventoryArr = rs.getString("product_inventory").split(",");
				
				// 상품의 색상 일련번호 배열
				String[] pkColorNoArr= rs.getString("pk_color_no").split(",");
				
				// 상품의 색상명 배열
				String[] colorNameArr = rs.getString("color_name").split(",");
				
				// 상품의 색상 코드 배열
				String[] colorCodeArr = rs.getString("color_code").split(",");
				
				// ColorDTO 리스트에 색상 정보 저장
				for(int i = 0; i < pkColorNoArr.length; i++) {
					ColorDTO colorDTO = new ColorDTO();
					
					colorDTO.setPkColorNo(Integer.parseInt(pkColorNoArr[i]));
					colorDTO.setColorName(colorNameArr[i]);
					colorDTO.setColorCode(colorCodeArr[i]);
					
					colorList.add(colorDTO);
				}
				
				// 상품 이미지 일련번호 배열
				String[] pkProductImageNoArr = rs.getString("pk_product_image_no").split(",");
				
				// 상품 이미지 주소 배열
				String[] imagePathArr = rs.getString("product_image_path").split(",");
				
				// 상품 이미지 명 배열
				String[] imageNameArr = rs.getString("product_image_name").split(",");
				
				// IamgeDTO 리스트에 이미지 정보 저장
				for(int i = 0; i < pkProductImageNoArr.length; i++) {
					ImageDTO imageDTO = new ImageDTO();
					
					imageDTO.setPkProductImageNo(Integer.parseInt(pkProductImageNoArr[i]));
					imageDTO.setImagePath(imagePathArr[i]);
					imageDTO.setImageName(imageNameArr[i]);
					
					imageList.add(imageDTO);
				}
				
				// ProductDetailDTO 리스트에 상품 상세 정보 저장
				for(int i = 0; i < pkProductDetailArr.length; i++) {
					ProductDetailDTO productDetailDTO = new ProductDetailDTO();
					
					productDetailDTO.setPkProductDetailNo(Integer.parseInt(pkProductDetailArr[i]));
					productDetailDTO.setSize(Integer.parseInt(sizeArr[i]));
					productDetailDTO.setInventory(Integer.parseInt(inventoryArr[i]));
					
					productDetailList.add(productDetailDTO);
				}
				
				// ProductDTO에 CategoryDTO 저장
				productDTO.setCategoryDTO(categoryDTO);
				
				// ProductDTO에 ColorDTO 리스트 저장
				productDTO.setColorList(colorList);
				
				// ProductDTO에 ProductDetailDTO 리스트 저장
				productDTO.setProductDetailList(productDetailList);
				
				// ProductDTO에 ImageDTO 리스트 저장
				productDTO.setImageList(imageList);
				
			}
			else {
				productDTO = null;
			}
			
		} catch(NumberFormatException e) {
			e.printStackTrace();
			productDTO = null;
		} finally {
			close();
		}
		
		return productDTO;
	}// end of 사용자 상품 상세 조회 -----
	
	
	/////////////////////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////// 카테고리 상품 ///////////////////////////////////////
	
	
	// DB 에서 상품 리스트 뽑아오는 메소드
		public List<ProductDTO> selectProductByCategory(Map<String, String> paraMap) throws SQLException {
			
		    List<ProductDTO> productList = new ArrayList<>();

		    try {
		        conn = ds.getConnection();

		        StringBuilder sql = new StringBuilder(
					                    " SELECT  p.pk_product_no, p.product_name, p.product_price, "
					                  + "         i.product_image_path, i.pk_product_image_no, "
					                  + "         c.pk_category_no, c.category_type, "
					                  + "         cr.color_name "
					                  + " FROM    tbl_product p "
					                  + "         JOIN tbl_category c "
					                  + "         ON p.fk_category_no = c.pk_category_no "
					                  + "         JOIN tbl_color cr "
					                  + "         ON p.pk_product_no = cr.fk_product_no "
					                  + "         JOIN tbl_product_image i "
					                  + "         ON p.pk_product_no = i.fk_product_no "
					                  + " WHERE   product_status_code = 1 "
					                  + " AND     i.pk_product_image_no ="
					                  + " ( "
					                  + "  SELECT  MIN(pk_product_image_no) "
					                  + "  FROM    tbl_product_image "
					                  + "  WHERE   fk_product_no = p.pk_product_no "
					                  + " ) " );

		        // 필터링 조건 추가
		        if (!StringUtil.isBlank(paraMap.get("choosePrice"))) {
		            sql.append(" AND p.product_price <= ? "); // 선택 가격 이하 상품만 가져오기
		        }
		        if (!StringUtil.isBlank(paraMap.get("chooseColor"))) {
		            String[] colors = paraMap.get("chooseColor").split(",");
		            if (colors.length > 0) {
		                sql.append(" AND cr.color_name IN (");
		                for (int i = 0; i < colors.length; i++) {
		                    sql.append(" ? ");
		                    if (i < colors.length - 1) {
		                        sql.append(", ");
		                    }
		                }
		                sql.append(")");
		            }
		        }
		        if (!StringUtil.isBlank(paraMap.get("chooseCategory"))) {
		            sql.append(" and c.pk_category_no = ? ");
		        }
		        if (!StringUtil.isBlank(paraMap.get("chooseType"))) {
		            sql.append(" and c.category_type = ? ");
		        }

		        pstmt = conn.prepareStatement(sql.toString());

			     // PreparedStatement에 필터 값을 넣어줌
			     int index = 1; // 인덱스는 1부터 시작
			     if (!StringUtil.isBlank(paraMap.get("choosePrice"))) {
			         pstmt.setInt(index++, Integer.parseInt(paraMap.get("choosePrice")));
			     }
			     if (!StringUtil.isBlank(paraMap.get("chooseColor"))) {
			         String[] colors = paraMap.get("chooseColor").split(",");
			         for (String color : colors) {
			             pstmt.setString(index++, color.trim().toUpperCase());
			         }
			     }
			     if (!StringUtil.isBlank(paraMap.get("chooseCategory"))) {
			         pstmt.setString(index++, paraMap.get("chooseCategory"));
			     }
			     if (!StringUtil.isBlank(paraMap.get("chooseType"))) {
			         pstmt.setString(index++, paraMap.get("chooseType"));
			     }
		        
		        if (!StringUtil.isBlank(paraMap.get("choosePrice"))) {
		            System.out.println("choosePrice: " + paraMap.get("choosePrice"));
		        }
		        if (!StringUtil.isBlank(paraMap.get("chooseColor"))) {
		            System.out.println("chooseColor: " + paraMap.get("chooseColor"));
		        }

		        rs = pstmt.executeQuery();
		        

		        // 결과를 ProductDTO 리스트로 매핑
		        while (rs.next()) {
		            ProductDTO product = new ProductDTO();
		            
		            product.setProductNo(rs.getInt("pk_product_no"));
		            product.setProductName(rs.getString("product_name"));
		            product.setPrice(rs.getInt("product_price"));
		            
	                // 이미지 정보 매핑
	                List<ImageDTO> imageList = new ArrayList<>(); // 이미지 정보를 ProductDTO의 이미지 리스트에 추가
	                
	                ImageDTO image = new ImageDTO();
	                image.setPkProductImageNo(rs.getInt("pk_product_image_no"));
	                image.setImagePath(rs.getString("product_image_path"));
	                
	                imageList.add(image);  // 이미지 리스트에 이미지 하나씩 쌓아주기
	                
	                product.setImageList(imageList);
	                
	                productList.add(product);
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		        System.out.println("SQL 실행 중 오류 발생: " + e.getMessage());
		    }   finally {
		        close();
		    }

		    return productList;
		}// end of public List<ProductDTO> categorySelect(Map<String, String> paraMap) throws SQLException ------------------------


		// 검색어 입력시 검색어가 상품명에 포함된 상품 리스트 뽑아오는 메소드
		@Override
		public List<ProductDTO> searchProduct(Map<String, String> paraMap) throws SQLException {
			
			List<ProductDTO> searchProductList = new ArrayList<>();
			
			try {
				conn = ds.getConnection();
				
				String sql = " SELECT   p.pk_product_no, p.product_name, p.product_price, "
						   + "          i.product_image_path, i.pk_product_image_no, "
						   + "          c.category_gender "
						   + " FROM     tbl_product p "
						   + "          JOIN tbl_category c "
						   + "          ON p.fk_category_no = c.pk_category_no "
						   + "          JOIN tbl_product_image i "
						   + "          ON p.pk_product_no = i.fk_product_no "
						   + " WHERE    product_status_code = 1 "
						   + " AND      i.pk_product_image_no = "
						   + " ( "
						   + "  SELECT   MIN(pk_product_image_no) "
						   + "  FROM     tbl_product_image "
						   + "  WHERE    fk_product_no = p.pk_product_no "
						   + " ) ";
				
		        // 검색어 필터링
		        if (paraMap.get("search_word") != null && !paraMap.get("search_word").trim().isEmpty()) {
		            sql += " AND p.product_name LIKE ? ";
		            System.out.println("검색어 조건: " + paraMap.get("search_word"));
		        } else {
		            System.out.println("검색어가 비어 있음");
		        }
				sql += " ORDER BY  p.pk_product_no ASC";
				
				pstmt = conn.prepareStatement(sql);
				
				// 검색어 파라미터 매핑
				if (paraMap.get("search_word") != null && !paraMap.get("search_word").trim().isEmpty()) {
				    pstmt.setString(1, "%" + paraMap.get("search_word") + "%");
				    
				    System.out.println("SQL 쿼리: " + pstmt.toString()); // 확인용 디버깅 메시지
				}
				
				rs = pstmt.executeQuery();
				
		         while (rs.next()) {
		        	 // 상품 정보 매핑
		        	 ProductDTO product = new ProductDTO();

		        	 product.setProductNo(rs.getInt("pk_product_no"));
		        	 product.setProductName(rs.getString("product_name"));
		        	 product.setPrice(rs.getInt("product_price"));


	                 // 이미지 정보 매핑
	                 List<ImageDTO> imageList = new ArrayList<>(); // 이미지 정보를 ProductDTO의 이미지 리스트에 추가
	                
	                 ImageDTO image = new ImageDTO();
	                 image.setPkProductImageNo(rs.getInt("pk_product_image_no"));
	                 image.setImagePath(rs.getString("product_image_path"));
	                
	                 imageList.add(image);  // 이미지 리스트에 이미지 하나씩 쌓아주기
	                
	                 product.setImageList(imageList);
	                
		             searchProductList.add(product);

		          } // end of while -------------------
				
				
			} finally {
				close();
		    }
			
			return searchProductList;
		}// end of public List<ProductDTO> searchProduct(Map<String, String> paraMap) throws SQLException ---------------------------
		
		
		
		// 사용자 추천 상품 리스트 뽑기
		@Override
		public List<Map<String, String>> selectRecommendProductList(String productNo) throws SQLException {
			
			List<Map<String, String>> recommendProductMapList = new ArrayList<>();
			
			try {
				conn = ds.getConnection();
				
				String sql  = "WITH FIRST_IMAGE AS ( "
							+ "    SELECT "
							+ "        fk_product_no, "
							+ "        product_image_path, "
							+ "        ROW_NUMBER() OVER (PARTITION BY fk_product_no ORDER BY pk_product_image_no ASC) AS row_num "
							+ "    FROM tbl_product_image "
							+ " ) "
							+ " SELECT "
							+ "    P.pk_product_no AS product_no, "
							+ "    P.product_name, "
							+ "    FI.product_image_path AS image_path "
							+ " FROM "
							+ "    tbl_product P "
							+ " JOIN "
							+ "    tbl_color C ON P.pk_product_no = C.fk_product_no "
							+ " JOIN "
							+ "    tbl_category CAT ON P.fk_category_no = CAT.pk_category_no "
							+ " JOIN "
							+ "    tbl_product RECOMMEND_PRODUCT ON RECOMMEND_PRODUCT.pk_product_no = ? "
							+ " JOIN "
							+ "    tbl_category RECOMMEND_CATEGORY ON RECOMMEND_PRODUCT.fk_category_no = RECOMMEND_CATEGORY.pk_category_no "
							+ " LEFT JOIN "
							+ "    FIRST_IMAGE FI ON FI.fk_product_no = P.pk_product_no AND FI.row_num = 1 "
							+ " WHERE "
							+ "    CAT.pk_category_no = RECOMMEND_CATEGORY.pk_category_no "
							+ "    AND CAT.category_gender = RECOMMEND_CATEGORY.category_gender "
							+ "    AND C.color_name IN ( "
							+ "        SELECT color_name "
							+ "        FROM tbl_color "
							+ "        WHERE fk_product_no = RECOMMEND_PRODUCT.pk_product_no "
							+ "    ) "
							+ "    AND P.pk_product_no != RECOMMEND_PRODUCT.pk_product_no "
							+ "    AND ROWNUM <= 4 "
							+ " ORDER BY "
							+ "    DBMS_RANDOM.VALUE ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, productNo);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					
					Map<String, String> recommendProductMap = new HashMap<String, String>();
					
					recommendProductMap.put("product_no", String.valueOf(rs.getString("product_no")));
					recommendProductMap.put("product_name", rs.getString("product_name"));
					recommendProductMap.put("image_path", rs.getString("image_path"));
					
					System.out.println(recommendProductMap.get("product_name"));
					System.out.println(recommendProductMap.get("image_path"));
					
					
					recommendProductMapList.add(recommendProductMap);
				}
				
				
			} finally {
				close();
			}
			
			
			return recommendProductMapList;
		}

		// 추천 상품 리스트 출력 메소드
		@Override
		public List<ProductDTO> RandomProducts() throws SQLException {
			
		    List<ProductDTO> randomProductList = new ArrayList<>();
		    
		    try {
		        conn = ds.getConnection();

		        String sql = " SELECT p.pk_product_no, p.product_name, p.product_price, "
		                   + "        i.product_image_path, i.pk_product_image_no "
		                   + " FROM   tbl_product p "
		                   + " JOIN   tbl_product_image i "
		                   + " ON     p.pk_product_no = i.fk_product_no "
		                   + " WHERE  product_status_code = 1 "
		                   + " AND    i.pk_product_image_no = "
		                   + "        ( "
		                   + "		  SELECT MIN(pk_product_image_no"
		                   + "		  ) "
		                   + "        FROM tbl_product_image "
		                   + "        WHERE fk_product_no = p.pk_product_no) "
		                   + " ORDER BY DBMS_RANDOM.VALUE "; // 랜덤으로 섞어서 가져옴

		        pstmt = conn.prepareStatement(sql);

		        rs = pstmt.executeQuery();

		        while (rs.next()) {
		        	// 상품 정보 매핑
		            ProductDTO product = new ProductDTO();
		            product.setProductNo(rs.getInt("pk_product_no"));
		            product.setProductName(rs.getString("product_name"));
		            product.setPrice(rs.getInt("product_price"));

		            // 이미지 정보 매핑
		            List<ImageDTO> imageList = new ArrayList<>();
		            ImageDTO image = new ImageDTO();
		            image.setPkProductImageNo(rs.getInt("pk_product_image_no"));
		            image.setImagePath(rs.getString("product_image_path"));
		            imageList.add(image);

		            product.setImageList(imageList);
		            randomProductList.add(product);
		        }
		    } finally {
		        close();
		    }

		    return randomProductList;
		}
		
		
		// 햄버거 메뉴 카테고리 클릭시 해당 카테고리 상품페이지로 이동 메소드
		@Override
		public List<CategoryDTO> HamburgerCategory() throws SQLException {
			List<CategoryDTO> Categories = new ArrayList<>();
			
			try {
				conn = ds.getConnection();
				
				String sql 	= " select pk_category_no, category_name, category_gender, category_type "
							+ " from tbl_category ";
				
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					CategoryDTO categoryDTO = new CategoryDTO();
					
					categoryDTO.setPkCategoryNo(rs.getInt("pk_category_no")); // 카테고리 일련번호
					categoryDTO.setCategoryName(rs.getString("category_name")); // 카테고리 명
					categoryDTO.setGender(rs.getInt("category_gender")); // 카테고리 성별 0:남자 1:여자
					categoryDTO.setType(rs.getInt("category_type")); // 카테고리 상의/하의 0:상의, 1:하의
					
					Categories.add(categoryDTO);
				}
				
			} finally {
				close();
			}
			
			return Categories;
		}
		
}
