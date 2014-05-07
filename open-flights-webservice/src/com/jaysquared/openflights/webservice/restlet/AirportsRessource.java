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
			result = this.getAirports();
		} else if (ressourceSub.equals(RESOURCE_INDEX)) {
			result = this.getAirportIndex();
		} else {
			result = "Ressource not found";
		}
		return result;
	}

	/**
	 * @return
	 */
	private String getAirports()
	{
		final Map<String, String> parameters = this.getQuery().getValuesMap();
		final AirportDatabase airportDatabase = ApplicationContext.getInstance().getFlightInformation()
				.getAirportDatabase();
		final Airport[] airports = airportDatabase.airportsByFields(parameters);

		final Gson gson = new Gson();
		final String jsonString = gson.toJson(airports);

		return jsonString;
	}

	/**
	 * @return
	 */
	private String getAirportIndex()
	{
		// Get Data
		final Map<String, String> parameters = this.getQuery().getValuesMap();
		final AirportDatabase airportDatabase = ApplicationContext.getInstance().getFlightInformation()
				.getAirportDatabase();
		final Map<String, ArrayList<String>> airportIndex = airportDatabase.airportIndexByFields(parameters);

		// Create json object
		ArrayList<Object> jsonObject = new ArrayList<Object>();
		for (Map.Entry<String, ArrayList<String>> entry : airportIndex.entrySet()) {
			final String airportCode = entry.getKey();
			for (String airportName : entry.getValue()) {
				Map<String, Object> jsonEntry = new HashMap<String, Object>();
				jsonEntry.put(AirportDatabase.FIELD_IATA_FAA, airportCode);
				jsonEntry.put(AirportDatabase.FIELD_NAME, airportName);
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
