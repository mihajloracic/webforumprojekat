

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
		url : "/rs.ftn.mr.webforum/rest/komentar/comments",
		contentType : 'application/json',
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
}