package member.controller;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class MemberDeactivate extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		 String method = request.getMethod();
		  
		 // 로그인 시도한 사람을 알아오기 위한 세션
		 HttpSession session = request.getSession();
		 
		 if("POST".equalsIgnoreCase(method)) {
			 
			 // 로그인 시도한 멤버의 pk 번호 알아오기
			 String memberNo = String.valueOf(session.getAttribute("memberNo"));
			 
			 // === 요청한 클라이언트의 IP 주소를 알아온다 === //
			 String clientip = request.getRemoteAddr();
			 
			 // 휴면 상태를 해제해주는 메소드
			 int result = mdao.UpdateMemberIdle(memberNo, clientip);
			 
			 if(result != 1) {
				 // 휴면 해제를 실패 한 경우
				 // 다시 휴면 해제 패이지로 이동
				 super.handleMessage(request, "휴면 해제를 실패했습니다", Constants.MEMBER_DEACTIVATE_URL);
				 return;	// 종료
			 }
			 
			 // 휴면 해제를 성공한 경우
			 // 로그인 페이지로 이동 (재로그인 해야됨)
			 super.handleMessage(request, "휴면해제가 완료되었습니다. 로그인을 해주세요", Constants.MEMBER_LOGIN_URL);
			 // 세션 초기화
			 session.invalidate();
		 
		 }// end of if("POST".equalsIgnoreCase(method)) -------------------------
		 else {
			// 정상적으로 로그인한 경우 또는 URL을 통해 비정상적으로 접근한 경우
			if ( super.checkLogin(request) || session.getAttribute("memberNo") == null ) {
				// URL을 통한 접근이므로 메인페이지로 이동
				super.handleMessage(request, Constants.INVALID_REQUEST, Constants.MAIN_URL);
				return; // 종료
			}
			// 휴면 해제 페이지로 이동
			else {
				super.setRedirect(false);
				super.setViewPage(Constants.MEMBER_DEACTIVATE_PAGE);
			}

		 }
		 


	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {} ---------

}
