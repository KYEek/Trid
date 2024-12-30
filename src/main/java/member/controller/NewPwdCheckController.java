package member.controller;

import java.sql.SQLException;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberDTO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class NewPwdCheckController extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple(); // db와 연결해주는 용도
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
//		System.out.println("새비밀번호 확인 페이지 컨트롤러 실행됨");
		String method = request.getMethod(); // "GET" 또는 "POST" 
		
		if("POST".equalsIgnoreCase(method)) {// 비밀번호 변경 페이지이므로 post 방식
		// **** POST 방식으로 넘어온 것이라면 **** //
			
			String newPwd = request.getParameter("newPwd");
			String pkNum = request.getParameter("pkNum");
			
			MemberDTO member = new MemberDTO();
			member.setMember_password(newPwd);
			member.setPk_member_no(Integer.parseInt(pkNum));
			
			try {
				boolean isExists = mdao.currentPwdCheck(member);

				if (isExists) {// 사용자의 일련번호와 비밀번호가 같은 행이 존재한다면

					JSONObject jsonObj = new JSONObject(); // {}
					jsonObj.put("isExists", isExists);//true

					String json = jsonObj.toString(); // "{"n":1, "message":"이연진님의 300,000원 결제가 완료되었습니다.",
														// "loc":"/MyMVC/index.up"}"
					request.setAttribute("json", json);

					super.setRedirect(false);
					super.setViewPage("/WEB-INF/jsonview.jsp");
				}

				else {// 사용자의 일련번호와 비밀번호가 같은 행이 존재하지 않는다면
					
					JSONObject jsonObj = new JSONObject(); // {}
					jsonObj.put("isExists", isExists);// false

					String json = jsonObj.toString(); // "{"n":1, "message":"이연진님의 300,000원 결제가 완료되었습니다.",
														// "loc":"/MyMVC/index.up"}"
					request.setAttribute("json", json);

					super.setRedirect(false);
					super.setViewPage("/WEB-INF/jsonview.jsp");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				super.setRedirect(true);
				super.setViewPage(request.getContextPath() + "/error.trd");
			}
			
		}// end of if("POST".equalsIgnoreCase(method))--------------------	
		
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception-------------------

}
