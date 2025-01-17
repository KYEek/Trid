package orders.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import orders.domain.OrderDTO;

public interface OrderDAO {

	// 주문의 전체 행 개수를 불러오는 메소드
	int selectTotalRowCountByAdmin(Map<String, Object> paraMap) throws SQLException;

	// 관리자에서 사용 주문 내역을 불러오는 메소드
	List<OrderDTO> selectOrderListByAdmin(Map<String, Object> paraMap) throws SQLException;

	// 관리자에서 주문 상세 내역을 불러오는 메소드
	OrderDTO selectOrderByAdminByAdmin(String orderNo) throws SQLException;

	// 관리자에서 주문 상태를 변경하는 메소드
	int updateOrderStatusByAdmin(String orderNo, String orderStatus) throws SQLException;

	// 유저의 주문 정보를 불러온다
	JSONArray selectOrderListByMember(int pk_member_no, int startNum) throws SQLException;

	// 유저의 주문 상세 정보를 불러온다
	JSONArray selectOrderDetail(int pk_member_no, int orderNO) throws SQLException;

	// 관리자 금주 주문 내역 개수 (결제완료 수, 상품 준비 수, 배송 중 수, 배송완료 수)
	Map<String, Integer> selectWeekPayment() throws SQLException;

	// 관리자 일주일간 일간 매출 조회
	List<Map<String, String>> selectdailySalesList() throws SQLException;

	// 관리자 월간 매출 조회
	List<Map<String, String>> selectMonthlySalesList() throws SQLException;

	// 관리자 연 매출 조회
	List<Map<String, String>> selectYearSalesList() throws SQLException;

}
