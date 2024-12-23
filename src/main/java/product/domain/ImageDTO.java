package product.domain;

/*
 * 상품 이미지의 대한 정보를 담는 DTO
 */
public class ImageDTO {
	
	private int pkProductImageNo; // 이미지 일련번호
	
	private String imageName; // 파일명
	
	private String imagePath; // 이미지 경로
	
	private String registerday; // 등록일자
	
	private String updateday; // 수정일자

	public int getPkProductImageNo() {
		return pkProductImageNo;
	}

	public void setPkProductImageNo(int pkProductImageNo) {
		this.pkProductImageNo = pkProductImageNo;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
	
}
