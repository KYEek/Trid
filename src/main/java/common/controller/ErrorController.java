package common.controller;

import common.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * 예외 처리 및 에러 페이지 컨트롤러 
 */
public class ErrorController extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.setRedirect(false);
		super.setViewPage(Constants.ERROR_PAGE);
	}

}
