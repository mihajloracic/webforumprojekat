$( document ).ready(function() {
    $("#search").click(function(e){
    	e.preventDefault();
    	$.ajax({
    		method : 'POST',
    		url : "../rs.ftn.mr.webforum/rest/podforum/search",
    		contentType : 'application/json',
    		data: JSON.stringify({
    			"naslov" : $("#naslov").val(),
    			"opis" : $("#opis").val(),
    			"moderator" : $("#moderator").val(),
    		}),
    		success : function(data) {
    			$("#posts").empty()
    			data.forEach(function(element) {
    				console.log(element);
    				$( "#posts" ).append( '<a href="home.html?name=' + element.id + '" class="list-group-item list-group-item-action">'+element.naziv+'</a>' );
    			});
    			
    		},
    		error : function(XMLHttpRequest, textStatus, errorThrown) {
    			console.log(textStatus);
    			console.log(errorThrown);
    		}
    	});
    });
});