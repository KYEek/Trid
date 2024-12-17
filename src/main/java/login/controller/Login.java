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

public class Login extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod(); // HTTP Method
		
		// POST 방식이라면
		if("POST".equalsIgnoreCase(method)) { 
			String member_email = request.getParameter("email");
			String member_password = request.getParameter("pwd");
			
			Map<String , String> paraMap = new HashMap<>();
			paraMap.put("member_email", member_email);
			paraMap.put("member_password", member_password);
			
			MemberDTO loginuser = mdao.login(paraMap);
			
			if(loginuser == null) {
			//	System.out.println("로그인 실패");
				//super.handleMessage(request, Constants.INVALID_EMAIL_OR_PASSWORD, Constants.MEMBER_LOGIN_URL);
				return;
			}
			else {
			//	System.out.println("로그인성공!");
				
				HttpSession session = request.getSession();
				
				session.setAttribute("loginuser", loginuser);
				
				super.setRedirect(true);
				super.setViewPage("/main.trd");
				return;
			}
		}
		else {// get 방식이라면
			super.setRedirect(false);
			super.setViewPage(Constants.MEMBER_LOGIN_PAGE);
		}

	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception------------------------

}
