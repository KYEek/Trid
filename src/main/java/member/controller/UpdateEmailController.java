package member.controller;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateEmailController extends AbstractController {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		System.out.println("회원이메일 수정 페이지 컨트롤러 실행됨");
		
   	 	super.setRedirect(false);
		super.setViewPage(Constants.MEMBER_UPDATE_EMAIL_PAGE);
		// /WEB-INF/member/updateEmail.jsp 페이지를 보여주는 용도
		
	}

}
