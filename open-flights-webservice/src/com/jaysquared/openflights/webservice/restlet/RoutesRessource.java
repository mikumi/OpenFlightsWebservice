/**
 * 
 */
package com.jaysquared.openflights.webservice.restlet;

import java.util.ArrayList;
import java.util.Map;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;
import com.jaysquared.openflights.webservice.ApplicationContext;
import com.jaysquared.openflights.webservice.data.Airline;
import com.jaysquared.openflights.webservice.data.AirlineDatabase;
import com.jaysquared.openflights.webservice.data.Airport;
import com.jaysquared.openflights.webservice.data.AirportDatabase;
import com.jaysquared.openflights.webservice.data.Route;
import com.jaysquared.openflights.webservice.data.RouteDatabase;

/**
 * @author michaelkuck
 *
 */
public class RoutesRessource extends ServerResource {
		
	public static final String URL_ROOT = "/routes";
	public static final String SUB_RESOURCE_PLACEHOLDER = "sub";
	public static final String[] URLS = {URL_ROOT, urlForRessource(SUB_RESOURCE_PLACEHOLDER)};
	
//	public static final String RESOURCE_LIST = "list";

	/**
	 * @return
	 */
	@Get
	public String getRessource()
	{
		final String result;
		final String ressourceSub = (String) getRequest().getAttributes().get(SUB_RESOURCE_PLACEHOLDER);
		if (ressourceSub == null) {
			result = getRoutes();
//		} else if (ressourceSub.equals(RESOURCE_LIST)) {
//			result = getRouteList();
		} else {
			result = "Ressource not found";
		}
		return result;
	}
	
	/**
	 * @return
	 */
	private String getRoutes() {
		Map<String, String> parameters = getQuery().getValuesMap();
		RouteDatabase routeDatabase = ApplicationContext.getInstance().getFlightInformation().getRouteDatabase();
		Route[] routes = routeDatabase.routesByFields(parameters);
			
		Gson gson = new Gson();
		String jsonString = gson.toJson(routes);
		
		return jsonString;
	}
	
//	/**
//	 * @return
//	 */
//	private String getAirportList()
//	{
//		Map<String, String> parameters = getQuery().getValuesMap();
//		AirportDatabase airportDatabase = ApplicationContext.getInstance().getFlightInformation().getAirportDatabase();
//		Map<String,ArrayList<String>> airportNames = airportDatabase.airportListByFields(parameters);
//			
//		Gson gson = new Gson();
//		String jsonString = gson.toJson(airportNames);
//		
//		return jsonString;
//	}
//	
	/**
	 * @param subRessourceName
	 * @return
	 */
	private static String urlForRessource(String subRessourceName) {
		return URL_ROOT + "/{" + subRessourceName + "}";
	}
	
}
