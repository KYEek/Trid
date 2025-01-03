
// null 또는 undefined 또는 공백일 경우를 확인하는 함수
function isBlank(value) {
    return (value == null) || (value == undefined) || (value.trim() == "");
}

// 값이 숫자인지 확인하는 함수
function isValidNumber(number) {
    const numberRegex = /^[0-9]+$/;
    return numberRegex.test(number);
}

// 값이 날짜인지 확인하는 함수
function isValidDate(date) {
    const dateRegex = /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])$/;
    return dateRegex.test(date);
}

function validateImage(file) {

    if (file != null && file != undefined) {

        if (!(file.type == 'image/jpeg' || file.type == 'image/jpg' || file.type == 'image/png')) {
            alert("jpeg, jpg, png 파일만 등록할 수 있습니다.");
            return false;
        }

        // 5MB 이상인 파일인지 확인
        if (file.size >= 1024 * 1024 * 5) {
            alert("5MB 이상인 파일은 업로드할 수 없습니다");
            return false;
        }

        return true;

    }

    return false;

}