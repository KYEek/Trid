/**
 *
 */

// 느낌표 아이콘
const regExp = /^010\d{8}$/;

const warning_icon = `<span>
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width="14"
    height="14"
    fill="currentColor"
    class="bi bi-exclamation-circle"
    viewBox="0 0 16 16"
  >
    <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16" />
    <path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0M7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0z" />
  </svg>
</span>`;

//각 경고에 들어갈 문구
const is_blanc = "<span>필수 입력란입니다.</span>";
const mobile_warning = "<span>전화번호가 유효하지 않습니다.</span>";
const name_condition = "이름을 입력해 주십시오.";
const addition_address_condition =
  "앞칸에 입력하신 글자수가 초과되면 다음 칸에 기재하십시오";
const mobile_condition = "-를 제외한 숫자만 입력해 주십시오.";

//문서가 로드되면 실행
document.addEventListener("DOMContentLoaded", function () {
  const input_text = document.querySelectorAll("input.input_area");
  const submit_btn = document.querySelector("button#submit_btn");
  input_text.forEach((item, index) => {
    //주소, 지역 버튼일 때
    if (index == 2 || index == 4) {
      item.setAttribute("readonly", "readonly");
      if (index == 4) item.setAttribute("value", "대한민국");
    }
    //나머지 버튼
    else {
      item.addEventListener("focus", function (e) {
        // const warning_div = e.target.parentElement.querySelector(".warning_div");
        let warning_div;
        if (index != 1) {
          warning_div = getSibilingElement(e.target, ".warning_div");
        } else {
          warning_div = e.target.parentElement.nextElementSibling;
        }
        const label = getSibilingElement(item, "label");
        if (index == 1) {
          label.classList.add("position_move2");
        } else {
          label.classList.add("position_move");
        }
        warning_div.style.display = "block";
        warning_div.style.color = "black";
        let html = "";
        switch (index) {
          case 0:
            html = warning_icon + name_condition;
            break;
          case 3:
            html = warning_icon + addition_address_condition;
            break;
          case 5:
            html = warning_icon + mobile_condition;
            break;
        }

        warning_div.innerHTML = html;
        e.stopPropagation();
      }); //end of focus--------------------------

      // 블러 이벤트
      item.addEventListener("blur", function (e) {
        let warning_div;
        if (index != 1) {
          warning_div = getSibilingElement(e.target, ".warning_div");
        } else {
          warning_div = e.target.parentElement.nextElementSibling;
        }
        //오류 메시지를 띄어주기 위한 함수
        warning_red(e.target, warning_div, index);
        const label = getSibilingElement(item, "label");
        if (index == 1) {
          label.classList.remove("position_move2");
        } else {
          if (e.target.value == "") {
            label.classList.remove("position_move");
          }
        }
        e.stopPropagation();
      }); //end of blur--------------------------
      //값이 입력됐을 때 플레이스 홀더를 위로 올리기 위해
      item.addEventListener("change", function (e) {
        console.log(e.target);
        if (index == 1 || index == 2) {
          //값이 비어 있지 않을 때
          if (e.target.value != "") {
            const label = getSibilingElement(item, "label");
            //우편번호 인풋에는 다른 클래스를 적용
            if (index == 1) {
              label.classList.add("position_move2");
            } else {
              label.classList.add("position_move");
            }
          }
        }
        e.stopPropagation();
      }); //end of change--------------------------
    }
  });
  //저장 버튼 클릭시
  submit_btn.addEventListener("click", function (e) {
    let is_submit = true;
    input_text.forEach((item, index) => {
      if (index == 0 || index == 1 || index == 2 || index == 5) {
        if (item.value == "") {
          item.dispatchEvent(new Event("blur"));
          is_submit = false;
        }
      }
    });
    if (is_submit) {
      const form = document.querySelector("#address_form");
      form.setAttribute("method", "post");
      form.setAttribute("action", "");
      form.submit();
    }
    e.stopPropagation();
  }); //end of click--------------------------

  //우편번호 찾기 버튼클릭시
  document.querySelector("#find_Zip").addEventListener("click", function () {
    new daum.Postcode({
      oncomplete: function (data) {
        // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

        // 각 주소의 노출 규칙에 따라 주소를 조합한다.
        // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
        let addr = ""; // 주소 변수
        let extraAddr = ""; // 참고항목 변수

        //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
        if (data.userSelectedType === "R") {
          // 사용자가 도로명 주소를 선택했을 경우
          addr = data.roadAddress;
        } else {
          // 사용자가 지번 주소를 선택했을 경우(J)
          addr = data.jibunAddress;
        }

        // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
        if (data.userSelectedType === "R") {
          // 법정동명이 있을 경우 추가한다. (법정리는 제외)
          // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
          if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
            extraAddr += data.bname;
          }
          // 건물명이 있고, 공동주택일 경우 추가한다.
          if (data.buildingName !== "" && data.apartment === "Y") {
            extraAddr +=
              extraAddr !== "" ? ", " + data.buildingName : data.buildingName;
          }
          // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
          if (extraAddr !== "") {
            extraAddr = " (" + extraAddr + ")";
          }
          // 조합된 참고항목을 해당 필드에 넣는다.
          document.getElementById("extraAddress").value = extraAddr;
        } else {
          document.getElementById("extraAddress").value = "";
        }

        // 우편번호와 주소 정보를 해당 필드에 넣는다.
        document.getElementById("input2").value = data.zonecode;
        getSibilingElement(
          document.getElementById("input2"),
          "label"
        ).classList.add("position_move2");
        document.getElementById("input3").value = addr;
        getSibilingElement(
          document.getElementById("input3"),
          "label"
        ).classList.add("position_move");
        // 커서를 상세주소 필드로 이동한다.
        document.getElementById("input4").focus();
      },
    }).open();

    // 주소를 비활성화 로 만들기
    //  $("input#address").attr("disabled", true);

    // 참고항목을 비활성화 로 만들기
    //  $("input#extraAddress").attr("disabled", true);

    // 주소를 쓰기가능 으로 만들기
    //  $("input#address").removeAttr("readonly");

    // 참고항목을 쓰기가능 으로 만들기
    //  $("input#extraAddress").removeAttr("readonly");

    // 주소를 활성화 시키기
    //	$("input#address").removeAttr("disabled");

    // 참고항목을 활성화 시키기
    //  $("input#extraAddress").removeAttr("disabled");
  });
}); //end of DOMContentLoaded--------------------------

//형제 요소를 찾는 함수
function getSibilingElement(target, selector) {
  const warning_div = target.parentElement.querySelector(selector);
  return warning_div;
} //end of getSibilingElement--------------------------

//경고문을 빨간색으로 바꾸는 함수
function warning_red(target, warning_div, index) {
  //값이 비어있을 때
  if (target.value == "") {
    if (index == 0 || index == 1 || index == 2 || index == 5) {
      warning_div.style.display = "block";
      warning_div.style.color = "red";
      warning_div.innerHTML = warning_icon + is_blanc;
    } else {
      warning_div.style.display = "none";
    }
  }
  //값이 입력이 되어있을 때
  else {
    //전화번호 유효성 검사
    if (index == 5) {
      if (!regExp.test(target.value)) {
        warning_div.style.color = "red";
        warning_div.innerHTML = warning_icon + mobile_warning;
      } else {
        warning_div.style.display = "none";
      }
    } else {
      warning_div.style.display = "none";
    }
  }
} //end of warning_red--------------------------
