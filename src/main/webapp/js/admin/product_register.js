$(document).ready(function() {
	
	$("form[name='image_frm']").hide();
	
	$(document).on("click", "button#add_image_button", function() {
		$("form[name='image_frm']").show();
	});
	
});
