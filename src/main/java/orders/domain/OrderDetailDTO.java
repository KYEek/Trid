package orders.domain;

public class OrderDetailDTO {
	
	private int pkOrderDetailNo; // 주문상세 일련번호
	
	private int productNo; // 상품 일련번호
	
	private String productName; // 상품명
	
	private int productSize; // 상품 사이즈
	
	private int productPrice; // 상품당 가격
	
	private int orderDetailPrice; // 주문총액, 해당 상품를 여러 개 주문할 경우 집계되는 단가
	
	private int orderDetailQuantity; // 상품 개수
	
	private String productImagePath; // 상품 첫번째 이미지

	public int getPkOrderDetailNo() {
		return pkOrderDetailNo;
	}

	public void setPkOrderDetailNo(int pkOrderDetailNo) {
		this.pkOrderDetailNo = pkOrderDetailNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductSize() {
		return productSize;
	}

	public void setProductSize(int productSize) {
		this.productSize = productSize;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public int getOrderDetailPrice() {
		return orderDetailPrice;
	}

	public void setOrderDetailPrice(int orderDetailPrice) {
		this.orderDetailPrice = orderDetailPrice;
	}

	public int getOrderDetailQuantity() {
		return orderDetailQuantity;
	}

	public void setOrderDetailQuantity(int setOrderDetailQuantity) {
		this.orderDetailQuantity = setOrderDetailQuantity;
	}

	public int getProductNo() {
		return productNo;
	}

	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}
	
	public String getProductImagePath() {
		return productImagePath;
	}

	public void setProductImagePath(String productImagePath) {
		this.productImagePath = productImagePath;
	}

	
}
