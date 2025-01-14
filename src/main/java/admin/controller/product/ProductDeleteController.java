package admin.controller.product;

import java.sql.SQLException;

import admin.model.AdminDAO;
import admin.model.AdminDAO_imple;
import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

/*
 * 관리자 상품 삭제 컨트롤러
 */
public class ProductDeleteController extends AbstractController {

	private final ProductDAO productDAO;
	
	public ProductDeleteController() {
		this.productDAO = new ProductDAO_imple(); // 상품 DAO 초기화
	}
 
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod(); // HTTP 메소드
		
		// 관리자 접근 검증
		if(!super.checkAdminLogin(request)) {
			return;
		}
		
		// POST 요청인 경우
		if ("POST".equalsIgnoreCase(method)) {
			String productNo = request.getParameter("productNo"); // 상품 일련번호

			try {
				// 상품 삭제를 실행하며 정상적으로 삭제되면 1이 반횐된다.
				int result = productDAO.deleteProduct(productNo);
				
				// 삭제 실패 시 뒤로가기 처리
				if(result != 1) {
					request.setAttribute("message", "상품 삭제를 실패하였습니다."); // 사용자 알림 메시지
					request.setAttribute("loc", Constants.HISTORY_BACK); // 사용자 알림 후 이동되는 url
					
					setRedirect(false);
					setViewPage(Constants.MESSAGE_PAGE);
					return;
				}
				
				// 삭제 성공 시 관리자 상품 관리 페이지로 이동
				super.handleMessage(request, "상품 삭제를 성공하였습니다.", Constants.ADMIN_PRODUCT_MANAGE_URL);
				
			} catch (SQLException e) {
				e.printStackTrace();
				super.handleServerError();
			}

		}
		// POST 요청이 아닌 경우 상품 관리 페이지로 리다이렉트
		else {
			super.handleMessage(request, Constants.INVALID_REQUEST, Constants.ADMIN_PRODUCT_MANAGE_URL);
		}
		
	}
	
}
