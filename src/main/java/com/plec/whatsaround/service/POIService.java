package com.plec.whatsaround.service;

import java.util.List;

import com.plec.whatsaround.dao.POIDao;
import com.plec.whatsaround.populate.bean.POI;

public class POIService {

	private POIDao poiDao;
	
	public List<POI> getPoiNearMe(double lat, double lng, int radius) {
		List<POI> poisNearMe = poiDao.getPoiNearPoint(lat, lng, radius);
		return poisNearMe;
	}

	public void setPoiDao(POIDao poiDao) {
		this.poiDao = poiDao;
	}

}
