<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<%-- 관리자 상품 상세 페이지 --%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>관리자 상품 상세</title>

<%-- css --%>
<link rel="stylesheet" href="${ctxPath}/css/admin/product_register.css">

<%-- js --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${ctxPath}/js/admin/product_register.js"></script>

</head>

<body>
	<c:set var="categoryList" value="${requestScope.categoryList}" />

	<div id="product_register_container">
		<%@ include file="side_navigation.jsp"%>

		<div id="main_container">

			<div id="register_header">
				<button>돌아가기</button>
			</div>

			<div id="register_container">

				<form name="product_register_frm" enctype="multipart/form-data">
					<div style="display: flex">
						<div id="register_form_container">
							<div class="input_container">
								<span>상품명</span> <input type="text" name="productName" />
							</div>
							<div class="input_container">
								<span>상품 설명</span> <input type="text" name="explanation" />
							</div>
							<div class="input_container">
								<span>상품 가격</span> <input type="text" name="price" />
							</div>
							<select name="categoryNo">
								<c:forEach items="${categoryList}" var="categoryDTO">
									<option value="${categoryDTO.pkCategoryNo}">${categoryDTO.categoryName}:${categoryDTO.type}: ${categoryDTO.gender}</option>
								</c:forEach>
							</select>
							<div id="size_container">
								<input type="number" id="small" class="size" value=0 /> <label id="small">S 사이즈 재고</label> <input type="number" id="medium" class="size" value=0 /> <label id="medium">M 사이즈 재고</label> <input type="number" id="large" class="size" value=0 /> <label id="large">L 사이즈 재고</label> <input type="number" id="x_large" class="size" value=0 /> <label id="x_large">XL 사이즈 재고</label>
							</div>

							<div>
								<input type="hidden" name="inventory" /> <input type="checkbox" id="color_white" name="colorName" value="white" /> <label id="color_white">휜색</label> <input type="checkbox" id="color_black" name="colorName" value="black" /> <label id="color_black">검은색</label> <input type="checkbox" id="color_white_code" name="colorCode" value="#ffffff" /> <label id="color_white_code">R아서 휜색</label> <input type="checkbox" id="color_black_code" name="colorCode" value="#000000" /> <label id="color_black_code">R아서 검은색</label>
							</div>


						</div>


						<div style="display: flex; flex-direction: column; justify-content: center;">
							<div id="image_container">
								<%@ include file="../image_carousel_register.jsp"%>
							</div>

							<button style="margin-top: 200px" id="add_image_button">이미지 추가하기</button>


							<div id="image_frm_container">
								<input type="hidden" name="fileName" /> 
								<input type="file" id="image_input" name="file" multiple onchange="readURL(this);" />
							</div>


						</div>
					</div>

					<button type="button" id="reset">전체 취소</button>
					<button id="product_register_button" type="button">등록하기</button>
				</form>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function () {
			$(document).on("click", "#product_register_button", function () {
				const arr_inventory = [];
				$("input.size").each((index, element) => {
					arr_inventory.push($(element).val());
				});

				const str_inventory = arr_inventory.join(",");

				$("input:hidden[name='inventory']").val(str_inventory);

				const register_frm = document.product_register_frm;
				register_frm.method = "post";
				register_frm.action = "productRegister.trd";

				register_frm.submit();
			});


			$("input[name='colorName']").change(function () {
				console.log($("input[name='colorName']:checked").val());
			});
			
		});
	</script>
</body>

</html>