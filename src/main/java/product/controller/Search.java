package product.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.domain.ProductDTO;

import java.util.List;

import product.model.*;

public class Search extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

	    // 추천 상품 호출 (전체 랜덤 출력)
	    List<ProductDTO> recommendProductList = pdao.RandomProducts(); // 전체 상품 랜덤 정렬
	    request.setAttribute("recommendProductList", recommendProductList);
        
        // View 설정
        super.setRedirect(false);
        super.setViewPage("/WEB-INF/product/search.jsp");
	}

}
