package member.controller;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class MemberDeactivate extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		 String method = request.getMethod();
		  
		 if("POST".equalsIgnoreCase(method)) {
		  
			 // 로그인한 회원의 멤버 PK를 맵에 넣어주기 paraMap.put("pk_member_no",
		 // String.valueOf(loginuser.getPk_member_no() );
			 
			 String memberNo = (String) request.getAttribute("memberNo");
			 // === 요청한 클라이언트의 IP 주소를 알아온다 === //
			 String clientip = request.getRemoteAddr();
			 
			 int result = mdao.UpdateMemberIdle(memberNo, clientip);
			 
			 if(result != 1) {
				 super.handleMessage(request, "휴면 해제를 실패했습니다", Constants.MEMBER_DEACTIVATE_URL);
				 return;
			 }
			 super.handleMessage(request, "휴면해제가 완료되었습니다. 로그인을 해주세요", Constants.MEMBER_LOGIN_URL);
		 
		 // 휴면 상태를 해제해주는 메소드 int n = 0; // input 태그의 밸류 값과 문자로 보낸 인증번호의 값이 일치하면 실행 //
		 // if( request.getParameter()로 가져온 입력값 == 문자로 보낸 인증번호의 값 ) // 메인페이지로 보내기 try { n
		 // = mdao.UpdateMemberIdle(paraMap); } catch (SQLException e) {
		 // e.printStackTrace(); } request.setAttribute("n", n);
		 
		 // else { 다를 경우에는 } // super.setRedirect(false); //
		 // super.setViewPage(Constants.MEMBER_DEACTIVATE_PAGE);
		 
		 }// end of if("POST".equalsIgnoreCase(method)) ------------------------- else
		 else {
			 super.setRedirect(false);
			 super.setViewPage(Constants.MEMBER_DEACTIVATE_PAGE);
		 }
		 


	}

}
