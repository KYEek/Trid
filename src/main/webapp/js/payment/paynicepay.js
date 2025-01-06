//세션에 있는 값들을 불러온다
let basket_item_arry_str;
let selected_address_no;
let total_price;
let instantPay;
const orderDetailArr = []; //장바구니 목록의 값들을 저장할 배열

//결제 함수
async function requestPayment() {
  const response = await PortOne.requestPayment({
    // Store ID 설정
    storeId: "store-96b0d25f-1d8d-4239-a08e-6efbda0c3e29",
    // 채널 키 설정
    channelKey: "channel-key-abcd395e-b82e-4b41-be89-02016c0d0995",
    paymentId: "payment-" + crypto.randomUUID(),
    orderName: `${member_name}님의 결제`,
    totalAmount: sessionStorage.getItem("total_price"),
    currency: "CURRENCY_KRW",
    payMethod: "CARD",
  });

  if (response.code !== undefined) {
    // 오류 발생
    alert(response.message);
    return location.href = sessionStorage.getItem("backURL");
  }

  // /payment/complete 엔드포인트를 구현해야 합니다. 다음 목차에서 설명합니다.
  const notified = await fetch("complete.trd", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    // paymentId와 주문 정보를 서버에 전달합니다
    body: JSON.stringify({
      paymentId: response.paymentId,
      orderDetailArr: JSON.stringify(orderDetailArr),
      selected_address_no: selected_address_no,
      input_total_price: total_price,
      instantPay: instantPay,
      // 주문 정보...
    }),
  })
    .then((data) => {
      document.querySelector("div#payment_complete").style.display = "block";

      // const input_orderDetailArr = document.createElement("input");
      // input_orderDetailArr.type = "hidden";
      // input_orderDetailArr.name = "orderDetailArr";
      // input_orderDetailArr.value = JSON.stringify(orderDetailArr);

      // const input_countnum = document.createElement("input");
      // input_countnum.type = "hidden";
      // input_countnum.name = "productCountNum";
      // input_countnum.value = JSON.stringify(productCountNum);

      // const input_detailnum = document.createElement("input");
      // input_detailnum.type = "hidden";
      // input_detailnum.name = "productDetailNo";
      // input_detailnum.value = JSON.stringify(productDetailNo);

      // const input_pricd = document.createElement("input");
      // input_pricd.type = "hidden";
      // input_pricd.name = "productPrice";
      // input_pricd.value = JSON.stringify(productPrice);

      // const input_selected_address_no = document.createElement("input");
      // input_selected_address_no.type = "hidden";
      // input_selected_address_no.name = "selected_address_no";
      // input_selected_address_no.value = selected_address_no;

      // const input_total_price = document.createElement("input");
      // input_total_price.type = "hidden";
      // input_total_price.name = "total_price";
      // input_total_price.value = total_price;

      // const input_instantPay = document.createElement("input");
      // input_instantPay.type = "hidden";
      // input_instantPay.name = "instantPay";
      // input_instantPay.value = instantPay;

      // form.appendChild(input_orderDetailArr);
      // form.appendChild(input_countnum);
      // form.appendChild(input_detailnum);
      // form.appendChild(input_pricd);
      // form.appendChild(input_selected_address_no);
      // form.appendChild(input_total_price);
      // form.appendChild(input_instantPay);
      //폼 생성 끝---------------------------------------------
      //폼 전송

	  //세션에 있던 값을 삭제
      sessionStorage.removeItem("basket_item_arry");
      sessionStorage.removeItem("selected_address_no");
      sessionStorage.removeItem("total_price");
      sessionStorage.removeItem("instantPay");
	  sessionStorage.removeItem("backURL");
//      console.log(data);
//      console.log(orderDetailArr);
	  sessionStorage.removeItem("isCorrect");
      alert("결제가 완료되었습니다.");
      return true;
    })
    .catch((message) => {
      console.error(message);
      return false;
    });
}

document.addEventListener("DOMContentLoaded", function () {
  if (sessionStorage.getItem("basket_item_arry") == null) {
    alert("잘못된 접근입니다 메인으로 돌아가겠습니다.");
    location.href = "/Trid/";
  }
  //정상적인 접근이 아닐 경우(이전페이지를 통해 오지 않거나 결제가 완료된 후에 접근하는 경우)
  if(sessionStorage.getItem("isCorrect") == null) {
	alert("잘못된 접근입니다..");
	location.href = "/Trid/";
  }
  //세션에 있는 값들을 불러온다
  basket_item_arry_str = sessionStorage.getItem("basket_item_arry");
  selected_address_no = sessionStorage.getItem("selected_address_no");
  total_price = sessionStorage.getItem("total_price");
  instantPay = sessionStorage.getItem("instantPay");
  //console.log(basket_item_arry_str);
  //장바구니 목록의 값들을 배열로 변환
  const basket_item_arry = JSON.parse(basket_item_arry_str);

  //주문상세 배열에 장바구니 목록의 값들을 저장
  basket_item_arry.forEach((item) => {
    const orderDetail = {}; //주문 상세를 저장할 객체
    orderDetail["productCountNum"] = item["productCountNum"];
    orderDetail["productDetailNo"] = item["productDetailNo"];
    orderDetail["productPrice"] = item["productPrice"];
    console.log(orderDetail);
    orderDetailArr.push(orderDetail);
  });
  //결제를 실행하면서 결제가 성공하면 로딩을 가리기
  if (requestPayment()) {
    document.querySelector("div#payment_loading_container").style.display =
      "none";
  }

  document.querySelector("span#total_price").textContent = Number(total_price).toLocaleString()+"₩";

  //결제 완료 버튼
  const pay_completed = document.querySelector("div#basket_footer_next_button");

  //결제 완료 버튼 클릭시 폼 전송
  pay_completed.addEventListener("click", (e) => {
    //페이지 이동을을 위한 폼 생성---------------------------------------------
    const form = document.createElement("form");
    document.body.appendChild(form);
    form.method = "POST";
    form.action = "/Trid/payment/paymentCompleted.trd";
    form.submit();
  });
});
