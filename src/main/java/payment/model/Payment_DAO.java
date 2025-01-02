package payment.model;

import java.sql.SQLException;
import java.util.Map;

public interface Payment_DAO {

	int insertOrderDate(Map<String, String> orderData, Map<String, String> orderDetailData) throws SQLException;

}
