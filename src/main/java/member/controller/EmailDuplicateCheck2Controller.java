package member.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

//tbl_member에 같은 이메일을 사용하는 사용자가 있는지 알아온다.
public class EmailDuplicateCheck2Controller extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method =	request.getMethod(); // "GET" 또는 "POST"
		
		if("POST".equalsIgnoreCase(method)) {
			
			String newEmail = request.getParameter("newEmail");
			String pkNum = request.getParameter("pkNum");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("newEmail",newEmail);
			paraMap.put("pkNum", pkNum);
			
			boolean isExists = mdao.emailDuplicateCheck2(paraMap); 
			// 자신이 사용중인 이메일인지 아닌지 알아오는 메소드
			
			
			if(!isExists) { // 자신이 변경하고자 하는 이메일이 자신이 사용중이지 않는 새로운 이메일 이라면(false)
				isExists = mdao.emailDuplicateCheck(newEmail);//자신이 변경하고자하는 이메일이 tbl_member 테이블에서 사용중인지 아닌지 여부 알아오기
				
			}
			
			JSONObject jsonObj = new JSONObject(); // {}
			jsonObj.put("isExists", isExists);     // {"isExists":true}  또는  {"isExists":false} 으로 만들어준다.
			
			String json = jsonObj.toString(); // 문자열 형태인 "{"isExists":true}"  또는  "{"isExists":false}" 으로 만들어준다.
		//	System.out.println(">>> 확인용 json => " + json);
			// >>> 확인용 json => {"isExists":true}
			// >>> 확인용 json => {"isExists":false}
			
			request.setAttribute("json", json);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
			
			
		}

	}

}
