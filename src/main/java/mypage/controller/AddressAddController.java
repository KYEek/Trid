package mypage.controller;

import common.Constants;
import mypage.model.*;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import mypage.domain.AddressDTO;

public class AddressAddController extends AbstractController {

	AddressDTO addrDto = new AddressDTO();
	AddressDAO addrDao = new AddressDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO)session.getAttribute("loginuser");
		
		if("post".equalsIgnoreCase(method) ) {
			
			addrDto.setPost_no(Integer.parseInt(request.getParameter("zipNo")));
			addrDto.setAddress(request.getParameter("address"));
			addrDto.setDetail(request.getParameter("addr_detail"));
			addrDto.setExtraaddr(request.getParameter("extraAddress"));
			addrDto.setMember_no(member.getPk_member_no());
//			addrDto.setMember_name(member.getMember_name());
//			addrDto.setAddr_mobile(member.getMember_mobile());
			
			int result = addrDao.insertAddress(addrDto);
			
			
			if(result == 0) {
				String message ="주소 추가가 실패했습니다.";
				String loc = Constants.MY_ADDRESS_ADD;
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage(Constants.MESSAGE_PAGE);
				
				
			}
			else {
				super.setRedirect(true);
				super.setViewPage(Constants.MY_ADDRESS_URL);
				
			}
			
		}
		else {

			
			super.setRedirect(false);
			super.setViewPage(Constants.MY_ADDRESS_ADD);
		}
		

	}

}
