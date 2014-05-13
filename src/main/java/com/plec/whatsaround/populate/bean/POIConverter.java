package com.plec.whatsaround.populate.bean;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.appengine.repackaged.org.codehaus.jackson.map.ObjectMapper;
import com.google.appengine.repackaged.org.codehaus.jackson.type.TypeReference;
import com.mongodb.DBObject;

public class POIConverter {
	private static final Logger LOGGER = Logger.getLogger(POIConverter.class);
	
	public static POI convertJsonStringToPoi(String jsonPoi) {
		LOGGER.info("converting poi from string " + jsonPoi);
		try {
			ObjectMapper mapper = new ObjectMapper();
			POI poi = mapper.readValue(jsonPoi, POI.class);
			return poi;
		} catch (Exception e) {
			LOGGER.error("erreur while connectiong to mongodb " + e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static POI convertBufferedRederToPoi(BufferedReader reader) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<POI> poi = mapper.readValue(reader, new TypeReference<List<POI>>() {
			});
			return poi.get(0);
		} catch (Exception e) {
			LOGGER.error("erreur while connectiong to mongodb " + e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<POI> convertJsonMongoCommandStringToListPoi(String json) {
		List<POI> pois = new ArrayList<POI>();
		try {
			JSONObject jsonObject = (JSONObject) new JSONParser().parse(json);
			if (isMongoCommandeOk(jsonObject.get("ok"))) {
				List<JSONObject> results = (List<JSONObject>) jsonObject.get("results");
				for (JSONObject currentObject : results) {
					pois.add(POIConverter.convertJsonStringToPoi(((JSONObject) currentObject.get("obj")).toJSONString()));
				}
				LOGGER.info("nb results " + pois.size());
			}
		} catch (Exception e) {
			LOGGER.error("Error while converting poi :" + e.getMessage(), e);
			LOGGER.error("Return empty results list:");
		}
		return pois;
	}
	public static POI convertDBObjectToPoi(DBObject dbo) {
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
	public static List<POI> convertBufferedRederToListPoi(BufferedReader reader) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<POI> poi = mapper.readValue(reader, new TypeReference<List<POI>>() {
			});
			return poi;
		} catch (Exception e) {
			LOGGER.error("erreur while reading response content " + e.getMessage(), e);
			return new ArrayList<POI>();
		}
	}
	private static boolean isMongoCommandeOk(Object okValue) {
		if (okValue instanceof Boolean) {
			return (Boolean) okValue;
		} else if (okValue instanceof Number) {
			return ((Number) okValue).intValue() == 1;
		} else {
			return false;
		}
	}
}
