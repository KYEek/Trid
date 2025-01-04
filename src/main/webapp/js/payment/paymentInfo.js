document.addEventListener("DOMContentLoaded", function () {
  //장바구니의 상품 정보 가져오기
  const basket_item_arry = JSON.parse(
    sessionStorage.getItem("basket_item_arry")
  );
  const instantPay = sessionStorage.getItem("instantPay");
  const item_count = document.querySelector("   #item_count");
  const delivery_product_list = document.querySelector(
    "#delivery_product_list"
  );
  const total_price_span = document.querySelector("#total_price");
  let imghtml = "";
  let total_price = 0;
  //장바구니의 상품 정보를 저장
  console.log(item_count);
  item_count.textContent = basket_item_arry.length;
	for (item of basket_item_arry) {
		total_price +=
			Number(item["productPrice"]) * Number(item["productCountNum"]);
		if (instantPay == "true") {
			imghtml += `<img src="/Trid/${item["imgSrc"]}" />`;
		} else {
			imghtml += ` <img src="${item["imgSrc"]}" />`;
		}
	}
  //주문 총액을 계산
  total_price = total_price + 3000;
  sessionStorage.setItem("total_price", total_price);
  total_price_span.textContent = "₩ " + total_price;

  delivery_product_list.innerHTML = imghtml;

  const form_var = document.forms[0];
  form_var.elements["total_price"].value = total_price;

  //주문 취소시 이전 페이지 이동을 위해 주소를 저장
  document
    .querySelector("#basket_footer_next_button")
    .addEventListener("click", function () {
		sessionStorage.setItem("backURL",window.location.href)
      form_var.submit();
    });
});
