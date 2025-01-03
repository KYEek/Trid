package product.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.domain.ProductDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import product.model.*;

public class SearchList extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, String> paraMap = new HashMap<>();
		
    	// 검색어 처리 하기
    	String searchWord = request.getParameter("search_word");
    	if (searchWord != null && !searchWord.trim().isEmpty()) {
    	    paraMap.put("search_word", searchWord.trim());
    	} else {
            System.out.println("검색어가 전달되지 않았습니다. search_word = " + searchWord);
        }
    	
        // 검색 결과 호출 용
        List<ProductDTO> searchResult = pdao.searchProduct(paraMap);
        request.setAttribute("searchWord", searchWord);
        request.setAttribute("searchProductList", searchResult);
        request.setAttribute("searchProductCount", searchResult.size());
        
        System.out.println("전달받은 검색어 : " + searchWord );
        System.out.println("검색 결과 개수 : " + searchResult.size());
        
        // View 설정
        super.setRedirect(false);
        super.setViewPage("/WEB-INF/product/search_list.jsp");
	}

}
