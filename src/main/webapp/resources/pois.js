
	function displayDetailsPois(poi) {
		//create the div details info content
		var div = $('#details')[0];
		var poiDetails = '<div onmouseover="overMarker(\''+poi.sourceId+'\', true)" onmouseout="overMarker(\''+poi.sourceId+'\', false)" >';
		poiDetails += '<strong>' + poi.name + '</strong><br/>';
		poiDetails += '<small><strong><i>Description : </i></strong>' + poi.description + '<br/>';
		poiDetails += '<strong><i>Adresse : </i></strong>' + poi.adresse + ' <br/>';
		poiDetails += '<strong><i>Type : </i></strong>' + poi.type + '<br/>';
		if (poi.horaires != null) {
			poiDetails += '<strong><i>Horaires : </i></strong>' + poi.horaires + '</small>';
		}
		poiDetails += '<hr/></div>';
		div.innerHTML += poiDetails;

	}
	function reset() {
		console.log("reset all");
		//reset details div
		var div = $('#details')[0];
		div.innerHTML = '';
		
		//reset markers
		 for (var i = 0; i < markersArray.length; i++ ) {
			 console.log("reset one poi");
			 markersArray[i].setMap(null);
		 }
		 markersArray.length = 0;
	}
	function selectMarker(poiId) {
		console.log("change marker for " + poiId);
		var currentPoi = pois[poiId];
		overMarker(poiId, currentPoi.selected);
	}
	function overMarker(poiId, isSelected) {
		console.log("change marker for " + poiId);
		var currentPoi = pois[poiId];
		if (isSelected) {
			currentPoi.selected = false;
			poisMarkers[poiId].setIcon(iconBaseFile + 'FF0000');
		} else {
			currentPoi.selected = true;
			poisMarkers[poiId].setIcon(iconBaseFile + currentPoi.typeColor);
		}
	}
	function displayPoi(poi) {
		console.log("Display poi " + poi.sourceId);
		var latLng = new google.maps.LatLng(poi.latlng.lat, poi.latlng.lng);

		//create marker
		var marker = new google.maps.Marker({
			map : map,
			position : latLng
		});


		var typeColor = '0000FF';
		if (poi.type == 'Musee') {
			typeColor = '9A9AFF';
		} else if (poi.type == 'MH') {
			typeColor = 'FF971F';
		}
		poi.typeColor = typeColor;
		poi.selected = false;
		//Ajout de la couleur en fonction du type
		var iconFile = iconBaseFile + typeColor;

		//set Image to marker
		marker.setIcon(iconFile);
		poisMarkers[poi.sourceId] = marker;
		markersArray.push(marker);
		//create the div info content
		var poiContent = '<div id="content" class="marker" >';
		poiContent += '<div id="siteNotice">';
		poiContent += '</div>';
		poiContent += '<h1 id="firstHeading" class="firstHeading"><strong>' + poi.name + '</strong></h1>';
		poiContent += '<div id="bodyContent"><strong><i>Description : </i></strong>' + poi.description + '<br/>';
		poiContent += '<strong><i>Adresse : </i></strong>' + poi.adresse + ' /<br/>('
				+ poi.formattedAddress + ')<br/>';
		poiContent += '<strong><i>Type : </i></strong>' + poi.type + '<br/>';
		if (poi.horaires != null) {
			poiContent += '<strong><i>Horaires : </i></strong>' + poi.horaires + '<br/>';
		}
		poiContent += '</div>';
		poiContent += '</div>';

		//make the div as info bulle
		var poiInfoWindow = new google.maps.InfoWindow({
			content : poiContent
		});

		//add click envent
		google.maps.event.addListener(marker, 'click', function() {
			poiInfoWindow.open(map, marker);
		});
	}
	function searchPoiAroundMe(lat, lng, radius) {
		var urlToCall = baseUrl + "/searchPoisNearMe?lat=" + lat + "&lng=" + lng + "&radius=" + radius;
		console.log(urlToCall);
		$.ajax({
			url : urlToCall,
			success : function(data) {
				//reinit pois
				pois = {};
				var dataLength = data.length;
				for ( var i = 0; i < dataLength; i++) {
					pois[data[i].sourceId] = data[i];
					displayPoi(data[i]);
					displayDetailsPois(data[i]);
				}
			}
		});
	}
	
	function searchPoiByName(query) {
		reset();
		var urlToCall = baseUrl + "/searchPoisByName?q=" + query;
		console.log(urlToCall);
		$.ajax({
			url : urlToCall,
			success : function(data) {
				//reinit pois
				pois = {};
				var dataLength = data.length;
				for ( var i = 0; i < dataLength; i++) {
					pois[data[i].sourceId] = data[i];
					displayPoi(data[i]);
					displayDetailsPois(data[i]);
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