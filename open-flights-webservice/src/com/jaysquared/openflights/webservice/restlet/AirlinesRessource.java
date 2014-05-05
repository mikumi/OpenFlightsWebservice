/**
 * 
 */
package com.jaysquared.openflights.webservice.restlet;

import java.util.Map;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;
import com.jaysquared.openflights.webservice.ApplicationContext;
import com.jaysquared.openflights.webservice.data.Airline;
import com.jaysquared.openflights.webservice.data.AirlineDatabase;

/**
 * @author michaelkuck
 *
 */
public class AirlinesRessource extends ServerResource {
		
	public static final String URL_ROOT = "/airlines";
	public static final String SUB_RESOURCE_PLACEHOLDER = "sub";
	public static final String[] URLS = {URL_ROOT, urlForRessource(SUB_RESOURCE_PLACEHOLDER)};
	
	public static final String RESOURCE_IDS = "ids";
	public static final String RESOURCE_LIST = "list";

	/**
	 * @return
	 */
	@Get
	public String getRessource()
	{
		final String result;
		final String ressourceSub = (String) getRequest().getAttributes().get(SUB_RESOURCE_PLACEHOLDER);
		if (ressourceSub.equals(RESOURCE_IDS)) {
			result = getAirlineIds();
		} else if (ressourceSub.equals(RESOURCE_LIST)) {
			result = getAirlineList();
		} else {
			result = "Ressource not found";
		}
		return result;
	}
	
	/**
	 * @return
	 */
	private String getAirlineIds() {
		Map<String, String> parameters = getQuery().getValuesMap();
		AirlineDatabase airlineDatabase = ApplicationContext.getInstance().getFlightInformation().getAirlineDatabase();
		int[] airlineIds = airlineDatabase.airlineIdsByFields(parameters);
		
		StringBuilder result = new StringBuilder(50);
		for (int airlineId : airlineIds) {
			result.append(airlineId);
			result.append(",");
		}
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(airlineIds);
		
		return jsonString;
	}
	
	/**
	 * @return
	 */
	private String getAirlineList() {
		Map<String, String> parameters = getQuery().getValuesMap();
		AirlineDatabase airlineDatabase = ApplicationContext.getInstance().getFlightInformation().getAirlineDatabase();
		Airline[] airlines = airlineDatabase.airlinesByFields(parameters);
			
		Gson gson = new Gson();
		String jsonString = gson.toJson(airlines);
		
		return jsonString;
	}
	
	/**
	 * @param subRessourceName
	 * @return
	 */
	private static String urlForRessource(String subRessourceName) {
		return URL_ROOT + "/{" + subRessourceName + "}";
	}
	
}
