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

	public static final String RESOURCE_IDS = "ids";
	public static final String RESOURCE_LIST = "list";

	/**
	 * @return
	 */
	@Get
	public String getRessource()
	{
		final String result;
		final String ressourceSub = (String) this.getRequest().getAttributes().get(SUB_RESOURCE_PLACEHOLDER);
		if (ressourceSub == null) {
			result = this.getAirports();
		} else if (ressourceSub.equals(RESOURCE_IDS)) {
			result = this.getAirportIds();
		} else if (ressourceSub.equals(RESOURCE_LIST)) {
			result = this.getAirportList();
		} else {
			result = "Ressource not found";
		}
		return result;
	}

	/**
	 * @return
	 */
	private String getAirportIds()
	{
		final Map<String, String> parameters = this.getQuery().getValuesMap();
		final AirportDatabase airportDatabase = ApplicationContext.getInstance().getFlightInformation()
				.getAirportDatabase();
		final int[] airportIds = airportDatabase.airportIdsByFields(parameters);

		final StringBuilder result = new StringBuilder(50);
		for (final int airportId : airportIds) {
			result.append(airportId);
			result.append(",");
		}

		final Gson gson = new Gson();
		final String jsonString = gson.toJson(airportIds);

		return jsonString;
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
	private String getAirportList()
	{
		final Map<String, String> parameters = this.getQuery().getValuesMap();
		final AirportDatabase airportDatabase = ApplicationContext.getInstance().getFlightInformation()
				.getAirportDatabase();
		final Map<String, ArrayList<String>> airportNames = airportDatabase.airportListByFields(parameters);

		final Gson gson = new Gson();
		final String jsonString = gson.toJson(airportNames);

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
