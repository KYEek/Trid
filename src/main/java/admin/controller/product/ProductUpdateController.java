package admin.controller.product;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import product.domain.CategoryDTO;
import product.domain.ImageDTO;
import product.domain.ProductDTO;
import product.domain.ProductDetailDTO;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

public class ProductUpdateController extends AbstractController {
	
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
			List<ImageDTO> imageList = new ArrayList<>();

			if(request.getParts().size() > 0) {
				
				String path = "/images/product_images";
			
				String uploadPath = request.getServletContext().getRealPath(path);
				
				File uploadDir = new File(uploadPath);
				
				// 해당 경로에 디렉토리가 존재하는 확인
				if (!uploadDir.exists()) {
					uploadDir.mkdir();
				}
				
				// 파일 업로드 처리
				for (Part part : request.getParts()) {
					if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isBlank()) {
						ImageDTO imageDTO = new ImageDTO();

						String fileName = part.getSubmittedFileName(); // 이미지 파일명 지정

						String imagePath = uploadPath + File.separator + fileName; // 이미지 파일 경로

						// ImageDTO에 이미지 정보 저장
						imageDTO.setImagePath(path +  File.separator + fileName);
						imageDTO.setImageName(fileName);

						// 지정된 경로에 이미지 저장
						part.write(imagePath);

						imageList.add(imageDTO);
					}
				}
			}


			////////////////////////// 이미지를 제외한 정보 저장 ////////////////////////////////

			try {

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
				productDTO.setImageList(imageList);

				// DB에 상품 추가 요청
				int result = productDAO.updateProduct(productDTO);

				// 상품 수정 실패 시
				if (result != 1) {
					request.setAttribute("message", "상품 수정을 실패했습니다.");
					request.setAttribute("loc", Constants.HISTORY_BACK);

					super.setRedirect(false);
					super.setViewPage(Constants.MESSAGE_PAGE);
					return;
				}

				// 상품 수정 성공 시
				super.handleMessage(request, "상품 수정을 성공했습니다.", Constants.ADMIN_PRODUCT_DETAIL_URL + "?productNo=" + productDTO.getProductNo());

			} catch (SQLException e) {
				e.printStackTrace();
				super.setRedirect(true);
				super.setViewPage(Constants.ERROR_URL);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

		}
		// GET 요청인 경우 상품 수정 페이지로 이동
		else {
			String productNo = request.getParameter("productNo");
			
			try {
				ProductDTO productDTO = productDAO.selectProduct(productNo);
				
				request.setAttribute("productDTO", productDTO);

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
				super.setRedirect(true);
				super.setViewPage(Constants.ERROR_URL);
			}
			
		}

	}

}
