package common.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * 메인페이지 요청을 처리하는 컨트롤러
 */
public class MainController extends AbstractController{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/main.jsp");
	}
	
}
