package product.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import product.domain.CategoryDTO;
import product.domain.ImageDTO;
import product.domain.ProductDTO;

public interface ProductDAO {
	
	// 관리자 상품 등록
	int insertProduct(ProductDTO productDTO) throws SQLException;
	
	// 카테고리 전체 목록 조회
	List<CategoryDTO> selectCategoryList() throws SQLException;
	
	// 상품 리스트 전체 행 개수 조회
	int selectTotalRowCount(Map<String, Object> paraMap) throws SQLException ;

	// 관리자 상품 리스트 조회
	List<ProductDTO> selectProductList(Map<String, Object> paraMap) throws SQLException;

	// 관리자 상품 상세 조회
	ProductDTO selectProduct(String productNo) throws SQLException;

	// 관리자 상품 삭제
	int deleteProduct(String productNo) throws SQLException;

	// 관리자 상품 수정
	int updateProduct(ProductDTO productDTO) throws SQLException;
	
	// 사용자 상품 상세 조회
	ProductDTO selectProductByMember(String productNo) throws SQLException;
	
	// 사용자 추천 상품 리스트 뽑기
	List<Map<String, String>> selectRecommendProductList(String productNo) throws SQLException;
	
	// 관리자 상품 수정 시 이미지 삭제
	int deleteProductImage(String pkProductImageNo) throws SQLException;
	
	// 관리자 상품 수정 시 이미지 추가
	int insertProductImage(List<ImageDTO> imageList, int productNo) throws SQLException;
	
	/////////////////////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////// 카테고리 상품 ///////////////////////////////////////
	
	// 카테고리 선택시 상품 리스트 뽑아오는 메소드
	public List<ProductDTO> selectProductByCategory(Map<String, String> paraMap) throws SQLException;
	
	// 검색어 입력시 검색어가 상품명에 포함된 상품 리스트 뽑아오는 메소드
	public List<ProductDTO> searchProduct(Map<String, String> paraMap) throws SQLException;

	// 추천 상품 리스트 출력 메소드
	public List<ProductDTO> RandomProducts() throws SQLException;
	
	// 햄버거 메뉴 카테고리 클릭시 해당 카테고리 상품페이지로 이동 메소드
	public List<CategoryDTO> HamburgerCategory() throws SQLException;
	
	// 카테고리 선택시 상품의 총 개수를 구하는 메소드
	public int selectCountProductByCategory(Map<String, String> paraMap) throws SQLException;

	// 일주일 재고가 빈 상품 리스트
	List<Map<String, String>> selectWeekEmptyInventoryList() throws SQLException;

	// 검색어 입력시 검색된 상품의 총 개수를 구하는 메소드
	public int searchCountProductByCategory(Map<String, String> paraMap) throws SQLException;


}
