package board.controller;

import board.model.BoardDAO_imple;
import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import board.domain.BoardDTO;



public class DetailController extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 질문번호를 파라미터로 받기
		String pk_question_no = request.getParameter("pk_question_no");
		
		// 질문 상세페이지 조회
		BoardDAO_imple bdao = new BoardDAO_imple();
		BoardDTO boardDTO = bdao.go_detail(Integer.parseInt(pk_question_no));
		
		// 다시 담아주기
		request.setAttribute("boardDTO", boardDTO);
		
		super.setRedirect(false);
		super.setViewPage(Constants.BOARD_DETAIL_PAGE);
	}

}
