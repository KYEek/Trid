package member.domain;

public class MemberDTO {

	private int pk_member_no;			// 회원일련번호
	    
    private String member_email;		// 회원이메일(아이디로 사용)
    
    private String member_password;		// 회원비밀번호
    
    private String member_name;			// 회원명
    
    private String member_mobile;		// 회원 전화번호
    
    private int member_gender;			// 성별
    
    private String member_birthday; 	// 생일
    
    private int member_status;			// 계정상태 => 1 : 활성   ||   0: 탈퇴   ||   2: 정지
    
    private int member_idle;			// 휴면유무 => 1 : 비휴면 || 0 :휴면 (6개월 기준)
    
    private String member_registerday;	// 가입일자
    
    private String member_pwdchangeday;	// 비밀번호 수정일자
    
    private String member_updateday; 	// 수정일자
    
    ////////////////////////////////////////////////////////////////////////////////////////////
    
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////
    
    public int getPk_member_no() {
		return pk_member_no;
	}

	public void setPk_member_no(int pk_member_no) {
		this.pk_member_no = pk_member_no;
	}

	public String getMember_email() {
		return member_email;
	}

	public void setMember_email(String member_email) {
		this.member_email = member_email;
	}

	public String getMember_password() {
		return member_password;
	}

	public void setMember_password(String member_password) {
		this.member_password = member_password;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public String getMember_mobile() {
		return member_mobile;
	}

	public void setMember_mobile(String member_mobile) {
		this.member_mobile = member_mobile;
	}

	public int getMember_gender() {
		return member_gender;
	}

	public void setMember_gender(int member_gender) {
		this.member_gender = member_gender;
	}

	public String getMember_birthday() {
		return member_birthday;
	}

	public void setMember_birthday(String member_birthday) {
		this.member_birthday = member_birthday;
	}

	public int getMember_status() {
		return member_status;
	}

	public void setMember_status(int member_status) {
		this.member_status = member_status;
	}

	public int getMember_idle() {
		return member_idle;
	}

	public void setMember_idle(int member_idle) {
		this.member_idle = member_idle;
	}

	public String getMember_registerday() {
		return member_registerday;
	}

	public void setMember_registerday(String member_registerday) {
		this.member_registerday = member_registerday;
	}

	public String getMember_pwdchangeday() {
		return member_pwdchangeday;
	}

	public void setMember_pwdchangeday(String member_pwdchangeday) {
		this.member_pwdchangeday = member_pwdchangeday;
	}

	public String getMember_updateday() {
		return member_updateday;
	}

	public void setMember_updateday(String member_updateday) {
		this.member_updateday = member_updateday;
	}

	
    
	
}
