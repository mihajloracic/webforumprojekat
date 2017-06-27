$(document).ready(function() {
	if($.cookie("web-forum") != null && $.cookie("web-forum") != undefined && $.cookie("web-forum") != ""){
		$( "#user-looged-in" ).hide();
		$( "#user-name" ).show();
		$( "#logout" ).show();
		$( "#addPodforum" ).hide();
	}else{
		$( "#user-looged-in" ).show();
		$( "#user-name" ).hide();
		$( "#logout" ).hide();
		$( "#addPodforum" ).hide();
	}
	$("#logout").on("click",function() {
		$.cookie("web-forum","");
		window.location.replace("http://localhost:7653/rs.ftn.mr.webforum/register.html");
	});
	$("#user-looged-in").on("click",function() {
		window.location.replace("http://localhost:7653/rs.ftn.mr.webforum/register.html");
	});
	prikaziPostove();
	$.ajax({
		method : 'POST',
		url : "../rs.ftn.mr.webforum/rest/user/cookie",
		contentType : 'text/plain',
		dataType : "text",
		data : $.cookie("web-forum"),
		success : function(data) {
			console.log(data);
			var d = JSON.parse(data);
			if(d.uloga == "admin" || d.uloga == "moderator"){
				$( "#addPodforum" ).show();
			}
			$("#user-name").text(d.user);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("Greska");
		}
	});
	
	$('#myModal').on('shown.bs.modal', function () {
		  $('#myInput').focus()
	})
	
	$('#buttonDodajPodforum').click(function(){
		var naziv = $("#modalNaziv").val();
		var opis = $("#modalOpis").val();
		var pravila = $("#modalPravila").val();
		$.ajax({
			method : 'POST',
			url : "../rs.ftn.mr.webforum/rest/podforum/add",
			contentType : 'application/json',
			dataType : "text",
			data : podforumFormToJSON(naziv,opis,pravila),
			success : function(data) {
				alert("podforum je dodat");
				$("#modalNaziv").val("");
				$("#modalOpis").val("");
				$("#modalPravila").val("");
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("ASDASD");
			}
		});
	})
	
});
function podforumFormToJSON(naziv,opis,spisakPravila) {
	return JSON.stringify({
		"id" : null,
		"naziv" : naziv,
		"opis" : opis,
		"spisakPravila" : spisakPravila,
		"odgovorniModerator" : null,
	});
}
function prikaziPostove(){
	$.ajax({
		method : 'POST',
		url : "../rs.ftn.mr.webforum/rest/podforum/podforumi",
		contentType : 'application/json',
		success : function(data) {
			$("#podforum-holder").empty();
			data.forEach(function(element) {
			    console.log(element.naziv);
			    $( "#podforum-holder" ).append( '<li><a href="#">' + element.naziv + "</a></li>" );
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("ASDASD");
		}
	});
}
