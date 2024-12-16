package admin.controller;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/*
 * 관리자 로그아웃 컨트롤러
 */
public class LogoutController extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod(); // HTTP 메소드
		
		// POST 요청일 경우
		if("POST".equalsIgnoreCase(method)) {
			HttpSession session = request.getSession();
			
			// 세션 해제, 내부 속성값 삭제
			session.invalidate();
			
			super.setRedirect(true);
			super.setViewPage(Constants.ADMIN_LOGIN_URL); // 관리자 로그인 페이지로 이동
		}
		// GET 요청일 경우
		else {
			// 잘못된 요청이므로 로그아웃되지 않고 관리자 메인 페이지로 이동
			super.handleMessage(request, Constants.INVALID_REQUEST, Constants.ADMIN_MAIN_URL);
		}
		
	}

}
