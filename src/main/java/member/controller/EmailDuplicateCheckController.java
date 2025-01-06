package member.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

/*
 	이메일 중복확인 컨트롤러
*/

public class EmailDuplicateCheckController extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple(); // MemberDAO 초기화
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method =	request.getMethod();
		
		if("POST".equalsIgnoreCase(method)) {
			
			String newEmail = request.getParameter("newEmail");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("newEmail",newEmail);
			
			boolean isExists = mdao.emailDuplicateCheck(newEmail);
			// 변경하고자 하는 이메일이 tbl_member 테이블에서 사용중인지 아닌지 여부 알아오는 메소드
			
			JSONObject jsonObj = new JSONObject(); 
			jsonObj.put("isExists", isExists);    
			
			super.handelJsonView(request, jsonObj);
			
		}// end of if("POST".equalsIgnoreCase(method))-------------------
		// GET 요청인 경우 잘못된 접근임으로 회원가입 페이지로 리다이렉트
		else {
			super.handleMessage(request, Constants.INVALID_REQUEST, Constants.REGISTER_URL);
		}
		

	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception---------------

}
