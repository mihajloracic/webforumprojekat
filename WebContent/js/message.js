$(document).ready(function(){
	if ($.cookie("web-forum") == null || $.cookie("web-forum") == undefined
			|| $.cookie("web-forum") == ""){
		window.location.assign("register.html")
		return;
	}
	$("#prikaziIme").text(getUrlVars()["name"]);
	$("#posalji").click(function(){
		$.ajax({
			type : 'POST',
			url : "../rs.ftn.mr.webforum/rest/poruka/add",
			contentType : 'application/json',
			data : formToJSON(getUrlVars()["id"], $("#subject").val(), $("#textPoruke").val()),
			success : function() {
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest)
			    console.log(errorThrown)
			    console.log(textStatus)
			}
		});
	})
});
function formToJSON(prima, subject,tekst) {
	return JSON.stringify({
		"prima" : prima,
		"subject" : subject,
		"tekst" : tekst
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