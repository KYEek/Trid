package mypage.controller;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddressController extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
//		if(!super.checkLogin(request)) {
//			super.setRedirect(false);
//			super.setViewPage(request.getContextPath());
//			return;
//		}
		
		super.setRedirect(false);
		super.setViewPage(Constants.MY_ADDRESS);

	}

}
