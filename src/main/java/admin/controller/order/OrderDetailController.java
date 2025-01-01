package admin.controller.order;

import java.io.PrintWriter;
import java.sql.SQLException;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import orders.domain.OrderDTO;
import orders.model.OrderDAO;
import orders.model.OrderDAO_imple;

/*
 * 관리자 주문 상세 및 주문 상태 변경 컨트롤러
 */
public class OrderDetailController extends AbstractController {
	
	private final OrderDAO orderDAO = new OrderDAO_imple(); // OrderDAO 초기화

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod(); // HTTP 메소드
		
		// 관리자 인증 확인
		if(!super.checkAdminLogin(request)) {
			return;
		}
		
		// POST 요청인 경우 주문 상태 변경
		if("POST".equalsIgnoreCase(method)) {
			String orderNo = request.getParameter("orderNo"); // 주문 일련번호
			String orderStatus = request.getParameter("orderStatus"); // 주문 상태
			
			String message = "";
			
			try {
				int result = orderDAO.updateOrderStatusByAdmin(orderNo, orderStatus); // 주문 상태 변경
				message = result == 1 ? "success" : "failed"; 
			} catch (SQLException e ) {
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
		// GET 요청인 경우 주문 상세 조회
		else {
			String orderNo = request.getParameter("orderNo"); // 주문 일련번호
			
			try {
				OrderDTO orderDTO = orderDAO.selectOrderByAdminByAdmin(orderNo); // 주문 상세 조회
				
				// OrderDTO 확인
				if(orderDTO == null) {
					handleMessage(request, "요청하신 주문을 찾을 수 없습니다.", Constants.ADMIN_ORDER_MANAGE_URL);
					return;
				}
				
				request.setAttribute("orderDTO", orderDTO);
				
				super.setRedirect(false);
				super.setViewPage(Constants.ADMIN_ORDER_DETAIL_JSP);

			}
			catch(SQLException e) {
				e.printStackTrace();
				super.handleServerError();
			}
			
		}
		
	}

}
