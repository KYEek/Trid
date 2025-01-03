<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<%-- 관리자 상품 수정 페이지 --%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>관리자 상품 수정</title>

<%-- css --%>
<link rel="stylesheet" href="${ctxPath}/css/admin/product_register.css">

<%-- js --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${ctxPath}/js/admin/product.js"></script>

</head>

<body>
	<%-- ProductDTO --%>
	<c:set var="productDTO" value="${requestScope.productDTO}" />
	<%-- CategoryDTO --%>
	<c:set var="categoryDTO" value="${requestScope.productDTO.categoryDTO}" />
	<%-- ProductDetailDTO List --%>
	<c:set var="productDetailList" value="${requestScope.productDTO.productDetailList}" />
	<%-- ImageDTO List --%>
	<c:set var="imageList" value="${requestScope.productDTO.imageList}" />

	<div id="product_register_container">

		<%@ include file="../side_navigation.jsp"%>

		<div id="main_container">

			<div id="register_header">
				<h2>상품 수정</h2>
				<div>
					<a id="return" href="productManage.trd">돌아가기</a>
				</div>
			</div>

			<div id="register_container">
				<form id="register_frm" name="product_register_frm" enctype="multipart/form-data">

					<div id="form_register_container">

						<div id="input_container">
							<div class="input_box">
								<span>상품명</span> <input type="text" name="productName" value="${productDTO.productName}" />
							</div>
							<div class="input_box" style="align-items: flex-start;">
								<span>상품 설명</span>
								<textarea id="explanation_textarea" cols="30" rows="5" name="explanation">${productDTO.explanation}</textarea>
							</div>
							<div class="input_box">
								<span>상품 가격</span> <input type="text" name="price" value="${productDTO.price}" />
							</div>

							<div id="select_container">
								<span>카테고리</span> 
								<span>
									<c:if test="${categoryDTO.gender eq 0}">남자&nbsp;</c:if>

									<c:if test="${categoryDTO.gender eq 1}">여자&nbsp;</c:if>

									<c:if test="${categoryDTO.type eq 0}">상의&nbsp;:</c:if>

									<c:if test="${categoryDTO.type eq 1}">하의&nbsp;:</c:if> 
									${categoryDTO.categoryName}
								</span>

							</div>

							<div style="display: flex; justify-content: space-between; align-items: start;">
								<span>사이즈별 재고</span>

								<div class="size_container">
									<div class="size_box">
										<span class="size_span">S</span>
										<input type="number" id="${productDetailList[0].pkProductDetailNo}" class="size" value="${requestScope.inventoryArr[0]}" />
									</div>

									<div class="size_box">
										<span class="size_span">M</span>
										<input type="number" id="${productDetailList[1].pkProductDetailNo}" class="size" value="${requestScope.inventoryArr[1]}" />
									</div>
								</div>
							</div>
							<div class="size_container">
								<div class="size_box">
									<span class="size_span">L</span>
									<input type="number" id="${productDetailList[2].pkProductDetailNo}" class="size" value="${requestScope.inventoryArr[2]}" />
								</div>
								<div class="size_box">
									<span class="size_span">XL</span>
									<input type="number" id="${productDetailList[3].pkProductDetailNo}" class="size" value="${requestScope.inventoryArr[3]}" />
								</div>
							</div>


						</div>

						<input type="hidden" name="productNo" value="${productDTO.productNo}" /> 
						<input type="hidden" name="inventory" /> 
						<input type="hidden" name="productDetailNoArr" /> 

						<div id="color_box"></div>


					</div>

					<div id="image_register_container">
						<div id="image_container">
						
							<%@ include file="../../image_carousel_update.jsp"%>
							
							<div id="image_frm_container">
								<input type="hidden" name="fileName" />
								<button type="button" id="file_upload_button">이미지 추가</button>

								<input type="file" id="image_input" name="file" multiple onchange="readURL(this);" />
								<button type="button" id="delete_image_button">삭제</button>

							</div>

							<button type="button" id="product_register_button">등록하기</button>
						</div>
					</div>

				</form>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$(document).ready(function () {
	
		$("select[name='categoryNo']").val(${ productCategoryDTO.pkCategoryNo })
	
		<c:forEach items="${productDTO.colorList}" var="colorDTO" >
				colorNameArr.push('${colorDTO.colorName}');
				colorCodeArr.push('${colorDTO.colorCode}');
		</c:forEach >	

		for (let i in colorNameArr) {
				console.log(colorNameArr[i]);

				const colorName = colorNameArr[i];
				const colorCode = colorCodeArr[i];

				addColorItem(colorName, colorCode);
			}

			$(document).on("click", "#product_register_button", function () {
				
				// 제츨 요소 유효성 검사
				if(!validateProductDetailsOnUpdate()) {
					return false;
				}
				
				const arr_inventory = [];
				const productDetailNoArr= [];
				
				$("input.size").each((index, element) => {
					arr_inventory.push($(element).val());
					productDetailNoArr.push($(element).attr("id"));
				});

				const str_inventory = arr_inventory.join(",");
				const str_productDetailNo = productDetailNoArr.join(",");

				$("input:hidden[name='inventory']").val(str_inventory);

				$("input:hidden[name='productDetailNoArr']").val(str_productDetailNo);

				const register_frm = document.product_register_frm;

				register_frm.method = "post";
				register_frm.action = "productUpdate.trd";

				register_frm.submit();
			});


			$("input[name='colorName']").change(function () {
				console.log($("input[name='colorName']:checked").val());
			});

		});
	</script>
</body>

</html>