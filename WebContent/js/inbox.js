$( document ).ready(function() {
    	$.ajax({
    		method : 'POST',
    		url : "../rs.ftn.mr.webforum/rest/poruka/getMessages",
    		success : function(data) {
    			$("#posts").empty();
    			console.log(data)
    			data.forEach(function(element) {
    				console.log(element);
    				$( "#posts" ).append( '<a href="#" class="list-group-item list-group-item-action">subject: '+element.subject + " username: " +element.senderName+'<p>' + element.tekst+ '</p></a>' );
    			});
    			
    		},
    		error : function(XMLHttpRequest, textStatus, errorThrown) {
    			console.log(textStatus);
    			console.log(errorThrown);
    		}
    	});
});