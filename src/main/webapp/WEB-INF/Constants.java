package common;

public class Constants {

	public static String PROPERTIES_PATH = "C:/Users/gpflv/git/Trid/src/main/webapp/WEB-INF/Command.properties"; // Command.propeties 경로

	public final static String KEY = "trid3333#gclass$"; // AES256 개인 키

	public final static String IMAGE_SAVE_PATH = "/images/product_images"; // 상품 이미지 저장 경로

	public final static String ACCESS_DENIED = "접근 권한이 없습니다. 로그인 페이지로 이동합니다.";

	public final static String INVALID_ID_OR_PASSWORD = "아이디 또는 비밀번호가 틀립니다.";

	public final static String INVALID_REQUEST = "잘못된 요청방식입니다.";

	////////////////////////////////////////////// URL 및 JSP 파일 경로
	////////////////////////////////////////////// //////////////////////////////////////////////

	//////////////// 공통 ////////////////

	// 뒤로가기
	public static String HISTORY_BACK = "javascript:window.history.back()";

	// 메인 JSP
	public static String MAIN_PAGE = "/WEB-INF/main.jsp";

	// 에러 JSP
	public static String ERROR_PAGE = "/WEB-INF/error.jsp";

	// 에러 URL
	public static String ERROR_URL = "/error.trd";

	// 메시지 JSP
	public static String MESSAGE_PAGE = "/WEB-INF/message.jsp";

	//////////////// 관리자 ////////////////

	// 관리자 로그인 JSP
	public static String ADMIN_LOGIN_PAGE = "/WEB-INF/admin/login.jsp";

	// 관리자 로그인 URL
	public static String ADMIN_LOGIN_URL = "/admin/login.trd";

	// 관리자 메인 JSP
	public static String ADMIN_MAIN_PAGE = "/WEB-INF/admin/admin_main.jsp";

	// 관리자 메인 URL
	public static String ADMIN_MAIN_URL = "/admin/main.trd";

	// 관리자 상품 관리 JSP
	public static String ADMIN_PRODUCT_MANAGE_PAGE = "/WEB-INF/admin/product/product_manage.jsp";

	// 관리자 상품 관리 URL
	public static String ADMIN_PRODUCT_MANAGE_URL = "/admin/productManage.trd";

	// 관리자 상품 상세 JSP
	public static String ADMIN_PRODUCT_DETAIL_PAGE = "/WEB-INF/admin/product/product_detail.jsp";

	// 관리자 상품 상세 URL
	public static String ADMIN_PRODUCT_DETAIL_URL = "/admin/productDetail.trd";

	// 관리자 상품 추가 JSP
	public static String ADMIN_PRODUCT_REGISTER_PAGE = "/WEB-INF/admin/product/product_register.jsp";

	// 관리자 상품 추가 URL
	public static String ADMIN_PRODUCT_REGISTER_URL = "/admin/productRegister.trd";

	// 관리자 상품 수정 JSP
	public static String ADMIN_PRODUCT_UPDATE_JSP = "/WEB-INF/admin/product/product_update.jsp";

	// 관리자 상품 수정 URL
	public static String ADMIN_PRODUCT_UPDATE_URL = "/admin/productUpdate.trd";

	// 관리자 Q&A 관리 JSP
	public static String ADMIN_BOARD_MANAGE_JSP = "/WEB-INF/admin/board/board_manage.jsp";

	// 관리자 Q&A 관리 URL
	public static String ADMIN_BOARD_MANAGE_URL = "/admin/boardManage.trd";

	// 관리자 Q&A 상세 JSP
	public static String ADMIN_BOARD_DETAIL_JSP = "/WEB-INF/admin/board/board_detail.jsp";

	// 관리자 Q&A 답변 처리 URL
	public static String ADMIN_BOARD_DETAIL_URL = "/admin/boardDetail.trd";

	// 관리자 주문 관리 JSP
	public static String ADMIN_ORDER_MANAGE_JSP = "/WEB-INF/admin/order/order_manage.jsp";

	// 관리자 주문 관리 URL
	public static String ADMIN_ORDER_MANAGE_URL = "/admin/orderManage.trd";

	// 관리자 주문 상세 JSP
	public static String ADMIN_ORDER_DETAIL_JSP = "/WEB-INF/admin/order/order_detail.jsp";

	// 관리자 주문 상세 JSP
	public static String ADMIN_ORDER_DETAIL_URL = "/admin/orderDetail.trd";

	// 관리자 사용자 관리 JSP
	public static String ADMIN_MEMBER_MANAGE_JSP = "/WEB-INF/admin/member/member_manage.jsp";

	// 관리자 사용자 관리 URL
	public static String ADMIN_MEMBER_MANAGE_URL = "/admin/memberManage.trd";

	// 관리자 사용자 상세 JSP
	public static String ADMIN_MEMBER_DETAIL_JSP = "/WEB-INF/admin/member/member_detail.jsp";

	// 관리자 사용자 상세 URL
	public static String ADMIN_MEMBER_DETAIL_URL = "/admin/memberDetail.trd";

	// 관리자 사용자 로그인 이력 JSP
	public static String ADMIN_LOGIN_HISTORY_JSP = "/WEB-INF/admin/member/member_login_history.jsp";

	// 관리자 사용자 로그인 이력 URL
	public static String ADMIN_LOGIN_HISTORY_URL = "/admin/memberLoginHistory.trd";

	//////////////// 회원 //////////////////

	// 회원가입 JSP
	public static String REGISTER_PAGE = "/WEB-INF/member/register.jsp";

	// 회원가입 URL
	public static String REGISTER_URL = "/register.trd";

