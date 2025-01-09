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
  	비밀번호 찾기중 변경을 처리해주는 페이지
 */
public class FindUpdatePwdEndController extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple(); // MemberDAO 초기화
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	//	System.out.println("회원비밀번호 찾기 업데이트 페이지 컨트롤러 실행됨");
		
		if("POST".equalsIgnoreCase(request.getMethod())) {
	        String newPwd = request.getParameter("newPwd");
	        String mobile = request.getParameter("mobile");
	        
	        Map<String, String> paraMap = new HashMap<>();
	        paraMap.put("newPwd", newPwd);
	        paraMap.put("mobile", mobile);
	        
	        JSONObject jsonObj = new JSONObject();
	        
	        try {
	            int n = mdao.updateFindPwdEnd(paraMap);
	            jsonObj.put("n", n);
	            
	            response.setContentType("application/json; charset=UTF-8");
	            response.getWriter().write(jsonObj.toString());
	            super.setRedirect(false);
	            super.handelJsonView(request, jsonObj);
	            
	        } catch(SQLException e) {
	            e.printStackTrace();
	            jsonObj.put("n", 0);
	            response.getWriter().write(jsonObj.toString());
	        }
	    }
	    else {
	        super.handleMessage(request, Constants.INVALID_REQUEST, Constants.MEMBER_UPDATE_PWD_URL);
	    }
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception----------

}
