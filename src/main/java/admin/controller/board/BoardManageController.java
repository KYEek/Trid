package admin.controller.board;

import common.Constants;
import common.controller.AbstractController;
import common.domain.PagingDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import board.domain.BoardDTO;
import board.model.*;

/*
 * 모든 회원의 Q&A 리스트를 조회하는 컨트롤러 
 */
public class BoardManageController extends AbstractController {

	private final BoardDAO boardDAO = new BoardDAO_imple(); // BoardDAO 초기화

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // HTTP 메소드

		// 관리자 접근 유효성 검사
		if (!super.checkAdminLogin(request)) {
			return;
		}

		// POST 요청인 경우
		if ("POST".equalsIgnoreCase(method)) {
			super.setRedirect(true);
			super.setViewPage(Constants.ADMIN_BOARD_MANAGE_URL);
		}
		// GET 요청인 경우
		else {
			
			int curPage = 1; // 현재 페이지 초기화

			// curPage가 정수인지 예외처리
			try {
				curPage = Integer.parseInt(request.getParameter("curPage"));
			} catch (NumberFormatException e) {
				System.out.println("curPage is not Integer");
				curPage = 1;
			}
			
			String searchType = request.getParameter("searchType");
			
			String searchWord = request.getParameter("searchWord");
			
			String sortCategory = request.getParameter("sortCategory");
			
			String privateStatus = request.getParameter("privateStatus");
			
			String answerStatus = request.getParameter("answerStatus");
			
			String dateMin = request.getParameter("dateMin");
			
			String dateMax = request.getParameter("dateMax");
			
			Map<String, Object> paraMap = new HashMap<>();
			
			paraMap.put("searchType", searchType);
			paraMap.put("searchWord", searchWord);
			paraMap.put("sortCategory", sortCategory);
			paraMap.put("privateStatus", privateStatus);
			paraMap.put("answerStatus", answerStatus);
			paraMap.put("dateMin", dateMin);
			paraMap.put("dateMax", dateMax);
			
			try {
				
				PagingDTO pagingDTO = new PagingDTO(); // PagingDTO 초기화
				
				int totalRowCount = boardDAO.selectTotalRowCountByAdmin(paraMap); // 전체 행 개수 조회
				
				// curPage 유효성 검사
				if(curPage > totalRowCount || curPage < 1 ) {
					curPage = 1;
				}
				
				pagingDTO.setRowSizePerPage(5);
				pagingDTO.setPageSize(5);
				pagingDTO.setCurPage(curPage);
				pagingDTO.setTotalRowCount(totalRowCount);
				pagingDTO.pageSetting(); // 페이징 시 필요한 나머지 정보 계산
				
				paraMap.put("pagingDTO", pagingDTO);
				
				List<BoardDTO> boardList = boardDAO.selectQuestionListByAdmin(paraMap);
				
				request.setAttribute("boardList", boardList);
				request.setAttribute("pagingDTO", pagingDTO);
				
				request.setAttribute("searchWord", searchWord);
				request.setAttribute("searchType", searchType);
				request.setAttribute("sortCategory", sortCategory);
				request.setAttribute("privateStatus", privateStatus);
				request.setAttribute("answerStatus", answerStatus);
				request.setAttribute("dateMin", dateMin);
				request.setAttribute("dateMax", dateMax);

				super.setRedirect(false);
				super.setViewPage(Constants.ADMIN_BOARD_MANAGE_JSP);

			} catch (SQLException e) {
				e.printStackTrace();
				super.setRedirect(true);
				super.setViewPage(Constants.ERROR_URL);
			}

		}

	}

}
