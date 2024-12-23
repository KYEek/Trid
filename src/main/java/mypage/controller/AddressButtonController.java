package mypage.controller;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;

public class AddressButtonController extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("loginuser");

		if ("post".equalsIgnoreCase(method)) {

			String action = request.getParameter("action");
			int address_no = Integer.parseInt(request.getParameter("address_no"));
			
			switch (action) {
			case value:
				
				break;

			default:
				break;
			}
			
			
//			System.out.println();
//			System.out.println();

		} else {

			super.setRedirect(false);
			super.setViewPage(Constants.MY_ADDRESS_ADD);
		}

	}

}
