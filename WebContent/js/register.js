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

$( document ).ready(function() {
	if($.cookie("web-forum") != null && $.cookie("web-forum") != "" && $.cookie("web-forum") != undefined){
		
		window.location.replace("http://localhost:7653/rs.ftn.mr.webforum/home.html");
	}
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
	if(password != $(this).find("#confirm-password").val()){
		alert("Niste dobro ponovoli password");
		return
	}
	var telefon = $(this).find("#telefon").val();
	$.ajax({
		method : 'POST',
		url : rootURL,
		contentType : 'application/json',
		dataType : "text",
		data : registerformToJSON(username,ime,prezime,email,password,telefon),
		success : function(data) {
			alert("Uspesno ste se registrovali");
	    	$("#login-form").delay(100).fadeIn(100);
	 		$("#register-form").fadeOut(100);
			$('#register-form-link').removeClass('active');
			$(this).addClass('active');
			e.preventDefault();
			$(this).find("#username").val(ime);
			$(this).find("#password").val(password);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("User name already taken");
		}
	});
});
//login
$(document).on('submit', '#login-form', function(e) {
	e.preventDefault();
	console.log("register");
//	var data = $('.productsform').serialize();
	var username = $(this).find("#username").val();
	var ime = "";
	var prezime = "";
	var email = "";
	var password = $(this).find("#password").val();
	var telefon = "";
	$.ajax({
		method : 'POST',
		url : "../rs.ftn.mr.webforum/rest/user/login",
		contentType : 'application/json',
		dataType : "text",
		data : registerformToJSON(username,ime,prezime,email,password,telefon),
		success : function(data) {
			$.cookie("web-forum", data,{expires: new Date(2017, 10, 29, 11, 00, 00)});
			console.log(getCookie("web-forum"));
			window.location.replace("http://localhost:7653/rs.ftn.mr.webforum/home.html");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("wrong username or password");
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
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}
function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}