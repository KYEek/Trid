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

// jsp 파일에 goUpdateEmail()함수를 클릭하면 form 이 UpdateEmailEndController로 보내져 이메일 수정 완료처리 //
public class UpdateEmailEndController extends AbstractController {

	// 현재 이메일 정보 필요, pkNum 필요, 현재 비밀번호 필요, 새로운 이메일 업데이트 필요
	private MemberDAO mdao = new MemberDAO_imple(); // db와 연결해주는 용도
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 내 정보를 수정 하기 위한 전제조건은 먼저 로그인을 해야 하는 것이다.(로그인이 되어있어야함.)//공통 클래스인 부모 클래스에서 만들어준다.
		if(super.checkLogin(request)) {
		
			String method = request.getMethod(); // "GET" 또는 "POST" 
			
			if("POST".equalsIgnoreCase(method)) {// 비밀번호와 이메일 정보가 필요하므로 무조건 post 방식
				// **** POST 방식으로 넘어온 것이라면 **** //
				String pwd = request.getParameter("pwd");
				String newEmail = request.getParameter("newEmail");
				String pkNum = request.getParameter("pkNum");
				
				MemberDTO member = new MemberDTO();
				member.setMember_password(pwd);
				member.setMember_email(newEmail);
				member.setPk_member_no(Integer.parseInt(pkNum));
				try {
			         int n = mdao.updateEmail(member);
			         
			         if(n==1) {
			           
			           // !!!! session 에 저장된 loginuser 를 변경된 사용자의 정보값으로 변경해주어야 한다. !!!!
		               HttpSession session = request.getSession();
		               MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
		               
		               loginuser.setMember_password(pwd);
		               loginuser.setMember_email(newEmail);
		               loginuser.setPk_member_no(Integer.parseInt(pkNum));
		               
		        	   String message = "회원이메일 수정 성공!!";
		        	   String loc = "/Trid/member/updateEmail.trd"; // 현재페이지에 머뭄.
		             
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
	
		}
		else {
			 // 로그인을 안 했으면
			 String message = "정보를 수정하기 위해서는 먼저 로그인을 하세요!!";
	         String loc = "javascript:history.back()";
	         
	         request.setAttribute("message", message);
	         request.setAttribute("loc", loc);
	         
	      // super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
		}
	}

}
