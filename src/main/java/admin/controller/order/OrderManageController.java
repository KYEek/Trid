package admin.controller.order;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Constants;
import common.controller.AbstractController;
import common.domain.PagingDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import orders.domain.OrderDTO;
import orders.model.OrderDAO;
import orders.model.OrderDAO_imple;

public class OrderManageController extends AbstractController {
	
	OrderDAO orderDAO = new OrderDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		
		if(!super.checkAdminLogin(request)) {
			return;
		}
		
		if("POST".equalsIgnoreCase(method)) {
			handleMessage(request, Constants.INVALID_REQUEST, Constants.ADMIN_ORDER_MANAGE_URL);
		}
		else {
			int curPage = 1; // 현재 페이지 초기화

			// curPage가 정수인지 예외처리
			try {
				curPage = Integer.parseInt(request.getParameter("curPage"));
			} catch (NumberFormatException e) {
				System.out.println("curPage is not Integer");
				curPage = 1;
			}
			
			try {
				
				PagingDTO pagingDTO = new PagingDTO(); // PagingDTO 초기화
				
				Map<String, Object> paraMap = new HashMap<>();
				
				int totalRowCount = orderDAO.selectTotalRowCount(paraMap); // 전체 행 개수 조회
				
				// curPage 유효성 검사
				if(curPage > totalRowCount || curPage < 1 ) {
					curPage = 1;
				}
				
				pagingDTO.setRowSizePerPage(5);
				pagingDTO.setPageSize(5);
				pagingDTO.setCurPage(curPage);
				pagingDTO.setTotalRowCount(totalRowCount);
				pagingDTO.pageSetting(); // 페이징 시 필요한 나머지 정보 계산
				
				paraMap.put("pagingDTO", pagingDTO);
		
				List<OrderDTO> orderList = orderDAO.selectOrderList(paraMap);
				
				request.setAttribute("orderList", orderList);
				request.setAttribute("pagingDTO", pagingDTO);

				super.setRedirect(false);
				super.setViewPage(Constants.ADMIN_ORDER_MANAGE_JSP);

			} catch (SQLException e) {
				e.printStackTrace();
				super.setRedirect(true);
				super.setViewPage(Constants.ERROR_URL);
			}
			
		}
		
	}

}
