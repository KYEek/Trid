//장바구니 개수
let basketCount = 0;
//재고가 있는지 파악하기 위한 변수
let is_inventory = true;

//장바구니 목록을 가져오는 함수
async function getBasketList() {
  try {
    //    const response = await fetch("json/basketList.json", {
    //      method: "get",
    //      headers: { contentType: "application/json" },
    //    });
    //    const basketList = await response.json();

    //각 json객체를 순환

    let html = "";
    let PK_BASKET_NO = "";
    let FK_MEMBER_NO = "";
    let FK_PRODUCT_DETAIL_NO = "";
    let BASKET_QUANTITY = "";
    let PRODUCT_NO = "";
    let PRODUCT_SIZE = "";
    let PRODUCT_INVENTORY = "";
    let PRODUCT_NAME = "";
    let PRODUCT_PRICE = "";
    let COLOR_NAME = "";
    let PRODUCT_IMAGE_PATH = "";
    let PRODUCT_IMAGE_NAME = "";
    basketCount = 0;
    basketList.forEach((basket) => {
      //   console.log(basket);
      //   json 객체의 값을 추출하기 위한 for문
      for (key in basket) {
        // console.log(basket[key]);
        switch (key) {
          case "PK_BASKET_NO":
            PK_BASKET_NO = basket[key];
            break;
          case "FK_MEMBER_NO":
            FK_MEMBER_NO = basket[key];
            break;
          case "FK_PRODUCT_DETAIL_NO":
            FK_PRODUCT_DETAIL_NO = basket[key];
            break;
          case "BASKET_QUANTITY":
            BASKET_QUANTITY = basket[key];
            break;
          case "PRODUCT_NO":
            PRODUCT_NO = basket[key];
            break;
          case "PRODUCT_SIZE":
            PRODUCT_SIZE = basket[key];
            break;
          case "PRODUCT_INVENTORY":
            PRODUCT_INVENTORY = basket[key];
            break;
          case "PRODUCT_NAME":
            PRODUCT_NAME = basket[key];
            break;
          case "PRODUCT_PRICE":
            PRODUCT_PRICE = basket[key];
            break;
          case "COLOR_NAME":
            COLOR_NAME = basket[key];
            break;
          case "PRODUCT_IMAGE_PATH":
            PRODUCT_IMAGE_PATH = basket[key];
            break;
          case "PRODUCT_IMAGE_NAME":
            PRODUCT_IMAGE_NAME = basket[key];
            break;
        }
      } // end of for-------------------------------
      html += `<div id="basket_${PK_BASKET_NO}" class="basket_item" data-basket_no="${PK_BASKET_NO}" >
            <div class="basket_img">
              <a><img src="/Trid/${PRODUCT_IMAGE_PATH}" /></a>
            </div>
            <div class="basket_product_info" data-product_detail_no="${FK_PRODUCT_DETAIL_NO}" >
              <div class="basket_product_info_header">
                <div><a class="product_link">${PRODUCT_NAME}</a></div>
                <div id= "basket_delete_${PK_BASKET_NO}" class = "basket_delete">⨉</div>
              </div>
              <div class="basket_pruduct_price">₩<span class="price_text" data-price ="${PRODUCT_PRICE}">${
        (PRODUCT_PRICE * BASKET_QUANTITY).toLocaleString()
      }</span></div>
              <div class="basket_pruduct_size_category">
                <span class="basket_product_size">${PRODUCT_SIZE}</span>&nbsp;|&nbsp;<span
                  class="basket_product_category"
                  >${COLOR_NAME}</span
                >
              </div>
              <div class="basket_product_count_container">
                <button class="plus_count">+</button>
                <div class="basket_product_count">
                  <span class="pruduct_count_num" data-inventory="${PRODUCT_INVENTORY}">${BASKET_QUANTITY}</span>
                </div>
                <button class="minus_count">-</button>
              </div>
            </div>`;
      if (PRODUCT_INVENTORY == 0) {
        html += `<div class="no_inventory"><div class="no_inventory_text">재고가 없습니다</div></div>`;
        is_inventory = false;
      }
      html += `</div>`;
      //장바구니의 총 개수를 증가
      basketCount = basketCount + 1;
    }); // end of forEach-------------------------------
    return html;
  } catch {
    console.log("error");
  }
}

