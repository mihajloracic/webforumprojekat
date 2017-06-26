$(function() {

    $('#login-form-link').click(function(e) {
    	$("#login-form").delay(100).fadeIn(100);
 		$("#register-form").fadeOut(100);
		$('#register-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
	$('#register-form-link').click(function(e) {
		$("#register-form").delay(100).fadeIn(100);
 		$("#login-form").fadeOut(100);
		$('#login-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
});
var rootURL = "../rs.ftn.mr.webforum/rest/user/add";
$(document).on('submit', '#register-form', function(e) {
	e.preventDefault();
	console.log("register");
//	var data = $('.productsform').serialize();
	var username = $(this).find("#rUsername").val();
	var ime = $(this).find("#ime").val();
	var prezime = $(this).find("#prezime").val();
	var email = $(this).find("#email").val();
	var password = $(this).find("#RPassword").val();
	var telefon = $(this).find("#telefon").val();
	$.ajax({
		method : 'POST',
		url : rootURL,
		contentType : 'application/json',
		dataType : "text",
		data : registerformToJSON(username,ime,prezime,email,password,telefon),
		success : function(data) {
			alert("Uspesno ste se registrovali");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
});
function registerformToJSON(username, ime,prezime,email,password,telefon) {
	return JSON.stringify({
		"user" : username,
		"ime" : ime,
		"prezime" : prezime,
		"email" : email,
		"lozinka" : password,
		"telefon" : telefon,
	    "uloga": null,
	    "datumRegistracije": null
	});
}