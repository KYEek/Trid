package orders.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;

import common.Constants;
import common.domain.PagingDTO;
import member.domain.MemberDTO;
import mypage.domain.AddressDTO;
import orders.domain.OrderDTO;
import orders.domain.OrderDetailDTO;
import util.StringUtil;
import util.security.AES256;

public class OrderDAO_imple implements OrderDAO {

	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	
	private Connection conn; // Database 연결 객체
	
	private PreparedStatement pstmt; // SQL문 실행 객체, 미리 컴파일을 통해 SQL Injection을 방지
	
	private ResultSet rs; // SQL 쿼리 실행 결과를 담는 객체
	
	private AES256 aes; // 양방향 암호화 방식인 AES256 객체

	public OrderDAO_imple() {
		// 데이터베이스 연결 설정 값을 불러온 후 Connection Pool 연결 
		try {
			Context initContext = new InitialContext();
			
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			
			ds = (DataSource) envContext.lookup("jdbc/semioracle");
			
			aes = new AES256(Constants.KEY);
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	// 사용한 자원을 반납하는 close() 메소드 생성하기
	private void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 주문의 전체 행 개수를 불러오는 메소드
	 */
	@Override
	public int selectTotalRowCountByAdmin(Map<String, Object> paraMap) throws SQLException {
		int totalRowCount = 0;
		
		String searchType = (String)paraMap.get("searchType"); // 검색타입 0:주문자명 1:상품명
		
		String searchWord = (String)paraMap.get("searchWord");
		
		String orderStatus = (String)paraMap.get("orderStatus");
		
		String dateMin = (String)paraMap.get("dateMin");
		
		String dateMax = (String)paraMap.get("dateMax");
		
		int count = 0;
		
		try {
			conn = ds.getConnection();

			String sql 	= " WITH "
						+ " T AS "
						+ "	( "
						+ "  	SELECT * "
						+ "		FROM ( "
						+ "			select min(product_name) as product_name, count(*) as total_detail_count, fk_order_no "
						+ " 		from tbl_order_detail od "
						+ "			join tbl_product_detail pd on od.fk_product_detail_no = pd.pk_product_detail_no "
						+ "			join tbl_product p on pd.fk_product_no = p.pk_product_no "
						+ "			where 1=1 ";
			
			if(!StringUtil.isBlank(searchWord)) {
				if("1".equals(searchType)) {
					sql += " and product_name like '%' || ? || '%' ";
				}
			}
			
			sql += "		group by fk_order_no "
				+ " 	) "
				+ " ) "
				+ " select count(*) as total "
				+ " from ( "
				+ " select o.pk_order_no, o.order_date, o.order_status, order_total_price, "
				+ " m.pk_member_no, m.member_name, m.member_mobile, m.member_email, "
				+ " a.pk_addr_no, a.addr_post_no, a.addr_address, a.addr_detail, a.addr_extraaddr, "
				+ " T.product_name "
				+ " from tbl_order o join tbl_member m on o.fk_member_no = m.pk_member_no "
				+ " join tbl_addr a on o.fk_addr_no = a.pk_addr_no "
				+ " join T on T.fk_order_no = o.pk_order_no ";
			
			if(!StringUtil.isBlank(searchWord)) {
				if("0".equals(searchType)) {
					sql += " and member_name = ? ";
				}
			}
			
			if(!StringUtil.isBlank(orderStatus)) {
				sql += " and order_status = ? ";
			}
			
			if(!StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				sql += " and order_date between to_date(?, 'yyyy-mm-dd') and to_date(?, 'yyyy-mm-dd hh24:mi:ss') ";
			}
			
			if(StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				sql += " and order_date <= to_date(?, 'yyyy-mm-dd hh24:mi:ss') ";
			}
			
			if(!StringUtil.isBlank(dateMin) && StringUtil.isBlank(dateMax)) {
				sql += " and order_date >= to_date(?, 'yyyy-mm-dd') ";
			}
			
			sql += " ) ";

			pstmt = conn.prepareStatement(sql);
			
			if(!StringUtil.isBlank(searchType) && !StringUtil.isBlank(searchWord)) {
				pstmt.setString(++count, searchWord);
			}
			
			if(!StringUtil.isBlank(orderStatus)) {
				pstmt.setString(++count, orderStatus);
			}
			
			if(!StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMin);
				pstmt.setString(++count, dateMax + " 23:59:59");
			}
			
			if(StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMax + " 23:59:59");
			}
			
			if(!StringUtil.isBlank(dateMin) && StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMin);
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totalRowCount = rs.getInt("total");
			}
	
		} finally {
			close();
		}
		
