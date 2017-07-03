$( document ).ready(function() {
    	$.ajax({
    		method : 'POST',
    		url : "../rs.ftn.mr.webforum/rest/user/users",
    		success : function(data) {
    			$("#posts").empty();
    			console.log(data)
    			data.forEach(function(element) {
    				var text = 	'<div class="row">'
    				+ '<span>' + element.user +  ' ' +  element.uloga   +   '</span>  <button class="btn btn-default dodaj" id="' +  element.id +  '">Postavi za moderatora</button>'
    				+ '</div>';
    				$( "#posts" ).append( text);
    			});
    			
    		},
    		error : function(XMLHttpRequest, textStatus, errorThrown) {
    			console.log(textStatus);
    			console.log(errorThrown);
    		}
    	});
});
$(document).on("click",".dodaj",function(){
	$.ajax({
		method : 'POST',
		url : "../rs.ftn.mr.webforum/rest/user/update",
		contentType : "application/json",
		data : JSON.stringify({ "id" : $(this).attr( "id")}),
		success : function(data) {
			document.location.href = document.location.href; 
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(textStatus);
			console.log(errorThrown);
		}
	});
	console.log($(this).attr( "id"));
})