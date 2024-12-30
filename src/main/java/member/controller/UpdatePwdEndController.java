package member.controller;

import java.sql.SQLException;

import org.json.JSONObject;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

/*
  	비밀번호 변경을 처리해주는 페이지
 */
public class UpdatePwdEndController extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple(); // db와 연결해주는 용도
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
//		System.out.println("회원비밀번호 업데이트 페이지 컨트롤러 실행됨");
		
		String method = request.getMethod(); // "GET" 또는 "POST" 
		
		if("POST".equalsIgnoreCase(method)) {// 비밀번호 변경 페이지이므로 post 방식
		// **** POST 방식으로 넘어온 것이라면 **** //
			
			String newPwd = request.getParameter("newPwd");
			String pkNum = request.getParameter("pkNum");
			
			
			MemberDTO member = new MemberDTO();
			member.setMember_password(newPwd);
			member.setPk_member_no(Integer.parseInt(pkNum));
			try {
		         int n = mdao.updatePwdEnd(member);
		         
		         if(n==1) {
		           
		           // !!!! session 에 저장된 loginuser 를 변경된 사용자의 정보값으로 변경해주어야 한다. !!!!
	               HttpSession session = request.getSession();
	               MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
	               
	               loginuser.setMember_password(newPwd);;
	               
	        	   String message = "회원비밀번호 수정 성공!!";
	        	   String loc = "/Trid/main.trd"; //메인 페이지로 돌아감.
	             
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
			
		}// end of if("POST".equalsIgnoreCase(method))-----------
		
		else {
			 super.setRedirect(false);
			 super.setViewPage(request.getContextPath()+"/error.trd");
		}
		
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception----------

}
