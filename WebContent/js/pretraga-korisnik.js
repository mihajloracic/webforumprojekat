$( document ).ready(function() {
    $("#search").click(function(e){
    	e.preventDefault();
    	$.ajax({
    		method : 'POST',
    		url : "../rs.ftn.mr.webforum/rest/user/search",
    		contentType : 'application/json',
    		data: JSON.stringify({
    			"korisnik" : $("#korisnik").val(),
    		}),
    		success : function(data) {
    			$("#posts").empty()
    			data.forEach(function(element) {
    				console.log(element);
    				$( "#posts" ).append( '<a href="message.html?id=' + element.id + '&name=' + element.user + '" class="list-group-item list-group-item-action">'+element.user+'</a>' );
    			});
    			
    		},
    		error : function(XMLHttpRequest, textStatus, errorThrown) {
    			console.log(textStatus);
    			console.log(errorThrown);
    		}
    	});
    });
});