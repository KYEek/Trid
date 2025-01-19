package admin.controller.order;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Constants;
import common.component.PagingComponent;
import common.controller.AbstractController;
import common.domain.PagingDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import orders.domain.OrderDTO;
import orders.model.OrderDAO;
import orders.model.OrderDAO_imple;

/*
 * 관리자 주문 관리 컨트롤러
 */
public class OrderManageController extends AbstractController {
	
	private final OrderDAO orderDAO;
	
	public OrderManageController() {
		this.orderDAO = new OrderDAO_imple(); // OrderDAO 초기화
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod(); // HTTP 메소드

		// 유효한 관리자 접근 검증
		if(!super.checkAdminLogin(request)) {
			return;
		}
		
		// POST 요청인 경우 동일 URL에 GET 요청으로 리다이렉트
		if("POST".equalsIgnoreCase(method)) {
			super.setRedirect(true);
			super.setViewPage(Constants.ADMIN_ORDER_MANAGE_URL);
		}
		// GET 요청인 경우
		else {
			int curPage = 1; // 현재 페이지 초기화

			// curPage가 정수인지 예외처리
			try {
				curPage = Integer.parseInt(request.getParameter("curPage"));
			} catch (NumberFormatException e) {}
			
			Map<String, Object> paraMap = createParaMap(request);  // URL 파라미터에서 받은 값을 Map에 저장
			
			try {
				int totalRowCount = orderDAO.selectTotalRowCountByAdmin(paraMap); // 전체 행 개수 조회
				
				PagingDTO pagingDTO = PagingComponent.createPaging(curPage, totalRowCount); // 페이징 관련 정보가 저장된 DTO 생성
				
				paraMap.put("pagingDTO", pagingDTO);
		
				List<OrderDTO> orderList = orderDAO.selectOrderListByAdmin(paraMap); // 주문 내역 리스트 조회
				
				request.setAttribute("orderList", orderList); // 주문 리스트 정보
				request.setAttribute("pagingDTO", pagingDTO); // 페이징 정보
				request.setAttribute("paraMap", paraMap); // 이전 요청의 필터링 및 카테고리 정보 map

				super.setRedirect(false);
				super.setViewPage(Constants.ADMIN_ORDER_MANAGE_JSP);

			} catch (SQLException e) {
				e.printStackTrace();
				super.handleServerError();
			}
			
		}
		
	}
	
	/*
	 * URL 파라미터에서 받은 값을 Map에 저장하여 반환하는 메소드
	 */
	private Map<String, Object> createParaMap(HttpServletRequest request) {
		Map<String, Object> paraMap = new HashMap<>();
		
		paraMap.put("searchType", request.getParameter("searchType")); // 검색 타입
		paraMap.put("searchWord", request.getParameter("searchWord")); // 검색어
		paraMap.put("sortCategory", request.getParameter("sortCategory")); // 정렬 타입 0:최신순, 1:오래된순
		paraMap.put("orderStatus", request.getParameter("orderStatus")); // 주문 상태 번호 0:결제완료 1:상품준비 2:배송준비 3:배송완료
		paraMap.put("dateMin", request.getParameter("dateMin")); // 최소 일자
		paraMap.put("dateMax", request.getParameter("dateMax")); // 최대 일자
		
		return paraMap;
	}

}
