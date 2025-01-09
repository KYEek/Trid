package admin.controller.product;

import java.io.PrintWriter;
import java.sql.SQLException;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

/*
 * 상품 이미지 삭제 컨트롤러
 * 실제 이미지 파일은 삭제하지 않고 데이터베이스 정보만 삭제
 */
public class ImageDeleteController extends AbstractController {
	
	private final ProductDAO productDAO = new ProductDAO_imple(); // ProductDAO 초기화

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod(); // HTTP 메소드
		
		// 관리자 인증 확인
		if(!super.checkAdminLogin(request)) {
			return;
		}
		
		// POST 요청인 경우
		if("POST".equalsIgnoreCase(method)) {
			String imageNo = request.getParameter("imageNo"); // 상품 이미지 일련번호
			String productNo = request.getParameter("productNo"); // 상품 일련번호
			
			String message = ""; // 응답 메시지
			
			try {
				int result = productDAO.deleteProductImage(imageNo, productNo); // 상품 이미지 삭제
				message = result == 1 ? "success" : "failed";
			} catch (SQLException e) { // 예외 발생 시 실패 처리
				e.printStackTrace();
				message = "failed";
			}
			
			super.setJsonResponse(true); // 클라이언트로 단순 응답 처리

		    response.setContentType("application/json"); // JSON 타입으로 MIME 설정
		    response.setCharacterEncoding("UTF-8");
			
		    String jsonData = "{\"message\":\"" + message + "\"}";
		    
		    PrintWriter out = response.getWriter();
		    out.print(jsonData);
		    out.flush();

		}
		// POST 요청이 아닌 경우 상품 관리 페이지로 리다이렉트
		else {
			super.handleMessage(request, Constants.INVALID_REQUEST, Constants.ADMIN_PRODUCT_MANAGE_URL);
		}
		
	}

}
