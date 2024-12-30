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

public class OrderDetailController extends AbstractController {
	
	private final OrderDAO orderDAO = new OrderDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		
		if(!super.checkAdminLogin(request)) {
			return;
		}
		
		if("POST".equalsIgnoreCase(method)) {
			
			String orderNo = request.getParameter("orderNo");
			String orderStatus = request.getParameter("orderStatus");
			
			String message = "";
			
			try {
				
				int result = orderDAO.updateOrderStatus(orderNo, orderStatus);
				
				message = result == 1 ? "success" : "failed";
				
			} catch (SQLException e ) {
				e.printStackTrace();
				message = "failed";
			}
			
			super.setJsonResponse(true);

		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    
		    String jsonData = "{\"message\":\"" + message + "\"}";
		    
		    PrintWriter out = response.getWriter();
		    out.print(jsonData);
		    out.flush();
			
		}
		else {
			
			String orderNo = request.getParameter("orderNo");
			
			try {
				OrderDTO orderDTO = orderDAO.selectOrderByAdmin(orderNo);
				
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
				super.setRedirect(true);
				super.setViewPage(Constants.ERROR_URL);
			}
			
		}
		
	}

}
