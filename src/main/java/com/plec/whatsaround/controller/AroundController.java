package com.plec.whatsaround.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application search page.
 */
@Controller
public class AroundController {
	private static final Logger LOGGER = Logger.getLogger(AroundController.class);

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
}
