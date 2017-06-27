$(document).ready(function() {
	if($.cookie("web-forum") != null && $.cookie("web-forum") != undefined && $.cookie("web-forum") != ""){
		$( "#user-looged-in" ).hide();
		$( "#user-name" ).show();
		$( "#logout" ).show();
	}else{
		$( "#user-looged-in" ).show();
		$( "#user-name" ).hide();
		$( "#logout" ).hide();
	}
	$("#logout").on("click",function() {
		$.cookie("web-forum","");
		window.location.replace("http://localhost:7653/rs.ftn.mr.webforum/register.html");
	});
	$("#user-looged-in").on("click",function() {
		window.location.replace("http://localhost:7653/rs.ftn.mr.webforum/register.html");
	});
	$.ajax({
		method : 'POST',
		url : "../rs.ftn.mr.webforum/rest/user/cookie",
		contentType : 'text/plain',
		dataType : "text",
		data : $.cookie("web-forum"),
		success : function(data) {
			console.log(data);
			var d = JSON.parse(data);
			$("#user-name").text(d.user);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("JADOOOOO");
		}
	});
	
});
