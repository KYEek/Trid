package product.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import common.Constants;
import product.domain.CategoryDTO;
import product.domain.ProductDTO;

import product.model.ProductDAO;
import product.model.ProductDAO_imple;
import util.StringUtil;

public class ProductListController extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
            
        Map<String, String> paraMap = new HashMap<>();
        
        // 카테고리 필터
        
        // 성별 필터
        String chooseGender = request.getParameter("chooseGender");
        paraMap.put("chooseGender", chooseGender);
        request.setAttribute("chooseGender", chooseGender);
        
        // 상 하의 필터
        String chooseType = request.getParameter("chooseType");
        paraMap.put("chooseType", chooseType);
        request.setAttribute("chooseType", chooseType);
        
        // 카테고리 번호 가져오기
        String chooseCategoryNo = request.getParameter("chooseCategoryNo");
        paraMap.put("chooseCategoryNo", chooseCategoryNo);
        request.setAttribute("chooseCategoryNo", chooseCategoryNo);
        
        // 가격 기본, 필터 값 설정
        String choosePrice = request.getParameter("choosePrice");
        paraMap.put("choosePrice", choosePrice);
        
        // 색상 필터 추가
        String[] colorArray = request.getParameterValues("chooseColor[]");
        
        if(colorArray != null) {
            paraMap.put("chooseColor", String.join(",", colorArray));
            System.out.println("색상 담기 : " + String.join(",", colorArray));
        }
        else {
        	paraMap.put("chooseColor", null);
        }
        
       	System.out.println("카테고리넘버 : " + chooseCategoryNo);
        System.out.println("paraMap.get 카테고리 넘버 : " + paraMap.get("chooseCategoryNo"));
    
        
        // 페이지 번호와 페이지당 항목 수 처리
        String pageNumberStr = request.getParameter("pageNumber") == null ? "1" : request.getParameter("pageNumber");
        
        paraMap.put("pageNumber", pageNumberStr);
        paraMap.put("pageSize", "18");
        
        
        try {           
        	int totalRowCount = pdao.selectCountProductByCategory(paraMap);
        	
        	// 상품 리스트 데이터 조회
            List<ProductDTO> productList = pdao.selectProductByCategory(paraMap);
            request.setAttribute("productList", productList);

            // 요청이 AJAX 요청인지 확인
            String ajaxRequest = request.getHeader("ajaxHeader");
            
            if ("true".equals(ajaxRequest)) {

                // JSON 응답 설정
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                // JSON 배열 생성
                JSONArray jsonArray = new JSONArray();
                
                System.out.println("개수 : " + productList.size());
                
                for (ProductDTO product : productList) {
                	
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("totalRowCount", totalRowCount);
                    jsonObject.put("productNo", product.getProductNo());
                    jsonObject.put("productName", product.getProductName());
                    jsonObject.put("price", product.getPrice());
                    jsonObject.put("imagePath", product.getImageList().get(0).getImagePath());
                    jsonArray.put(jsonObject);
                }

                System.out.println("chooseColor: " + paraMap.get("chooseColor"));
                System.out.println("choosePrice: " + paraMap.get("choosePrice"));
                System.out.println("chooseGender: " + paraMap.get("chooseGender"));
                System.out.println("chooseType: " + paraMap.get("chooseType"));
                System.out.println("chooseCategoryNo: " + paraMap.get("chooseCategoryNo"));
                
                super.setJsonResponse(true);
                
                System.out.println("상품 개수 : " + totalRowCount);
                
                // JSON 배열을 응답으로 전송
                PrintWriter out = response.getWriter();
                out.print(jsonArray.toString());
                out.flush();
            } else {
                request.setAttribute("productList", productList);
                request.setAttribute("totalRowCount", totalRowCount);
                // View 설정
                super.setRedirect(false); // forward 방식
                super.setViewPage(Constants.CATEGORY_LIST_PAGE); // 뷰 페이지 경로
            }

        } catch (SQLException e) {
            e.printStackTrace();

            super.setRedirect(false); // forward 방식
            super.setViewPage(Constants.ERROR_PAGE); // 뷰 페이지 경로
        }
    }
}
