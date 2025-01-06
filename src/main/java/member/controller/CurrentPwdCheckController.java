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
 	이메일 수정 전, 현재 비밀번호로 인증해주는 절차를 위해 사용자의 현재 비밀번호를 알아오는 컨트롤러
 	비밀번호 확인 컨트롤러
*/
public class CurrentPwdCheckController extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if(!super.checkLogin(request)) {
            super.handleMessage(request, "로그인이 필요한 페이지입니다.", Constants.MEMBER_LOGIN_URL);
            return;
         }
		
		String method = request.getMethod(); // "GET" 또는 "POST"

		if ("POST".equalsIgnoreCase(method)) {// 비밀번호 정보가 필요하므로 post 방식
			// **** POST 방식으로 넘어온 것이라면 **** //
			String currentPwd = request.getParameter("currentPwd");
			String pkNum = request.getParameter("pkNum");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("memberNo", pkNum);
			paraMap.put("currentPwd", currentPwd);
			
			try {
				boolean isExists = mdao.currentPwdCheck(paraMap);	// 비밀번호 확인

					JSONObject jsonObj = new JSONObject(); // {}
					jsonObj.put("isExists", isExists); // true
					super.handelJsonView(request, jsonObj); // viewpage로 이동
					
			} catch (SQLException e) {
				e.printStackTrace();
				super.handleServerError(); // 에러 페이지로 이동
			}

			}
		
		else {
			super.handleMessage(request, "잘못된 요청방식입니다.", "/member/mypage.trd");
			
		}


}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception-----------------

}
