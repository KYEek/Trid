package product.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import common.domain.PageDTO;
import product.domain.ProductDTO;

public interface ProductDAO {

	// 상품 리스트 조회
	 Map<String, Object> selectProductList(int currentPage) throws SQLException;

	// 관리자 상품 상세 조회
	ProductDTO selectProduct(String productDetailNo) throws SQLException;

}
