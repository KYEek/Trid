package payment.controller;

import org.json.JSONArray;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;

public class PaymentSummary extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("loginuser");
		JSONArray basketList = null;
		
		
		//로그인 되어 있지 않으면 메인으로 돌려보내기
		if(!super.checkLogin(request)) {
			System.out.println("로그인 안되어짐");
			super.setRedirect(true);
			super.setViewPage("/main.trd");
			return;
		}
		

		super.setRedirect(false);
		super.setViewPage(Constants.PAYMENT_SUMMARY);

	}

}
