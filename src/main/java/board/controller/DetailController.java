package board.controller;

import board.model.BoardDAO_imple;
import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;

import java.util.HashMap;
import java.util.Map;

import board.domain.BoardDTO;



public class DetailController extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		// 세션에서 로그인한 유저의 정보 받아오기
		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
		
		// 질문번호를 파라미터로 받기
		String pk_question_no = request.getParameter("pk_question_no");
		
		// 공개여부를 파라미터로 받기
		String question_isprivate = request.getParameter("question_isprivate");
		
		// 질문 상세페이지 조회
		BoardDAO_imple bdao = new BoardDAO_imple();
		
		Map<String, String> paraMap = new HashMap<>();
		
		paraMap.put("pk_question_no", pk_question_no);
		paraMap.put("question_isprivate", question_isprivate);
		
		// 로그인 했을 때 맵에 담아주기
		if(loginuser != null) {
			paraMap.put("pk_member_no", String.valueOf(loginuser.getPk_member_no()));
		}
		
		BoardDTO boardDTO = bdao.selectQuestionDetail(paraMap);
		
		if(boardDTO == null) {
			super.handleMessage(request, "접근할 수 없는 게시글입니다.", Constants.BOARD_LIST_URL);
			return;
		}
		
		
		// 다시 담아주기
		request.setAttribute("boardDTO", boardDTO);
		
		super.setRedirect(false);
		super.setViewPage(Constants.BOARD_DETAIL_PAGE);
	}

}
