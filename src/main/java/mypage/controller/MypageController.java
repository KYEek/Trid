package mypage.controller;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MypageController extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
//		System.out.println("마이페이지 컨트롤러 실행됨");
		
		super.setRedirect(false);
		super.setViewPage(Constants.MY_PAGE);
		

	}

}
