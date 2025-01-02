package admin.controller.auth;

import java.sql.SQLException;

import admin.domain.AdminDTO;
import admin.model.AdminDAO;
import admin.model.AdminDAO_imple;
import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.security.Sha256;

/*
 * 관리자 로그인 컨트롤러
 */
public class LoginController extends AbstractController {

	private final AdminDAO adminDAO = new AdminDAO_imple(); // 관리자 DAO 초기화

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		HttpSession session = request.getSession(); // 세션 생성

		// POST 요청인 경우 관리자 로그인 처리
		if ("POST".equalsIgnoreCase(method)) {
			String adminId = request.getParameter("adminId"); // 관리자 아이디
			String password = request.getParameter("password"); // 관리자 비밀번
			String encryptPw = Sha256.encrypt(password); // 비밀번호 암호화

			try {
				AdminDTO adminDTO = adminDAO.selectAdmin(adminId, encryptPw); // 관리자 로그인 검사

				// 로그인 실패 시 사용자 알림
				if (adminDTO == null) {
					super.handleMessage(request, Constants.INVALID_ID_OR_PASSWORD, Constants.ADMIN_LOGIN_URL);
					return;
				}

				session.setAttribute("adminId", adminDTO.getAdminId());
				session.setAttribute("adminName", adminDTO.getAdminName());

				super.setRedirect(true);
				super.setViewPage(Constants.ADMIN_MAIN_URL);
				
			} catch (SQLException e) {
				e.printStackTrace();
				super.handleServerError();
			}

		}
		// GET 요청인 경우 관리자 로그인 페이지로 연결
		else {
			if (session.getAttribute("adminId") != null) {
				super.handleMessage(request, "이미 로그인 된 관리자입니다. 메인페이지로 이동합니다.", Constants.ADMIN_MAIN_URL);
				return;
			}

			super.setRedirect(false);
			super.setViewPage(Constants.ADMIN_LOGIN_PAGE);
		}

	}

}
