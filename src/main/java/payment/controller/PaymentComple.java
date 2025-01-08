package payment.controller;

import java.io.BufferedReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import payment.model.Payment_DAO;
import payment.model.Payment_DAO_imple;

public class PaymentComple extends AbstractController {

	Payment_DAO pdao = new Payment_DAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("loginuser");
		JSONArray basketList = null;

		// 로그인 되어 있지 않으면 메인으로 돌려보내기
		if (!super.checkLogin(request)) {
			System.out.println("로그인 안되어짐");
			super.setRedirect(true);
			super.setViewPage("/main.trd");
			return;
		}
		try {
			// 요청이 포스트 방식일 때(결제가 완료 됐을 때)
			if ("post".equalsIgnoreCase(method)) {
				String result = null;

				StringBuilder jsonData = new StringBuilder();
				String line = null;
				try (BufferedReader reader = request.getReader()) {
					while ((line = reader.readLine()) != null) {
						jsonData.append(line);
					}
				}
				// 나온 데이터들을 스트링으로 변환
				String jsonStr = jsonData.toString();
				System.out.println(jsonStr);

				// 들어온 값을 JSON객체로 변경
				JSONObject json = new JSONObject(jsonStr);
				System.out.println(json);

				JSONArray orderDetailArr = new JSONArray(json.getString("orderDetailArr"));

				// 주문 상세 배열을 저장
//			JSONArray productDetailArr = new JSONArray(json.getString("orderDetailArr"));
//			System.out.println(productDetailArr.get(0));
				// jsonview로 보낼 데이터를 설정
				request.setAttribute("result", "성공");

				//////////////////////////////////////////

				// 즉시결제인지 파악하기 위한 변수
				String instantPay = json.getString("instantPay");

				Map<String, String> orderData = new HashMap<>();
				Map<String, String> orderDetailData = new HashMap<>();

				// 주문 데이터들을 Map에 저장
				orderDetailData.put("orderDetailArr", orderDetailArr.toString());
				System.out.println(orderDetailData.get("orderDetailArr"));
				// 주소와 가격, 멤버번호를 저장
				orderData.put("selected_address_no", json.getString("selected_address_no"));
				orderData.put("total_price", json.getString("input_total_price"));
				orderData.put("member_No", String.valueOf(member.getPk_member_no()));
				// 결과로 나온 주문번호를 저장할 변수
				int resultOrderNo = 0;

				// 즉시결제일 경우
				if ("true".equalsIgnoreCase(instantPay)) {
					// sql문을 실행해서 주문번호를 불러온다
					resultOrderNo = pdao.instantPay(orderData, orderDetailData);
				} else {
					// sql문을 실행해서 주문번호를 불러온다
					resultOrderNo = pdao.insertOrderDate(orderData, orderDetailData);
				}

				System.out.println(resultOrderNo);
				//sql문 오류가 생겼을 경우(데이터 입력값 이상)
				if(resultOrderNo == -50001)
				{
					
					request.setAttribute("result", "오류");
					System.out.println("오류");
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/basket/jsonview.jsp");
					
				}
				//재고가 없는 값을 결제하는 경우
				else if(resultOrderNo == -50000){
					request.setAttribute("result", "재고없음");
					System.out.println("재고없음");
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/basket/jsonview.jsp");
					
				}
				//주문수량이 재고수량보다 많을 경우
				else if(resultOrderNo >-50000 && resultOrderNo <0) {
					request.setAttribute("result", "과다주문");
					System.out.println("과다주문");
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/basket/jsonview.jsp");
				}
				//정상적인 경우
				else {
					// 주문번호를 전송
					session.setAttribute("resultOrderNo", resultOrderNo);

					/////////////////////////////////////////////

					System.out.println("성공");
					request.setAttribute("result", "성공");
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/basket/jsonview.jsp");
				}

			}
		}
		// sql 오류시 오류페이지로 이동
		catch (SQLException e) {
			e.printStackTrace();

			super.setRedirect(false);
			super.setViewPage(request.getContextPath() + "/error.jsp");
		}

	}

}
