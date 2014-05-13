package com.plec.whatsaround.dao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;

import com.plec.whatsaround.populate.bean.POI;
import com.plec.whatsaround.populate.bean.POIConverter;

public class HttpPOIDao implements IPoiDao {
	private static final Logger LOGGER = Logger.getLogger(HttpPOIDao.class);

	private String API_KEY = "apiKey=i8ByvoJgp_YD3RaBhA05w4IyGSwPJ3eu";

	private static final int DEFAULT_LIMIT = 100;

	private String proxyHost;
	private String proxyPort;

	private Proxy proxy;

	@Override
	public void init() {
		LOGGER.info("Init HttpPOIDao");
		if (proxyPort != null && proxyPort.length() > 0) {
			LOGGER.info("Setting proxy");
			System.setProperty("http.proxyHost", proxyHost);
			System.setProperty("http.proxyPort", proxyPort);
			proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, Integer.parseInt(proxyPort)));
		}
		LOGGER.info("Init HttpPOIDao done");
	}

	private int limit = DEFAULT_LIMIT;
	private static final String MONGO_REST_API_URL = "https://api.mongolab.com/api/1/databases/whatsaroundnextbase/collections/POI";

	@Override
	public POI getPoiByID(String id) {
		StringBuilder query = new StringBuilder();
		query.append(MONGO_REST_API_URL);
		query.append("?q={\"sourceId\":");
		query.append("\"");
		query.append(id);
		query.append("\"");
		query.append("}&");
		query.append(API_KEY);
		BufferedReader response = callGetHttpRestMongoApi(query.toString());
		return POIConverter.convertBufferedRederToPoi(response);

	}

	@Override
	public List<POI> getPoiNearPoint(double lat, double lng, int radius) {
		// {"latlng": {"$geoWithin": {"$centerSphere" : [LAT, LNG],
		// radius/3959}}}
		StringBuilder query = new StringBuilder();
		query.append(MONGO_REST_API_URL);
		query.append("?q={\"latlng\":{\"$geoWithin\":{\"$centerSphere\":[[");
		query.append(lat);
		query.append(",");
		query.append(lng);
		query.append("],");
		query.append(radius / 3959.0);
		query.append("]}}}&");
		query.append(API_KEY);
		query.append("&l=");
		query.append(limit);
		return POIConverter.convertBufferedRederToListPoi(callGetHttpRestMongoApi(query.toString()));
	}

	@Override
	public List<POI> searchPoisByName(String query) {
		try {
			StringBuilder jsonCommandStream = new StringBuilder();
			jsonCommandStream.append("{\"text\":\"POI\",");
			jsonCommandStream.append(" \"search\":\"");
			jsonCommandStream.append(query);
			jsonCommandStream.append("\",");
			jsonCommandStream.append("\"language\":\"french\",");
			jsonCommandStream.append("\"limit\":");
			jsonCommandStream.append(limit);
			jsonCommandStream.append("}");
			String response = callPostHttpRestMongoApi(jsonCommandStream.toString());
			return POIConverter.convertJsonMongoCommandStringToListPoi(response);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	private String callPostHttpRestMongoApi(String data) {
		try {
			String postUrl = "https://api.mongolab.com/api/1/databases/whatsaroundnextbase/runCommand?" + API_KEY;
			LOGGER.info("Call mongoDB with url " + postUrl);
			LOGGER.info("Json data : " + data);
			URL url = new URL(postUrl);
			LOGGER.info("Url created, open connection");
			HttpURLConnection conn = null;
			if (proxy != null) {
				conn = (HttpURLConnection) url.openConnection(proxy);
			} else {
				conn = (HttpURLConnection) url.openConnection(proxy);
			}
			LOGGER.info("Http connection using proxy? " + conn.usingProxy());
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Content-Length", "" + Integer.toString(data.getBytes().length));

			// Send request
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(data);
			wr.flush();
			wr.close();

			// Get response
			InputStream is = conn.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			LOGGER.info("Reponse : " + response.toString());

			return response.toString();
		} catch (Exception e) {
			LOGGER.error("erreur while connectiong to mongodb " + e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private BufferedReader callGetHttpRestMongoApi(String mongoUrl) {
		try {
			LOGGER.info("Call mongoDB with url " + mongoUrl);
			URL url = new URL(mongoUrl);
			LOGGER.info("Url created, open connection");
			HttpURLConnection conn = null;
			if (proxy != null) {
				conn = (HttpURLConnection) url.openConnection(proxy);
			} else {
				conn = (HttpURLConnection) url.openConnection(proxy);
			}
			LOGGER.info("Http connection using proxy? " + conn.usingProxy());
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			LOGGER.info("open connection, getting return code");
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				return in;
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("erreur while connectiong to mongodb " + e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}


	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @param proxyHost
	 *            the proxyHost to set
	 */
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	/**
	 * @param proxyPort
	 *            the proxyPort to set
	 */
	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

}
