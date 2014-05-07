/**
 * 
 */
package com.jaysquared.openflights.webservice.restlet;

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
public class RoutesRessource extends ServerResource {

	public static final String URL_ROOT = "/routes";
	public static final String SUB_RESOURCE_PLACEHOLDER = "sub";
	public static final String[] URLS = { URL_ROOT, urlForRessource(SUB_RESOURCE_PLACEHOLDER) };

	/**
	 * @return
	 */
	@Get
	public String getRessource()
	{
		final String result;
		final String ressourceSub = (String) this.getRequest().getAttributes().get(SUB_RESOURCE_PLACEHOLDER);
		if (ressourceSub == null) {
			result = this.getRoutes();
		} else {
			result = "Ressource not found";
		}
		return result;
	}

	/**
	 * @return
	 */
	private String getRoutes()
	{
		final Map<String, String> parameters = this.getQuery().getValuesMap();
		final RouteDatabase routeDatabase = ApplicationContext.getInstance().getFlightInformation().getRouteDatabase();
		final Route[] routes = routeDatabase.routesByFields(parameters);

		final Gson gson = new Gson();
		final String jsonString = gson.toJson(routes);

		return jsonString;
	}

	/**
	 * @param subRessourceName
	 * @return
	 */
	private static String urlForRessource(final String subRessourceName)
	{
		return URL_ROOT + "/{" + subRessourceName + "}";
	}

}
