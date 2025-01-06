package login.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;


/*
 	비밀번호 찾기를 할때 필요한 이메일 인증 컨트롤러
 */
public class FindPwdEmailCheckController extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method =	request.getMethod(); // "GET" 또는 "POST"
		
		if("POST".equalsIgnoreCase(method)) {// "POST" 방식이라면
			
			String email = request.getParameter("email");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("email",email);
			
			boolean isExists = mdao.isUserExist(paraMap);
			
			JSONObject jsonObj = new JSONObject(); 
			jsonObj.put("isExists", isExists);
			
			String json = jsonObj.toString();
			
			request.setAttribute("json", json); 
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
			
			
		}// end of if("POST".equalsIgnoreCase(method)) -------------------

		else {// "GET" 방식이라면
			super.setRedirect(false);
       	 	super.setViewPage(request.getContextPath()+"/error.trd");
		}	
	}
}	

