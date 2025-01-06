package member.controller;

import java.sql.SQLException;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

/*
 	회원 전화번호 수정 컨트롤러(update)
 */

public class UpdateMobileEndController extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple(); 
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if(!super.checkLogin(request)) {
            super.handleMessage(request, "로그인이 필요한 페이지입니다.", Constants.MEMBER_LOGIN_URL);
            return;
         }
		
		String method = request.getMethod(); // "GET" 또는 "POST" 
		
		if("POST".equalsIgnoreCase(method)) {// "POST" 방식이라면
	
			String newMobile = request.getParameter("newMobile");
			String pkNum = request.getParameter("pkNum");
			
			MemberDTO member = new MemberDTO();
			
			member.setMember_mobile(newMobile);
			member.setPk_member_no(Integer.parseInt(pkNum));
			
			try {
		         int n = mdao.updateMobile(member);
		         
		         if(n==1) {
		           
	               HttpSession session = request.getSession();
	               MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
	               
	               loginuser.setMember_mobile(newMobile);
	               
	        	   String message = "회원전화번호 수정 성공!!";
	        	   String loc = "/Trid/member/updateMobile.trd"; 
	             
	               request.setAttribute("message", message);
	               request.setAttribute("loc", loc);
	             
	               super.setRedirect(false); 
	               super.setViewPage(Constants.MESSAGE_PAGE);
		         }
	         } catch(SQLException e) {
	        	 e.printStackTrace();
	        	 super.setRedirect(true);
	        	 super.setViewPage(request.getContextPath()+"/error.trd");
	         }
			
		}
		else { // GET 방식이라면
			super.handleMessage(request, "잘못된 요청방식입니다.", "/member/mypage.trd");
			
		}
	}

}
