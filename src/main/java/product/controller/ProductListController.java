package product.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;



import common.Constants;
import product.domain.ProductDTO;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

public class ProductListController extends AbstractController {

	private ProductDAO pdao = new ProductDAO_imple();
	
	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
            
        Map<String, String> paraMap = new HashMap<>();

        // 가격 기본, 필터 값 설정
        String price = request.getParameter("choosePrice");
        if (price != null && !price.isEmpty()) {
            paraMap.put("choosePrice", price);
        } else {
            paraMap.put("choosePrice", "200000"); // 기본값
        }
        
        // 색상 필터 추가
        String[] colorArray = request.getParameterValues("chooseColor[]");
        if (colorArray != null) {
            paraMap.put("chooseColor", String.join(",", colorArray));
        }
        
        // 페이지 번호와 페이지당 항목 수 처리
        String pageNumberStr = request.getParameter("pageNumber");
        String pageSizeStr = request.getParameter("pageSize");
        int pageNumber = (pageNumberStr != null && !pageNumberStr.isEmpty()) ? Integer.parseInt(pageNumberStr) : 1;
        int pageSize = (pageSizeStr != null && !pageSizeStr.isEmpty()) ? Integer.parseInt(pageSizeStr) : 18;
        paraMap.put("pageNumber", String.valueOf(pageNumber));
        paraMap.put("pageSize", String.valueOf(pageSize));
    
        try {
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
                for (ProductDTO product : productList) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("productNo", product.getProductNo());
                    jsonObject.put("productName", product.getProductName());
                    jsonObject.put("price", product.getPrice());
                    jsonObject.put("imagePath", product.getImageList().get(0).getImagePath());
                    jsonArray.put(jsonObject);
                }

                System.out.println("chooseColor: " + paraMap.get("chooseColor"));
                System.out.println("choosePrice: " + paraMap.get("choosePrice"));
                
                super.setJsonResponse(true);
                
                // JSON 배열을 응답으로 전송
                PrintWriter out = response.getWriter();
                out.print(jsonArray.toString());
                out.flush();
            } else {
                request.setAttribute("productList", productList);
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
