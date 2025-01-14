package admin.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import board.model.BoardDAO;
import board.model.BoardDAO_imple;
import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;
import orders.model.*;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

/*
 * 관리자 메인 페이지 컨트롤러 (통계 차트 컨트롤러)
 */
public class MainController extends AbstractController {

	private final OrderDAO orderDAO;

	private final ProductDAO productDAO;

	private final BoardDAO boardDAO;

	private final MemberDAO memberDAO;
	
	public MainController() {
		this.orderDAO = new OrderDAO_imple(); // OrderDAO 초기화
		this.productDAO = new ProductDAO_imple(); // ProductDAO 초기화
		this.boardDAO = new BoardDAO_imple(); // MemberDAO 초기화
		this.memberDAO = new MemberDAO_imple(); // MemberDAO 초기화
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod(); // HTTP 메소드

		// 유효한 관리자 접속인지 확인
		if (!super.checkAdminLogin(request)) {
			return;
		}

		// GET 요청인 경우 관리자 메인 페이지 연결
		if ("POST".equalsIgnoreCase(method)) {
			super.handleMessage(request, Constants.ACCESS_DENIED, Constants.ADMIN_MAIN_URL);
		}
		// 다른 요청일 경우 해당 URL의 GET 요청으로 다시 연결
		else {
			// keys (paid, preparing, shipping, delivered)
			Map<String, Integer> selectWeekPayment = orderDAO.selectWeekPayment(); // 일주일 주문 내역 개수 (결제완료 수, 상품 준비 수, 배송 중 수, 배송완료 수)

			// keys (productNo, productName, size, updateday)
			List<Map<String, String>> weekEmptyInventoryList = productDAO.selectWeekEmptyInventoryList(); // 일주일 재고가 빈 상품 리스트

			// keys(questionNo, memberName, title, registerday)
			List<Map<String, String>> weekUnansweredQuestionList = boardDAO.selectWeekUnansweredQuestionList(); // 일주일 미답변 질문 리스트

			// keys(day, count)
			List<Map<String, String>> weekLoginUserList = memberDAO.selectWeekLoginUserList(); // 일주일 사용자 접속 수
			
			// 일일 로그인 유저 기록 JSON 응답
			JSONArray jsonWeekLoginUser = new JSONArray();

			for (Map<String, String> map : weekLoginUserList) {
				JSONObject json = new JSONObject();

				json.put("logindate", map.get("logindate"));
				json.put("count", map.get("count"));

				jsonWeekLoginUser.put(json);
			}

			// keys(orderdate, sales)
			List<Map<String, String>> dailySalesList = orderDAO.selectdailySalesList();

			// 일 매출 JSON 응답			
			JSONArray jsonDailySales = new JSONArray();
			
			for (Map<String, String> map : dailySalesList) {
				JSONObject json = new JSONObject();

				json.put("orderdate", map.get("orderdate"));
				json.put("sales", map.get("sales"));

				jsonDailySales.put(json);
			}

			// keys(month, sales)
			List<Map<String, String>> monthlySalesList = orderDAO.selectMonthlySalesList();
			
			// 월 매출 JSON 응답
			JSONArray jsonMonthlySales = new JSONArray();
			
			for (Map<String, String> map : monthlySalesList) {
				JSONObject json = new JSONObject();

				json.put("month", map.get("month"));
				json.put("sales", map.get("sales"));
				
				System.out.println(map.get("month") + map.get("sales"));

				jsonMonthlySales.put(json);
			}
			
			// keys(year, sales)
			List<Map<String, String>> yearSalesList = orderDAO.selectYearSalesList();
			
			// 연 매출 JSON 응답
			JSONArray jsonYearSales = new JSONArray();
			
			for (Map<String, String> map : yearSalesList) {
				JSONObject json = new JSONObject();

				json.put("year", map.get("year"));
				json.put("sales", map.get("sales"));

				jsonYearSales.put(json);
			}

			request.setAttribute("selectWeekPayment", selectWeekPayment);
			request.setAttribute("weekEmptyInventoryList", weekEmptyInventoryList);
			request.setAttribute("weekUnansweredQuestionList", weekUnansweredQuestionList);
			request.setAttribute("jsonWeekLoginUser", jsonWeekLoginUser.toString());
			request.setAttribute("jsonDailySales", jsonDailySales.toString());
			request.setAttribute("jsonMonthlySales", jsonMonthlySales.toString());
			request.setAttribute("jsonYearSales", jsonYearSales.toString());

			super.setRedirect(false);
			super.setViewPage(Constants.ADMIN_MAIN_PAGE);
		}

	}

}
