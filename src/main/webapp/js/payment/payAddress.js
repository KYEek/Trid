document.addEventListener("DOMContentLoaded", function () {
  const closeBtn = document.querySelector("#address_list_close > span"); //주소목록 x버튼
  const addressList = document.querySelector("#address_list_container"); //주소목록
  const address_info_and_select = document.querySelector(
    "#address_info_and_select"
  ); //지정된 주소가 표시되는 div
  const address_list_main = document.querySelector("#address_list_main"); //주소목록이 표시되는 div
  let html = "";
  const address_arr = []; //주소목록을 담을 배열

  //주소목록을 가져와서 출력해주는 함수
//  fetch("json/address.json")
//    .then((Response) => Response.json())
//    .then((data) => {
      //주소를 넣어줄 html
      let address_html = "<ul>";
      for (const item of addrList) {
        // console.log(item[key]);
        address_arr.push(item);
        address_html += `<li>
              <div class="address_list_item" data-addr_no ="${item["pk_addr_no"]}">
                <div class="address_list_item_text">${item["member_name"]}</div>
                <div class="address_list_item_text">${item["addr_address"]}</div>
                <div class="address_list_item_text">${item["addr_detail"]}</div>
                <div class="address_list_item_text">${item["addr_post_no"]}</div>
                <div class="address_list_item_text">${item["member_mobile"]}</div>
              </div>
            </li>`;
        if (item["addr_isdefault"] == "1") {
          sessionStorage.setItem("selected_address_no", item["pk_addr_no"]);
          address_info_and_select.innerHTML = ` <div>${item["addr_address"]}${item["addr_extraaddr"]}</div>
                    <a href="#">변경</a>`;
        }
      }

      address_html += "</ul>";
      address_list_main.innerHTML = address_html;

      const address_list_item = document.querySelectorAll(".address_list_item"); //주소목록의 각각의 주소

      //주소목록의 각각의 주소를 클릭했을때 이벤트
      address_list_item.forEach((item) => {
        item.addEventListener("click", (e) => {
          if (e.target.getAttribute("class") != "address_list_item") {
            // console.log(e.target.parentElement.getAttribute("data-addr_no"));
            sessionStorage.setItem(
              "selected_address_no",
              e.target.parentElement.getAttribute("data-addr_no")
            );
            address_arr.forEach((item) => {
              if (
                e.target.parentElement.getAttribute("data-addr_no") ==
                item["pk_addr_no"]
              ) {
                address_info_and_select.innerHTML = ` <div>${item["addr_address"]}${item["addr_extraaddr"]}</div>
                    <a href="#">변경</a>`;
              }
            });
          } else {
            // console.log(e.target.getAttribute("data-addr_no"));
            sessionStorage.setItem(
              "selected_address_no",
              e.target.getAttribute("data-addr_no")
            );
            address_arr.forEach((item) => {
              if (e.target.getAttribute("data-addr_no") == item["pk_addr_no"]) {
                address_info_and_select.innerHTML = ` <div>${item["addr_address"]}${item["addr_extraaddr"]}</div>
                    <a href="#">변경</a>`;
              }
            });
          }
          //사이드바 가리기
          closeBtn.dispatchEvent(new Event("click"));
        });
      }); //end of address_list_item.forEach-------------------
//    }); //end of fetch
  //이미지가 출력되는 div
  const delivery_product_list = document.querySelector(
    "div#delivery_product_list"
  );
  //장바구니 목록을 담는 변수
  const basket_list_str = sessionStorage.getItem("basket_item_arry");
  const basket_list = JSON.parse(basket_list_str);

  //장바구니 이미지 목록을 출력
  basket_list.forEach((item) => {
    html += `<img src="${item["imgSrc"]}" />`;
  });
  delivery_product_list.innerHTML = html;

  //주소목록 x버튼 클릭이벤트
  closeBtn.addEventListener("click", (e) => {
    // 사이드 바 가리가가
    addressList.style.display = "none";
  });

  //주소 클릭 이벤트
  address_info_and_select.addEventListener("click", (e) => {
    //사이드바 표시
    addressList.style.display = "block";
  });

  document
    .querySelector("#basket_footer_next_button")
    .addEventListener("click", (e) => {
      this.location.href = "summary.trd";
    });
});
