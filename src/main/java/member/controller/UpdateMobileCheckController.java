package member.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

/*
 	tbl_memeber 테이블에서 현재 사용중인 전화번호인지 중복체크 컨트롤러
*/

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
			
			boolean isExists = mdao.mobileDuplicateCheck(newMobile); 
			// 사용중인 전화번호인지 아닌지 알아오는 메소드
			
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("isExists", isExists);     
			
			String json = jsonObj.toString(); 
			request.setAttribute("json", json); // 새로운 전화번호가 맞다면 true
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
			
		}
		else {
			super.handleMessage(request, "잘못된 요청방식입니다.", "/member/mypage.trd");
			
		}
		
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception-----------------

}
