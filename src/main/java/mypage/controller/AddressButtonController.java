package mypage.controller;

import org.json.JSONObject;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import mypage.domain.AddressDTO;
import mypage.model.AddressDAO;
import mypage.model.AddressDAO_imple;

public class AddressButtonController extends AbstractController {
	
	AddressDAO addrDao = new AddressDAO_imple();
	AddressDTO addrDto = new AddressDTO();
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("loginuser");
		JSONObject addrJson = null;

		if ("post".equalsIgnoreCase(method)) {
			//어떤 버튼을 눌렀는지 알기 위한 변수
			String action = request.getParameter("action");
			
			//버튼이 눌린 주소 번호를 저장하는 변수
			int addrNo = Integer.parseInt(request.getParameter("address_no"));
			switch (action) {
			//편집 버튼을 눌렀을 경우
			case "edit":
				//주소 정보를 조회
				addrJson = addrDao.selectOneAddr(addrNo, member.getPk_member_no());
				//편집페이지에 현재 선택한 페이지의 번호를 전송
				request.setAttribute("address_no", request.getParameter("address_no"));
				//주소 정보가 정상적으로 잘 들어올 경우
				if(addrJson != null) {
					
					String addr = addrJson.toString();
					//가져온 주소를 보냅니다.
					request.setAttribute("addr", addr);
					
					//주소 편집 페이지로 이동
					super.setRedirect(false);
					super.setViewPage(Constants.MY_ADDRESS_EDIT);
					break;
				}
				//뭔가 잘 안됐을 때
				else {
					super.setRedirect(true);
					super.setViewPage(Constants.MY_ADDRESS_URL);
					break;
				}
				
				
			//편집 버튼을 입력할 경우
			case "editComplete":
				addrDto.setAddr_no(Integer.parseInt(request.getParameter("address_no")));
				addrDto.setPost_no(Integer.parseInt(request.getParameter("zipNo")));
				addrDto.setAddress(request.getParameter("address"));
				addrDto.setDetail(request.getParameter("addr_detail"));
				addrDto.setExtraaddr(request.getParameter("extraAddress"));
				addrDto.setMember_no(member.getPk_member_no());
//				addrDto.setMember_name(member.getMember_name());
//				addrDto.setAddr_mobile(member.getMember_mobile());
				
				int result = addrDao.updateAddress(addrDto);
				
				//편집이 정상적으로 이루어 지지 않았다면 실행
				if(result == 0) {
					String message ="주소 추가가 실패했습니다.";
					String loc = Constants.MY_ADDRESS_ADD;
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
					super.setRedirect(false);
					super.setViewPage(Constants.MESSAGE_PAGE);
					
					
				}
				//편집이 정상적으로 실행이 되면 주소 페이지로 복귀
				else {
					super.setRedirect(true);
					super.setViewPage(Constants.MY_ADDRESS_URL);
					
				}
				break;
				
			//기본 주소 설정을 눌렀을 경우
			case "setDefault":
				
				addrDao.setDefault(addrNo, member.getPk_member_no());
				
				
				super.setRedirect(true);
				super.setViewPage(Constants.MY_ADDRESS_URL);
				
				break;
				
			//삭제 버튼을 눌렀을 경우
			case "delete":

				addrDao.deleteAddr(addrNo, member.getPk_member_no());

				super.setRedirect(true);
				super.setViewPage(Constants.MY_ADDRESS_URL);

				break;

			}
			

		} 
		//get방식일 떄
		else {
			
			super.setRedirect(false);
			super.setViewPage(Constants.MY_ADDRESS_ADD);
		}

	}

}
