$( document ).ready(function() {
	$("#likeTemaAdd").click(function(){
		$.ajax({
			method : 'POST',
			url : "/rs.ftn.mr.webforum/rest/post/like",
			contentType : 'application/json',
			data :  formLikeToJSON(true),
			success : function() {
				window.location.href = window.location.href;
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
			}
		});
	});
	$("#dislikeTemaAdd").click(function(){
		$.ajax({
			method : 'POST',
			url : "/rs.ftn.mr.webforum/rest/post/like",
			contentType : 'application/json',
			data :  formLikeToJSON(false),
			success : function() {
				window.location.href = window.location.href;
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
			}
		});
	});
	$(".content").hide();
	$.ajax({
		method : 'POST',
		url : "/rs.ftn.mr.webforum/rest/post/temaById",
		contentType : 'text/plain',
		data :  getUrlVars()["id"],
		success : function(data) {
			console.log(data);
			$("#brojLike").append(data.brojLike);
			$("#brojDislike").append(data.brojDislike);
			$("#" + data.tip).show();
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
	$("#dodajKomentar").click(function(e){
		e.preventDefault();
		if($.cookie("web-forum") != null && $.cookie("web-forum") != "" && $.cookie("web-forum") != undefined){		
		}else{
			window.location.href = "register.html";			
		}
		$.ajax({
			method : 'POST',
			url : "/rs.ftn.mr.webforum/rest/komentar/add",
			contentType : 'application/json',
			data : formToJSON($("#textKomentar").val()),
			success : function(userStigao) {
				//window.location.href = window.location.href;
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(textStatus);
				console.log(errorThrown);
			}
		});
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
function formToJSON(tekst) {
	return JSON.stringify({
		"id" : null,
		"id_tema" : getUrlVars()["id"] ,
		"autor" : null,
		"datum_kreiranja" : null,
		"id_parent_komentar" : -1,
		"tekst_komentar" : tekst,
		"obirsan" : false,
		izmenjen : false
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