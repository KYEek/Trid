package member.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

/*
 	이메일 수정 컨트롤러(update)
*/

public class UpdateEmailEndController extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple(); 
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			String method = request.getMethod(); // "GET" 또는 "POST" 
			
			if(!super.checkLogin(request)) {
	            super.handleMessage(request, "로그인이 필요한 페이지입니다.", Constants.MEMBER_LOGIN_URL);
	            return;
	         }
			
			if("POST".equalsIgnoreCase(method)) {// "POST" 방식이라면 이메일 변경 작업 수행
				
				String pwd = request.getParameter("pwd");
				String newEmail = request.getParameter("newEmail");
				String memberNo = request.getParameter("memberNo");
				
				Map<String , String> paraMap = new HashMap<>();
				paraMap.put("pwd", pwd);
				paraMap.put("newEmail", newEmail);
				paraMap.put("memberNo", memberNo);
				
				try {
			         int n = mdao.updateEmail(paraMap);
			         
			         if(n==1) {
			           
		               HttpSession session = request.getSession();
		               session.invalidate(); // 이메일 변경 완료되면 로그아웃 처리하기
		               
		               // 로그아웃 후 로그인 페이지로 이동
		               super.handleMessage(request, "이메일 변경이 완료되었습니다. 다시 로그인해주세요", Constants.MEMBER_LOGIN_URL);
		               
			         }
			         else {// 이메일 변경을 실패한 경우
			           super.handleMessage(request, "이메일 수정을 실패하였습니다. 다시 시도해주세요", Constants.MEMBER_UPDATE_EMAIL_URL);

			         }
			         
		         } catch(SQLException e) {
		        	 e.printStackTrace();
		        	 super.setRedirect(true);
		        	 super.setViewPage(request.getContextPath()+"/error.trd");
		         }
				
			
			}
			else {
				super.handleMessage(request, "잘못된 요청방식입니다.", "/member/mypage.trd");
				
			}
			
			
		
		
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception----------------------

}
