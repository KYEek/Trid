package admin.controller.product;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Constants;
import common.component.PagingComponent;
import common.controller.AbstractController;
import common.domain.PagingDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.domain.CategoryDTO;
import product.domain.ProductDTO;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;
import util.StringUtil;

/*
 * 관리자 상품 리스트 조회 컨트롤러
 */
public class ProductManageController extends AbstractController {
	
	private final ProductDAO productDAO = new ProductDAO_imple(); // ProductDAO 초기화

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod(); // HTTP 메소드
		
		// 유효한 관리자 접근 검증
		if(!super.checkAdminLogin(request)) {
			return;
		}
		
		// POST 요청인 경우 동일 URL에 GET 요청으로 리다이렉트
		if("POST".equalsIgnoreCase(method)) {
			super.setRedirect(true);
			super.setViewPage(Constants.ADMIN_PRODUCT_MANAGE_URL);
		}
		// GET 요청인 경우
		else {
			int curPage = 1; // 현재 페이지 초기화

			// curPage가 정수인지 예외처리
			try {
				curPage = Integer.parseInt(request.getParameter("curPage"));
			} catch (NumberFormatException e) {}
			
			Map<String, Object> paraMap = createParaMap(request);  // URL 파라미터에서 받은 값을 Map에 저장
			
			// 상품 리스트 가져오기
			try {
				int totalRowCount = productDAO.selectTotalRowCount(paraMap); // 전체 행 개수 조회
				
				PagingDTO pagingDTO = PagingComponent.createPaging(curPage, totalRowCount); // 페이징 관련 정보가 저장된 DTO 생성
				
				paraMap.put("pagingDTO", pagingDTO);
				
				List<ProductDTO> productList = productDAO.selectProductList(paraMap); // 상품 리스트 조회

				request.setAttribute("productList", productList);
				request.setAttribute("pagingDTO", pagingDTO);
				request.setAttribute("searchWord", paraMap.get("searchWord"));
				request.setAttribute("categoryNo", paraMap.get("categoryNo"));
				request.setAttribute("sortCategory", paraMap.get("sortCategory"));
				request.setAttribute("priceMin", paraMap.get("priceMin"));
				request.setAttribute("priceMax", paraMap.get("priceMax"));
				request.setAttribute("dateMin", paraMap.get("dateMin"));
				request.setAttribute("dateMax", paraMap.get("dateMax"));
				
				super.setRedirect(false);
				super.setViewPage(Constants.ADMIN_PRODUCT_MANAGE_PAGE);
				
			} 
			catch (SQLException e) {
				e.printStackTrace();
				super.handleServerError();
			}
			
		}

	}
	
	/*
	 * URL 파라미터에서 받은 값을 Map에 저장하여 반환하는 메소드
	 */
	private Map<String, Object> createParaMap(HttpServletRequest request) {
		Map<String, Object> paraMap = new HashMap<>();
		
		paraMap.put("searchWord", request.getParameter("searchWord")); // 상품명 검색어
		paraMap.put("categoryNo", request.getParameter("categoryNo")); // 카테고리 일련번호
		paraMap.put("sortCategory", request.getParameter("sortCategory")); // 정렬 타입 0:최신순, 1:오래된순, 2:높은 가격순, 3:낮은 가격순
		paraMap.put("priceMin", request.getParameter("priceMin")); // 최소 가격
		paraMap.put("priceMax", request.getParameter("priceMax")); // 최대 가격
		paraMap.put("dateMin", request.getParameter("dateMin")); // 최소 일자
		paraMap.put("dateMax", request.getParameter("dateMax")); // 최대 일자
		
		return paraMap;
	}

}
