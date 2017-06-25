var rootURL = "../rs.ftn.jersey.webshop/rest/users/add";
$("#submit").click( function(e) {
	e.preventDefault();
	console.log("add product");
//	var data = $('.productsform').serialize();
	var id = $(this).find("#name").val();
	var count = $(this).find("#paw").val();
	$.ajax({
		type : 'POST',
		url : rootURL,
		contentType : 'application/json',
		dataType : "text",
		data : formToJSON(id, count),
		success : function(data) {
			toastr.options = {
					  "closeButton": true,
					  "debug": false,
					  "newestOnTop": false,
					  "progressBar": false,
					  "positionClass": "toast-top-right",
					  "preventDuplicates": false,
					  "onclick": null,
					  "showDuration": "300",
					  "hideDuration": "1000",
					  "timeOut": "5000",
					  "extendedTimeOut": "1000",
					  "showEasing": "swing",
					  "hideEasing": "linear",
					  "showMethod": "fadeIn",
					  "hideMethod": "fadeOut"
					}
			toastr.info('Proizvod [ ' + data + '] uspesno dodat u korpu.');

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
});
function formToJSON(id, name) {
	return JSON.stringify({
		"id" : id,
		"name" : name,
	});
}