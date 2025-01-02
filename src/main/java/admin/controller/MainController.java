package admin.controller;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * 관리자 메인 페이지 컨트롤러
 */
public class MainController extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod(); // HTTP 메소드

		// 유효한 관리자 접속인지 확인
		if (!super.checkAdminLogin(request)) {
			return;
		}

		// GET 요청인 경우 관리자 메인 페이지 연결
		if ("POST".equalsIgnoreCase(method)) {
			super.handleMessage(request, Constants.ACCESS_DENIED, Constants.ADMIN_MAIN_URL);
		}
		// 다른 요청일 경우 해당 URL의 GET 요청으로 다시 연결
		else {
			super.setRedirect(false);
			super.setViewPage(Constants.ADMIN_MAIN_PAGE);	
		}

	}

}
