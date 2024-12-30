<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

						<%@ include file="../side_navigation.jsp" %>

							<div id="main_container">

								<div id="register_header">
									<h2>상품 추가</h2>
									<div>
										<a id="return" href="productManage.trd">돌아가기</a>
									</div>
								</div>

								<div id="register_container">
									<form id="register_frm" name="product_register_frm" enctype="multipart/form-data">

										<div id="form_register_container">

											<div id="input_container">
												<div class="input_box">
													<span>상품명</span> <input type="text" name="productName" />
												</div>
												<div class="input_box" style="align-items: flex-start;">
													<span>상품 설명</span> <textarea id="explanation_textarea" cols="30" rows="5" name="explanation"></textarea>
												</div>
												<div class="input_box">
													<span>상품 가격</span> <input type="text" name="price" />
												</div>

												<div id="select_container">
													<span>카테고리</span>
													<select name="categoryNo">
														<option value="">카테고리를 선택하세요</option>
														<c:forEach items="${categoryList}" var="categoryDTO">
															<option value="${categoryDTO.pkCategoryNo}">

																<c:if test="${categoryDTO.gender eq 0}">
																	남자&nbsp;
																</c:if>

																<c:if test="${categoryDTO.gender eq 1}">
																	여자&nbsp;
																</c:if>

																<c:if test="${categoryDTO.type eq 0}">
																	상의&nbsp;:
																</c:if>

																<c:if test="${categoryDTO.type eq 1}">
																	하의&nbsp;:
																</c:if> ${categoryDTO.categoryName}

															</option>
														</c:forEach>
													</select>
												</div>

												<div style="display: flex; justify-content: space-between; align-items: start;">
													<span>사이즈별 재고</span>


													<div class="size_container">
														<div class="size_box">
															<span class="size_span">S</span><input type="number" id="small" class="size" value=0 />
														</div>

														<div class="size_box">
															<span class="size_span">M</span><input type="number" id="medium" class="size" value=0 />
														</div>
													</div>
												</div>
												<div class="size_container">
													<div class="size_box">
														<span class="size_span">L</span><input type="number" id="large" class="size" value=0 />
													</div>
													<div class="size_box">
														<span class="size_span">XL</span><input type="number" id="x_large" class="size" value=0 />
													</div>
												</div>


											</div>

											<input type="hidden" name="inventory" /> <input type="hidden" id="colorName" name="colorName" /> <input type="hidden" id="colorCode" name="colorCode" />

											<div id="color_box"></div>

											<div id="color_container">

												<select id="color_select">
													<option value="">색상을 선택하세요</option>
													<option value="RED">RED</option>
													<option value="ORANGE">ORANGE</option>
													<option value="YELLOW">YELLOW</option>
													<option value="GREEN">GREEN</option>
													<option value="BLUE">BLUE</option>
													<option value="PURPLE">PURPLE</option>
													<option value="GRAY">GRAY</option>
													<option value="WHITE">WHITE</option>
													<option value="BLACK">BLACK</option>
													<option value="BROWN">BROWN</option>
												</select> <input id="color_code_input" type="text" placeholder="색상코드" />

												<button type="button" id="add_color_button">색상 추가하기</button>




											</div>

										</div>

										<div id="image_register_container">
											<div id="image_container">
												<%@ include file="../../image_carousel_register.jsp" %>
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
							$(document).on("click", "#product_register_button", function () {
								const arr_inventory = [];
								$("input.size").each((index, element) => {
									arr_inventory.push($(element).val());
								});

								const str_inventory = arr_inventory.join(",");

								$("input:hidden[name='inventory']").val(str_inventory);

								$("input:hidden[name='colorName']").val(colorNameArr);
								$("input:hidden[name='colorCode']").val(colorCodeArr);

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