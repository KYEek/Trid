package orders.domain;

import member.domain.MemberDTO;
import mypage.domain.AddressDTO;

import java.util.List;

public class OrderDTO {
	
	private int pkOrderNo; // 주문번호
	
	private String orderDate; // 주문시간
	
	private int orderStatus; // 주문 상태
	
	private int totalProductDetailCount; // 총 주문 상품 개수, 다른 사이즈는 별도 상품으로 취급
	
	private String firstProductName; // 첫번째 상품명
	
	private int orderTotalPrice; // 총 주문 금액
	
	private AddressDTO addressDTO; // 주소 DTO
	
	private MemberDTO memberDTO; // 사용자 DTO
	
	private List<OrderDetailDTO> orderDetailList; // 주문상세 DTO 리스트

	public int getPkOrderNo() {
		return pkOrderNo;
	}

	public void setPkOrderNo(int pkOrderNo) {
		this.pkOrderNo = pkOrderNo;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(int orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public AddressDTO getAddressDTO() {
		return addressDTO;
	}

	public void setAddressDTO(AddressDTO addressDTO) {
		this.addressDTO = addressDTO;
	}

	public MemberDTO getMemberDTO() {
		return memberDTO;
	}

	public void setMemberDTO(MemberDTO memberDTO) {
		this.memberDTO = memberDTO;
	}

	public List<OrderDetailDTO> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetailDTO> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public int getTotalProductDetailCount() {
		return totalProductDetailCount;
	}

	public void setTotalProductDetailCount(int totalProductDetailCount) {
		this.totalProductDetailCount = totalProductDetailCount;
	}

	public String getFirstProductName() {
		return firstProductName;
	}

	public void setFirstProductName(String firstProductName) {
		this.firstProductName = firstProductName;
	}
	
}
