package payment.controller;

import java.io.BufferedReader;

import org.json.JSONArray;
import org.json.JSONObject;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;

public class PaymentComple extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("loginuser");
		JSONArray basketList = null;

		System.out.println("完了");
		
		//로그인 되어 있지 않으면 메인으로 돌려보내기
		if(!super.checkLogin(request)) {
			System.out.println("로그인 안되어짐");
			super.setRedirect(true);
			super.setViewPage("/main.trd");
			return;
		}
		
		//요청이 포스트 방식일 때(결제가 완료 됐을 때)
		if("post".equalsIgnoreCase(method)) {
			String result = null;

			StringBuilder jsonData = new StringBuilder();
			String line = null;
			try (BufferedReader reader = request.getReader()){
				while((line = reader.readLine()) != null) {
					jsonData.append(line);
				}
			}
			System.out.println(jsonData.toString());
			JSONObject jsonObj = new JSONObject(jsonData.toString());			
			System.out.println(jsonObj.getString("paymentId"));
			
			request.setAttribute("result", result);
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/basket/jsonview.jsp");
			
		}

	}

}