	// 회원탈퇴 JSP
	public static String MEMBERDELETE_PAGE = "/WEB-INF/member/memberDelete.jsp";

	// 회원탈퇴 URL
	public static String MEMBERDELETE_URL = "/member/memberDelete.trd";

	// 회원탈퇴 재확인 JSP
	public static String CHECKDELETE_PAGE = "/WEB-INF/member/memberDeletePwdCheck.jsp";

	// 회원탈퇴 재확인 URL
	public static String CHECKDELETE_URL = "/member/memberDeletePwdCheck.trd";

	// 로그인 JSP
	public static String MEMBER_LOGIN_PAGE = "/WEB-INF/login/login.jsp";

	// 로그인 URL
	public static String MEMBER_LOGIN_URL = "/login.trd";

	// 회원조회 JSP
	public static String MEMBER_Detail_PAGE = "/WEB-INF/member/memberDetail.jsp";

	// 회원조회 URL
	public static String MEMBER_Detail_URL = "/member/memberDetail.trd";

	// 회원 이메일 수정 JSP
	public static String MEMBER_UPDATE_EMAIL_PAGE = "/WEB-INF/member/updateEmail.jsp";

	// 회원 이메일 수정 URL
	public static String MEMBER_UPDATE_EMAIL_URL = "/member/updateEmail.trd";

	// 회원 전화번호 수정 JSP
	public static String MEMBER_UPDATE_MOBILE_PAGE = "/WEB-INF/member/updateMobile.jsp";

	// 회원 전화번호 수정 URL
	public static String MEMBER_UPDATE_MOBILE_URL = "/member/updateMobile.trd";

	// 회원 비밀번호 수정 JSP
	public static String MEMBER_UPDATE_PWD_PAGE = "/WEB-INF/member/updatePwd.jsp";

	// 회원 비밀번호 수정 URL
	public static String MEMBER_UPDATE_PWD_URL = "/member/updatePwd.trd";

	// 회원 비밀번호 찾기 URL
	public static String FIND_PWD_URL = "/login/findPwd.trd";

	// 회원 비밀번호 찾기 JSP
	public static String FIND_PWD_PAGE = "/WEB-INF/login/findPwd.jsp";

	// 휴면 해제 하기 JSP
	public static String MEMBER_DEACTIVATE_PAGE = "/WEB-INF/member/memberDeactivate.jsp";

	// 휴면 해제 하기 URL
	public static String MEMBER_DEACTIVATE_URL = "/member/memberDeactivate.trd";

	//////////////// Q&A 게시판 ////////////////

	// Q&A 게시판 메인 페이지 JSP
	public static String BOARD_LIST_PAGE = "/WEB-INF/board/list.jsp";

	// Q&A 게시판 메인 페이지 URL
	public static String BOARD_LIST_URL = "/board/list.trd";

	// Q&A 게시판 상세 페이지 JSP
	public static String BOARD_DETAIL_PAGE = "/WEB-INF/board/detail.jsp";

	// Q&A 게시판 등록 페이지 JSP
	public static String BOARD_REGISTER_PAGE = "/WEB-INF/board/register.jsp";

	//////////////// 상품 관련 페이지 ////////////////

	// 상품상세 페이지 JSP
	public static String PRODUCT_DETAIL_PAGE = "/WEB-INF/product/detail.jsp";

	// 상품 상세 페이지 URL
	public static String PRODUCT_DETAIL_URL = "/product/detail.trd";

	////////// 내 상수 ////////////////////////////

	// 회원의 마이페이지
	public static String MY_PAGE = "/WEB-INF/mypage/mypage.jsp";

	// 회원의 주소 페이지
	public static String MY_ADDRESS = "/WEB-INF/mypage/address.jsp";
	
	// 회원의 주소 URL
	public static String MY_ADDRESS_URL = "/member/address.trd";

	// 주소 추가 페이지
	public static String MY_ADDRESS_ADD = "/WEB-INF/mypage/addressAdd.jsp";

	// 회원의 주소 편집 페이지
	public static String MY_ADDRESS_EDIT = "/WEB-INF/mypage/addressEdit.jsp";

	// 주소 편집, 삭제, 기본주소를 선택 했을 떄의 주소에요
	public static String MY_ADDR_BUTTON_URL = "/member/addressButtonController.trd";

	// 주문내역 페이지
	public static String ORDERS_PAGE = "/WEB-INF/orders/orders.jsp";

	// 주문상세 페이지
	public static String ORDER_DETAIL_PAGE = "/WEB-INF/orders/ordersDetail.jsp";

	// 장바구니 페이지
	public static String BASKET_PAGE = "/WEB-INF/basket/basket.jsp";

	// 주문에서 주소 선택 페이지
	public static String PAYMENT_ADDRESS = "/WEB-INF/payment/payAddress.jsp";

	// 주문전 주문 내용 요약 페이지
	public static String PAYMENT_SUMMARY = "/WEB-INF/payment/paymentInfo.jsp";

	// 주문하는 헤이지 이동
	public static String PAYING_PAGE = "/WEB-INF/payment/paynicepay.jsp";

	// 주문 완료 후 나오는 json페이지
	public static String PAYMENT_COMPLETED = "/WEB-INF/payment/payCompleted.jsp";

	//////////////// 카테고리 ////////////////

	public static String CATEGORY_LIST_PAGE = "/WEB-INF/product/category_list.jsp";

	public static String SEARCH_PAGE = "/WEB-INF/product/search.jsp";

	// 검색 후 상품 리스트 페이지
	public static String SEARCH_LIST_PAGE = "/WEB-INF/product/search_list.jsp";

	public static String CATEGORY_LIST_URL = "/product/category_list.trd";

}
