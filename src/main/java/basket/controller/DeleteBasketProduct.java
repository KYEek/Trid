package basket.controller;

import java.io.BufferedReader;

import org.json.JSONArray;
import org.json.JSONObject;

import basket.domain.BasketDTO;
import basket.model.BasketDAO;
import basket.model.BasketDAO_imple;
import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;

public class DeleteBasketProduct extends AbstractController {

	BasketDAO bskDao = new BasketDAO_imple();
	BasketDTO bskDTO = null;
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("loginuser");
		
		//비동기 호출 시 보내온 데이터를 불러오기 위한 과정
		//문자열을 담을 스트링 빌더를 생성
		StringBuilder sb = new StringBuilder();
		//전송할 데이터를 담을 버퍼리더를 생성
		BufferedReader reader = request.getReader();
		//한 줄마다 불러온 값을 저장할 변수
		String line;
		//한줄씩 불러오면서 더이상 불러올 데이터가 없으면 끝나는 반복문
		while(!((line = reader.readLine())==null)) {
			//json 데이터를 sb에 담는다
			sb.append(line);
		}
		
		//sb에 담긴 스트링을 소스로 하는 json 객체 생성
		int basketNumber = Integer.parseInt(sb.toString());
		
		
		
		
		//로그인 되어 있지 않으면 메인으로 돌려보내기 이것은 생략 왜냐하면 로그인 검증을 하고 장바구니에 들어가서 실행해야 하기 때문에 로그인 검증 됐다고 판단하겠다
		if(!super.checkLogin(request)) {
			System.out.println("로그인 안되어짐");
			super.setRedirect(true);
			super.setViewPage("/main.trd");
			return;
		}
		
		//결과를 알기위한 변수
		String result = null;
		//멤버 넘버
		int memberNum = member.getPk_member_no();
		
		//post 방식일 때
		if("post".equalsIgnoreCase(method)) {
			//삭제 함수 실행
			result = bskDao.delectBasketProduct(basketNumber, memberNum);
			
			request.setAttribute("result", result);
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/basket/jsonview.jsp");
			
		}
		

	}

}
