package com.plec.whatsaround.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(value = "/searchPoisNearMe", method = RequestMethod.GET)
	public @ResponseBody
	List<POI> searchPoisNearMe(@RequestParam("lat") String lat, @RequestParam("lng") String lng, @RequestParam("radius") String radius) {
		LOGGER.info("GET POIs near " + lat + "/" + lng + " radius :" + radius);
		return poiService.getPoisNearMe(Double.parseDouble(lat), Double.parseDouble(lng), Integer.parseInt(radius));
		//return poiService.getPoisNearMeMock();
	}

	@RequestMapping(value = "/searchPoisByName", method = RequestMethod.GET)
	public @ResponseBody
	List<POI> searchPoisByName(@RequestParam("q") String q) {
		LOGGER.info("search POIs like '" + q + "'");
		return poiService.searchPoisByName(q);
		//return poiService.getPoisNearMeMock();
	}

	@RequestMapping(value = "/getPoi/{id}", method = RequestMethod.GET)
	public @ResponseBody
	POI getPoi(@PathVariable("id") String id) {
		LOGGER.info("GET POI by id " + id);
		return poiService.getPoi(id);
	}

	public void setPoiService(POIService poiService) {
		this.poiService = poiService;
	}

}
