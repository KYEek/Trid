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

	private final BoardDAO boardDAO;
	
	public BoardManageController() {
		this.boardDAO = new BoardDAO_imple(); // BoardDAO 초기화
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod(); // HTTP 메소드

		// 관리자 접근 유효성 검사
		if (!super.checkAdminLogin(request)) {
			return;
		}

		// POST 요청인 경우 동일 URL에 GET 요청으로 리다이렉트
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
			} catch (NumberFormatException e) {}
			
			Map<String, Object> paraMap = createParaMap(request);  // URL 파라미터에서 받은 값을 Map에 저장
			
			try {
				int totalRowCount = boardDAO.selectTotalRowCountByAdmin(paraMap); // 전체 행 개수 조회
				
				PagingDTO pagingDTO = new PagingDTO(curPage, totalRowCount); // 페이징 관련 정보가 저장된 DTO 생성
				
				paraMap.put("pagingDTO", pagingDTO);
				
				List<BoardDTO> boardList = boardDAO.selectQuestionListByAdmin(paraMap); // 질문 리스트 조회
				
				request.setAttribute("boardList", boardList); // Q&A 리스트 정보
				request.setAttribute("pagingDTO", pagingDTO); // 페이징 정보
				request.setAttribute("paraMap", paraMap); // 이전 요청 시 받았던 필터링, 카테고리 정보가 담긴 map
				
				super.setRedirect(false);
				super.setViewPage(Constants.ADMIN_BOARD_MANAGE_JSP);

			} catch (SQLException e) {
				e.printStackTrace();
				super.handleServerError();
			}

		}

	}
	
	/*
	 * URL 파라미터에서 받은 값을 Map에 저장하여 반환하는 메소드
	 */
	private Map<String, Object> createParaMap(HttpServletRequest request) {
		Map<String, Object> paraMap = new HashMap<>();
		
		paraMap.put("searchType", request.getParameter("searchType")); // 검색 타입 0:글제목 1:작성자면
		paraMap.put("searchWord", request.getParameter("searchWord")); // 검색어
		paraMap.put("sortCategory", request.getParameter("sortCategory")); // 정렬 타입 0:최신순, 1:오래된순
		paraMap.put("privateStatus", request.getParameter("privateStatus")); // 비밀글 여부 번호 0:공개글 1:비밀글 
		paraMap.put("answerStatus", request.getParameter("answerStatus")); // 답변 여부 번호 0:답변대기 1:답변완료
		paraMap.put("dateMin", request.getParameter("dateMin")); // 최소 일자
		paraMap.put("dateMax", request.getParameter("dateMax")); // 최대 일자
		
		return paraMap;
	}

}
