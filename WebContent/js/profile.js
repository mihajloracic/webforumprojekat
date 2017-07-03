$( document ).ready(function() {
	$.ajax({
		method : 'POST',
		url : "../rs.ftn.mr.webforum/rest/podforum/getFollowed",
		success : function(data) {
			console.log(data);
			$("#praceniPodformi").empty()
			data.forEach(function(element) {
				$( "#praceniPodformi" ).append( '<a href="home.html?name=' + element.id + '" class="list-group-item list-group-item-action">'+element.naziv+'</a>' );
			});		
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(textStatus);
			console.log(errorThrown);
		}
	});
	$.ajax({
		method : 'POST',
		url : "../rs.ftn.mr.webforum/rest/post/liked",
		success : function(data) {
			$("#likeDislikeTema").empty()
			data.forEach(function(element) {
				$( "#likeDislikeTema" ).append( '<a href="tema.html?id=' + element.id + '" class="list-group-item list-group-item-action">'+element.naslov+'</a>' );
			});		
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(textStatus);
			console.log(errorThrown);
		}
	});
	$.ajax({
		method : 'POST',
		url : "../rs.ftn.mr.webforum/rest/komentar/liked",
		success : function(data) {
			$("#likeDislikeKomentar").empty()
			data.forEach(function(element) {
				$( "#likeDislikeKomentar" ).append( '<a href="komentar.html?id=' + element.id + '" class="list-group-item list-group-item-action">'+element.tekst_komentar+'</a>' );
			});		
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(textStatus);
			console.log(errorThrown);
		}
	});
});