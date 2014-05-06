package com.plec.whatsaround.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.code.morphia.Morphia;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.plec.whatsaround.populate.bean.LatLng;
import com.plec.whatsaround.populate.bean.POI;

public class POIDao {
	private static final Logger LOGGER = Logger.getLogger(POIDao.class);

	
	private String MongoUrl = "mongodb://whatsaround:whatsaround@ds029630.mongolab.com:29630/whatsaroundbase";
	
	private String MongoDBCollection = "POI";
	
	private DB db;
	private DBCollection poiCollection;
	private MongoClient client;
	
	public POIDao() {
		try {
			MongoClientURI uri  = new MongoClientURI(MongoUrl);
	        client = new MongoClient(uri);
	        db = client.getDB(uri.getDatabase());
	        poiCollection = db.getCollection(MongoDBCollection);

		} catch (UnknownHostException uhe) {
			LOGGER.error("Error inserting pois : " + uhe.getMessage(), uhe);
			throw new RuntimeException(uhe.getMessage(), uhe);
		}
	}
	
	public List<POI> getPoiNearPoint(double lat, double lng, int radius) {
		List<POI> poisNearMe = new ArrayList<POI>();
		LOGGER.info("Execute geo near request on mongodb");
		long l = System.currentTimeMillis();
		DBCursor result = poiCollection.find(new BasicDBObject(
        		"latlng" , new BasicDBObject(
        				"$geoWithin", 
        				new BasicDBObject(
        						"$centerSphere",new Object[]{
        								new Double[]{(double) lat,(double) lng},
        								(double) radius/3959}
        						)
        				)
        		));
		if (result.count() > 0) {
			LOGGER.info("Request executed on MongoDB on " + (System.currentTimeMillis() - l) + "ms " + result.count() + " results");
			while (result.hasNext()) {
				DBObject curPoi = result.next();
				poisNearMe.add(convert(curPoi));
			}
		}
        LOGGER.info("NB result : " + result.count());
		return poisNearMe;
	}
	private POI convert(DBObject dbo) {
		POI poi = new POI();
		poi.setAdresse((String)dbo.get("adresse"));
		poi.setFormattedAddress((String)dbo.get("formattedAddress"));
		poi.setDescription((String)dbo.get("description"));
		poi.setName((String)dbo.get("name"));
		poi.setType((String)dbo.get("type"));
		
		poi.setLatlng(new LatLng());
		poi.getLatlng().setLat((Double) ((DBObject)dbo.get("latlng")).get("lat"));
		poi.getLatlng().setLat((Double) ((DBObject)dbo.get("latlng")).get("lng"));
		return poi;
	}
}