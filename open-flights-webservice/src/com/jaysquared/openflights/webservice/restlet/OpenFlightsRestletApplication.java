package com.jaysquared.openflights.webservice.restlet;

/**
 * 
 */

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * @author michaelkuck
 * 
 */
public class OpenFlightsRestletApplication extends Application {

	@Override
	public synchronized Restlet createInboundRoot()
	{
		// Create a new restlet router and attach all restlet resources
		final Router router = new Router(this.getContext());
		// Airlines
		for (String url : AirlinesRessource.URLS) {
			router.attach(url, AirlinesRessource.class);
		}
		// Airports
		for (String url : AirportsRessource.URLS) {
			router.attach(url, AirportsRessource.class);
		}
		// Routes
		for (String url : RoutesRessource.URLS) {
			router.attach(url, RoutesRessource.class);
		}
		// Info
		router.attach("/info", InfoRessource.class);
		return router;
	}

}
