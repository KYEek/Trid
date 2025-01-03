package member.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class MemberDeactivate extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		 String method = request.getMethod();
		  
		 if("POST".equalsIgnoreCase(method)) {
		  
		 HttpSession session = request.getSession();
		  
		 // 세션에서 로그인한 유저의 정보 받아오기 MemberDTO loginuser = (MemberDTO)
		 session.getAttribute("loginuser");
		  
		 Map<String, String> paraMap = new HashMap<>();
		 
		 // 로그인한 회원의 멤버 PK를 맵에 넣어주기 paraMap.put("pk_member_no",
		 // String.valueOf(loginuser.getPk_member_no() );
		 
		 // 휴면 상태를 해제해주는 메소드 int n = 0; // input 태그의 밸류 값과 문자로 보낸 인증번호의 값이 일치하면 실행 //
		 // if( request.getParameter()로 가져온 입력값 == 문자로 보낸 인증번호의 값 ) // 메인페이지로 보내기 try { n
		 // = mdao.UpdateMemberIdle(paraMap); } catch (SQLException e) {
		 // e.printStackTrace(); } request.setAttribute("n", n);
		 
		 // else { 다를 경우에는 } // super.setRedirect(false); //
		 // super.setViewPage(Constants.MEMBER_DEACTIVATE_PAGE);
		 
		 }// end of if("POST".equalsIgnoreCase(method)) ------------------------- else
		 { super.handleMessage(request, "잘못된 접근입니다.", Constants.MAIN_PAGE); return; }
		 

		//super.setViewPage(Constants.MEMBER_DEACTIVATE_PAGE);

	}

}
