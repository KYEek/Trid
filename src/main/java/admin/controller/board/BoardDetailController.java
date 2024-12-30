package admin.controller.board;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import board.domain.BoardDTO;
import board.model.BoardDAO;
import board.model.BoardDAO_imple;
import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BoardDetailController extends AbstractController {

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
			Map<String, String> paraMap = new HashMap<>();
			
			String pkQuestionNo = request.getParameter("pkQuestionNo"); // 질문 일련번호
			String questionAnswer = request.getParameter("questionAnswer"); // 답변
			
			if(questionAnswer == null || questionAnswer.isBlank()) {
				request.setAttribute("message", "질문 수정을 실패했습니다.");
				request.setAttribute("loc", Constants.HISTORY_BACK);

				super.setRedirect(false);
				super.setViewPage(Constants.MESSAGE_PAGE);
				return;
			}
			 
			paraMap.put("pkQuestionNo", pkQuestionNo);
			paraMap.put("questionAnswer", questionAnswer);

			
			try {
				// 질문 수정 요청
				int result = boardDAO.updateQuestion(paraMap);
				
				// 질문 수정 실패 시
				if (result != 1) {
					request.setAttribute("message", "질문 수정을 실패했습니다.");
					request.setAttribute("loc", Constants.HISTORY_BACK);

					super.setRedirect(false);
					super.setViewPage(Constants.MESSAGE_PAGE);
					return;
				}
				
				// 질문 수정 성공 후 질문 리스트 페이지로 이동
				super.handleMessage(request, "질문 수정을 성공하였습니다.", Constants.ADMIN_BOARD_MANAGE_URL);

			} catch (SQLException e) {
				e.printStackTrace();
				super.setRedirect(true);
				super.setViewPage(Constants.ERROR_URL);
			}

		}
		// GET 요청인 경우
		else {
			
			String questionNo = request.getParameter("questionNo");
			
			try {
				BoardDTO boardDTO = boardDAO.selectQuestionDetailByAdmin(questionNo);
				
				if(boardDTO == null) {
					super.handleMessage(request, "해당 질문이 존재하지 않습니다.", Constants.ADMIN_BOARD_MANAGE_URL);
					return;
				}
				
				request.setAttribute("boardDTO", boardDTO);
				
				super.setRedirect(false);
				super.setViewPage(Constants.ADMIN_BOARD_DETAIL_JSP);
			}
			catch(SQLException e ) {
				e.printStackTrace();
				super.setRedirect(true);
				super.setViewPage(Constants.ERROR_URL);
			}
			
		}

	}
	
}
