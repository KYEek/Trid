package product.domain;

public class ColorDTO {
	
	private int colorNo; // 색상 일련번호
	
	private String colorName; // 색상명

	private String color_code; // 색상 코드
	
	public int getColorNo() {
		return colorNo;
	}

	public void setColorNo(int colorNo) {
		this.colorNo = colorNo;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getColor_code() {
		return color_code;
	}

	public void setColor_code(String color_code) {
		this.color_code = color_code;
	}

}
