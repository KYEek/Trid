package product.domain;

import java.util.List;

/*
 * 상품 정보가 담긴 DTO
 */
public class ProductDTO {

	private int productNo; // 상품 일련 번호
	
	private String productName; // 상품명
	
	private String explanation; // 상품 설명
	
	private int price; // 상품 가격
	
	private String registerday; // 상품 등록일자
	
	private String updateday; // 상품 수정일자
	
	private int status; // 상태코드
	
	private CategoryDTO categoryDTO; // 카테고리 정보가 담긴 DTO
	
	private List<ColorDTO> colorList; // 색상정보들이 담긴 리스트
	
	private List<ProductDetailDTO> productDetailList; // 상품 상세(사이즈, 재고) 정보들이 담긴 리스트
	
	private List<ImageDTO> imageList; // 이미지 저장 경로 리스트
	
	private String[] colorNameArray; // 컬러 저장 리스트

	public int getProductNo() {
		return productNo;
	}

	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getRegisterday() {
		return registerday;
	}

	public void setRegisterday(String registerday) {
		this.registerday = registerday;
	}

	public String getUpdateday() {
		return updateday;
	}

	public void setUpdateday(String updateday) {
		this.updateday = updateday;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public List<ColorDTO> getColorList() {
		return colorList;
	}

	public void setColorList(List<ColorDTO> colorList) {
		this.colorList = colorList;
	}

	public List<ProductDetailDTO> getProductDetailList() {
		return productDetailList;
	}

	public void setProductDetailList(List<ProductDetailDTO> productDetailList) {
		this.productDetailList = productDetailList;
	}

	public CategoryDTO getCategoryDTO() {
		return categoryDTO;
	}

	public void setCategoryDTO(CategoryDTO categoryDTO) {
		this.categoryDTO = categoryDTO;
	}

	public List<ImageDTO> getImageList() {
		return imageList;
	}

	public void setImageList(List<ImageDTO> imageList) {
		this.imageList = imageList;
	}
	
	public String[] getColorNameArray() {
		return colorNameArray;
	}

	public void setColorNameArray(String[] colorNameArray) {
		this.colorNameArray = colorNameArray;
	}

}
