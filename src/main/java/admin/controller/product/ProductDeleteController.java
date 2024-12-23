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

	private final ProductDAO productDAO = new ProductDAO_imple(); // 상품 DAO 초기화
	
	private final AdminDAO adminDAO = new AdminDAO_imple(); // 관리자 DAO 초기화
 
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod(); // HTTP 메소드
		
		// 유효한 관리자 아이디인지 확인
		HttpSession session = request.getSession();
		String adminId = (String)session.getAttribute("adminId");
		
		// 관리자 접근 검증
		if(!super.checkAdminLogin(request)) {
			return;
		}
		
		// POST 요청인 경우
		if ("POST".equalsIgnoreCase(method)) {
			String productNo = request.getParameter("productNo"); // 상품 일련번호

			try {
				
				// adminId를 통해 관리자를 조회하여 계정이 존재하면 1이 반횐된다.
				boolean isAdmin = adminDAO.selectCountAdmin(adminId) == 1 ? true : false; 
				
				// 세션의 관리자아이디 값이 유효하지 않으면 자동 로그아웃 후 로그인 화면으로 이동
				if(!isAdmin) {
					session.invalidate(); // 세션 무효화
					super.handleMessage(request, Constants.ACCESS_DENIED, Constants.ADMIN_LOGIN_URL);
					return;
				}
				
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
				super.setViewPage(Constants.ERROR_URL);
				super.setRedirect(true);
			}

		}
		// POST 요청이 아닌 경우
		else {
			super.handleMessage(request, Constants.INVALID_REQUEST, Constants.ADMIN_PRODUCT_MANAGE_URL);
		}
		
	}
	
}
