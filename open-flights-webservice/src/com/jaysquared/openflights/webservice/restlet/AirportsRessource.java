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

/**
 * @author michaelkuck
 *
 */
public class AirportsRessource extends ServerResource {
		
	public static final String URL_ROOT = "/airports";
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
		if (ressourceSub == null) {
			result = getAirports();
		} else if (ressourceSub.equals(RESOURCE_IDS)) {
			result = getAirportIds();
		} else if (ressourceSub.equals(RESOURCE_LIST)) {
			result = getAirportList();
		} else {
			result = "Ressource not found";
		}
		return result;
	}
	
	/**
	 * @return
	 */
	private String getAirportIds() {
		Map<String, String> parameters = getQuery().getValuesMap();
		AirportDatabase airportDatabase = ApplicationContext.getInstance().getFlightInformation().getAirportDatabase();
		int[] airportIds = airportDatabase.airportIdsByFields(parameters);
		
		StringBuilder result = new StringBuilder(50);
		for (int airportId : airportIds) {
			result.append(airportId);
			result.append(",");
		}
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(airportIds);
		
		return jsonString;
	}
	
	/**
	 * @return
	 */
	private String getAirports() {
		Map<String, String> parameters = getQuery().getValuesMap();
		AirportDatabase airportDatabase = ApplicationContext.getInstance().getFlightInformation().getAirportDatabase();
		Airport[] airports = airportDatabase.airportsByFields(parameters);
			
		Gson gson = new Gson();
		String jsonString = gson.toJson(airports);
		
		return jsonString;
	}
	
	/**
	 * @return
	 */
	private String getAirportList()
	{
		Map<String, String> parameters = getQuery().getValuesMap();
		AirportDatabase airportDatabase = ApplicationContext.getInstance().getFlightInformation().getAirportDatabase();
		Map<String,ArrayList<String>> airportNames = airportDatabase.airportListByFields(parameters);
			
		Gson gson = new Gson();
		String jsonString = gson.toJson(airportNames);
		
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
