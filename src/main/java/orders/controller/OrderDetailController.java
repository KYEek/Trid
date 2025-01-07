package orders.controller;

import java.sql.SQLException;

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
import orders.model.OrderDAO;
import orders.model.OrderDAO_imple;

public class OrderDetailController extends AbstractController {

	OrderDAO odao = new OrderDAO_imple();
	AddressDAO addrdao = new AddressDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod();
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("loginuser");
		JSONArray orderDetail = null;
		
		//조회할 주문 번호
		int orderNO = Integer.parseInt(request.getParameter("orderNo"));

		// 로그인 되어 있지 않으면 메인으로 돌려보내기
		if (!super.checkLogin(request)) {
			System.out.println("로그인 안되어짐");
			super.setRedirect(true);
			super.setViewPage("/main.trd");
			return;
		}	
		
		
		try {
			// sql에서 주문 내용 가져오기
			orderDetail = odao.selectOrderDetail(member.getPk_member_no(), orderNO);
			
			
			
			
			//json배열에서 주소번호를 추출하기
			JSONObject addrNoJSON = (JSONObject)orderDetail.get(0);
			int addrNo = (int)addrNoJSON.get("FK_ADDR_NO");
			//멤버넘버 가져오기
			int member_no = addrNoJSON.getInt("fk_member_no");
			
			//로그인 한 사람과 같은 사람일 경우
			if(member_no == member.getPk_member_no()) {
			
				//주소 정보 가져오기
				JSONObject addrInfo =  addrdao.selectOneAddr(addrNo, member.getPk_member_no());
				
				//주소완료 페이지에서 온경우에는 주소 데이터를 삭제	
		    	session.removeAttribute("temp_address_info");
				
				//데이터값 전송
				request.setAttribute("orderDetail", orderDetail.toString());
				request.setAttribute("addrInfo", addrInfo.toString());
		
				super.setRedirect(false);
				super.setViewPage(Constants.ORDER_DETAIL_PAGE);
				
				
			}
			//로그인 한 사람과 다른 사람인경우
			else {
				System.out.println("다른 사람의 구매내역에 접근했습니다.");

				super.setRedirect(false);
				super.setViewPage(request.getContextPath() + "/error.jsp");
			}
			
		}
		// sql 오류시 오류페이지로 이동
		catch (SQLException e) {
			e.printStackTrace();

			super.setRedirect(false);
			super.setViewPage(request.getContextPath() + "/error.jsp");
		}

	}

}
