package mypage.controller;

import org.json.JSONObject;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import mypage.domain.AddressDTO;
import mypage.model.*;

public class AddressController extends AbstractController {

	AddressDAO addr_dao = new AddressDAO_imple();
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO)session.getAttribute("loginuser");
		
//		if(!super.checkLogin(request)) {
//			super.setRedirect(false);
//			super.setViewPage(request.getContextPath());
//			return;
//		}
		
		JSONObject addrList = addr_dao.selectAddrs(member.getPk_member_no());
	
		
		
		
		
		
		super.setRedirect(false);
		super.setViewPage(Constants.MY_ADDRESS);

	}

}
