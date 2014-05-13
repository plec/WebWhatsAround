package com.plec.whatsaround.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.plec.whatsaround.populate.bean.LatLng;
import com.plec.whatsaround.populate.bean.POI;
import com.plec.whatsaround.populate.bean.POIConverter;

public class POIDao implements IPoiDao {
	private static final Logger LOGGER = Logger.getLogger(POIDao.class);

	
	//private String mongoDbUrl = "mongodb://whatsaround:whatsaround@ds029630.mongolab.com:29630/whatsaroundbase";
	private String mongoDbUrl = "mongodb://whatsaround:whatsaround@ds039379.mongolab.com:39379/whatsaroundnextbase";
	
	private String MongoDBCollection = "POI";
	
	private static final int DEFAULT_LIMIT = 100;
	
	private int limit = DEFAULT_LIMIT;
	
	private DB db;
	private DBCollection poiCollection;
	private MongoClient client;
	
	public void init() {
		try {
			LOGGER.info("Init dao !");
			LOGGER.info("mongoDbUrl" + mongoDbUrl);
			MongoClientURI uri  = new MongoClientURI(mongoDbUrl);
			
	        client = new MongoClient(uri);
	        db = client.getDB(uri.getDatabase());
			LOGGER.info("mongoDbCollection" + MongoDBCollection );
	        poiCollection = db.getCollection(MongoDBCollection);
			LOGGER.info("Init dao done !");

		} catch (UnknownHostException uhe) {
			LOGGER.error("Error init poi dao : " + uhe.getMessage(), uhe);
			throw new RuntimeException(uhe.getMessage(), uhe);
		}
	}
	/* (non-Javadoc)
	 * @see com.plec.whatsaround.dao.IPoiDao#getPoiByID(java.lang.String)
	 */
	@Override
	public POI getPoiByID(String id) {
		LOGGER.info("Search POI by source ID : " + id); 
		DBObject result = poiCollection.findOne(new BasicDBObject("sourceId", id));
		if (result != null) {
			LOGGER.info("POI found: " + id); 
			return POIConverter.convertDBObjectToPoi(result);
		}
		LOGGER.warn("no POI match ! "); 
		return null;
	}
	/* (non-Javadoc)
	 * @see com.plec.whatsaround.dao.IPoiDao#searchPoisByName(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<POI> searchPoisByName(String query) {
		LOGGER.info("Search POI by name or description match : " + query);
		List<POI> pois = new ArrayList<POI>();
		long l = System.currentTimeMillis();
		
		final DBObject textSearchCommand = new BasicDBObject();
	    textSearchCommand.put("text", "POI");
	    textSearchCommand.put("search", query);
	    textSearchCommand.put("language", "french");
	    textSearchCommand.put("limit", limit);
	    final CommandResult commandResult = db.command(textSearchCommand);
	    if (!commandResult.ok()) {
			List<DBObject> results = (List<DBObject>) commandResult.get("results");
	    	if (results.size() > 0) {
		    	LOGGER.info("Request executed on MongoDB on " + (System.currentTimeMillis() - l) + "ms " + results.size() + " results");
		    	for (DBObject curPoi : results) {
		    		pois.add(POIConverter.convertDBObjectToPoi(curPoi));
		    	}
	    	}
	    } else {
	    	LOGGER.warn("no POI match ! ");
	    }
    	return pois;
	}
	/* (non-Javadoc)
	 * @see com.plec.whatsaround.dao.IPoiDao#getPoiNearPoint(double, double, int)
	 */
	@Override
	public List<POI> getPoiNearPoint(double lat, double lng, int radius) {
		this.init();
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
        		)).limit(limit);
		if (result.count() > 0) {
			LOGGER.info("Request executed on MongoDB on " + (System.currentTimeMillis() - l) + "ms " + result.count() + " results");
			while (result.hasNext()) {
				DBObject curPoi = result.next();
				poisNearMe.add(POIConverter.convertDBObjectToPoi(curPoi));
			}
		}
        LOGGER.info("NB result : " + result.count());
		return poisNearMe;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
	/**
	 * @param mongoDbUrl the mongoDbUrl to set
	 */
	public void setMongoDbUrl(String mongoDbUrl) {
		this.mongoDbUrl = mongoDbUrl;
	}
}
