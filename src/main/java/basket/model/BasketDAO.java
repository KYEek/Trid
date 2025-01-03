package basket.model;

import java.sql.SQLException;

import org.json.JSONArray;

public interface BasketDAO {

	//장바구니의 목록을 조회하는 메서드
	JSONArray selectBasketList(int pk_member_no) throws SQLException;
	
	//장바구니의 개수 추가 메서드
	String incrementBasketQuantity(int basketNo, int memberNum) throws SQLException;
	
	//장바구니의 개수 감소 메서드
	String decrementBasketQuantity(int basketNo, int memberNum) throws SQLException;
	
	//장바구니 상품 삭제 메서드
	String delectBasketProduct(int basketNo, int memberNum) throws SQLException;

	//장바구니 삽입 메서드
	int insertBasket(int productDetailNum, int pk_member_no) throws SQLException;

	//기존 장바구니에 있는 상품인지 확인
	int checkDuplicate(int productDetailNum, int pk_member_no) throws SQLException;
}
