package member.controller;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;

/*
 	회원 상세페이지 이동 컨트롤러
*/

public class MemberDetailController extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
		
		if(loginuser != null) {// 사용자가 로그인 되어있을 경우
			super.setRedirect(false);
			super.setViewPage(Constants.MEMBER_Detail_PAGE);
		}
		else { // 사용자가 로그인 하지 않은 경우
			super.handleMessage(request, "로그인이 필요한 페이지 입니다.",  Constants.MEMBER_LOGIN_URL);
		}
		
		

	}

}
