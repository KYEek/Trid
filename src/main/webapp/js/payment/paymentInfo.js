document.addEventListener("DOMContentLoaded", function () {
  if (sessionStorage.getItem("basket_item_arry") == null) {
    location.href = "/Trid/";
  }
  
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
  //주문금액이 50,000원 미만일 경우 배송비 3,000원 추가
  if (total_price < 50000) {
    total_price = total_price + 3000;
  }

  //배송비가 합쳐진 금액을 세션에 저장
  sessionStorage.setItem("total_price", total_price);
  //총 금액을 하단에 표시
  total_price_span.textContent = "₩ " + total_price.toLocaleString();

  delivery_product_list.innerHTML = imghtml;
  const product_info = sessionStorage.getItem("basket_item_arry");
  const form_var = document.forms[0];
  form_var.elements["total_price"].value = total_price;
  form_var.elements["product_info"].value = product_info;

  //날짜를 계산
   const today = new Date();
   const nextday = new Date();
   nextday.setDate(today.getDate() + 2);
   const week = ["일", "월", "화", "수", "목", "금", "토"];

   const month = today.getMonth() + 1;
   const date = today.getDate();
   const day = today.getDay();
   
   const next_month = nextday.getMonth() + 1;
   const next_date = nextday.getDate();
   const next_day = nextday.getDay();

   const delivery_date = week[day] + "요일 " + date + ", " + month +"월 - " + week[next_day] + "요일 " + next_date + ", " + next_month +"월";
   //날짜를 삽입
   document.querySelector("span#delivery_date").textContent = delivery_date;
   document.querySelector("span#delivery_day").textContent = delivery_date;
  
  //주문하기
  document
    .querySelector("#basket_footer_next_button")
    .addEventListener("click", function () {
      //비정상 적인 접근일 경우 메인으로 이동
      if (product_info == null) {
        alert("비정상적인 접근입니다.");
        location.href = "/Trid/";
      } else {
		//취소했을 때 돌아올 주소 정보
		sessionStorage.setItem("backURL",window.location.href)
		sessionStorage.setItem("isCorrect", "true");
        form_var.submit();
      }
    });
});
