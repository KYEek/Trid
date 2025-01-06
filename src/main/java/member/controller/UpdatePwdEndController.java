package member.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
		
		if(!super.checkLogin(request)) { // 세션에 로그인 유저 없음
			// 메시지 보내기 
			super.handleMessage(request, "로그인이 필요한 페이지입니다.", Constants.MEMBER_LOGIN_URL);
			return;
		}
		
		if("POST".equalsIgnoreCase(method)) {// 비밀번호 변경 페이지이므로 post 방식
		// **** POST 방식으로 넘어온 것이라면 **** //
			HttpSession session = request.getSession();
            MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
			
			String newPwd = request.getParameter("newPwd");
			String memberNo = String.valueOf(loginuser.getPk_member_no()); 
			
			Map<String, String> paraMap = new HashMap<>();
			
			paraMap.put("newPwd", newPwd);
			paraMap.put("memberNo", memberNo);
			
			try {
		         int n = mdao.updatePwdEnd(paraMap);
		         
		         // 비밀번호 변경 성공 시
		         if(n==1) {
	               session.invalidate();
	               
	        	   super.handleMessage(request, "비밀번호 수정 성공! 다시 로그인 해주세요", Constants.MEMBER_LOGIN_URL);
		         }
		         // 비밀번호 변경 실패 시
		         else {
		        	 super.handleMessage(request, "비밀번호 수정을 실패하였습니다. 다시 시도해주세요", Constants.MEMBER_UPDATE_PWD_URL);
		         }
	         } catch(SQLException e) {
	        	 e.printStackTrace();
	        	 super.handleServerError();
	         }
			
		}// end of if("POST".equalsIgnoreCase(method))-----------
		
		else {
			 super.handleMessage(request, Constants.INVALID_REQUEST, Constants.MEMBER_UPDATE_PWD_URL);
		}
		
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception----------

}