		return totalRowCount;
	}

	/*
	 * 관리자에서 사용 주문 내역을 불러오는 메소드
	 */
	@Override
	public List<OrderDTO> selectOrderListByAdmin(Map<String, Object> paraMap) throws SQLException {
		List<OrderDTO> orderList = new ArrayList<>();
		
		PagingDTO pagingDTO = (PagingDTO)paraMap.get("pagingDTO");
		
		String searchType = (String)paraMap.get("searchType"); // 검색타입 0:주문자명 1:상품명
		
		String searchWord = (String)paraMap.get("searchWord");
		
		String sortCategory = (String)paraMap.get("sortCategory") == null ? "0" : (String)paraMap.get("sortCategory");
		
		String orderStatus = (String)paraMap.get("orderStatus");
		
		String dateMin = (String)paraMap.get("dateMin");
		
		String dateMax = (String)paraMap.get("dateMax");
		
		int count = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " WITH "
						+ " T AS "
						+ " ( "
						+ "  	SELECT * "
						+ "		FROM ( "
						+ "			select min(product_name) as product_name, count(*) as total_detail_count, fk_order_no "
						+ "			from tbl_order_detail od join tbl_product_detail pd on od.fk_product_detail_no = pd.pk_product_detail_no "
						+ "			join tbl_product p on pd.fk_product_no = p.pk_product_no "
						+ "			where 1=1 ";
						
			if(!StringUtil.isBlank(searchWord)) {
				if("1".equals(searchType)) {
					sql 	+= " and fk_order_no in ( select fk_order_no "
							+ " 					 from tbl_order_detail sod join tbl_product_detail spd "
							+ " 					 on sod.fk_product_detail_no = spd.pk_product_detail_no"
							+ "						 join tbl_product sp on spd.fk_product_no = sp.pk_product_no "
							+ "  					 where sp.product_name like '%' || ? || '%' "
							+ "						) ";
				}
			}
			
			sql += "		group by fk_order_no "
				+ " 	) "
				+ " ) " 
				+ " select * "
				+ " from ( "
				+ " 	select ROWNUM as rnum, a.* "
				+ "		from "
				+ "		( "
				+ "			select o.pk_order_no, o.order_date, o.order_status, order_total_price, "
				+ " 		m.pk_member_no, m.member_name, m.member_mobile, m.member_email, "
				+ " 		a.pk_addr_no, a.addr_post_no, a.addr_address, a.addr_detail, a.addr_extraaddr,"
				+ "			T.product_name, T.total_detail_count "
				+ " 		from tbl_order o join tbl_member m on o.fk_member_no = m.pk_member_no "
				+ " 		join tbl_addr a on o.fk_addr_no = a.pk_addr_no "
				+ "			join T on T.fk_order_no = o.pk_order_no ";
			
			if(!StringUtil.isBlank(searchWord)) {
				if("0".equals(searchType)) {
					sql += " and member_name = ? ";
				}
			}
			
			if(!StringUtil.isBlank(orderStatus)) {
				sql += " and order_status = ? ";
			}
			
			if(!StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				sql += " and order_date between to_date(?, 'yyyy-mm-dd') and to_date(?, 'yyyy-mm-dd hh24:mi:ss') ";
			}
			
			if(StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				sql += " and order_date <= to_date(?, 'yyyy-mm-dd hh24:mi:ss') ";
			}
			
			if(!StringUtil.isBlank(dateMin) && StringUtil.isBlank(dateMax)) {
				sql += " and order_date >= to_date(?, 'yyyy-mm-dd') ";
			}
			
			if(!StringUtil.isBlank(sortCategory)) {
				switch(sortCategory) {
					case "0" : {
						sql += " order by order_date desc ";
						break;
					}
					case "1" : {
						sql += " order by order_date ";
						break;
					}
					default : {
						sql += " order by order_date desc ";
						break;
					}
				}
			}
			
			sql += " ) a "
				+ ") "
				+ " where rnum between ? and ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			if(!StringUtil.isBlank(searchType) && !StringUtil.isBlank(searchWord)) {
				pstmt.setString(++count, searchWord);
			}
			
			if(!StringUtil.isBlank(orderStatus)) {
				pstmt.setString(++count, orderStatus);
			}
			
			if(!StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMin);
				pstmt.setString(++count, dateMax + " 23:59:59");
			}
			
			if(StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMax + " 23:59:59");
			}
			
			if(!StringUtil.isBlank(dateMin) && StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMin);
			}
			
			pstmt.setInt(++count, pagingDTO.getFirstRow());
			pstmt.setInt(++count, pagingDTO.getLastRow());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				OrderDTO orderDTO = new OrderDTO();
				MemberDTO memberDTO = new MemberDTO();
				AddressDTO addrDTO = new AddressDTO();
				
				orderDTO.setPkOrderNo(rs.getInt("pk_order_no"));
				orderDTO.setOrderDate(rs.getString("order_date"));
				orderDTO.setOrderStatus(rs.getInt("order_status"));
				orderDTO.setOrderTotalPrice(rs.getInt("order_total_price"));
				orderDTO.setFirstProductName(rs.getString("product_name"));
				orderDTO.setTotalProductDetailCount(rs.getInt("total_detail_count"));
				
				memberDTO.setPk_member_no(rs.getInt("pk_member_no"));
				memberDTO.setMember_name(rs.getString("member_name"));
				memberDTO.setMember_mobile(rs.getString("member_mobile"));
				memberDTO.setMember_email(rs.getString("member_email"));
				
				addrDTO.setAddr_no(rs.getInt("pk_addr_no"));
				addrDTO.setPost_no(rs.getInt("addr_post_no"));
				addrDTO.setAddress(rs.getString("addr_address"));
				addrDTO.setDetail(rs.getString("addr_detail"));
				addrDTO.setExtraaddr(rs.getString("addr_extraaddr"));
				
				orderDTO.setMemberDTO(memberDTO);
				orderDTO.setAddressDTO(addrDTO);
				
				orderList.add(orderDTO);
			}
			
		} finally {
			close();
		}
		
		return orderList;
	}

	/*
	 * 관리자에서 주문 상세 내역을 불러오는 메소드
	 */
	@Override
	public OrderDTO selectOrderByAdminByAdmin(String orderNo) throws SQLException {
		OrderDTO orderDTO = new OrderDTO();
		
		try {
			
			conn = ds.getConnection();
			
			// 공통 주문 내역을 먼저 조회
			String sql 	= " select "
						+ " o.pk_order_no, o.order_date, o.order_status, order_total_price, "
						+ "	m.pk_member_no, m.member_name, m.member_mobile, m.member_email, "
						+ " a.pk_addr_no, a.addr_post_no, a.addr_address, a.addr_detail, a.addr_extraaddr "
						+ " from "
						+ " tbl_order o join tbl_member m on o.fk_member_no = m.pk_member_no "
						+ "	join tbl_addr a on o.fk_addr_no = a.pk_addr_no "
						+ "	join tbl_order_detail od on od.fk_order_no = o.pk_order_no "
						+ " where pk_order_no = ? ";
					
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, orderNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				MemberDTO memberDTO = new MemberDTO();
				AddressDTO addrDTO = new AddressDTO();
				
				// OrderDTO 저장
				orderDTO.setPkOrderNo(rs.getInt("pk_order_no"));
				orderDTO.setOrderDate(rs.getString("order_date"));
				orderDTO.setOrderStatus(rs.getInt("order_status"));
				orderDTO.setOrderTotalPrice(rs.getInt("order_total_price"));
				
				// MemberDTO 저장
				memberDTO.setPk_member_no(rs.getInt("pk_member_no"));
				memberDTO.setMember_name(rs.getString("member_name"));
				memberDTO.setMember_mobile(aes.decrypt(rs.getString("member_mobile")));
				memberDTO.setMember_email(aes.decrypt(rs.getString("member_email")));
				
				// AddressDTO 저장
				addrDTO.setAddr_no(rs.getInt("pk_addr_no"));
				addrDTO.setPost_no(rs.getInt("addr_post_no"));
				addrDTO.setAddress(rs.getString("addr_address"));
				addrDTO.setDetail(rs.getString("addr_detail"));
				addrDTO.setExtraaddr(rs.getString("addr_extraaddr"));
				
				orderDTO.setMemberDTO(memberDTO);
				orderDTO.setAddressDTO(addrDTO);
				
			}
			// 공통 주문내역 조회 실패 시 null 반환 
			else {
				System.out.println("tbl_order select failed");
				return null;
			}
			
			// 주문 상세내역 리스트 조회
			List<OrderDetailDTO> orderDetailList = new ArrayList<>();
			
			sql = " select count(*) over() as total_detail_count, "
				+ "	od.pk_order_detail_no, od.order_detail_price, od.order_detail_quantity, "
				+ " p.pk_product_no, p.product_name, p.product_price, "
				+ "	pd.product_detail_size,"
				+ " (select product_image_path from tbl_product_image i where rownum = 1 "
				+ "		and i.fk_product_no = p.pk_product_no) as product_image_path "
				+ " from tbl_order o "
				+ "	join tbl_order_detail od on od.fk_order_no = o.pk_order_no "
				+ " join tbl_product_detail pd on pd.pk_product_detail_no = od.fk_product_detail_no "
				+ " join tbl_product p on p.pk_product_no = pd.fk_product_no "
				+ " where pk_order_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, orderNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
				
				// 주문 내역 속 총 상품 개수 (다른 사이즈는 별도 상품으로 취급)
				orderDTO.setTotalProductDetailCount(rs.getInt("total_detail_count"));
				
				// OrderDetailDTO 저장
				orderDetailDTO.setPkOrderDetailNo(rs.getInt("pk_order_detail_no"));
				orderDetailDTO.setOrderDetailPrice(rs.getInt("order_detail_price"));
				orderDetailDTO.setOrderDetailQuantity(rs.getInt("order_detail_quantity"));
				
				orderDetailDTO.setProductName(rs.getString("product_name"));
				orderDetailDTO.setProductNo(rs.getInt("pk_product_no"));
				orderDetailDTO.setProductSize(rs.getInt("product_detail_size"));
				orderDetailDTO.setProductPrice(rs.getInt("product_price"));
				orderDetailDTO.setProductImagePath(rs.getString("product_image_path"));
				
				orderDetailList.add(orderDetailDTO);
				
			}
			
			if(orderDetailList.size() < 1) {
				System.out.println("tbl_order_detail select failed");
				return null;
			}
			
			orderDTO.setOrderDetailList(orderDetailList);
		
		} catch(UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return orderDTO;
	}

	/*
	 * 관리자에서 주문 상태를 변경하는 메소드
	 */
	@Override
	public int updateOrderStatusByAdmin(String orderNo, String orderStatus) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql 	= " update tbl_order set order_status = ? "
						+ " where pk_order_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, orderStatus);
			pstmt.setString(2, orderNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
	}

	// 유저의 주문 정보를 불러온다
	@Override
	public JSONArray selectOrderListByMember(int pk_member_no) throws SQLException {
		
		JSONArray jsonArr = null;
		
		try {
			conn = ds.getConnection();
			
			
			String sql = " select FK_ORDER_NO, ORDER_DATE, ORDER_STATUS, ORDER_TOTAL_PRICE, FK_PRODUCT_DETAIL_NO, PRODUCT_IMAGE_PATH, PRODUCT_IMAGE_NAME "
					+ " from ( "
					+ " with order_info as ( "
					+ " select PK_ORDER_DETAIL_NO, FK_ORDER_NO, FK_MEMBER_NO, FK_ADDR_NO, ORDER_DATE, ORDER_STATUS, ORDER_TOTAL_PRICE, FK_PRODUCT_DETAIL_NO, ORDER_DETAIL_PRICE, ORDER_DETAIL_QUANTITY, (order_detail_price * order_detail_quantity) as product_total_price "
					+ " from tbl_order "
					+ " join tbl_order_detail "
					+ " on pk_order_no = fk_order_no) "
					+ " select row_number() over(partition by PK_ORDER_DETAIL_NO order by PRODUCT_IMAGE_NAME)as rownumber ,FK_ORDER_NO, ORDER_DATE, ORDER_STATUS, ORDER_TOTAL_PRICE, FK_PRODUCT_DETAIL_NO, PRODUCT_IMAGE_PATH, PRODUCT_IMAGE_NAME, FK_MEMBER_NO "
					+ " from order_info "
					+ " join select_basket_product_info product_info "
					+ " on order_info.fk_product_detail_no = product_info.PRODUCT_DETAIL_NO "
					+ " ) "
					+ " where rownumber = 1 and fk_member_no = ? "
					+ " order by fk_order_no desc ";
			
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt. setInt(1, pk_member_no);
			
			rs = pstmt.executeQuery();
			jsonArr = new JSONArray();
			
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("FK_ORDER_NO", rs.getInt("FK_ORDER_NO"));
				json.put("ORDER_DATE", rs.getString("ORDER_DATE"));
				json.put("ORDER_STATUS", rs.getInt("ORDER_STATUS"));
				json.put("ORDER_TOTAL_PRICE", rs.getInt("ORDER_TOTAL_PRICE"));
				json.put("FK_PRODUCT_DETAIL_NO", rs.getInt("FK_PRODUCT_DETAIL_NO"));
				json.put("PRODUCT_IMAGE_PATH", rs.getString("PRODUCT_IMAGE_PATH"));
				json.put("PRODUCT_IMAGE_NAME", rs.getString("PRODUCT_IMAGE_NAME"));
				jsonArr.put(json);
			}
			
		}
		finally {
			close();
		}
		
		
		return jsonArr;
	}//end of public JSONArray selectOrderListByMember(int pk_member_no) throws SQLException ----------------------------------------------------

	// 유저의 주문 상세 정보를 불러온다
	@Override
	public JSONArray selectOrderDetail(int pk_member_no, int orderNO) throws SQLException {
		
		JSONArray jsonArr = new JSONArray();
		
		conn = ds.getConnection();
		
		String sql = " with order_detail as( "
				+ " select PK_ORDER_DETAIL_NO, FK_ORDER_NO, FK_PRODUCT_DETAIL_NO, product_price, PRODUCT_DETAIL_NO, PRODUCT_NO, PRODUCT_SIZE, PRODUCT_NAME, COLOR_NAME, PRODUCT_IMAGE_PATH, PRODUCT_IMAGE_NAME "
				+ " from ( "
				+ " select row_number() over(partition by PK_ORDER_DETAIL_NO order by PRODUCT_IMAGE_NAME)as rownumber, PK_ORDER_DETAIL_NO, FK_ORDER_NO, FK_PRODUCT_DETAIL_NO, (ORDER_DETAIL_PRICE * ORDER_DETAIL_QUANTITY) product_price, PRODUCT_DETAIL_NO, PRODUCT_NO, PRODUCT_SIZE, PRODUCT_NAME, COLOR_NAME, PRODUCT_IMAGE_PATH, PRODUCT_IMAGE_NAME "
				+ " from tbl_order_detail "
				+ " join select_basket_product_info "
				+ " on product_detail_no = fk_product_detail_no) "
				+ " where rownumber = 1  "
				+ " ) "
				+ "  "
				+ " select PK_ORDER_NO, ORDER_TOTAL_PRICE, SUM_PRODUC_PRICE, ORDER_STATUS, ORDER_DATE, FK_ADDR_NO, PRODUCT_DETAIL_NO, PRODUCT_PRICE, PRODUCT_SIZE, PRODUCT_NAME, COLOR_NAME, PRODUCT_IMAGE_PATH, PRODUCT_IMAGE_NAME, product_no, fk_member_no  "
				+ " from order_detail "
				+ "  "
				+ " join ( "
				+ "    with product_price as ( "
				+ "    select fk_order_no, sum(product_price) as product_price "
				+ "    from ( "
				+ "    select fk_order_no, (order_detail_price * order_detail_quantity) product_price "
				+ "    from tbl_order_detail "
				+ "    ) "
				+ "    group by fk_order_no) "
				+ "    select PK_ORDER_NO, FK_MEMBER_NO, FK_ADDR_NO, ORDER_DATE, ORDER_STATUS, ORDER_TOTAL_PRICE, PRODUCT_PRICE as sum_produc_price "
				+ "    from tbl_order "
				+ "    join product_price "
				+ "    on fk_order_no = pk_order_no "
				+ " ) "
				+ " on PK_ORDER_NO = FK_ORDER_NO "
				+ " where fk_member_no = ? and pk_order_no = ? "
				+ " order by pk_order_no desc ";	
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pk_member_no);
			pstmt.setInt(2, orderNO);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("PK_ORDER_NO", rs.getInt("PK_ORDER_NO"));
				json.put("ORDER_TOTAL_PRICE", rs.getInt("ORDER_TOTAL_PRICE"));
				json.put("SUM_PRODUC_PRICE", rs.getInt("SUM_PRODUC_PRICE"));
				json.put("ORDER_STATUS", rs.getInt("ORDER_STATUS"));
				json.put("ORDER_DATE", rs.getString("ORDER_DATE"));
				json.put("FK_ADDR_NO", rs.getInt("FK_ADDR_NO"));
				json.put("PRODUCT_DETAIL_NO", rs.getInt("PRODUCT_DETAIL_NO"));
				json.put("PRODUCT_PRICE", rs.getInt("PRODUCT_PRICE"));
				json.put("PRODUCT_SIZE", rs.getString("PRODUCT_SIZE"));
				json.put("PRODUCT_NAME", rs.getString("PRODUCT_NAME"));
				json.put("COLOR_NAME", rs.getString("COLOR_NAME"));
				json.put("PRODUCT_IMAGE_PATH", rs.getString("PRODUCT_IMAGE_PATH"));
				json.put("PRODUCT_IMAGE_NAME", rs.getString("PRODUCT_IMAGE_NAME"));
				json.put("PRODUCT_NO", rs.getInt("PRODUCT_NO"));
				json.put("fk_member_no", rs.getInt("fk_member_no"));
				
				
				jsonArr.put(json);
			}
		}
		finally {
			close();
		}
		return jsonArr;
	}//end of public JSONArray selectOrderDetail(int pk_member_no, int orderNO) throws SQLException ----------------------------------------------


	// 관리자 일주일간 주문 내역 개수 (결제완료 수, 상품 준비 수, 배송 중 수, 배송완료 수)
	@Override
	public Map<String, Integer> selectWeekPayment() throws SQLException {
		Map<String, Integer> weekPaymentMap = new HashMap<>(); // (결제완료 수, 상품준비 수, 배송중 수, 배송완료 수)
		
		weekPaymentMap.put("paid", 0);
		weekPaymentMap.put("preparing", 0);
		weekPaymentMap.put("shipping ", 0);
		weekPaymentMap.put("delivered ", 0);
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " select order_status "
						+ " from tbl_order "
						+ " where order_date between sysdate-6 and sysdate ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				switch(rs.getInt("order_status")) {
					case 0 : {
						int pc = weekPaymentMap.get("paid");
						weekPaymentMap.put("paid", ++pc);
						break;
					}
					case 1 : {
						int pc = weekPaymentMap.get("preparing");
						weekPaymentMap.put("preparing", ++pc);
						break;
					}
					case 2 : {
						int pc = weekPaymentMap.get("shipping");
						weekPaymentMap.put("shipping", ++pc);
						break;
					}
					case 3 : {
						int pc = weekPaymentMap.get("delivered");
						weekPaymentMap.put("delivered", ++pc);
						break;
					}
				}
			}
			
		} finally {
			close();
		}
		
		return weekPaymentMap;
	}

	// 관리자 일주일간 일간 매출 조회
	@Override
	public List<Map<String, String>> selectdailySalesList() throws SQLException {
		List<Map<String, String>> dailySalesList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql 	= " select to_char(order_date, 'yyyy-mm-dd') as orderdate, sum(order_total_price) as sales "
						+ " from tbl_order"
						+ " where order_date between sysdate-6 and sysdate "
						+ " group by to_char(order_date, 'yyyy-mm-dd')"
						+ " order by orderdate ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, String> map = new HashMap<>();
				
				map.put("orderdate", rs.getString("orderdate"));
				map.put("sales", String.valueOf(rs.getInt("sales")));
				
				dailySalesList.add(map);
			}
			
		} finally {
			close();
		}
		
		return dailySalesList;
	}

	// 관리자 월간 매출 조회
	@Override
	public List<Map<String, String>> selectMonthlySalesList() throws SQLException {
		List<Map<String, String>> monthlySalesList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql 	
					= "WITH months AS ( "
					+ "    SELECT LEVEL AS month "
					+ "    FROM DUAL "
					+ "    CONNECT BY LEVEL <= 12 "
					+ ") "
					+ "SELECT "
					+ "    LPAD(m.month, 2, '0') AS month, "
					+ "    NVL(SUM(o.order_total_price), 0) AS sales "
					+ "FROM "
					+ "    months m "
					+ "LEFT JOIN "
					+ "    tbl_order o "
					+ "ON  "
					+ "    EXTRACT(MONTH FROM o.order_date) = m.month "
					+ "    AND EXTRACT(YEAR FROM o.order_date) = EXTRACT(YEAR FROM SYSDATE) "
					+ "GROUP BY "
					+ "    m.month "
					+ "ORDER BY "
					+ "    m.month ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, String> map = new HashMap<>();
				
				map.put("month", rs.getString("month"));
				map.put("sales", String.valueOf(rs.getInt("sales")));
				
				monthlySalesList.add(map);
			}
			
		} finally {
			close();
		}
		
		return monthlySalesList;
	}

	
	// 관리자 연 매출 조회
	@Override
	public List<Map<String, String>> selectYearSalesList() throws SQLException {
		List<Map<String, String>> yearSalesList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql 	
					= "WITH years AS ( "
					+ "    SELECT (EXTRACT(YEAR FROM SYSDATE) - LEVEL + 1) AS year "
					+ "    FROM DUAL "
					+ "    CONNECT BY LEVEL <= 5 "
					+ ") "
					+ "SELECT "
					+ "    y.year AS year, "
					+ "    NVL(SUM(o.order_total_price), 0) AS sales "
					+ "FROM "
					+ "    years y "
					+ "LEFT JOIN "
					+ "    tbl_order o "
					+ "ON  "
					+ " EXTRACT(YEAR FROM o.order_date) = y.year "
					+ "GROUP BY "
					+ "    y.year "
					+ "ORDER BY "
					+ "    y.year ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, String> map = new HashMap<>();
				
				map.put("year", rs.getString("year"));
				map.put("sales", String.valueOf(rs.getInt("sales")));
				
				yearSalesList.add(map);
			}
			
		} finally {
			close();
		}
		
		return yearSalesList;
	}

}
