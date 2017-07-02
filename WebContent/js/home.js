var odgvorniModerator;
$(document).ready(function() {
	$("#buttonObrisiPodforum").hide();
	$("#buttonObrisiPodforum").click(function(){
		$.ajax({
			method : 'POST',
			url : "../rs.ftn.mr.webforum/rest/podforum/delete",
			contentType : "application/json",
			data : JSON.stringify({ "id" : getUrlVars()["name"]}),
			success : function() {
                window.location.href = window.location.href;
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("Greska");
			}
		});
	});
	$(document).on("submit","#slanjeSlike512",function(e){
        e.preventDefault();
        var data = new FormData(this);
       
        var file = $('#pictureLink')[0].files[0];
         $.ajax({
                url : "../rs.ftn.mr.webforum/rest/post/upload",
                type : "POST",
                contentType : "multipart/form-data",
                dataType: "JSON",
                data: file,
                processData: false,
                async: false,
                complete: function(data) {
                    console.log(data.responseText);
                    var naslov = $("#temaNaziv").val();
                	var tekst = $("#postOpis").val();
                	var link = $("#postLik").val();
                	var slika = data.responseText;
                	var tip;
                	$( "#tipSelect option:selected" ).each(function() {
                	  tip = $( this ).text();
                	});
                	$.ajax({
                		method : 'POST',
                		url : "/rs.ftn.mr.webforum/rest/post/add",
                		contentType : 'application/json',
                		dataType : "",
                		data : temaformToJSON(naslov,tip,tekst,link,slika,getUrlVars()["name"]),
                		success : function(data) {
                			
                		},
                		error : function(XMLHttpRequest, textStatus, errorThrown) {
                			console.log(temaformToJSON(naslov,tip,tekst,link,slika,getUrlVars()["name"]));
                		}
                	});
                    
                    
                    window.location.href = window.location.href;
                }
         });
       
    });
	$('#buttonDodajPost').click(function(){
		$('#slanjeSlike512').submit();
	}); 
	$("#formLink").hide();
	$("#formSlika").hide();
	$( "#tipSelect" ).change(function() {
		$( "#tipSelect option:selected" ).each(function() {
		      console.log($( this ).text());
		      $(".formpost").hide();
		      var idShow = "#form" + $( this ).text();
		      $(idShow).show();
		});
	});
	$("#podforum-info-holder").hide();
	if($.cookie("web-forum") != null && $.cookie("web-forum") != undefined && $.cookie("web-forum") != ""){
		$( "#user-looged-in" ).hide();
		$( "#user-name" ).show();
		$( "#logout" ).show();
		$( "#addPodforum" ).hide();
	}else{
		$( "#user-looged-in" ).show();
		$( "#user-name" ).hide();
		$( "#logout" ).hide();
		$( "#addPodforum" ).hide();
	}
	$("#logout").on("click",function() {
		$.ajax({
			method : 'POST',
			dataType : "text",
			data : $.cookie("web-forum"),
			url : "../rs.ftn.mr.webforum/rest/user/deleteCookie",
			success : function(data) {
				console.log("izbrisan");
			},
		});
		$.cookie("web-forum","");
		
		window.location.href = "register.html";
	});
	$("#user-looged-in").on("click",function() {
		window.location.href = "/rs.ftn.mr.webforum/register.html";
	});
	prikaziPostove();
	$.ajax({
		method : 'POST',
		url : "../rs.ftn.mr.webforum/rest/post/teme",
		contentType : 'text/plain',
		data: getUrlVars()["name"],
		success : function(data) {
			odgvorniModerator = data.odgovorniModerator;
			data.forEach(function(element) {
				$( "#posts" ).append( '<a href="tema.html?id=' + element.id + '" class="list-group-item list-group-item-action">'+element.naslov+'</a>' );
			});
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(textStatus);
			console.log(errorThrown);
		}
	});
	$.ajax({
		method : 'POST',
		url : "../rs.ftn.mr.webforum/rest/user/cookie",
		contentType : 'text/plain',
		dataType : "text",
		data : $.cookie("web-forum"),
		success : function(data) {
			console.log(data);
			var d = JSON.parse(data);
			if(d.uloga == "admin" || d.id == odgvorniModerator){
				$( "#addPodforum" ).show();
				$("#buttonObrisiPodforum").show();
			}
			$("#user-name").text(d.user);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("Greska");
		}
	});
	
	$('#myModal').on('shown.bs.modal', function () {
		  $('#myInput').focus()
	})
	
	$('#buttonDodajPodforum').click(function(){
		var naziv = $("#modalNaziv").val();
		var opis = $("#modalOpis").val();
		var pravila = $("#modalPravila").val();
		$.ajax({
			method : 'POST',
			url : "../rs.ftn.mr.webforum/rest/podforum/add",
			contentType : 'application/json',
			dataType : "text",
			data : podforumFormToJSON(naziv,opis,pravila),
			success : function(data) {
				alert("podforum je dodat");
				$("#modalNaziv").val("");
				$("#modalOpis").val("");
				$("#modalPravila").val("");
				$( "#podforum-holder" ).append(  '<li><a href=" /rs.ftn.mr.webforum/home.html?name=' + element.naziv +'">' + element.data + "</a></li>" );
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {

			}
		});
	})

});
function podforumFormToJSON(naziv,opis,spisakPravila) {
	return JSON.stringify({
		"id" : null,
		"naziv" : naziv,
		"opis" : opis,
		"spisakPravila" : spisakPravila,
		"odgovorniModerator" : null,
	});
}
function prikaziPostove(){
	$.ajax({
		method : 'POST',
		url : "../rs.ftn.mr.webforum/rest/podforum/podforumi",
		contentType : 'application/json',
		success : function(data) {
			$("#podforum-holder").empty();
			var tag = 0;
			var selected;
			if(getUrlVars()["name"]){
				selected = getUrlVars()["name"];
				
			}else{
				window.location.href = "/rs.ftn.mr.webforum/home.html?name=" + data[0].id;
				
				selected = data[0].id;
			}
			data.forEach(function(element) {
				if(selected == element.id){
					$( "#podforum-holder" ).append( '<li class="active"><a href=" /rs.ftn.mr.webforum/home.html?name=' + element.id +'">' + element.naziv + "</a></li>" );
					$("#podforum-naslov").append(element.naziv);
					$("#podforum-opis").append(element.opis);
					$("#podforum-pravila").append(element.spisakPravila);
				}else{
					$( "#podforum-holder" ).append( '<li><a href=" /rs.ftn.mr.webforum/home.html?name=' + element.id +'">' + element.naziv + "</a></li>" );
					
				}
			    
			    });
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("ASDASD");
		}
	});
	$( "#podforum-naslov" ).click(function() {
		$( "#podforum-info-holder" ).toggle( "slow", function() {
		    // Animation complete.
		});
	});
	$("#imgInp").change(function(){
	    readURL(this);
	});
	$("#otvaraceForme").click(function(){
		if($.cookie("web-forum") != null && $.cookie("web-forum") != "" && $.cookie("web-forum") != undefined){

		}else{
			window.location.href = "register.html";
		}
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
function readURL(input) {

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#blah').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}
function temaformToJSON(naslov,tip,tekst,link,slika, nazivPodforum) {
	return JSON.stringify({
		"id" : null,
		"id_podforum" : null,
		"naslov" : naslov,
		"tip" : tip,
		"autor" : null,
		"tekst" : tekst,
	    "link": link,
	    "datum_kreiranja": null,
	    "slika": slika,
	    "nazivPodforum" : nazivPodforum
	});
}
