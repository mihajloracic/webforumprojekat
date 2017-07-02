$( document ).ready(function() {
    $("#search").click(function(e){
    	e.preventDefault();
    	$.ajax({
    		method : 'POST',
    		url : "../rs.ftn.mr.webforum/rest/post/search",
    		contentType : 'application/json',
    		data: JSON.stringify({
    			"naslov" : $("#naslov").val(),
    			"sadrzaj" : $("#sadrzaj").val(),
    			"autor" : $("#autor").val(),
    			"podforum" : $("#podforum").val()
    		}),
    		success : function(data) {
    			data.forEach(function(element) {
    				$( "#posts" ).append( '<a href="tema.html?id=' + element.id + '" class="list-group-item list-group-item-action">'+element.naslov+'</a>' );
    			});
    			
    		},
    		error : function(XMLHttpRequest, textStatus, errorThrown) {
    			console.log(textStatus);
    			console.log(errorThrown);
    		}
    	});
    });
});