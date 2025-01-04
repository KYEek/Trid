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
import product.domain.CategoryDTO;
import product.domain.ColorDTO;
import product.domain.ImageDTO;
import product.domain.ProductDTO;
import product.domain.ProductDetailDTO;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

/*
 * 상품 등록 컨트롤러
 */
public class ProductRegisterController extends AbstractController {

	private final ProductDAO productDAO = new ProductDAO_imple(); // 상품 DAO 초기화

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod(); // HTTP 메소드

		// 관리자 요청 유효성 검사
		if (!super.checkAdminLogin(request)) {
			return;
		}

		// POST 요청인 경우 상품 추가 처리
		if ("POST".equalsIgnoreCase(method)) {

			try {
				// 이미지 파일을 지정된 경로에 저장 후 이미지 정보가 담긴 ImageDTO list 반환
				List<ImageDTO> imageList = FileComponent.saveImages(request); 
				
				String message = "";

				// 상품 이미지가 존재하는지 확인
				if (imageList.size() < 1) {
					System.out.println("[ERROR] : imageList is empty");
					message = "failed";
				}
				else {
					//  이미지를 제외한 정보 저장
					ProductDTO productDTO = createProductDTO(request);
				
					productDTO.setImageList(imageList);
					
					// DB에 상품 추가 요청
					int result = productDAO.insertProduct(productDTO);

					// 상품 등록 실패 시
					message = (result == 1) ? "success" : "failed";
				}

				// 상품 등록 성공 시
				super.handelJsonResponse(response, message);

			} catch (SQLException | NumberFormatException | IOException | ServletException e ) {
				e.printStackTrace();
				super.handleServerError();
			}

		}
		// GET 요청인 경우 상품 추가 페이지로 이동
		else {
			try {
				List<CategoryDTO> categoryList = productDAO.selectCategoryList();

				request.setAttribute("categoryList", categoryList);

				super.setRedirect(false);
				super.setViewPage(Constants.ADMIN_PRODUCT_REGISTER_PAGE);
				
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

		CategoryDTO categoryDTO = new CategoryDTO(); // 카테고리 DTO 초기화

		List<ColorDTO> colorList = new ArrayList<>(); // 상품 색상 리스트

		List<ProductDetailDTO> productDetailList = new ArrayList<>(); // 상품 상세 리스트

		// Request에서 정보 추출
		String[] inventoryArr = request.getParameter("inventory").split(","); // 재고 배열 (100, 100, 100, 100) 사이즈 순서에 맞추어 형성

		String[] colorNameArr = request.getParameter("colorName").split(","); // 색상명 (red, blue) 배열

		String[] colorCodeArr = request.getParameter("colorCode").split(","); // 색상코드 (#ffffff, #000000) 배열

		String productName = request.getParameter("productName"); // 상품명

		String explanation = request.getParameter("explanation"); // 상품 설명

		int categoryNo = Integer.parseInt(request.getParameter("categoryNo")); // 카테고리 일련번호

		int price = Integer.parseInt(request.getParameter("price")); // 상품 가격

		// 리스트를 순회하며 DTO에 정보 저장

		// 상품의 색상들을 ColorDTO List에 저장
		for (int i = 0; i < colorNameArr.length; i++) {
			ColorDTO colorDTO = new ColorDTO();
			colorDTO.setColorName(colorNameArr[i]);
			colorDTO.setColorCode(colorCodeArr[i]);

			colorList.add(colorDTO);
		}

		// 사이즈 (0, 1, 2, 3) 순서에 맞추어 재고 입력
		for (int i = 0; i < 4; i++) {
			ProductDetailDTO productDetailDTO = new ProductDetailDTO();
			productDetailDTO.setSize(i);
			productDetailDTO.setInventory(Integer.parseInt(inventoryArr[i]));

			productDetailList.add(productDetailDTO);
		}

		categoryDTO.setPkCategoryNo(categoryNo);

		productDTO.setProductName(productName);
		productDTO.setExplanation(explanation);
		productDTO.setPrice(price);

		productDTO.setColorList(colorList);
		productDTO.setProductDetailList(productDetailList);
		productDTO.setCategoryDTO(categoryDTO);
		
		return productDTO;
	}

}
