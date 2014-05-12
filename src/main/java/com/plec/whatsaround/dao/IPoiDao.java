package com.plec.whatsaround.dao;

import java.util.List;

import com.plec.whatsaround.populate.bean.POI;

public interface IPoiDao {
	/**
	 * Init method
	 */
	public void init();
	/**
	 * Get a by by source Id e.g. the id set in the import file
	 * @param id the source id e.g. IPXXX
	 * @return the Poi or null if not found
	 */
	public POI getPoiByID(String id);

	/**
	 * Search pois by queriing the name or descirption
	 * @param query : the query searched
	 * @return a list of POIs (limited by configuration default 100) or null if not found
	 */
	public List<POI> searchPoisByName(String query);

	/**
	 * Search pois around a point
	 * @param lat latitude of the center point
	 * @param lng longitude of the center point
	 * @param radius of the search e.g. search around the point in a circle of <i>radius</i> meters
	 * @return a list of POIs (limited by configuration default 100) or null if not found
	 */
	public List<POI> getPoiNearPoint(double lat, double lng, int radius);

}