
$(document).ready(function(){
	
	$("div.message").hide();
	$("input#email").focus();
	
	$("input#email").blur((e) => {
		
		const email = $(e.target).val().trim();
		if(email == ""){
			// 입력하지 않거나 공백만 입력했을 경우
			
			$("input").prop("disabled", true);
			$(e.target).prop("disabled", false);
			$(e.target).val("").focus();
			
			$(e.target).parent().find("div.message").show();
			
		}
		else{
			// 공백이 아닌 글자를 입력했을 경우
			$("input").prop("disabled", false);
			$(e.target).parent().find("div.message").hide();
		}
		
	});
	
});// end of $(document).ready(function()----------
