package com.plec.whatsaround.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.appengine.repackaged.org.codehaus.jackson.map.ObjectMapper;
import com.google.appengine.repackaged.org.codehaus.jackson.type.TypeReference;
import com.plec.whatsaround.populate.bean.POI;

public class HttpPOIDao implements IPoiDao {
	private static final Logger LOGGER = Logger.getLogger(HttpPOIDao.class);
	
	private String API_KEY = "apiKey=i8ByvoJgp_YD3RaBhA05w4IyGSwPJ3eu";
	
	private static final int DEFAULT_LIMIT = 100;
	
	private int limit = DEFAULT_LIMIT;
	private static final String MONGO_REST_API_URL = "https://api.mongolab.com/api/1/databases/whatsaroundnextbase/collections/POI";

	@Override
	public POI getPoiByID(String id) {
		StringBuilder  query = new StringBuilder();
		query.append(MONGO_REST_API_URL);
		query.append("?q={\"sourceId\":");
		query.append("\"");
		query.append(id);
		query.append("\"");
		query.append("}&");
		query.append(API_KEY);
		BufferedReader response = callHttpRestMongoApi(query.toString());
		return convertOnePOI(response);
		
	}
	
	@Override
	public List<POI> getPoiNearPoint(double lat, double lng, int radius) {
		// {"latlng": {"$geoWithin": {"$centerSphere" : [LAT, LNG], radius/3959}}}
		StringBuilder  query = new StringBuilder();
		query.append(MONGO_REST_API_URL);
		query.append("?q={\"latlng\":{\"$geoWithin\":{\"$centerSphere\":[[");
		query.append(lat);
		query.append(",");
		query.append(lng);
		query.append("],");
		query.append(radius/3959.0);
		query.append("]}}}&");
		query.append(API_KEY);
		return convertPOIs(callHttpRestMongoApi(query.toString()));
	}
	
	@Override
	public List<POI> searchPoisByName(String query) {
		try  {
			StringBuilder  queryUrl = new StringBuilder();
			queryUrl.append(MONGO_REST_API_URL);
			
			queryUrl.append("?q=");
			StringBuilder jsonStream = new StringBuilder();
			jsonStream.append("{\"text\":\"POI\",");
			jsonStream.append(" \"search\":\"");
			jsonStream.append(query);
			jsonStream.append("\",");
			jsonStream.append("\"language\":\"french\",");
			jsonStream.append("\"limit\":\"");
			jsonStream.append(limit);
			jsonStream.append("\"");
			jsonStream.append("}");
			queryUrl.append(URLEncoder.encode(jsonStream.toString(), "UTF-8"));
			queryUrl.append("&");
			queryUrl.append(API_KEY);
			BufferedReader response = callHttpRestMongoApi(queryUrl.toString());
			return convertPOIs(response);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	private BufferedReader callHttpRestMongoApi(String mongoUrl) {
		try {
			LOGGER.info("Call mongoDB with url " + mongoUrl);
			URL url = new URL(mongoUrl);
			LOGGER.info("Url created, open connection");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			LOGGER.info("open connection, getting return code");
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(conn.getInputStream()));
				return in;
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("erreur while connectiong to mongodb " + e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	private POI convertOnePOI(BufferedReader reader) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<POI> poi = mapper.readValue(reader, new TypeReference<List<POI>>(){});
			return poi.get(0);
		} catch (Exception e) {
			LOGGER.error("erreur while connectiong to mongodb " + e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	private List<POI> convertPOIs(BufferedReader reader) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<POI> poi = mapper.readValue(reader, new TypeReference<List<POI>>(){});
			return poi;
		} catch (Exception e) {
			LOGGER.error("erreur while connectiong to mongodb " + e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
}
