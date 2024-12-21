package mypage.domain;

public class AddressDTO {

	
	int addr_no;        
	int member_no;        
	int post_no; 
	String address;
	String detail; 
	int addr_isdefault; 
	String extraaddr; 
	
	
	//조인한 데이터
	String addr_mobile;
	String member_name;
	
	
	
	
	public int getAddr_no() {
		return addr_no;
	}
	public void setAddr_no(int addr_no) {
		this.addr_no = addr_no;
	}
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	public int getPost_no() {
		return post_no;
	}
	public void setPost_no(int post_no) {
		this.post_no = post_no;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getAddr_isdefault() {
		return addr_isdefault;
	}
	public void setAddr_isdefault(int addr_isdefault) {
		this.addr_isdefault = addr_isdefault;
	}
	public String getExtraaddr() {
		return extraaddr;
	}
	public void setExtraaddr(String extraaddr) {
		this.extraaddr = extraaddr;
	}
	public String getAddr_mobile() {
		return addr_mobile;
	}
	public void setAddr_mobile(String addr_mobile) {
		this.addr_mobile = addr_mobile;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	
	
}
