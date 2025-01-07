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
 	뷰단 페이지의 "현재 비밀번호"값에 들어온 비밀번호가 테이블에 있는 비밀번호와 같은지 검사하는 컨트롤러
*/
public class PwdCheckController extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple(); // MemberDAO 초기화
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(!super.checkLogin(request)) {
            super.handleMessage(request, "로그인이 필요한 페이지입니다.", Constants.MEMBER_LOGIN_URL);
            return;
         }
		
		String method = request.getMethod(); // "GET" 또는 "POST"

		if ("POST".equalsIgnoreCase(method)) {// 비밀번호 정보가 필요하므로 post 방식
			// **** POST 방식으로 넘어온 것이라면 **** //
			HttpSession session = request.getSession();
			MemberDTO loginuser =(MemberDTO)session.getAttribute("loginuser");
			
			String currentPwd = request.getParameter("currentPwd");
			String memberNo = String.valueOf(loginuser.getPk_member_no());
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("memberNo", memberNo);
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

		}

}
