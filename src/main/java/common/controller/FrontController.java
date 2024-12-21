package common.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import common.Constants;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * 클라이언트의 요청을 가장 먼저 처리하는 컨트롤러
 */
@WebServlet(urlPatterns = {"*.trd"})
@MultipartConfig(
maxFileSize = 1024 * 1024 * 5,      // 파일당 최대 크기 제한 5MB
maxRequestSize = 1024 * 1024 * 20   // 전체 파일들의 최대 크기 제한 20MB
)
public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	// Command.properties의 {uri, 클래스 경로}를 저장하는 map
	private Map<String, Object> cmdMap = new HashMap<>();
	
	/*
	 * 서블릿 초기화 메소드
	 * Command.properties의 {uri, 컨트롤러 경로}를 읽어 cmdMap에 저장한다.
	 */
	public void init(ServletConfig config) throws ServletException {
		
		FileInputStream fls = null; 
		
		String props = Constants.PROPERTIES_PATH; // Command.properties 경로
		
		try {
			
			fls = new FileInputStream(props); // 파일 내용을 읽어오기 위한 객체
			
			Properties pr = new Properties(); // Collection 중 HashMap 계열중의 하나
			
			pr.load(fls); // Command.properties {key, value} 형태로 불러오기
			
			Enumeration<Object> en = pr.keys(); // key 값들 불러오기
			
			while(en.hasMoreElements()) {
				
				String key = (String) en.nextElement(); // uri
				
				String className = pr.getProperty(key); // 컨트롤러 경로
				
				if(className != null) {
				
					className = className.trim();
					
					Class<?> cls = Class.forName(className); // 컨트롤러 지정
					
				 	Constructor<?> constrt = cls.getDeclaredConstructor(); // 컨트롤러 생성자 불러오기
					
					Object obj = constrt.newInstance(); // 컨트롤러 인스턴스 생성
					
					cmdMap.put(key, obj); // map에 uri, 컨트롤러 인스턴스 저장
					
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String uri = request.getRequestURI().toString(); // request 요청에서 uri 추출
		
		String key = uri.substring(request.getContextPath().length()); // context path 뒤의 uri 추출
		
		AbstractController controllerInstance = (AbstractController) cmdMap.get(key); // 해당 컨트롤러 인스턴스 추출
		
		if(controllerInstance == null) {
			System.out.println(">>> " + key + " 은 URI 패턴에 매핑된 클래스가 존재하지 않습니다.");
		}
		
		else {
			try {
				controllerInstance.execute(request, response); // 컨트롤러 인스턴스의 execute 메소드 실행
				
				boolean isRedirect = controllerInstance.isRedirect(); // 리다이렉트 처리 여부
				 
				String viewPage = controllerInstance.getViewPage(); // 페이지 경로
				
				// 포워드인 경우
				if(!isRedirect) {
					if(viewPage != null) {
						RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
						dispatcher.forward(request, response);
					}
					else {
						System.out.println(">>> viewPage가 존재하지 않습니다.");
						response.sendRedirect(request.getContextPath() + "/error.trd");
					}
				}
				
				// 리다이렉트인 경우
				else {
					if(viewPage != null) {
						response.sendRedirect(request.getContextPath() + viewPage);
					}
					else {
						System.out.println(">>> viewPage가 존재하지 않습니다.");
						response.sendRedirect(request.getContextPath() + "/error.trd");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * POST 요청을 GET 요청을 처리하는 메소드로 보낸다. 
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
