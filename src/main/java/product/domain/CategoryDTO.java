package product.domain;

public class CategoryDTO {

	private int pkCategoryNo; // 카테고리일련번호
	
	private String categoryName; // 카테고리 명

	private int gender; // 성별 0(남자) 1(여자)

	private int type; // 상품구분 0(상의) 1(하의)

	public int getPkCategoryNo() {
		return pkCategoryNo;
	}

	public void setPkCategoryNo(int pkCategoryNo) {
		this.pkCategoryNo = pkCategoryNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
