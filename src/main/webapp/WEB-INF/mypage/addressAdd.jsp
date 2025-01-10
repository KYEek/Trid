<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head>
    <meta charset="UTF-8" />
    <title>주소 추가</title>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header/hamburger.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header/header.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/myPage/address.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/myPage/addressAdd.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/myPage/mypage.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/css/bootstrap.min.css">
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/mypage/addressAdd.js"></script>
    <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  </head>
<body>
	<%@include file="../header.jsp"%>
	<main>
		<div id="address_header">
			<div>새로운 주소</div>
			<p>
				우편번호를 입력하여 주소를 검색하세요. 주소 필드는 검색을 기반으로 자동 완성됩니다. 주소 <b>2</b> 필드에 필요한
				정보를 입력하여 주소를 완성할 수 있습니다.
			</p>
		</div>
		<div id="menu_container" style="border:0px solid black">
			<div id="address_input">
				<form id="address_form">
					<div class="input_area">
						<label for="input1">이름</label> <input class="input_area"
							name="name" type="text" id="input1" />
						<div class="warning_div"></div>
					</div>
					<div>
						<div id="zip_area" class="input_area">
							<div id="zip_input">
								<label for="input2">우편번호</label> <input class="input_area"
									name="zipNo" type="text" id="input2" />
								<button id="find_Zip" type="button">
									<span>우편번호 찾기</span>
								</button>
							</div>

							<div class="warning_div"></div>
						</div>
					</div>
					<div class="input_area">
						<label for="input3">주소</label> <input class="input_area"
							name="address" type="text" id="input3" />

						<div class="warning_div"></div>
					</div>
					<div class="input_area">
						<label for="input4">상세주소</label> <input class="input_area"
							name="addr_detail" type="text" id="input4" />

						<div class="warning_div"></div>
					</div>
					<div class="input_area">
						<label for="input5">지역</label> <input class="input_area"
							type="text" id="input5" />
						<div class="warning_div"></div>
					</div>
					<div class="input_area">
						<label for="input6">전화번호</label> <input class="input_area"
							name="mobile_num" type="text" id="input6" />
						<div class="warning_div"></div>
					</div>
					<input class="input_area" name="addr_extraaddr" type="text"
						id="extraAddress" style="display: none" />
					<button type="button" id="submit_btn">저장</button>
				</form>
			</div>
		</div>
	</main>
	<%@include file="../footer.jsp"%>
</body>
</html>