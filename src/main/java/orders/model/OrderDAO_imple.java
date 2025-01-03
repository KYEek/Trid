package orders.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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
		
		String sortCategory = (String)paraMap.get("sortCategory") == null ? "" : (String)paraMap.get("sortCategory");
		
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
					sql += " and product_name like '%' || ? || '%' ";
				}
			}
			
			sql += "		group by fk_order_no "
				+ " 	) "
				+ " ) " 
				+ " select * "
				+ " from ( "
				+ " 	select ROWNUM as rnum, o.pk_order_no, o.order_date, o.order_status, order_total_price, "
				+ " 	m.pk_member_no, m.member_name, m.member_mobile, m.member_email, "
				+ " 	a.pk_addr_no, a.addr_post_no, a.addr_address, a.addr_detail, a.addr_extraaddr,"
				+ "		T.product_name, T.total_detail_count "
				+ " 	from tbl_order o join tbl_member m on o.fk_member_no = m.pk_member_no "
				+ " 	join tbl_addr a on o.fk_addr_no = a.pk_addr_no "
				+ "		join T on T.fk_order_no = o.pk_order_no ";
			
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
			
			sql += " 	order by o.order_date desc "
				+ " ) a "
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
				+ " p.pk_product_no, p.product_name, "
				+ "	pd.product_detail_size "
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
	
}
