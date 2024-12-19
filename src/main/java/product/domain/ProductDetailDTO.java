package product.domain;

/*
 * 상품 상세 정보가 담긴 DTO
 */
public class ProductDetailDTO {
	
	private int pkProductDetailNo; // 상품 상세 일련번호
	
	private int inventory; // 재고
	
	private int size; // 상품 사이즈
	
	public int getPkProductDetailNo() {
		return pkProductDetailNo;
	}

	public void setPkProductDetailNo(int pkProductDetailNo) {
		this.pkProductDetailNo = pkProductDetailNo;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
