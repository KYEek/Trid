package orders.controller;

import java.io.BufferedReader;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberDTO;
import orders.model.*;

public class OrdersController extends AbstractController {

	private OrderDAO odao = new OrderDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("loginuser");
		//주문 정보를 담기 위한 배열
		JSONArray orderList = null;
		int startNum = 1;
		
		
		
		//로그인 되어 있지 않으면 메인으로 돌려보내기
		if(!super.checkLogin(request)) {
			System.out.println("로그인 안되어짐");
			super.setRedirect(true);
			super.setViewPage("/main.trd");
			return;
		}
		
		//get 요청일 때 (처음 페이지로 왔을 때)
		if("get".equalsIgnoreCase(method)) {
			try {
				//sql에서 주문 값을 가져오기
				orderList = odao.selectOrderListByMember(member.getPk_member_no(), startNum);
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
		//post요청 시 (무한 스크롤 시)
		else {
			try {
				System.out.println("무한스크롤 실행");
				StringBuilder strb = new StringBuilder();
				try (BufferedReader bffr = request.getReader()){
					String line;
					while((line = bffr.readLine()) != null) {
						strb.append(line);
					}
				}
				JSONObject startNumJson = new JSONObject(strb.toString());
				startNum = startNumJson.getInt("startNum");
				//System.out.println(startNum);
				
				//sql에서 주문 값을 가져오기
				orderList = odao.selectOrderListByMember(member.getPk_member_no(), startNum);
				request.setAttribute("orderList", orderList.toString());
				
				//System.out.println(orderList);
				request.setAttribute("result", orderList);
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/basket/jsonview.jsp");
			}
			//sql 오류시 오류페이지로 이동
			catch(SQLException e) {
				e.printStackTrace();
				
				super.setRedirect(false);
				super.setViewPage(request.getContextPath()+"/error.jsp");
			}
		}
		
	}

}
