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
 	현재 비밀번호를 알아와서 뷰단 페이지에 들어온 비밀번호 값과 비교해주는 페이지
*/
public class CurrentPwdCheckController extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple(); // db와 연결해주는 용도
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

	//	System.out.println("회원비밀번호 수정 페이지 컨트롤러 실행됨");
		
		String method = request.getMethod(); // "GET" 또는 "POST" 
		
		// 세션으로 사용자 확인 절차 필요
		
		if("POST".equalsIgnoreCase(method)) {// 비밀번호와 이메일 정보가 필요하므로 무조건 post 방식
			// **** POST 방식으로 넘어온 것이라면 **** //
			String currentPwd = request.getParameter("currentPwd");
			String pkNum = request.getParameter("pkNum");
			
	        String message = "";
	        String loc = "";
			
			
			MemberDTO member = new MemberDTO();
	//		System.out.println("pkNum =>"+pkNum);
			member.setMember_password(currentPwd);
			member.setPk_member_no(Integer.parseInt(pkNum));
			try {
		         boolean isExists = mdao.currentPwdCheck(member);
		         
		         if(isExists) {
		           
		           // !!!! session 에 저장된 loginuser 를 변경된 사용자의 정보값으로 변경해주어야 한다. !!!!
	               HttpSession session = request.getSession();
	               MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
	               
	               loginuser.setMember_password(currentPwd);
	               loginuser.setPk_member_no(Integer.parseInt(pkNum));
	               
	        	   message = "회원 비밀번호 확인 성공!!";
	        	   loc = "/Trid/member/updateEmail.trd"; // 현재페이지에 머뭄.
	             
	        	   
	        	   JSONObject jsonObj = new JSONObject(); // {}
        	       jsonObj.put("message", message); // {"n":1, "message":"이연진님의 300,000원 결제가 완료되었습니다."} 
        	       jsonObj.put("loc", loc);         // {"n":1, "message":"이연진님의 300,000원 결제가 완료되었습니다.", "loc":"/MyMVC/index.up"}   
        	       jsonObj.put("isExists", isExists);
        	       
        	       String json = jsonObj.toString();  // "{"n":1, "message":"이연진님의 300,000원 결제가 완료되었습니다.", "loc":"/MyMVC/index.up"}"
        	       request.setAttribute("json", json);
	             
	               super.setRedirect(false); 
	               super.setViewPage("/WEB-INF/jsonview.jsp");
	               
		         }
		         
		         else {
		        	 JSONObject jsonObj = new JSONObject(); // {}
		        	 jsonObj.put("isExists",isExists);
		        	 
			         String json = jsonObj.toString();  // "{"n":1, "message":"이연진님의 300,000원 결제가 완료되었습니다.", "loc":"/MyMVC/index.up"}"
	        	     request.setAttribute("json", json);
		             
		             super.setRedirect(false); 
		             super.setViewPage("/WEB-INF/jsonview.jsp");
		        	 
		         }
	         } catch(SQLException e) {
	        	 e.printStackTrace();
	        	 super.setRedirect(true);
	        	 super.setViewPage(request.getContextPath()+"/error.trd");
	         }
			
			
		}
			

	}

}