//장바구니 삭제 비동기 메서드
async function deleteBasketList(basket_no, loading_box) {
  try {
    showLoading(loading_box);
    // console.log(basket_no);
    //자바 실행 요청
    const response = await fetch("/Trid/basket/delete.trd", {
      method: "post",
      headers: { "Content-Type": "text/plain" },
      body: basket_no,
    });
    //자바의 실행 결과를 받는다
    const text = await response.text();
    //자바에서 반환 된 값이 success인 경우(정상적으로 실행)
    if (text.trim() == "success") {
      console.log("삭제 함수 성공");
      hideLoading(loading_box);
      return "success";
    }
    //    //자바에서 반환 된 값이 success가 아닌 경우(실패)
    else {
      console.log("fail");
      return "fail";
    }
  } catch (error) {
    //에러 발생시
    console.error(error.message);
    hideLoading(loading_box);
    return "fail";
  }
}

//개수 변경 비동기 메서드
async function changeBasketCount(json, loading_box) {
  try {
    showLoading(loading_box);
    //자바 실행 요청
    const response = await fetch("/Trid/basket/change.trd", {
      method: "post",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(json),
    });
    //자바의 실행 결과를 문자형태로 저장한다
    const result = await response.text();
    //자바의 실행 결과가 success인 경우
    if (result.trim() == "success") {
      console.log(result.trim());
      hideLoading(loading_box);
      return "success";
    }
    //자바의 실행 결과가 success가 아닌 경우
    else {
      console.log("실패");
      return "fail";
    }
  } catch (error) {
    //에러 발생시
    console.error(error.message);
    hideLoading(loading_box);
    return "fail";
  }
}

//장바구니의 총 가격을 계산하는 함수
function calculateTotalPrice() {
  //각 장바구니의 가격정보 불러오기
  const priceList = document.querySelectorAll("span.price_text");
  // 총 가격 생성
  let totlaPrice = 0;
  priceList.forEach((element) => {
	//총 가격을 더할 때 콤마를 제거 한 후 저장
    totlaPrice += Number(element.textContent.replaceAll(",",""));
  });
  document.querySelector("span#total_price").textContent =
    totlaPrice.toLocaleString();
}

//로딩화면을 보여주는 함수
function showLoading(loading_box) {
  loading_box.style.display = "block";
}

//로딩화면을 숨기는 함수
function hideLoading(loading_box) {
  loading_box.style.display = "none";
}

