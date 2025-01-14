package admin.controller.member;

import java.sql.SQLException;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberDTO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

/*
 * 관리자 회원 상세 조회 컨트롤러
 */
public class MemberDetailController extends AbstractController {
	
	private final MemberDAO memberDAO;
	
	public MemberDetailController() {
		this.memberDAO = new MemberDAO_imple(); // MemberDAO 초기화
	}

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
			super.setViewPage(Constants.ADMIN_MEMBER_DETAIL_URL);
		}
		// GET 요청인 경우
		else {
			String memberNo = request.getParameter("memberNo"); // 회원 일련번호
			
			try {
				MemberDTO memberDTO = memberDAO.selectMemberByAdmin(memberNo); // 회원 상세 조회
				
				request.setAttribute("memberDTO", memberDTO);
			
				super.setRedirect(false);
				super.setViewPage(Constants.ADMIN_MEMBER_DETAIL_JSP);

			} catch (SQLException e) {
				e.printStackTrace();
				super.handleServerError();
			}

		}

	}

}
