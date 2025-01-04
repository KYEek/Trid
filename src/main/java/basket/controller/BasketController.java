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

			//제품상세 번호 변수
			int ProductDetailNum = Integer.parseInt(request.getParameter("go_basket"));
			
			//기존에 있는 상품인지 확인하기 위한 변수
			int isDuplicate = bskDao.checkDuplicate(ProductDetailNum, member.getPk_member_no());
			
			//기존에 중복되는 상품이 있는경우 1 추가해준다
			if(isDuplicate > 0) {
				String result = bskDao.incrementBasketQuantity(isDuplicate, member.getPk_member_no());
				//삽입이 정상적으로 된 경우
				if("success".equalsIgnoreCase(result)) {

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
					request.setAttribute("loc", "/Trid/");
					
					super.setRedirect(false);
					super.setViewPage(Constants.MESSAGE_PAGE);
				}
			}
			//기존에 상품이 없으면 추가해준다
			else {
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
					request.setAttribute("loc", "/Trid/");
					
					super.setRedirect(false);
					super.setViewPage(Constants.MESSAGE_PAGE);
				}
			}
			
			
			
		}
		//get 방식일 때
		//상품리스트를 간단히 불러온다
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
