<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

        
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>whatsAround</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
.h6 {
	text-align:center;
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
#detail {
	border-bottom: thin;
}
img 
{
    max-width: none;
}
</style>
<script src="resources/jquery.js"></script>
<script src="resources/slider/bootstrap-slider.js"></script>
<script src="resources/pois.js"></script>


<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
<script>
	var dev = true;
	var baseUrl;
	
	if (dev == true) {
		baseUrl = "/WebWhatsAround";
	} else {
		baseUrl = "";
	}
	var iconBaseFile = 'https://chart.googleapis.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|';
	
	var map;
	var geocoder;
	var poisMarkers = {};
	var pois = {};

	function initialize() {
		console.log("initialize");
		geocoder = new google.maps.Geocoder();
		var latlng = new google.maps.LatLng(48.833, 2.333);
		pois = {};
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
	<h1 style="text-align:center;">Voir les lieux intéressants autour de moi</h1>
	<div id="detailsContainer" style="width:20%; position:absolute; top:100px; left:1%;" ><fieldset><legend>Détails</legend></fieldset></div>
	<div id="details" style="height:75%; width:20%; position:absolute; top:150px; left:1%; overflow:auto" ></div>
	<div id="map-canvas" style="height:80%; width:55%; position:absolute; top:100px; left:21% "></div>
	<div id="legend-canvas" style="height:80%; width:20%; position:absolute; top:100px;  left:76%;">
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
