package product.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import board.domain.BoardDTO;
import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.domain.ProductDTO;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

public class DetailController extends AbstractController {

	private final ProductDAO productDAO = new ProductDAO_imple(); // ProductDAO 초기화
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // HTTP method
		
		/*
		String referer = request.getHeader("referer");
		// referer = request.getHeader("referer"); 은 이전 페이지의 URL 을 가져오는 것이다.

		if (referer == null) {
			// referer == null 은 웹브라우저 주소창에 URL 을 직접 입력하고 들어온 경우
			super.handleMessage(request, "잘못된 접근입니다.", Constants.CATEGORY_LIST_PAGE);
			return;
		}
		*/
		
		// GET 요청인 경우
		if("GET".equalsIgnoreCase(method)) {

			// 상품 상세 번호
			String productNo = request.getParameter("productNo");
			
			try {
				// 상품 상세 알아오기
				ProductDTO productDTO = productDAO.selectProductByMember(productNo);
				// 추천 상품 리스트 뽑기
				List<Map<String, String>> recommendProductMapList = productDAO.selectRecommendProductList(productNo);
				
				request.setAttribute("productDTO", productDTO);
				request.setAttribute("recommendProductMapList", recommendProductMapList);
				
				// productDTO가 존재하지 않는다면?
				if(productDTO == null) {
					super.handleMessage(request, "요청하신 상품이 존재하지 않습니다.", Constants.CATEGORY_LIST_URL);
				}
				else {
					super.setRedirect(false);
					super.setViewPage(Constants.PRODUCT_DETAIL_PAGE);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				super.setRedirect(true);
				super.setViewPage(Constants.ERROR_URL);
			}
			
		}
		// GET 요청이 아닌 경우 상품 리스트 페이지로 이동
		else {
			super.setRedirect(true);
			super.setViewPage(Constants.CATEGORY_LIST_PAGE);
		}

		
		
	}

}
