package member.controller;

import java.sql.SQLException;

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
 	뷰단 페이지의 "현재 비밀번호" 값에 들어온 비밀번호가 tbl_member 에 있는 비밀번호와 같고 
 	새로운 비밀번호가 현재 비밀번호와 같지 않아야한다.
 */
public class PwdCheckController extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple(); // db와 연결해주는 용도
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	//	System.out.println("회원비밀번호 확인 페이지 실행됨");
		
		String method = request.getMethod(); // "GET" 또는 "POST"

		if ("POST".equalsIgnoreCase(method)) {// 비밀번호 정보가 필요하므로 post 방식
			// **** POST 방식으로 넘어온 것이라면 **** //
			String currentPwd = request.getParameter("currentPwd");
			String pkNum = request.getParameter("pkNum");

			MemberDTO member = new MemberDTO();
			member.setMember_password(currentPwd);
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

		}

	}

}
