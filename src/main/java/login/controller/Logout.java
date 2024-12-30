package login.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Logout extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
//		System.out.println("로그아웃 클릭됨");
		// 로그아웃 처리하기
		HttpSession session = request.getSession();
		
		session.invalidate();
		
		super.setRedirect(true);
		super.setViewPage("/main.trd");

	}

}
