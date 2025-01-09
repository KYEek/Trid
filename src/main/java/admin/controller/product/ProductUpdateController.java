package admin.controller.product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.Constants;
import common.component.FileComponent;
import common.controller.AbstractController;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.domain.ImageDTO;
import product.domain.ProductDTO;
import product.domain.ProductDetailDTO;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

/*
 * 관리자 상품 수정 컨트롤러
 */
public class ProductUpdateController extends AbstractController {
	
	private final ProductDAO productDAO = new ProductDAO_imple(); // 상품 DAO 초기화

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod(); // HTTP 메소드

		// 관리자 요청 유효성 검사
		if (!super.checkAdminLogin(request)) {
			return;
		}

		// POST 요청인 경우 상품 수정 처리
		if ("POST".equalsIgnoreCase(method)) {
			
			try {
				// 이미지 파일을 지정된 경로에 저장 후 이미지 정보가 담긴 ImageDTO list 반환
				List<ImageDTO> imageList = FileComponent.saveImages(request); 
				
				String message = "";
				
				// 이미지를 제외한 정보 저장
				ProductDTO productDTO = createProductDTO(request);
				
				productDTO.setImageList(imageList);
				
				// DB에 상품 추가 요청
				int result = productDAO.updateProduct(productDTO);

				// 상품 수정 실패 시
				message = (result == 1) ? "success" : "failed";
				
				super.handelJsonResponse(response, message);

			} catch (SQLException | NumberFormatException | IOException | ServletException e ) {
				e.printStackTrace();
				super.handleServerError();
			}

		}
		// GET 요청인 경우 상품 수정 페이지로 이동, 기존 상품 정보를 조회
		else {
			String productNo = request.getParameter("productNo");
			
			try {
				ProductDTO productDTO = productDAO.selectProduct(productNo); // 상품 조회
				
				request.setAttribute("productDTO", productDTO);

				// TODO 좀 더 나은 방법 찾아보기
				int[] inventoryArr = new int[4];
				int[] productDetailNoArr = new int[4];
				
				for(ProductDetailDTO productDetailDTO : productDTO.getProductDetailList()) {
					inventoryArr[productDetailDTO.getSize()] = productDetailDTO.getInventory();
					productDetailNoArr[productDetailDTO.getSize()] = productDetailDTO.getPkProductDetailNo();
				}
				
				request.setAttribute("inventoryArr", inventoryArr);
				request.setAttribute("productDetailNoArr", productDetailNoArr);

				super.setRedirect(false);
				super.setViewPage(Constants.ADMIN_PRODUCT_UPDATE_JSP);
			
			} catch (SQLException e) {
				e.printStackTrace();
				super.handleServerError();
			}
			
		}

	}
	
	/*
	 * POST 요청 파라미터에서 받은 값들을 ProductDTO에 저장하여 반환하는 메소드
	 * ImageDTO, ColorDTO, CategoryDTO,  
	 */
	private ProductDTO createProductDTO(HttpServletRequest request) {
		// DTO, DTO List 초기화
		ProductDTO productDTO = new ProductDTO(); // 상품 DTO 초기화

		List<ProductDetailDTO> productDetailList = new ArrayList<>(); // 상품 상세 리스트

		// Request에서 정보 추출
		String[] inventoryArr = request.getParameter("inventory").split(","); // 재고 배열 (100, 100, 100, 100) 사이즈 순서에 맞추어 형성

		String[] productDetailNoArr = request.getParameter("productDetailNoArr").split(",");
		
		String productName = request.getParameter("productName"); // 상품 명

		String explanation = request.getParameter("explanation"); // 상품 설명

		int price = Integer.parseInt(request.getParameter("price")); // 상품 가격
		
		int productNo = Integer.parseInt(request.getParameter("productNo")); // 상품 일련번호
		
		
		// 사이즈 (0, 1, 2, 3) 순서에 맞추어 재고 입력
		for (int i = 0; i < 4; i++) {
			ProductDetailDTO productDetailDTO = new ProductDetailDTO();
			productDetailDTO.setPkProductDetailNo(Integer.parseInt(productDetailNoArr[i]));
			productDetailDTO.setSize(i);
			productDetailDTO.setInventory(Integer.parseInt(inventoryArr[i]));

			productDetailList.add(productDetailDTO);
		}

		productDTO.setProductName(productName);
		productDTO.setExplanation(explanation);
		productDTO.setPrice(price);
		productDTO.setProductNo(productNo);

		productDTO.setProductDetailList(productDetailList);
		
		return productDTO;

	}

}
