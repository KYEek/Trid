package common.controller;

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
	
}
