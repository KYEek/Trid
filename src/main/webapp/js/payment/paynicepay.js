//결제 함수
async function requestPayment() {
  const response = await PortOne.requestPayment({
    // Store ID 설정
    storeId: "store-96b0d25f-1d8d-4239-a08e-6efbda0c3e29",
    // 채널 키 설정
    channelKey: "channel-key-abcd395e-b82e-4b41-be89-02016c0d0995",
    paymentId: "payment-" + crypto.randomUUID(),
    orderName: `${member_name}님의 결제`,
    totalAmount: 100,
    currency: "CURRENCY_KRW",
    payMethod: "CARD",
  });

  if (response.code !== undefined) {
    // 오류 발생
    alert(response.message);
    return (history.go(-4));
  }

  // /payment/complete 엔드포인트를 구현해야 합니다. 다음 목차에서 설명합니다.
  const notified = await fetch("complete.trd", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    // paymentId와 주문 정보를 서버에 전달합니다
    body: JSON.stringify({
      paymentId: response.paymentId,
      // 주문 정보...
    }),
  })
    .then((data) => {
      document.querySelector("div#payment_complete").style.display = "block";

      alert("결제가 완료되었습니다.");
      return true;
    })
    .catch((message) => {
      console.error(message);
      return false;
    });
}

document.addEventListener("DOMContentLoaded", function () {
  //결제를 실행하면서 결제가 성공하면 로딩을 가리기
  if (requestPayment()) {
    document.querySelector("div#payment_loading_container").style.display =
      "none";
  }
  //세션에 있는 값들을 불러온다다
  const basket_item_arry_str = sessionStorage.getItem("basket_item_arry");
  const selected_address_no = sessionStorage.getItem("selected_address_no");
  const total_price = sessionStorage.getItem("total_price");
  const instantPay = sessionStorage.getItem("instantPay");
  document.querySelector("span#total_price").textContent = total_price;

  //장바구니 목록의 값들을 저장할 배열
  const orderDetailArr = [];

  const basket_item_arry = JSON.parse(basket_item_arry_str);

  console.log(basket_item_arry);
  basket_item_arry.forEach((item) => {
    const orderDetail = {};
    orderDetail["productCountNum"] = item["productCountNum"];
    orderDetail["productDetailNo"] = item["productDetailNo"];
    orderDetail["productPrice"] = item["productPrice"];
    console.log(orderDetail);
    orderDetailArr.push(orderDetail);
  });
  console.log(orderDetailArr);
  //결제 완료 버튼
  const pay_completed = document.querySelector("div#basket_footer_next_button");

  //데이터 전송을 위한 폼 생성성
  const form = document.createElement("form");
  document.body.appendChild(form);
  form.method = "POST";
  form.action = "/Trid/payment/paymentCompleted.trd";

  const input_orderDetailArr = document.createElement("input");
  input_orderDetailArr.type = "hidden";
  input_orderDetailArr.name = "orderDetailArr";
  input_orderDetailArr.value = JSON.stringify(orderDetailArr);

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

  const input_selected_address_no = document.createElement("input");
  input_selected_address_no.type = "hidden";
  input_selected_address_no.name = "selected_address_no";
  input_selected_address_no.value = selected_address_no;

  const input_total_price = document.createElement("input");
  input_total_price.type = "hidden";
  input_total_price.name = "total_price";
  input_total_price.value = total_price;
  
  const input_instantPay = document.createElement("input");
  input_instantPay.type = "hidden";
  input_instantPay.name = "instantPay";
  input_instantPay.value = instantPay;

  form.appendChild(input_orderDetailArr);
  // form.appendChild(input_countnum);
  // form.appendChild(input_detailnum);
  // form.appendChild(input_pricd);
  form.appendChild(input_selected_address_no);
  form.appendChild(input_total_price);
  form.appendChild(input_instantPay);
  pay_completed.addEventListener("click", (e) => {
    form.submit();
  });
});
