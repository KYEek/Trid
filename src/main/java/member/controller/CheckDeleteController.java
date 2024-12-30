package member.controller;

import java.util.HashMap;
import java.util.Map;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import member.model.*;


public class CheckDeleteController extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	// 계정 삭제 재확인을 요청하는 페이지 처리
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
		
		String method = request.getMethod(); // HTTP Method
		
		// POST 방식이라면
		if("POST".equalsIgnoreCase(method)) { 
			System.out.println("POST방식");
			String member_password = request.getParameter("pwd");
			
			Map<String , String> paraMap = new HashMap<>();
			paraMap.put("member_password", member_password);
			paraMap.put("pk_member_no", String.valueOf(loginuser.getPk_member_no()));
			
			int result = mdao.memberDelete(paraMap);
			
			if(result == 1) {
				// 로그아웃 처리하기
				session.invalidate();
				
				String message = "계정 탈퇴가 완료되었습니다."; 
				String loc = "/main.trd";
			//	System.out.println("계정탈퇴성공");
				super.handleMessage(request, message, loc);
				
				return;
			}
			else {
				String message = "비밀번호가 올바르지 않습니다.";
				String loc = Constants.CHECKDELETE_URL;
			//	System.out.println("계정탈퇴실패");
				super.handleMessage(request, message, loc);
				
				return; 
			}
		}
		else {// get 방식이라면
		//	System.out.println("get방식");
			super.setRedirect(false);
			super.setViewPage(Constants.CHECKDELETE_PAGE);
		}
		


	}

}
