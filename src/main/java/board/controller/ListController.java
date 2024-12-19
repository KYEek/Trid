package board.controller;

import java.sql.SQLException;
import java.util.List;

import board.domain.BoardDTO;
import board.model.BoardDAO;
import board.model.BoardDAO_imple;
import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;





public class ListController extends AbstractController {
	
	private BoardDAO bdao = null;
	
	public ListController() {
		bdao = new BoardDAO_imple();
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			List<BoardDTO> questionList = bdao.questionSelect();
			request.setAttribute("questionList", questionList);
			
			super.setRedirect(false);
			super.setViewPage(Constants.BOARD_LIST_PAGE);
		} catch(SQLException e) {
			e.printStackTrace();
			
			super.setRedirect(false);
			super.setViewPage(request.getContextPath()+"/error.jsp");
		}

	}

}
