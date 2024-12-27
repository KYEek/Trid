package product.controller;

import java.sql.SQLException;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.domain.ProductDTO;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

public class DetailController extends AbstractController {

	private final ProductDAO productDAO = new ProductDAO_imple(); // ProductDAO 초기화
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // HTTP method
		
		// GET 요청인 경우
		if("GET".equalsIgnoreCase(method)) {

			// 상품 상세 번호
			String productNo = request.getParameter("productNo");
			
			try {
				ProductDTO productDTO = productDAO.selectProductByMember(productNo);
				
				// productDTO가 존재하지 않는다면?
				if(productDTO == null) {
					super.handleMessage(request, "요청하신 상품이 존재하지 않습니다.", Constants.ADMIN_PRODUCT_MANAGE_URL);	// 나중에 상품 리스트 페이지로 보내기
				}
				
				request.setAttribute("productDTO", productDTO);
				
				super.setRedirect(false);
				super.setViewPage(Constants.PRODUCT_DETAIL_PAGE);
				
			} catch (SQLException e) {
				e.printStackTrace();
				super.setRedirect(true);
				super.setViewPage(Constants.ERROR_URL);
			}
			
		}
		// GET 요청이 아닌 경우 관리자 상품 상세 URL로 이동
		else {
			super.setRedirect(true);
			super.setViewPage(Constants.ADMIN_PRODUCT_DETAIL_URL);
		}

		
		
	}

}
