//장바구니 개수
let basketCount = 0;

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
            <div class="basket_product_info">
              <div class="basket_product_info_header">
                <div><a class="product_link">${PRODUCT_NAME}</a></div>
                <div id= "basket_delete_${PK_BASKET_NO}" class = "basket_delete">⨉</div>
              </div>
              <div class="basket_pruduct_price"><span class="price_text" data-price ="${PRODUCT_PRICE}">${
        PRODUCT_PRICE * BASKET_QUANTITY
      }</span>₩</div>
              <div class="basket_pruduct_size_category">
                <span class="basket_product_size">${PRODUCT_SIZE}</span>&nbsp;|&nbsp;<span
                  class="basket_product_category"
                  >${COLOR_NAME}</span
                >
              </div>
              <div class="basket_product_count_container">
                <button class="plus_count">+</button>
                <div class="basket_product_count">
                  <span class="pruduct_count_num">${BASKET_QUANTITY}</span>
                </div>
                <button class="minus_count">-</button>
              </div>
            </div>
          </div>`;
      //장바구니의 총 개수를 증가
      basketCount = basketCount + 1;
    }); // end of forEach-------------------------------
    return html;
  } catch {
    console.log("error");
  }
}

//장바구니 삭제 비동기 메서드
async function deleteBasketList(basket_no) {
  try {
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
//    if (text == "success") {
      console.log("삭제 함수 성공");
      return "success";
//    }
//    //자바에서 반환 된 값이 success가 아닌 경우(실패)
//    else {
//      console.log("fail");
//      return "fail";
//    }
  } catch (error) {
    //에러 발생시
    console.error(error.message);
    return "fail";
  }
}

//개수 변경 비동기 메서드
async function changeBasketCount(json) {
  try {
    //자바 실행 요청
    const response = await fetch("/Trid/basket/change.trd", {
      method: "post",
       headers: { "Content-Type": "application/json" },
       body: JSON.stringify(json),
    });
    //자바의 실행 결과를 문자형태로 저장한다
    const result = await response.text();
    //자바의 실행 결과가 success인 경우
//    if (result == "success") {
      console.log("개수변경성공");
      return "success";
//    }
    //자바의 실행 결과가 success가 아닌 경우
//    else {
//      console.log("실패");
//      return "fail";
//    }
  } catch (error) {
    //에러 발생시
    console.error(error.message);
    return "fail";
  }
}

//장바구니의 총 가격을 계산하는 함수
function calculateTotalPrice() {
  //각 장바구니의 가격정보 불러오기
  const priceList = document.querySelectorAll("span.price_text");
  // 총 가격 생성성
  let totlaPrice = 0;
  priceList.forEach((element) => {
    totlaPrice += Number(element.textContent);
  });
  document.querySelector("span#total_price").textContent = totlaPrice;
}

//로딩화면을 보여주는 함수
function showLoading(loading_box) {
  loading_box.style.display = "block";
}

//로딩화면을 숨기는 함수
function hideLoading(loading_box) {
  loading_box.style.display = "none";
}

document.addEventListener("DOMContentLoaded", function () {
  const basket_list = document.querySelector("div#basket_list");
  const loading_box = document.getElementById("roading_container");
  const next_button = document.querySelector("#basket_footer_next_button");
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
        deleteBasketList(basket_No)
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

        //요청 json객체 생성
        request_json = { basketNo: basket_No, status: "plus" };
        changeBasketCount(request_json)
          .then((result) => {
            if (result == "success") {
              console.log("증가성공");
              //상품의 가격에서 증가시킨다
              const after_price =
                Number(product_price.textContent) +
                Number(product_price.getAttribute("data-price"));
              //span태그의 값을 증가시킨다
              count_num.textContent = Number(count_num.textContent) + 1;
              product_price.textContent = after_price;
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
        changeBasketCount(request_json)
          .then((result) => {
            if (result == "success") {
              console.log("감소성공");
              //상품의 가격에서 감소시킨다
              const after_price =
                Number(product_price.textContent) -
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
              product_price.textContent = after_price;
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
  
  //계속 버튼 클릭시 결제페이지로 이동 개수가 0 이 아니고
  next_button.addEventListener("click", (e) => {
	if(Number(document.querySelector("span#total_price").textContent) > 0) {
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
	
	        let item = {
	          imgSrc: imgSrc,
	          productDetailNo: productDetailNo,
	          productCountNum: productCountNum,
	          productPrice: productPrice,
	        };
	
	        basket_item_arry.push(item);
	      });
	      console.log(basket_item_arry);
	      sessionStorage.setItem(
	        "basket_item_arry",
	        JSON.stringify(basket_item_arry)
	      );
	      location.href = "payment/address.trd";
	  }
    });
});
