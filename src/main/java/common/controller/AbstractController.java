package common.controller;

import common.Constants;
import jakarta.servlet.http.HttpServletRequest;

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
		request.setAttribute("loc", loc); // 사용자 알림 후 이동되는 url
		
		setRedirect(false);
		setViewPage(Constants.MESSAGE_PAGE);
	}
	
}
