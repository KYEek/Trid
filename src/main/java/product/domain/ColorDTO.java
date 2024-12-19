package product.domain;

public class ColorDTO {
	
	private int pkColorNo; // 색상 일련 번호
	
	private String colorName; // 색상명

	private String colorCode; // 색상 코드

	public int getPkColorNo() {
		return pkColorNo;
	}

	public void setPkColorNo(int pkColorNo) {
		this.pkColorNo = pkColorNo;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

}
