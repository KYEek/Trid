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

public class UpdateMobileEndController extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple(); // db와 연결해주는 용도
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod(); // "GET" 또는 "POST" 
		
		if("POST".equalsIgnoreCase(method)) {// 비밀번호와 이메일 정보가 필요하므로 무조건 post 방식
			// **** POST 방식으로 넘어온 것이라면 **** //
			
			String newMobile = request.getParameter("newMobile");// 뷰단 페이지에서 넘겨받은 mobile 값
			String pkNum = request.getParameter("pkNum");
			
			MemberDTO member = new MemberDTO();
			
			member.setMember_mobile(newMobile);
			member.setPk_member_no(Integer.parseInt(pkNum));
			
			try {
		         int n = mdao.updateMobile(member);
		         
		         if(n==1) {
		           
		           // !!!! session 에 저장된 loginuser 를 변경된 사용자의 정보값으로 변경해주어야 한다. !!!!
	               HttpSession session = request.getSession();
	               MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
	               
	               loginuser.setMember_mobile(newMobile);
	               
	        	   String message = "회원전화번호 수정 성공!!";
	        	   String loc = "/Trid/member/updateMobile.trd"; // 현재페이지에 머뭄.
	             
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
		else {
			 super.setRedirect(false);
        	 super.setViewPage(request.getContextPath()+"/error.trd");
        	 
		}
	}

}
