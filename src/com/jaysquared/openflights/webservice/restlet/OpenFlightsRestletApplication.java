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

	public synchronized Restlet createInboundRoot()
	{
		// Create a new restlet router and attach all restlet resources
		Router router = new Router(getContext());
		router.attach("/", RootRessource.class);
		return router;
	}

}
