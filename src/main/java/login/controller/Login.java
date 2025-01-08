package login.controller;

import java.util.HashMap;
import java.util.Map;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import member.model.*;

/*
  	사용자 로그인 컨트롤러
 */
public class Login extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod(); // HTTP Method
		
		if("POST".equalsIgnoreCase(method)) { // POST 방식이라면
			String member_email = request.getParameter("email");
			String member_password = request.getParameter("pwd");
			String clientIp = request.getRemoteAddr();
			
			Map<String , String> paraMap = new HashMap<>();
			paraMap.put("member_email", member_email);
			paraMap.put("member_password", member_password);
			paraMap.put("clientIp", clientIp);
			
			MemberDTO loginuser = mdao.login(paraMap);
			
			HttpSession session = request.getSession();
			
			if(loginuser == null) {
				
				 String message = "사용자 이메일과 비밀번호 조합이 Trid.trd 계정과 일치하지 않습니다.";
	        	 String loc = "/Trid/main.trd"; // 현재페이지에 머뭄.
	             
	             request.setAttribute("message", message);
	             request.setAttribute("loc", loc);
	             
	             super.setRedirect(false); 
	             super.setViewPage(Constants.MESSAGE_PAGE);
	             //	System.out.println("로그인 실패");
	             //super.handleMessage(request, Constants.INVALID_EMAIL_OR_PASSWORD, Constants.MEMBER_LOGIN_URL);
	             return;
			}
			else {
			//	System.out.println("로그인성공!");
				
				// 사용자 계정이 휴면처리된 경우
				if(loginuser.getMember_idle() == 0) {
					session.setAttribute("memberNo", loginuser.getPk_member_no());
					session.setAttribute("memberMobile", loginuser.getMember_mobile());
					
					System.out.println(loginuser.getPk_member_no());
					System.out.println(loginuser.getMember_mobile());
					
					super.handleMessage(request, "휴면처리된 계정입니다. 휴면해제를 요청하세요", Constants.MEMBER_DEACTIVATE_URL);
					return;
				}
				
				
				
				session.setAttribute("loginuser", loginuser);
				
				String message = "로그인 성공!!";
	        	String loc = "/Trid/main.trd"; // 현재페이지에 머뭄.
	             
	            request.setAttribute("message", message);
	            request.setAttribute("loc", loc);
	             
	            super.setRedirect(false); 
	            super.setViewPage(Constants.MESSAGE_PAGE);
	             
				return;
			}
		}
		else {// GET 방식이라면
			super.setRedirect(false);
			super.setViewPage(Constants.MEMBER_LOGIN_PAGE);
		}

	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception------------------------

}
