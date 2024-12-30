package member.controller;

import java.sql.SQLException;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberDTO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

/*
 * 회원가입 요청을 처리하는 컨트롤러
 */

public class RegisterController extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // "GET" 또는 "POST"
		
		if("POST".equalsIgnoreCase(method)) {// POST 방식이라면
			
			String member_email = request.getParameter("email");
			String member_password = request.getParameter("pwd");
			String member_name = request.getParameter("name");
			String member_mobile = request.getParameter("mobile");
			String member_gender = request.getParameter("member_gender");
			String member_birthday = request.getParameter("member_birthday");
			String member_registerday = request.getParameter("member_registerday");
			String member_pwdchangeday = request.getParameter("member_pwdchangeday");
			String member_updateday = request.getParameter("member_updateday");
			
			
			MemberDTO member = new MemberDTO();
			member.setMember_email(member_email);
			member.setMember_password(member_password);
			member.setMember_name(member_name);
			member.setMember_mobile(member_mobile);
//			member.setMember_gender(0);
//			member.setMember_birthday(member_birthday);
//			member.setMember_registerday(member_registerday);
//			member.setMember_pwdchangeday(member_pwdchangeday);
//			member.setMember_updateday(member_updateday);
			
			// ==== 회원가입이 성공되어지면 "회원가입 성공" 이라는 alert 를 띄우고 시작페이지로 이동한다. === // 
			String message = "";
			String loc = "";
			
			try {
				int n = mdao.registerMember(member);
				
				if(n == 1) {
				
					message = "회원가입이 완료되었습니다";
					loc = request.getContextPath()+"/main.trd"; // 시작페이지로 이동한다. 
				}
			} catch(SQLException e) {
				e.printStackTrace();
                
				message = "회원가입을 재시도 하십시오.";
				loc = "javascript:history.back()";  // 자바스크립트를 이용한 이전페이지로 이동하는 것. 
			}
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/message.jsp");
		}
			
		
		else {// GET 방식이라면
			
			super.setRedirect(false);
			super.setViewPage(Constants.REGISTER_PAGE);
			
		}
		
	}

}
