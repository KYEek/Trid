package board.controller;

import java.sql.SQLException;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import board.domain.BoardDTO;
import board.model.BoardDAO;
import board.model.BoardDAO_imple;
import common.Constants;
import common.controller.AbstractController;
import common.domain.PagingDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class ListController extends AbstractController {
	
	private BoardDAO bdao = null;
	
	public ListController() {
		bdao = new BoardDAO_imple();
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		int curPage = 1;
		
		try {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		} catch (NumberFormatException e) {
			curPage = 1;
		}
		
		try {
			PagingDTO pagingDTO = new PagingDTO();

			int totalRowCount = bdao.selectTotalRowCount(pagingDTO);
			
			if(curPage > totalRowCount || curPage < 1) {
				curPage = 1;
			}
			
			pagingDTO.setRowSizePerPage(8);
			
			pagingDTO.setCurPage(curPage);
			pagingDTO.setTotalRowCount(totalRowCount);
			pagingDTO.pageSetting();
			
			List<BoardDTO> questionList = bdao.selectQuestionList(pagingDTO);
			
			System.out.println(questionList.get(0).getQuestion_content());
			
			request.setAttribute("questionList", questionList);
			request.setAttribute("pagingDTO", pagingDTO);
			
			super.setRedirect(false);
			super.setViewPage(Constants.BOARD_LIST_PAGE);
		} catch(SQLException e) {
			e.printStackTrace();
			
			super.setRedirect(false);
			super.setViewPage(request.getContextPath()+"/error.jsp");
		}

	}

}
