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
import util.StringUtil;

/*
 * 관리자 Q&A 상세 조회 및 답변 추가 컨트롤러
 */
public class BoardDetailController extends AbstractController {

	private final BoardDAO boardDAO;
	
	public BoardDetailController() {
		this.boardDAO = new BoardDAO_imple(); // BoardDAO 초기화
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod(); // HTTP 메소드

		// 관리자 접근 유효성 검사
		if (!super.checkAdminLogin(request)) {
			return;
		}

		// POST 요청인 경우 답변 수정 및 등록
		if ("POST".equalsIgnoreCase(method)) {
			Map<String, String> paraMap = new HashMap<>();
			
			String questionNo = request.getParameter("questionNo"); // 질문 일련번호
			String questionAnswer = request.getParameter("questionAnswer"); // 답변
			
			// 답변 내용 유효성 검사
			if(StringUtil.isBlank(questionAnswer)) {
				handleBoardUpdateFail(request);
				return;
			}
			 
			paraMap.put("questionNo", questionNo);
			paraMap.put("questionAnswer", questionAnswer);
			
			try {
				// 질문 수정 요청
				int result = boardDAO.updateQuestion(paraMap);
				
				// 질문 수정 실패 시
				if (result != 1) {
					handleBoardUpdateFail(request);
					return;
				}
				
				// 질문 수정 성공 후 질문 리스트 페이지로 이동
				super.handleMessage(request, "질문 수정을 성공하였습니다.", Constants.ADMIN_BOARD_MANAGE_URL);

			} catch (SQLException e) {
				e.printStackTrace();
				super.handleServerError();
			}

		}
		// GET 요청인 경우 Q&A 상세 조회
		else {
			String questionNo = request.getParameter("questionNo"); // 질문 일련번호
			
			try {
				BoardDTO boardDTO = boardDAO.selectQuestionDetailByAdmin(questionNo); // 질문 상세 조회
				
				// BoardDTO 확인
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
				super.handleServerError();
			}
			
		}

	}
	
	/*
	 * 답변 등록 실패 시 사용자 알림 후 뒤로가기로 지정하는 메소드
	 */
	private void handleBoardUpdateFail(HttpServletRequest request) {
		request.setAttribute("message", "답변 등록/수정을 실패했습니다.");
		request.setAttribute("loc", Constants.HISTORY_BACK);

		super.setRedirect(false);
		super.setViewPage(Constants.MESSAGE_PAGE);
	}
	
}
