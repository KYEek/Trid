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
	
	// DTO, DAO 변수 생성
	AddressDTO addrDto = new AddressDTO();
	AddressDAO addrDao = new AddressDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO)session.getAttribute("loginuser");
		
		
		// 로그인 되어 있지 않으면 메인으로 돌려보내기
		if (!super.checkLogin(request)) {
			System.out.println("로그인 안되어짐");
			super.setRedirect(true);
			super.setViewPage("/main.trd");
			return;
		}
		//포스트 방식일 경우 실행
		if("post".equalsIgnoreCase(method) ) {
			//폼에서 받은 값들을 저장
			addrDto.setPost_no(Integer.parseInt(request.getParameter("zipNo")));
			addrDto.setAddress(request.getParameter("address"));
			addrDto.setDetail(request.getParameter("addr_detail"));
			addrDto.setExtraaddr(request.getParameter("extraAddress"));
			addrDto.setMember_no(member.getPk_member_no());
//			addrDto.setMember_name(member.getMember_name());
//			addrDto.setAddr_mobile(member.getMember_mobile());
			
			//sql문 실행
			int result = addrDao.insertAddress(addrDto);
			
			// 삽입이 실패할 경우 메시지 출력 후 다시 복귀
			if(result == 0) {
				String message ="주소 추가가 실패했습니다.";
				String loc = Constants.MY_ADDRESS_ADD;
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage(Constants.MESSAGE_PAGE);
				
				
			}
			// 삽입 성공후 주소 페이지로 복귀
			else {
				super.setRedirect(true);
				super.setViewPage(Constants.MY_ADDRESS_URL);
				
			}
			
		}
		//Get방식으로 접근 시 되돌려 보내기
		else {

			
			super.setRedirect(false);
			super.setViewPage(Constants.MY_ADDRESS_ADD);
		}
		

	}

}
