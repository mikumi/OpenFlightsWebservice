package com.jaysquared.openflights.webservice.restlet;

/**
 * 
 */

import java.util.Map;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;
import com.jaysquared.openflights.webservice.ApplicationContext;
import com.jaysquared.openflights.webservice.data.Route;
import com.jaysquared.openflights.webservice.data.RouteDatabase;

/**
 * @author michaelkuck
 * 
 */
public class RouteListRessource extends ServerResource {

	@Get
	public String getRessource()
	{
		Map<String, String> parameters = getQuery().getValuesMap();
		RouteDatabase routeDatabase = ApplicationContext.getInstance().getFlightInformation().getRouteDatabase();
		Route[] routes = routeDatabase.routesByFields(parameters);
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(routes);
		
		return jsonString;
	}

}
