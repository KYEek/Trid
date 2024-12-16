package admin.domain;

public class AdminDTO {
	
	private String adminId; // 관리자 아이디
	
	private String password; // 관리자 비밀번호
	
	private String adminName; // 관리자 성명

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

}
