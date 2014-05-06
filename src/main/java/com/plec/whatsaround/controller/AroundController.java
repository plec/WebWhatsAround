package com.plec.whatsaround.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plec.whatsaround.populate.bean.POI;
import com.plec.whatsaround.service.POIService;
/**
 * Handles requests for the application search page.
 */
@Controller
public class AroundController {
	private static final Logger LOGGER = Logger.getLogger(AroundController.class);

	private POIService poiService;
	
	public AroundController() {
		LOGGER.info("CREATE");
	}

	/**
	 * Page call with HTTP GET method : return the search page without results
	 */
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public String init() {
		LOGGER.info("INIT");
		return "init";
	}
	@RequestMapping(value = "/getPoi/{lat}/{lng}/{radius}", method = RequestMethod.GET)
	public @ResponseBody List<POI> getPois(@PathVariable String lat, @PathVariable String lng, @PathVariable String radius) {
		LOGGER.info("GET POIs");
		return poiService.getPoiNearMe(Double.parseDouble(lat), Double.parseDouble(lng), Integer.parseInt(radius));
	}

	public void setPoiService(POIService poiService) {
		this.poiService = poiService;
	}

}
