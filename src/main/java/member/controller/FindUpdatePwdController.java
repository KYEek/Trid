package member.controller;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;

public class FindUpdatePwdController extends AbstractController {
	
/*
 *  비밀번호 찾기중 변경 페이지 이동 컨트롤러	
 */
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		System.out.println("회원비밀번호 찾기 수정 페이지 컨트롤러 실행됨");
		
		String method = request.getMethod(); // "GET" 또는 "POST"
		
		
		if("POST".equalsIgnoreCase(method)) {// 비밀번호 변경 페이지이므로 post 방식
		// **** POST 방식으로 넘어온 것이라면 **** //
			
			String mobile = request.getParameter("mobile"); 
			request.setAttribute("mobile", mobile);
			
		super.setRedirect(false);
		super.setViewPage(Constants.UPDATE_FIND_PWD_PAGE);
		
		}
		else {// "GET" 방식이라면
			 super.handleMessage(request, Constants.INVALID_REQUEST, Constants.MEMBER_UPDATE_PWD_URL);
		}
		
	}

}
