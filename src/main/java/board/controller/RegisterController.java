package board.controller;

import java.sql.SQLException;

import board.domain.BoardDTO;
import board.model.BoardDAO;
import board.model.BoardDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterController extends AbstractController {

	private BoardDAO bdao = new BoardDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // "GET" 또는 "POST"
		
		if ("GET".equals(method)) {
			// System.out.println("GET 요청 호출됨");
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/board/register.jsp");
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
				int n = bdao.registerMember(boardDTO);
				
				String message = "";
				String loc = "";
				
				if(n == 1) {
					message = "질문등록 성공 ^^";
					loc = request.getContextPath()+"/board/list.trd";
				}
				else {
					message = "질문등록 실패 ㅠㅜ";
					loc = request.getContextPath()+"/board/list.trd";
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
