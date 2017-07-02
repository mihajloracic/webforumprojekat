var temaId;

$(document).ready(function(){
	$("#buttonIzmeni").click(function(){
		if($("#textIzmena").val() == ""){
			return;
		}
		$.ajax({
			method : 'POST',
			url : "../rs.ftn.mr.webforum/rest/komentar/update",
			contentType : 'application/json',
			data: JSON.stringify({
				"id" : getUrlVars()["id"],
				"tekst_komentar" : $("#textIzmena").val()
			}),
			success : function() {
				$("#naslov").text($("#textIzmena").val());
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest);
				console.log(textStatus);
				console.log(errorThrown);
			}
		});
	});
	$("#deleteKomentar").click(function(){
		$.ajax({
			method : 'POST',
			url : "../rs.ftn.mr.webforum/rest/komentar/delete",
			contentType : 'application/json',
			data: JSON.stringify({
				"id" : getUrlVars()["id"],
			}),
			success : function(data) {
				alert("uspesno je izbrisan");
				window.location.href = "home.html"
			},
			error : function(XMLHttpRequest, textStatus, erlororThrown) {
				console.log(textStatus);
				console.log(errorThrown);
			}
		});
	});
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
	$(".edit").hide();
	updateComment();
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
	$.ajax({
		method : 'POST',
		url : "../rs.ftn.mr.webforum/rest/komentar/comments",
		contentType : 'application/json',
		data: JSON.stringify({ "id" : getUrlVars()["id"]}),
		success : function(Nedata) {
			console.log("Komentari" + Nedata)
			Nedata.forEach(function(element) {
				addComment(element);
			});
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(textStatus);
			console.log(errorThrown);
		}
	});
});

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
function updateComment(){
	$(".content").hide();
	$.ajax({
		method : 'POST',
		url : "/rs.ftn.mr.webforum/rest/komentar/getComment",
		contentType : 'text/plain',
		data :  getUrlVars()["id"],
		success : function(data) {
			console.log(data);
			$("#brojLike").append(data.brojLike);
			$("#brojDislike").append(data.brojDislike);
			$("#naslov").append(data.tekst_komentar);
			$("#createdOn").append(data.datum_komentara);
			temaId = data.id_tema;
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
			$.ajax({
				method : 'POST',
				url : "/rs.ftn.mr.webforum/rest/user/cookie",
				contentType : 'text/plain',
				data :  $.cookie("web-forum"),
				success : function(userStigao) {
					if(data.autor == userStigao.id ){
						$(".edit").show();
					}
					if(userStigao.uloga == "admin" || userStigao.uloga == "moderator"){
						$(".edit").show();
					}
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
function formToJSON(tekst) {
	return JSON.stringify({
		"id" : null,
		"id_tema" : temaId ,
		"autor" : null,
		"datum_kreiranja" : null,
		"id_parent_komentar" : getUrlVars()["id"].replace("#",""),
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
		$("#komentar" + comment.id).find(".izmenjen").append("izm");
	}
	if(comment.obrisan == true) {
		$("#komentar" + comment.id).find(".textKomentara").append("OBRISAN");
	}else{
		$("#komentar" + comment.id).find(".textKomentara").append(comment.tekst_komentar);
		$("#komentar" + comment.id).find(".textKomentara").attr("href", "komentar.html?id=" + comment.id);
	}
	$("#komentar" + comment.id).find(".brojLike").text("like: " + comment.brojLike + " dislike: " + comment.brojDislike)

}