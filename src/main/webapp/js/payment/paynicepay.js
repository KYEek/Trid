//결제 함수
async function requestPayment() {
  const response = await PortOne.requestPayment({
    // Store ID 설정
    storeId: "store-96b0d25f-1d8d-4239-a08e-6efbda0c3e29",
    // 채널 키 설정
    channelKey: "channel-key-abcd395e-b82e-4b41-be89-02016c0d0995",
    paymentId: "payment-" + crypto.randomUUID(),
    orderName: "${requestScope.member_name}님의 결제",
    totalAmount: 100,
    currency: "CURRENCY_KRW",
    payMethod: "CARD",
  });

  if (response.code !== undefined) {
    // 오류 발생
    alert(response.message);
    return (location.href = sessionStorage.getItem("backURL"));
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
  document.querySelector("span#total_price").textContent = total_price;

  //장바구니 목록의 값들을 저장할 배열
  const productCountNum = [];
  const productDetailNo = [];
  const productPrice = [];

  const basket_item_arry = JSON.parse(basket_item_arry_str);

  basket_item_arry.forEach((item) => {
    productCountNum.push(item["productCountNum"]);
    productDetailNo.push(item["productDetailNo"]);
    productPrice.push(item["productPrice"]);
  });
  console.log(productCountNum);
  console.log(productDetailNo);
  console.log(productPrice);

  //결제 완료 버튼
  const pay_completed = document.querySelector("div#basket_footer_next_button");

  //데이터 전송을 위한 폼 생성성
  const form = document.createElement("form");
  form.method = "POST";
  form.action = "/Trid/payment/paymentCompleted.trd";

  const input_countnum = document.createElement("input");
  input_countnum.type = "hidden";
  input_countnum.name = "productCountNum";
  input_countnum.value = JSON.stringify(productCountNum);

  const input_detailnum = document.createElement("input");
  input_countnum.type = "hidden";
  input_countnum.name = "productDetailNo";
  input_countnum.value = JSON.stringify(productDetailNo);

  const input_pricd = document.createElement("input");
  input_countnum.type = "hidden";
  input_countnum.name = "productPrice";
  input_countnum.value = JSON.stringify(productPrice);

  const input_selected_address_no = document.createElement("input");
  input_countnum.type = "hidden";
  input_countnum.name = "selected_address_no";
  input_countnum.value = selected_address_no;

  const input_total_price = document.createElement("input");
  input_countnum.type = "hidden";
  input_countnum.name = "total_price";
  input_countnum.value = total_price;

  form.appendChild(input_countnum);
  form.appendChild(input_detailnum);
  form.appendChild(input_pricd);
  form.appendChild(input_selected_address_no);
  form.appendChild(input_total_price);

  pay_completed.addEventListener("click", (e) => {
    form.submit();
  });
});
