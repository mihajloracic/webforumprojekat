$( document ).ready(function() {
	$.ajax({
		method : 'POST',
		url : "../rs.ftn.mr.webforum/rest/post/getFollowed",
		success : function(data) {
			console.log(data);
			$("#praceniPodformi").empty()
			data.forEach(function(element) {
				if(Math.floor((Math.random() * 3) + 1) == 1){
					
				}else{
					$( "#praceniPodformi" ).append( '<a href="tema.html?id=' + element.id + '" class="list-group-item list-group-item-action">'+element.naslov+'</a>' );
				}
			});		
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(textStatus);
			console.log(errorThrown);
		}
	});

});