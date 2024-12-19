package board.controller;

import java.sql.SQLException;

import board.domain.BoardDTO;
import board.model.BoardDAO;
import board.model.BoardDAO_imple;
import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;

public class RegisterController extends AbstractController {

	private BoardDAO bdao = new BoardDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // "GET" 또는 "POST"
		
		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
		
		if(!checkLogin(request)) {
			super.handleMessage(request, Constants.ACCESS_DENIED, Constants.MEMBER_LOGIN_URL);
			return;
		}
		
		
		if ("GET".equals(method)) {
			// System.out.println("GET 요청 호출됨");
			super.setRedirect(false);
			super.setViewPage(Constants.BOARD_REGISTER_PAGE);
		}
		else {
			String title = request.getParameter("title");
			int isprivate = Integer.parseInt(request.getParameter("isprivate"));  
			String content = request.getParameter("content");
			
			BoardDTO boardDTO = new BoardDTO(); 
			
			boardDTO.setQuestion_title(title);
			boardDTO.setQuestion_isprivate(isprivate);
			boardDTO.setQuestion_content(content);
			
			try {
				int n = bdao.insertQuestionRegister(boardDTO, loginuser.getPk_member_no());
				
				String message = "";
				String loc = "";
				
				if(n == 1) {
					message = "질문등록 성공 ^^";
					loc = request.getContextPath()+Constants.BOARD_LIST_URL;
				}
				else {
					message = "질문등록 실패 ㅠㅜ";
					loc = request.getContextPath()+Constants.BOARD_LIST_URL;
				}
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/message.jsp");
			
		}
		
	}

}
