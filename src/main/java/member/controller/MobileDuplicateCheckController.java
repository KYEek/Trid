package member.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;
/*
 *	전화번호 중복확인 컨트롤러
*/
public class MobileDuplicateCheckController extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple(); // MemberDAO 초기화
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method =	request.getMethod(); // "GET" 또는 "POST"
		
		if("POST".equalsIgnoreCase(method)) {
			try {		
				String mobile = request.getParameter("mobile");

				//자신이 변경하고자하는 전화번호가 tbl_member 테이블에서 사용중인지 아닌지 여부 알아오기
				boolean isExists = mdao.mobileDuplicateCheck(mobile);
				
				JSONObject jsonObj = new JSONObject();
				
				// {"isExists":true}  또는  {"isExists":false} 으로 만들어준다.
				jsonObj.put("isExists", isExists);     

				super.handelJsonView(request, jsonObj);
			
			} catch (SQLException e) {
				e.printStackTrace();
				super.handleServerError();
			}
		}
		
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception--------------

}
