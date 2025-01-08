package orders.controller;

import java.sql.SQLException;

import org.json.JSONArray;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import orders.model.*;

public class OrdersController extends AbstractController {

	OrderDAO odao = new OrderDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("loginuser");
		//주문 정보를 담기 위한 배열
		JSONArray orderList = null;
		
		
		
		//로그인 되어 있지 않으면 메인으로 돌려보내기
		if(!super.checkLogin(request)) {
			System.out.println("로그인 안되어짐");
			super.setRedirect(true);
			super.setViewPage("/main.trd");
			return;
		}
		
		try {
		//sql에서 주문 값을 가져오기
		orderList = odao.selectOrderListByMember(member.getPk_member_no());
		request.setAttribute("orderList", orderList.toString());
		
		super.setRedirect(false);
		super.setViewPage(Constants.ORDERS_PAGE);
		}
		//sql 오류시 오류페이지로 이동
		catch(SQLException e) {
			e.printStackTrace();
			
			super.setRedirect(false);
			super.setViewPage(request.getContextPath()+"/error.jsp");
		}
	}

}
