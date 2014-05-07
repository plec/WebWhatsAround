package com.plec.whatsaround.service;

import java.util.List;

import com.plec.whatsaround.dao.IPoiDao;
import com.plec.whatsaround.populate.bean.POI;

public class POIService {

	private IPoiDao poiDao;
	
	public List<POI> getPoisNearMe(double lat, double lng, int radius) {
		List<POI> poisNearMe = poiDao.getPoiNearPoint(lat, lng, radius);
		return poisNearMe;
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
