package admin.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.domain.ProductDTO;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

public class ProductManageController extends AbstractController{
	
	private final ProductDAO productDAO = new ProductDAO_imple(); // 상품 DAO 초기화

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod(); // HTTP 메소드
		
		// POST인 경우 상품 관리 페이지 URL로 리다이렉트
		if("POST".equalsIgnoreCase(method)) {
			super.setRedirect(true);
			super.setViewPage(Constants.ADMIN_PRODUCT_MANAGE_URL);
		}
		else {
			// 유효한 관리자 접근 검증
			if(!super.checkAdminLogin(request)) {
				return;
			}
			
			// TODO 예외처리
			// 상품 리스트 가져오기
			try {
				Map<String, Object> map = productDAO.selectProductList(1);
				List<ProductDTO> productList = (List<ProductDTO>) map.get("productList");
	
				request.setAttribute("productList", productList);
				
				super.setRedirect(false);
				super.setViewPage(Constants.ADMIN_PRODUCT_MANAGE_PAGE);
				
			} catch (SQLException e) {
				e.printStackTrace();
				super.setRedirect(true);
				super.setViewPage(Constants.ERROR_URL);
			}
			
		}

	}

}
