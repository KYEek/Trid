package payment.model;

import java.sql.SQLException;
import java.util.Map;

import org.json.JSONObject;

public interface Payment_DAO {

	//주문을 입력하는 메서드
	int insertOrderDate(Map<String, String> orderData, Map<String, String> orderDetailData) throws SQLException;
	
	//주문을 입력하는 메서드
	int instantPay(Map<String, String> orderData, Map<String, String> orderDetailData) throws SQLException;

	//바로 결제에서 상품 정보를 가져올 함수
	JSONObject selectProductInfo(int productDetailNo) throws SQLException;

}
