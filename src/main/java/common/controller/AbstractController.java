package common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONObject;

import common.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import product.domain.CategoryDTO;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

/*
 * InterCommand 인터페이스를 구현하는 추상 클래스
 */
public abstract class AbstractController implements InterCommand {
	
	private ProductDAO productDAO = new ProductDAO_imple();
	
	private boolean isRedirect = false;

	private String viewPage;
	
	private boolean isJsonResponse = false; // 클라이언트에 직접 JSON 문자열로 응답
	
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

	public boolean isJsonResponse() {
		return isJsonResponse;
	}

	public void setJsonResponse(boolean isJsonResponse) {
		this.isJsonResponse = isJsonResponse;
	}
	
	/*
	 * 모든 컨트롤러의 공통기능 및 개별기능을 수행하기 위한 메소드
	 */
	public void executeCommand(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 헤더 메뉴의 카테고리 데이터를 불러오기
		try {
			List<CategoryDTO> categoryList = productDAO.selectCategoryList();
			request.setAttribute("categoryList", categoryList);
		} catch (SQLException e) {
			e.printStackTrace();
			handleServerError();
		}
		
		// 자식 컨트롤러의 개별 기능 수행
		execute(request, response);
	}
	
	/*
	 * 자식 컨트롤러의 개별 기능을 수행하기 위한 추상 메소드
	 */
	public abstract void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
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
		
		if(!result) {
			handleMessage(request, Constants.ACCESS_DENIED, Constants.ADMIN_LOGIN_URL);	
		}
		
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
	
	/*
	 * 서버 예외가 발생할 경우 에러페이지로 포워드하는 메소드
	 */
	public void handleServerError() {
		setRedirect(true);
		setViewPage(Constants.ERROR_URL);
	}
	
	/*
	  	AJAX JSON 응답 처리 메소드
	*/
	public void handelJsonResponse(HttpServletResponse response, String message) {
		try {
			setJsonResponse(true); // 클라이언트로 단순 응답 처리

		    response.setContentType("application/json"); // JSON 타입으로 MIME 설정
		    response.setCharacterEncoding("UTF-8");
		    
		    JSONObject json = new JSONObject();
		    json.put("message", message);
		    
		    PrintWriter out = response.getWriter();
		    out.print(json.toString());
		    out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	/*
	 	JSON view 응답 처리 메소드
	*/
	public void handelJsonView(HttpServletRequest request, JSONObject jsonObj) {
		String json = jsonObj.toString(); // JSON 객체 문자열 변환
		request.setAttribute("json", json);

		setRedirect(false);
		setViewPage("/WEB-INF/jsonview.jsp");
	}
	
}
