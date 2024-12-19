package common.controller;

import common.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;

/*
 * InterCommand 인터페이스를 구현하는 추상 클래스
 */
public abstract class AbstractController implements InterCommand {
	
	private boolean isRedirect = false;

	private String viewPage;
	
	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

	public String getViewPage() {
		return viewPage;
	}

	public void setViewPage(String viewPage) {
		this.viewPage = viewPage;
	}
	
	/*
	 * 사용자 알림 처리를 위한 메소드
	 */
	public void handleMessage(HttpServletRequest request, String message, String loc) {
		request.setAttribute("message", message); // 사용자 알림 메시지
		request.setAttribute("loc", request.getContextPath() + loc); // 사용자 알림 후 이동되는 url
		
		setRedirect(false);
		setViewPage(Constants.MESSAGE_PAGE);
	}
	
	/*
	 * 현재 세션이 유효한 관리자 접속인지 확인하는 메소드
	 */
	public boolean checkAdminLogin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String adminId = (String)session.getAttribute("adminId");
		
		// 세션에 관리자 아이디가 있는지 확인
		boolean result = adminId == null ? false : true;
		
		handleMessage(request, Constants.ACCESS_DENIED, Constants.ADMIN_LOGIN_URL);
		return result;
	}
	
	   // 로그인 유무를 검사해서 로그인 했으면 true 를 리턴해주고
	   // 로그인 안했으면 false 를 리턴해주도록 한다.
		public boolean checkLogin(HttpServletRequest request) {

			HttpSession session = request.getSession();
			MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");

			if (loginuser != null) {
				// 로그인 한 경우
				return true;
			} else {
				// 로그인을 하지 않은 경우
				return false;
			}

		}// end of public boolean checkLogin(HttpServletRequest request) {} ----------
	
}
