package admin.controller.product;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Constants;
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
		
		// POST인 경우 상품 관리 페이지 URL로 리다이렉트
		if("POST".equalsIgnoreCase(method)) {
			super.setRedirect(true);
			super.setViewPage(Constants.ADMIN_PRODUCT_MANAGE_URL);
		}
		else {
			int curPage = 1; // 현재 페이지 초기화

			// curPage가 정수인지 예외처리
			try {
				curPage = Integer.parseInt(request.getParameter("curPage"));
			} catch (NumberFormatException e) {
				curPage = 1;
			}
			
			String searchWord = request.getParameter("searchWord");
			
			String categoryNo = request.getParameter("categoryNo");
			
			String sortCategory = request.getParameter("sortCategory");
			
			String priceMin = request.getParameter("priceMin");
			
			String priceMax = request.getParameter("priceMax");
			
			String dateMin = request.getParameter("dateMin");
			
			String dateMax = request.getParameter("dateMax");
			
			Map<String, Object> paraMap = new HashMap<>();
		
			paraMap.put("searchWord", searchWord);
			paraMap.put("categoryNo", categoryNo);
			paraMap.put("sortCategory", sortCategory);
			paraMap.put("priceMin", priceMin);
			paraMap.put("priceMax", priceMax);
			paraMap.put("dateMin", dateMin);
			paraMap.put("dateMax", dateMax);
			
			// 상품 리스트 가져오기
			try {
				
				PagingDTO pagingDTO = new PagingDTO(); // PagingDTO 초기화
				
				int totalRowCount = productDAO.selectTotalRowCount(paraMap); // 전체 행 개수 조회
				
				// curPage 유효성 검사
				if(curPage > totalRowCount || curPage < 1 ) {
					curPage = 1;
				}
				
				pagingDTO.setCurPage(curPage);
				pagingDTO.setTotalRowCount(totalRowCount);
				pagingDTO.setPageSize(5);
				pagingDTO.setRowSizePerPage(6);
				pagingDTO.pageSetting(); // 페이징 시 필요한 나머지 정보 계산
				
				paraMap.put("pagingDTO", pagingDTO);
				
				List<ProductDTO> productList = productDAO.selectProductList(paraMap);
				List<CategoryDTO> categoryList = productDAO.selectCategoryList();

				request.setAttribute("categoryList", categoryList);
				request.setAttribute("productList", productList);
				request.setAttribute("pagingDTO", pagingDTO);
				
				request.setAttribute("searchWord", searchWord);
				request.setAttribute("categoryNo", categoryNo);
				request.setAttribute("sortCategory", sortCategory);
				request.setAttribute("priceMin", priceMin);
				request.setAttribute("priceMax", priceMax);
				request.setAttribute("dateMin", dateMin);
				request.setAttribute("dateMax", dateMax);
				
				super.setRedirect(false);
				super.setViewPage(Constants.ADMIN_PRODUCT_MANAGE_PAGE);
				
			} 
			catch (SQLException e) {
				e.printStackTrace();
				super.setRedirect(true);
				super.setViewPage(Constants.ERROR_URL);
			}
			
		}

	}

}
