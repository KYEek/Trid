package payment.controller;

import org.json.JSONArray;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;

public class PayingPage extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("loginuser");
		
		//결제 제목을 위한 멤버 아이디 설정
		request.setAttribute("member_name", member.getMember_name());
		
		//로그인 되어 있지 않으면 메인으로 돌려보내기
		if(!super.checkLogin(request)) {
			System.out.println("로그인 안되어짐");
			super.setRedirect(true);
			super.setViewPage("/main.trd");
			return;
		}
		
		//포스트 방식으로 진입한 경우
		if("post".equalsIgnoreCase(method)) {
			super.setRedirect(false);
			super.setViewPage(Constants.PAYING_PAGE);
		}
		//get방식으로 진입한 경우(비정상 접근)
		else {
			super.setRedirect(true);
			super.setViewPage("/main.trd");
		}
	}

}
