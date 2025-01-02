package member.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class UpdateMobileCheckController extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod();
		
		if("POST".equalsIgnoreCase(method)) {// "POST" 방식이라면
			
			String newMobile = request.getParameter("newMobile");
			String pkNum = request.getParameter("pkNum");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("newMobile",newMobile);
			paraMap.put("pkNum", pkNum);
			
			boolean isExists = mdao.MobileDuplicateCheck(paraMap); 
			// 자신이 사용중인 전화번호인지 아닌지 알아오는 메소드
			
			
			JSONObject jsonObj = new JSONObject(); // {}
			jsonObj.put("isExists", isExists);     // {"isExists":true}  또는  {"isExists":false} 으로 만들어준다.
			
			String json = jsonObj.toString(); // 문자열 형태인 "{"isExists":true}"  또는  "{"isExists":false}" 으로 만들어준다.
		//	System.out.println(">>> 확인용 json => " + json);
			// >>> 확인용 json => {"isExists":true}
			// >>> 확인용 json => {"isExists":false}
			
			request.setAttribute("json", json); // 새로운 전화번호가 맞다면 true
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
			
		}
		else {// "GET" 방식이라면
			super.setRedirect(false);
       	 	super.setViewPage(request.getContextPath()+"/error.trd");
		}
		
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception-----------------

}
