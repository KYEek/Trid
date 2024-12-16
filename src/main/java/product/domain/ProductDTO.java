package product.domain;

public class ProductDTO {

	private int productNo; // 상품 일련 번호
	
	private int productDetailNo; // 상품 상세 일련 번호

	private int categoryNo; // 카테고리 일련 번호
	
	private String categoryName; // 카테고리명
	
	private int size; // 상품 사이즈
	
	private String productName; // 상품명
	
	private String explanation; // 상품 설명
	
	private int price; // 상품 가격
	
	private int inventory; // 재고
	
	private String registerday; // 상품 등록일자
	
	private String updateday; // 상품 수정일자
	
	private int status; // 상태코드
	
	private ColorDTO colorDTO; // 색상 DTO
	
	public int getProductNo() {
		return productNo;
	}

	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}

	public int getProductDetailNo() {
		return productDetailNo;
	}

	public void setProductDetailNo(int productDetailNo) {
		this.productDetailNo = productDetailNo;
	}

	public int getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
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

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
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

	public ColorDTO getColorDTO() {
		return colorDTO;
	}

	public void setColorDTO(ColorDTO colorDTO) {
		this.colorDTO = colorDTO;
	}

}
