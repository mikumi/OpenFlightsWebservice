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
//		router.attach("/airlines", AirlineIdsRessource.class);
		router.attach("/airlines/ids", AirlineIdsRessource.class);
		router.attach("/airlines/list", AirlineListRessource.class);
		router.attach("/airports/ids", AirportIdsRessource.class);
		router.attach("/airports/list", AirportListRessource.class);
		router.attach("/routes/list", RouteListRessource.class);
		router.attach("/info", InfoRessource.class);
		return router;
	}

}
