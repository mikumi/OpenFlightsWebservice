/**
 * 
 */
package com.jaysquared.openflights.webservice.restlet;

import java.util.ArrayList;
import java.util.HashMap;
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
	public static final String[] URLS = { URL_ROOT, urlForRessource(SUB_RESOURCE_PLACEHOLDER) };

	public static final String RESOURCE_INDEX = "index";

	/**
	 * @return
	 */
	@Get("json")
	public String getRessource()
	{
		final String result;
		final String ressourceSub = (String) this.getRequest().getAttributes().get(SUB_RESOURCE_PLACEHOLDER);
		if (ressourceSub == null) {
			result = this.getAirlines();
		} else if (ressourceSub.equals(RESOURCE_INDEX)) {
			result = this.getAirlineIndex();
		} else {
			result = "Ressource not found";
		}
		return result;
	}

	/**
	 * @return
	 */
	private String getAirlines()
	{
		final Map<String, String> parameters = this.getQuery().getValuesMap();
		final AirlineDatabase airlineDatabase = ApplicationContext.getInstance().getFlightInformation()
				.getAirlineDatabase();
		final Airline[] airlines = airlineDatabase.airlinesByFields(parameters);

		final Gson gson = new Gson();
		final String jsonString = gson.toJson(airlines);

		return jsonString;
	}

	/**
	 * @return
	 */
	private String getAirlineIndex()
	{
		// Get data
		final Map<String, String> parameters = this.getQuery().getValuesMap();
		final AirlineDatabase airlineDatabase = ApplicationContext.getInstance().getFlightInformation()
				.getAirlineDatabase();
		final Map<String, ArrayList<String>> airlineIndex = airlineDatabase.airlineIndexByFields(parameters);

		// Create json object
		ArrayList<Object> jsonObject = new ArrayList<Object>();
		for (Map.Entry<String, ArrayList<String>> entry : airlineIndex.entrySet()) {
			final String airlineCode = entry.getKey();
			for (String airlineName : entry.getValue()) {
				Map<String, Object> jsonEntry = new HashMap<String, Object>();
				jsonEntry.put(AirlineDatabase.FIELD_IATA, airlineCode);
				jsonEntry.put(AirlineDatabase.FIELD_NAME, airlineName);
				jsonObject.add(jsonEntry);
			}
		}
		
		// convert json to string
		final Gson gson = new Gson();
		final String jsonString = gson.toJson(jsonObject);

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
