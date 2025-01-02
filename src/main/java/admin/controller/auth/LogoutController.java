package admin.controller.auth;

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
		HttpSession session = request.getSession(); // 현재 HTTP 세션

		// 세션 해제, 내부 속성값 삭제
		session.invalidate();

		super.setRedirect(true);
		super.setViewPage(Constants.ADMIN_LOGIN_URL); // 관리자 로그인 페이지로 이동

	}

}
