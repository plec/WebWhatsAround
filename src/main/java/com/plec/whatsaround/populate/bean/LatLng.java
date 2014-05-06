package com.plec.whatsaround.populate.bean;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Index;

@Entity("latlng")
public class LatLng {
	/** latitude */
	private double lat;
	/** longitude */
	private double lng;
	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}
	/**
	 * @return the lng
	 */
	public double getLng() {
		return lng;
	}
	/**
	 * @param lng the lng to set
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}
}

