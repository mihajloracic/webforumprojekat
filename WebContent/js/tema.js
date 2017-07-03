var idAutora;
$( document ).ready(function() {
	$("#izbrisiTemu").click(function(){
		$.ajax({
			method : 'POST',
			url : "../rs.ftn.mr.webforum/rest/post/delete",
			contentType : 'application/json',
			data: JSON.stringify({
				"id" :  getUrlVars()["id"]
			}),
			success : function(data) {
				console.log(data)
				window.location.href = "home.html"
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
			}
		});
	})
	$("#izbrisiTemu").hide();
	$(document).on('click','.likeKomentar', function(){
		var id = $(this).parent().parent().attr("id");
		var idCont = id;
		id = id.replace("komentar","");
		$.ajax({
			method : 'POST',
			url : "../rs.ftn.mr.webforum/rest/komentar/like",
			contentType : 'application/json',
			data: JSON.stringify({
				"idKomentar" : id,
				"like" : true
			}),
			success : function(data) {
				console.log(data)
				$("#" + idCont).find(".brojLike").text("like: " + data.brojLike);
				$("#" + idCont).find(".brojLike").text("like: " + data.brojLike + " dislike: " + data.brojDislike)

			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
			}
		});
	});
	$(document).on('click','.dislikeKomentar', function(){
		var id = $(this).parent().parent().attr("id");
		var idCont = id;
		id = id.replace("komentar","");
		$.ajax({
			method : 'POST',
			url : "../rs.ftn.mr.webforum/rest/komentar/like",
			contentType : 'application/json',
			data: JSON.stringify({
				"idKomentar" : id,
				"like" : false
			}),
			success : function(data) {
				console.log(data)
				$("#" + idCont).find(".brojLike").text("like: " + data.brojLike);
				$("#" + idCont).find(".brojLike").text("like: " + data.brojLike + " dislike: " + data.brojDislike)

			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
			}
		});
	});

	$.ajax({
		method : 'POST',
		url : "../rs.ftn.mr.webforum/rest/komentar/getComments",
		contentType : 'text/plain',
		data: getUrlVars()["id"],
		success : function(data) {
			console.log("Komentari" + data)
			data.forEach(function(element) {
				addComment(element);
			});
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(textStatus);
			console.log(errorThrown);
		}
	});
	$("#likeTemaAdd").click(function(){
		$.ajax({
			method : 'POST',
			url : "/rs.ftn.mr.webforum/rest/post/like",
			contentType : 'application/json',
			data :  formLikeToJSON(true),
			success : function(temaInfo) {
				console.log(temaInfo);
				$("#brojLike").text("Like: " + temaInfo.brojLike);
				$("#brojDislike").text("Dislike: " + temaInfo.brojDislike);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
				window.location.href = "register.html"
			}
		});
	});
	$("#dislikeTemaAdd").click(function(){
		$.ajax({
			method : 'POST',
			url : "/rs.ftn.mr.webforum/rest/post/like",
			contentType : 'application/json',
			data :  formLikeToJSON(false),
			success : function(temaInfo) {
				console.log(temaInfo);
				$("#brojLike").text("Like: " + temaInfo.brojLike);
				$("#brojDislike").text("Dislike: " + temaInfo.brojDislike);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
				window.location.href = "register.html"
			}
		});
	});
	$("#dodajKomentar").click(function(e){
		e.preventDefault();
		if($.cookie("web-forum") != null && $.cookie("web-forum") != "" && $.cookie("web-forum") != undefined){		
		}else{
			window.location.href = "register.html";			
		}
		if($("#textKomentar").val() == ""){
			return;
		}
		
		$.ajax({
			method : 'POST',
			url : "/rs.ftn.mr.webforum/rest/komentar/add",
			contentType : 'application/json',
			data : formToJSON($("#textKomentar").val()),
			success : function(data) {
				addComment(data)
				$("#textKomentar").val("")
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
			}
		});
	});
	updateTema();
	$.ajax({
		method : 'POST',
		url : "../rs.ftn.mr.webforum/rest/user/cookie",
		contentType : 'text/plain',
		dataType : "text",
		data : $.cookie("web-forum"),
		success : function(data) {
			console.log(data);
			var d = JSON.parse(data);
			if(d.uloga == "admin" || d.uloga == "moderator" || d.id == idAutora){
				$("#izbrisiTemu").show();
			}
			$("#user-name").text(d.user);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("Greska");
		}
	});
});
function updateTema(){
	$(".content").hide();
	$.ajax({
		method : 'POST',
		async: false,
		url : "/rs.ftn.mr.webforum/rest/post/temaById",
		contentType : 'text/plain',
		data :  getUrlVars()["id"],
		success : function(data) {
			console.log(data);
			$("#brojLike").append(data.brojLike);
			$("#brojDislike").append(data.brojDislike);
			$("#" + data.tip).show();
			if(data.tip == "Slika"){
				$("#Slika").attr("src", "images/" + data.slika);
			}
			temaAutor = data.autor;
			$("#naslov").append(data.naslov);
			$("#Tekst").append(data.tekst);
			$("#Link").append(data.link);
			$("#Link").attr("href", data.link);
			$("#createdOn").append(data.datum_kreiranja);
			
			$.ajax({
				method : 'POST',
				url : "/rs.ftn.mr.webforum/rest/user/getUserById",
				contentType : 'text/plain',
				data :  data.autor+"",
				success : function(userStigao) {
					$("#user").append(userStigao.user);
					
					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					console.log(textStatus);
					console.log(errorThrown);
				}
			});
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(textStatus);
			console.log(errorThrown);
		}
	});
}
function getPodforum(){
	var id = getUrlVars()["locationId"];
}
function getUrlVars()
{
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}
function formToJSON(tekst) {
	return JSON.stringify({
		"id" : null,
		"id_tema" : getUrlVars()["id"].replace("#","") ,
		"autor" : null,
		"datum_kreiranja" : null,
		"id_parent_komentar" : -1,
		"tekst_komentar" : tekst,
		"obrisan" : false,
		"izmenjen" : false
	});
}
function formLikeToJSON(like) {
	return JSON.stringify({
		"id" : null,
		"idTema" : getUrlVars()["id"],
		"idUser" : null,
		"like" : like
	});
}

function addComment(comment){
	console.log(comment);
	text = '<div class="row" id="komentar' + comment.id + '"> <div class="col-sm-5"> <a href="#" class="textKomentara"></a> </div> <div class="col-sm-1"> <p class="izmenjen"></p> </div> <div class="col-sm-2 "> <p class="brojLike"></p> </div> <div class="col-sm-1"> <button class="btn btn-default dislikeKomentar">Dislike</button> </div> <div class="col-sm-1"> <button class="btn btn-default likeKomentar">Like</button> </div> </div>'
	$(".commentContainer").append(text).val();
	var selector = "#komentar" + comment.id + " .textKomentara";

	if(comment.izmenjen == true) {
		$("#komentar" + comment.id).find(".izmenjen").append("IZMENJEN");
	}
	if(comment.obrisan == true) {
		$("#komentar" + comment.id).find(".textKomentara").append("OBRISAN");
	}else{
		$("#komentar" + comment.id).find(".textKomentara").append(comment.tekst_komentar);
		$("#komentar" + comment.id).find(".textKomentara").attr("href", "komentar.html?id=" + comment.id);
	}
	$("#komentar" + comment.id).find(".brojLike").text("like: " + comment.brojLike + " dislike: " + comment.brojDislike)

}
