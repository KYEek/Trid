package basket.controller;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import basket.domain.BasketDTO;
import basket.model.*;

public class BasketController extends AbstractController {

	BasketDAO bskDao = new BasketDAO_imple();
	BasketDTO bskDTO = null;
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("loginuser");
		JSONArray basketList = null;
		
		
		//로그인 되어 있지 않으면 메인으로 돌려보내기
		if(!super.checkLogin(request)) {
			System.out.println("로그인 안되어짐");
			super.setRedirect(true);
			super.setViewPage("/main.trd");
			return;
		}
		
		//post 방식일 때
		if("post".equalsIgnoreCase(method)) {

			int ProductDetailNum = Integer.parseInt(request.getParameter("go_basket"));
			
			int result = bskDao.insertBasket(ProductDetailNum, member.getPk_member_no());
			//삽입이 정상적으로 된 경우
			if(result == 1) {

				//json으로 상품 리스트를 가져온다
				basketList = bskDao.selectBasketList(member.getPk_member_no());
				//josn을 문자로 변경
				String basketListStr = basketList.toString();
				//이스케이프 문자 오류 때문에 변환 가능하도록 변경
				basketListStr = basketListStr.replace("\\", "\\\\");
				
				
				//json파일을 전송
				request.setAttribute("basketList", basketListStr);

				super.setRedirect(false);
				super.setViewPage(Constants.BASKET_PAGE);
			}
			//정상적으로 삽입 실패 시 
			else {
				
				request.setAttribute("message", "장바구니 입력이 정상적으로 되지 않았습니다.");
				request.setAttribute("loc", request.getHeader("Referer"));
				
				super.setRedirect(false);
				super.setViewPage(Constants.MESSAGE_PAGE);
			}
			
			
			
		}
		//get 방식일 때
		else {
			
			//json으로 상품 리스트를 가져온다
			basketList = bskDao.selectBasketList(member.getPk_member_no());
			//josn을 문자로 변경
			String basketListStr = basketList.toString();
			//이스케이프 문자 오류 때문에 변환 가능하도록 변경
			basketListStr = basketListStr.replace("\\", "\\\\");
			
			
			//json파일을 전송
			request.setAttribute("basketList", basketListStr);
			
			
			super.setRedirect(false);
			super.setViewPage(Constants.BASKET_PAGE);
		}
		

	}

}
