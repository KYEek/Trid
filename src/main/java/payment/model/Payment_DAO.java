package payment.model;

import java.sql.SQLException;
import java.util.Map;

public interface Payment_DAO {

	//주문을 입력하는 메서드
	int insertOrderDate(Map<String, String> orderData, Map<String, String> orderDetailData) throws SQLException;

}
