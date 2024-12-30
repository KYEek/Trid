package payment.controller;

import org.json.JSONArray;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import mypage.model.AddressDAO;
import mypage.model.AddressDAO_imple;

public class PaymentAddressPage extends AbstractController {

	AddressDAO addr_dao = new AddressDAO_imple();
	
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
		
		JSONArray addrList = addr_dao.selectAddrs(member.getPk_member_no());
		
		String addrList_str = addrList.toString();
		
		
		request.setAttribute("addrList", addrList_str);

		
		
		super.setRedirect(false);
		super.setViewPage(Constants.PAYMENT_ADDRESS);
		

	}

}