//////////////////////////////////////////////////////////////////////////////////////////
document.addEventListener("DOMContentLoaded", function () {
  const basket_list = document.querySelector("div#basket_list");
  const loading_box = document.getElementById("roading_container");
  const next_button = document.querySelector("#basket_footer_next_button");
  const basket_header = document.querySelector("div#basket_header");
  const header = document.querySelector("div.header");
  const category = document.querySelector("div#category_box");
  
  //장바구니 헤더를 헤더 밑에 위치시키기 위해서
  basket_header.style.top = `${header.offsetHeight}px`;
  basket_header.style.zIndex = "150";
  header.style.backgroundColor = "white";
  header.style.zIndex = "150";
  
  //카테고리 메뉴를 항상위에 올림
  category.style.zInddx= "500";
  
  //장바구니 목록을 가져오기—
  getBasketList().then((html) => {
    basket_list.innerHTML = html;
    //장바구니 목록을 가져온 후 총 가격 계산
    calculateTotalPrice();
    document.querySelector(
      "span#basket_count"
    ).textContent = `(${basketCount})`;
  }); // end of getBasketList().then((html) => {

  //장바구니 목록의 요소를 클릭했을 경우
  basket_list.addEventListener(
    "click",
    (e) => {
      const basket_item = e.target.closest("div.basket_item");
      const basket_No = basket_item.getAttribute("data-basket_no");
      //데이터 전송을 위한 json객체 생성
      let request_json;

      //상품의 가격을 가져온다
      const product_price = basket_item.querySelector("span.price_text");
      //삭제버튼 클릭시
      if (e.target.className == "basket_delete") {
        //삭제 비동기 실행
        deleteBasketList(basket_No, loading_box)
          .then((result) => {
            console.log("비동기 실행결과 : ", result);
            if (result == "success") {
              console.log("삭제성공");
              // 요소를 삭제시킨다
              basket_item.remove();
              //삭제시킨 상품의 가격을 뺀다
              calculateTotalPrice();
              //장바구니 개수를 변경
              basketCount--;
              document.querySelector(
                "span#basket_count"
              ).textContent = `(${basketCount})`;
            }
          })
          .catch((error) => {
            console.log(error);
          });
      }
      //+ 버튼 클릭시시
      else if (e.target.className == "plus_count") {
        // console.log(e.target.nextElementSibling.firstElementChild);
        //span 태그를 가져온다
        const count_num = e.target.nextElementSibling.firstElementChild;
        const inventory = count_num.getAttribute("data-inventory");

        //재고 이상으로 증가시키려 할 때
        if (inventory < Number(count_num.textContent)) {
          alert("재고가 부족합니다");
          return;
        }

        //요청 json객체 생성
        request_json = { basketNo: basket_No, status: "plus" };
        changeBasketCount(request_json, loading_box)
          .then((result) => {
            if (result == "success") {
              console.log("증가성공");
              //상품의 가격에서 증가시킨다
              const after_price =
                Number(product_price.textContent.replaceAll(",","")) +
                Number(product_price.getAttribute("data-price"));
              //span태그의 값을 증가시킨다
              count_num.textContent = Number(count_num.textContent) + 1;
              product_price.textContent = after_price.toLocaleString();
              // 가격을 다시 계산한다.
              calculateTotalPrice();
            } else {
              console.log("실패");
            }
          })
          //에러 발생시
          .catch((error) => {
            console.log(error);
          });
      }
      // -버튼 클릭시
      else if (e.target.className == "minus_count") {
        // console.log(e.target.previousElementSibling.firstElementChild);
        //span 태그를 가져온다
        const count_num = e.target.previousElementSibling.firstElementChild;
        //요청 json객체 생성
        request_json = { basketNo: basket_No, status: "minus" };
        changeBasketCount(request_json, loading_box)
          .then((result) => {
            if (result == "success") {
              console.log("감소성공");
              //상품의 가격에서 감소시킨다
              const after_price =
                Number(product_price.textContent.replaceAll(",","")) -
                Number(product_price.getAttribute("data-price"));
              //만약 상품의 개수가 1개일 때 -버튼을 누르면 삭제
              if (count_num.textContent == 1) {
                basket_item.remove();
                //삭제시킨 상품의 가격을 뺀다
                calculateTotalPrice();
                console.log(basket_item);
                //장바구니 개수를 변경
                basketCount--;
                document.querySelector(
                  "span#basket_count"
                ).textContent = `(${basketCount})`;
                return;
              } //end of if(count_num.textContent == 1)-------------------------------
              //span태그의 값을 감소시킨다
              count_num.textContent = Number(count_num.textContent) - 1;
              product_price.textContent = after_price.toLocaleString();
              // 가격을 다시 계산한다.
              calculateTotalPrice();
            } else {
              console.log("실패");
            }
          })
          //에러 발생시
          .catch((error) => {
            console.log(error);
          });
      }
    },
    true
  );

  //계속 버튼 클릭시 결제페이지로 이동, 개수가 0 이 아니고
  next_button.addEventListener("click", (e) => {
    if (
      Number(document.querySelector("span#total_price").textContent) != "0" &&
      is_inventory
    ) {
      const basket_item_arry = [];
      const basket_item_list = document.querySelectorAll("div.basket_item");
      basket_item_list.forEach((element) => {
        const imgSrc = element.querySelector(".basket_img img").src;
        const productDetailNo = element
          .querySelector(".basket_product_info")
          .getAttribute("data-product_detail_no");
        const productCountNum =
          element.querySelector(".pruduct_count_num").textContent;
        const productPrice = element
          .querySelector(".price_text")
          .getAttribute("data-price");
        //			console.log(element.querySelector(".price_text"));
        let item = {
          imgSrc: imgSrc,
          productDetailNo: productDetailNo,
          productCountNum: productCountNum,
          productPrice: productPrice,
        };

        basket_item_arry.push(item);
      });
      //	      console.log(basket_item_arry);
      sessionStorage.setItem(
        "basket_item_arry",
        JSON.stringify(basket_item_arry)
      );

      sessionStorage.setItem(
        "total_price",
        document.querySelector("span#total_price").textContent
      );

      location.href = "payment/address.trd";
    }
  });
});
