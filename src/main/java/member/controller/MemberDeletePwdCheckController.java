package member.controller;

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
  	비밀번호 인증으로 계정 삭제 재확인을 요청하는 컨트롤러
*/

public class MemberDeletePwdCheckController extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
		
		if(!super.checkLogin(request)) {
            super.handleMessage(request, "로그인이 필요한 페이지입니다.", Constants.MEMBER_LOGIN_URL);
            return;
         }
		
		String method = request.getMethod(); // HTTP Method
		
		if("POST".equalsIgnoreCase(method)) { // POST 방식이라면
			
			String member_password = request.getParameter("pwd");
			
			Map<String , String> paraMap = new HashMap<>();
			paraMap.put("member_password", member_password);
			paraMap.put("pk_member_no", String.valueOf(loginuser.getPk_member_no()));
			
			int result = mdao.memberDelete(paraMap);
			
			if(result == 1) {
				
				session.invalidate(); // 로그아웃 처리하기
				
				String message = "계정 탈퇴가 완료되었습니다."; 
				String loc = "/main.trd";
				super.handleMessage(request, message, loc);
				
				return;
			}
			else {
				String message = "비밀번호가 올바르지 않습니다.";
				String loc = Constants.CHECKDELETE_URL;
				super.handleMessage(request, message, loc);
				
				return; 
			}
		}
		else {// GET 방식이라면
			
			super.setRedirect(false);
			super.setViewPage(Constants.CHECKDELETE_PAGE);
		}
		


	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception-----------------

}
