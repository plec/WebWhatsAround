package com.plec.whatsaround.service;

import java.util.ArrayList;
import java.util.List;

import com.plec.whatsaround.dao.IPoiDao;
import com.plec.whatsaround.populate.bean.LatLng;
import com.plec.whatsaround.populate.bean.POI;

public class POIService {

	private IPoiDao poiDao;
	
	public List<POI> getPoisNearMe(double lat, double lng, int radius) {
		List<POI> poisNearMe = poiDao.getPoiNearPoint(lat, lng, radius);
		return poisNearMe;
	}

	public List<POI> getPoisNearMeMock() {
		List<POI> pois = new ArrayList<POI>();

		POI poi = new POI();
		poi.setSourceId("MDF447");
		poi.getLatlng().setLat(48.8341938);
		poi.getLatlng().setLng(2.3324357);
		poi.setDescription("www.catacombes-de-paris.fr");
		poi.setName("Les Catacombes");
		poi.setType("Musee");
		poi.setAdresse("1, avenue du Colonel Henri Rol-Tanguy(Place Denfert-Rochereau) 75014 PARIS");
		poi.setFormattedAddress("Place Denfert-Rochereau, 75014 Paris, France");
		pois.add(poi);
		
		poi = new POI();
		poi.setSourceId("IP3462");
		poi.getLatlng().setLat(48.836203);
		poi.getLatlng().setLng(2.331067);
		poi.setDescription("Appartement à l'entresol (lots n° 7 et 9, bâtiments A et B) (cad. AO 4) : inscription par arrêté du 4 mars 1999 - Ensemble des façades et des toitures ; vestibule d'entrée ; appartement du premier étage dans le bâtiment A (lots n° 2 et 8) (cad. AO 4) : classement par arrêté du 27 mars 2000");
		poi.setName("Immeuble");
		poi.setType("MH");
		poi.setAdresse("Schoelcher (rue) 5");
		poi.setFormattedAddress("5 Rue Victor Schoelcher, 75014 Paris, France");
		pois.add(poi);

		poi = new POI();
		poi.setSourceId("IP1943");
		poi.getLatlng().setLat(48.8287579);
		poi.getLatlng().setLng(2.3352601);
		poi.setDescription("Les façades et toitures du bâtiment en L ; les ateliers en rez-de-chaussée en vis-à-vis ; le sol de la cour (cad. 14-02 BN 30) : inscription par arrêté du 17 octobre 2006");
		poi.setName("Immeuble");
		poi.setType("MH");
		poi.setAdresse("Saint-Gothard (rue du) 16");
		poi.setFormattedAddress("16 Rue du Saint-Gothard, 75014 Paris, France");
		pois.add(poi);
		return pois;
	}

	
	public List<POI> searchPoisByName(String query) {
		List<POI> pois = poiDao.searchPoisByName(query);
		return pois;
	}

	
	public POI getPoi(String id) {
		return poiDao.getPoiByID(id);
	}

	public void setPoiDao(IPoiDao poiDao) {
		this.poiDao = poiDao;
	}

}
