<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>whatsAround</title>
<!-- Bootstrap -->
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="resources/bootstrap/css/docs.css" rel="stylesheet">
<link href="resources/slider/slider.css" rel="stylesheet">
<link href="resources/bootstrap/css/pygments-manni.css" rel="stylesheet">
<style>
html,body,#map-canvas {
	height: 100%;
	margin: 0px;
	padding: 0px
}

#panel {
	position: absolute;
	top: 5px;
	left: 50%;
	margin-left: -180px;
	z-index: 5;
	background-color: #fff;
	padding: 5px;
	border: 1px solid #999;
}
</style>
<script src="resources/jquery.js"></script>
<script src="resources/slider/bootstrap-slider.js"></script>


<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
<script>
var map;
var geocoder;
	function displayPoi(poi) {

		var latLng = new google.maps.LatLng(poi.latlng.lat, poi.latlng.lng);

		//create marker
		var marker = new google.maps.Marker({
			map : map,
			position : latLng
		});

		var iconBaseFile = 'https://chart.googleapis.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|';

		var typeColor = '0000FF';
		if (poi.type == 'Musee') {
			typeColor = '9A9AFF';
		} else if (poi.type == 'MH') {
			typeColor = 'FF971F';
		}
		//Ajout de la couleur en fonction du type
		var iconFile = iconBaseFile + typeColor;

		//set Image to marker
		marker.setIcon(iconFile);

		//create the div info content
		var div = document.createElement('div');
		var poiContent = '<div class="marker" >';
		poiContent += '<H6><strong>' + poi.name + '</strong></h6>';
		poiContent += '<p><strong><i>Description : </i></strong>' + poi.description + '</p>';
		poiContent += '<p><strong><i>Adresse : </i></strong>' + poi.adresse + ' /<br/>'
				+ poi.formattedAddress + '</p>';
		poiContent += '<p><strong><i>Type : </i></strong>' + poi.type + '</p>';
		poiContent += '<p><strong><i>Horaires : </i></strong>' + poi.horaires + '</p>';
		poiContent += '</div>';
		div.innerHTML = poiContent;

		//make the div as info bulle
		var poiInfoWindow = new google.maps.InfoWindow({
			content : div
		});

		//add click envent
		google.maps.event.addListener(marker, 'click', function() {
			poiInfoWindow.open(map, marker);
		});
	}
	function searchPoiAroundMe(lat, lng, radius) {
		console.log("searchPoiAroundMe "+lat + "/" + lng + " radius :" +radius);
		$.ajax({
			url : "/getPois?lat=" + lat + "&lng=" + lng + "&radius=" + radius,
			success : function(data) {
				var dataLength = data.length;
				for ( var i = 0; i < dataLength; i++) {
					displayPoi(data[i]);
				}
			}
		});
	}
	
	function searchPoiByName(query) {
		console.log("searchPoisByName "+query);
		$.ajax({
			url : "/searchPoisByName?q=" + query,
			success : function(data) {
				var dataLength = data.length;
				for ( var i = 0; i < dataLength; i++) {
					displayPoi(data[i]);
				}
			}
		});
	}
	
	function searchPoi() {
		var query = $('#querySearch').val();
		if (query == "") {
			searchPoiAroundMe(48.833, 2.333, $('#slider').val());
		} else {
			searchPoiByName(query);
		}
	}
	

	function initialize() {
		console.log("initialize");
		geocoder = new google.maps.Geocoder();
		var latlng = new google.maps.LatLng(48.833, 2.333);
		var mapOptions = {
			zoom : 15,
			center : latlng
		}
		map = new google.maps.Map(document.getElementById("map-canvas"),
				mapOptions);
		
		$('#slider').slider();
	}


	searchPoiAroundMe(48.833, 2.333, 100);
	google.maps.event.addDomListener(window, 'load', initialize);
</script>
</head>
<body>
	<h1>Voir les lieux intéressants autour de moi</h1>
	<div id="map-canvas" style="height:80%; width:75%; position:absolute; top:100px; left:1%"></div>
	<div id="legend-canvas" style="height:80%; width:20%; position:absolute; top:100px;  left:76%">
	<fieldset>
	<legend>Legend</legend>
	<ul>
		<li><strong><i>Musée : </i></strong><img height="50%" src="https://chart.googleapis.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|9A9AFF"></li>
		<li><strong><i>Monument historique : </i></strong><img height="50%" src="https://chart.googleapis.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|FF971F"></li>
		<li><strong><i>Autre : </i></strong><img height="50%" src="https://chart.googleapis.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|0000FF"></li>
	</ul>
	<br/>
	 </fieldset>
	<fieldset>
	<legend>Options de recherche</legend>
	   Zone de recherche : 100m <input type="text" class="span2" value="100" id="slider" data-slider-min="50" data-slider-max="1000" data-slider-step="10" data-slider-value="100">1000m <br/>
	   Recherche de Point d'intérêt <input id="querySearch" type="text" placeholder="Recherche ..."/><br/>
	   <button class="btn" onclick="searchPoi();">Rechercher</button><br/>
	 </fieldset>
	</div>
</body>
</html>
