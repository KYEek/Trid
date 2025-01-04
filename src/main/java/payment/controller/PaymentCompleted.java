package payment.controller;

import java.io.BufferedReader;
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
import payment.model.*;

public class PaymentCompleted extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("loginuser");
		Payment_DAO pdao = new Payment_DAO_imple();

		
		//로그인 되어 있지 않으면 메인으로 돌려보내기
		if(!super.checkLogin(request)) {
			System.out.println("로그인 안되어짐");
			super.setRedirect(true);
			super.setViewPage("/main.trd");
			return;
		}
		
		//요청이 포스트 방식일 때(결제가 완료 됐을 때)
		if("post".equalsIgnoreCase(method)) {
			Map<String, String> orderData = new HashMap<>();
			Map<String, String> orderDetailData = new HashMap<>();
			
			//즉시결제인지 파악하기 위한 변수
			String instantPay = request.getParameter("instantPay");

			System.out.println(request.getParameter("orderDetailArr"));
			System.out.println(request.getParameter("selected_address_no"));
			System.out.println(request.getParameter("total_price"));
			
			//주문 데이터들을 Map에 저장
			orderDetailData.put("orderDetailArr", request.getParameter("orderDetailArr"));

			//주소와 가격, 멤버번호를 저장
			orderData.put("selected_address_no", request.getParameter("selected_address_no"));
			orderData.put("total_price", request.getParameter("total_price"));
			orderData.put("member_No", String.valueOf(member.getPk_member_no()));
			//결과로 나온 주문번호를 저장할 변수
			int resultOrderNo = 0;
			
			//즉시결제일 경우
			if("true".equalsIgnoreCase(instantPay)) {
				//sql문을 실행해서 주문번호를 불러온다
				resultOrderNo = pdao.instantPay(orderData, orderDetailData);
			}
			else {
				//sql문을 실행해서 주문번호를 불러온다
				resultOrderNo = pdao.insertOrderDate(orderData, orderDetailData);
			}
			System.out.println(orderData.toString());
			System.out.println(resultOrderNo);
			//주문번호를 전송
			request.setAttribute("orderNo", resultOrderNo);
			
			super.setRedirect(false);
			super.setViewPage(Constants.PAYMENT_COMPLETED);
		}

	}

}
