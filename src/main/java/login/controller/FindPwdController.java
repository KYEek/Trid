package login.controller;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FindPwdController extends AbstractController {
	
	// 비밀번호 찾기 페이지로 이동
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

	//	System.out.println("회원 비밀번호 찾기 컨트롤러 실행됨");
		
		super.setRedirect(false);
		super.setViewPage(Constants.FIND_PWD_PAGE); 
		
	}

}
