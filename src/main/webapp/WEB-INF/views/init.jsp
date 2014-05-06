<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>whatsAround</title>
<!-- Bootstrap -->
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="resources/bootstrap/css/docs.css" rel="stylesheet">
<link href="resources/bootstrap/css/pygments-manni.css" rel="stylesheet">
<style>
      html, body, #map-canvas {
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

<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
<script>
	  var geo;
	  var map;
	function displayPoi(lat, lng, poiName, description, adresse, type, map) {
		var latLng = new google.maps.LatLng(lat, lng);
		
		//create marker
		var marker = new google.maps.Marker({map: map, position: latlng});
		
		var iconBaseFile = 'https://chart.googleapis.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|';
		
		var typeColor = 'FF8075';
		if (type == 'Musee') {
			var typeColor = 'FF8075';
		} else if (type == MH) {
			var typeColor = 'FF8075';
		}
		//Ajout de la couleur en fonction du type
		var iconFile = iconBaseFile + typeColor;
		
		//set Image to marker
		marker.setIcon(iconFile);
		
		//create the div info content
		var div = document.createElement('div');
		var poiContent = '<div class="marker" >';
		poiContent += '<H6><strong>'+poiName+'</strong></h6>';
		poiContent += '<p>Description : '+description+'</p>';
		poiContent += '<p>Adresse : '+adresse+'</p>';
		poiContent += '<p>Horaires : '+horaire+'</p>';
		poiContent += '</div>';
		div.innerHTML = poiContent;
		
		//make the div as info bulle
		var poiInfoWindow = new google.maps.InfoWindow({content: div});
		
		//add click envent
		google.maps.event.addListener(marker, 'click', function() { poiInfoWindow.open(map, marker); });
	}
	  function initialize() {
	    geocoder = new google.maps.Geocoder();
	    var latlng = new google.maps.LatLng(48.833, 2.333);
	    var mapOptions = {
	      zoom: 8,
	      center: latlng
	    }
	    map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
	  }
	  google.maps.event.addDomListener(window, 'load', initialize);
	  </script>
</head>
<body>
   <h1>Voir les lieux intéressants autour de moi</h1>
	<script>

	 </script>
    <div id="map-canvas"></div>
</body>
</html>
