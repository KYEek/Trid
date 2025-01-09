package product.controller;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.domain.ProductDTO;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import product.model.*;

public class SearchList extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, String> paraMap = new HashMap<>();
		
	    // 검색어 처리
	    String searchWord = request.getParameter("search_word");
	    paraMap.put("search_word", searchWord != null && !searchWord.trim().isEmpty() ? searchWord.trim() : "");
    	
	    // 페이지 처리
	    String pageNumberStr = request.getParameter("pageNumber") == null ? "1" : request.getParameter("pageNumber");
	    
	    paraMap.put("pageNumber", pageNumberStr);
	    paraMap.put("pageSize", "18");
        
        try {
        	int searchRowCount = pdao.searchCountProductByCategory(paraMap);
        	
            // 검색 결과 호출용도
            List<ProductDTO> searchResult = pdao.searchProduct(paraMap);
            request.setAttribute("searchWord", searchWord);
            request.setAttribute("searchProductList", searchResult);
            request.setAttribute("searchProductCount", searchResult.size());
            
            // 서버로 전달된 값 확인
            System.out.println("검색 단어: " + paraMap.get("search_word"));
            System.out.println("페이지 번호: " + paraMap.get("pageNumber"));
            System.out.println("페이지 크기: " + paraMap.get("pageSize"));
            
            // 요청이 AJAX 요청인지 확인
            String ajaxRequest = request.getHeader("ajaxHeader");
            
            if ("true".equals(ajaxRequest)) {
            	
                // JSON 응답 설정
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                
                // JSON 배열 생성
                JSONArray jsonArray = new JSONArray();
                
                System.out.println("JSON 배열 안 개수 : " + searchResult.size());
                
                for (ProductDTO product : searchResult) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("searchRowCount", searchRowCount);
                    jsonObject.put("productName", product.getProductName());
                    jsonObject.put("productNo", product.getProductNo());
                    jsonObject.put("price", product.getPrice());
                    jsonObject.put("imagePath", product.getImageList().get(0).getImagePath());
                    jsonArray.put(jsonObject);
                }
                
                super.setJsonResponse(true);
                
                System.out.println("상품 개수 : " + searchRowCount);
                
				/*
				 * // 현재 불러온 페이지 데이터와 전체 상품 개수(totalRowCount) 함께 반환 JSONObject result = new
				 * JSONObject(); result.put("products", jsonArray); result.put("searchRowCount",
				 * searchRowCount);
				 */
				
				System.out.println("검색된 상품의 총 개수  : " + searchRowCount );
		        System.out.println("전달받은 검색어 : " + searchWord );
		        System.out.println("검색 결과 개수 : " + searchResult.size());
                
                // JSON 배열을 응답으로 전송
                PrintWriter out = response.getWriter();
                out.print(jsonArray.toString());
                out.flush();
            } else {
            	request.setAttribute("searchRowCount", searchRowCount);
            	request.setAttribute("searchResult", searchResult);
            	request.setAttribute("searchProductCount", searchResult.size());
                
                // View 설정
                super.setRedirect(false); // forward 방식
                super.setViewPage(Constants.SEARCH_LIST_PAGE); // 뷰 페이지 경로
            }
            
		} catch (Exception e) {
            e.printStackTrace();
            super.setRedirect(false); // forward 방식
            super.setViewPage(Constants.ERROR_PAGE); // 뷰 페이지 경로
		}
        
	}

}
