package payment.controller;

import org.json.JSONArray;
import org.json.JSONObject;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import mypage.model.AddressDAO;
import mypage.model.AddressDAO_imple;
import payment.model.Payment_DAO;
import payment.model.Payment_DAO_imple;

public class PaymentAddressPage extends AbstractController {

	AddressDAO addr_dao = new AddressDAO_imple();
	Payment_DAO pdao = new Payment_DAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("loginuser");
		String instantPay = "false";
		//바로 결제에서 받은 값을 저장 없으면 null
		instantPay = request.getParameter("instantPay");
		
		//로그인 되어 있지 않으면 메인으로 돌려보내기
		if(!super.checkLogin(request)) {
			System.out.println("로그인 안되어짐");
			super.setRedirect(true);
			super.setViewPage("/main.trd");
			return;
		}
		
		//장바구니에서 들어온 경우
		if(instantPay == null) {
			//주소들을 불러와 json배열에 저장
			JSONArray addrList = addr_dao.selectAddrs(member.getPk_member_no());
			// 주소가 있을 때
			if(addrList.length() != 0) {
				//페이지에 보내기 위해 스트링으로 변경
				String addrList_str = addrList.toString();
				
				//리퀘스트에 저장
				request.setAttribute("addrList", addrList_str);
				//장바구니를 통해 들어왔다는 것을 전달
				request.setAttribute("instantPay", instantPay);
				
				//주소 페이지로 이동
				super.setRedirect(false);
				super.setViewPage(Constants.PAYMENT_ADDRESS);
				
			}
			//주소가 없으면 주소 생성 페이지로 이동
			else {
				sendToAddressAdd(request);
			}
		}
		
		//바로구매로 들어온 경우
		else {
			JSONArray addrList = addr_dao.selectAddrs(member.getPk_member_no());
			// 주소가 있을 때
			if(addrList.length() != 0) {
				//페이지에 보내기 위해 스트링으로 변경
				String addrList_str = addrList.toString();
				
				//리퀘스트에 저장
				request.setAttribute("addrList", addrList_str);
				
				
				//상품 상세번호 조회
				int productDetailNo = Integer.parseInt(request.getParameter("go_payment"));
				
				//페이지에 보내기 위해 스트링으로 변경
				JSONObject json = pdao.selectProductInfo(productDetailNo);
				String jsonStr = json.toString();
				
				//리퀘스트에 저장
				request.setAttribute("productInfo", jsonStr);
				//바로결제를 통해 들어왔다는 것들 전달
				request.setAttribute("instantPay", instantPay);
				
				//주소 페이지로 이동
				super.setRedirect(false);
				super.setViewPage(Constants.PAYMENT_ADDRESS);
				
			}
			//주소가 없으면 주소 생성 페이지로 이동
			else {
				sendToAddressAdd(request);
			}
			
		}
		
	

		

	}//end of execute ---------------------------------------------------

	
	
	//주소 생성페이지로 이동
	private void sendToAddressAdd(HttpServletRequest request) {
		request.setAttribute("message", "주소가 없어서 주소 생성 페이지로 이동합니다");
        request.setAttribute("loc", "/Trid/member/addressAdd.trd");
         
      // super.setRedirect(false);
         super.setViewPage("/WEB-INF/msg.jsp");
		
	}//end of sendToAddressAdd-------------------------------------------

}
