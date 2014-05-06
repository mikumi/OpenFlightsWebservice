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

/**
 * @author michaelkuck
 * 
 */
public class AirlinesRessource extends ServerResource {

	public static final String URL_ROOT = "/airlines";
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
			result = this.getAirlines();
		} else if (ressourceSub.equals(RESOURCE_IDS)) {
			result = this.getAirlineIds();
		} else if (ressourceSub.equals(RESOURCE_LIST)) {
			result = this.getAirlineList();
		} else {
			result = "Ressource not found";
		}
		return result;
	}

	/**
	 * @return
	 */
	private String getAirlineIds()
	{
		final Map<String, String> parameters = this.getQuery().getValuesMap();
		final AirlineDatabase airlineDatabase = ApplicationContext.getInstance().getFlightInformation()
				.getAirlineDatabase();
		final int[] airlineIds = airlineDatabase.airlineIdsByFields(parameters);

		final StringBuilder result = new StringBuilder(50);
		for (final int airlineId : airlineIds) {
			result.append(airlineId);
			result.append(",");
		}

		final Gson gson = new Gson();
		final String jsonString = gson.toJson(airlineIds);

		return jsonString;
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
	private String getAirlineList()
	{
		final Map<String, String> parameters = this.getQuery().getValuesMap();
		final AirlineDatabase airlineDatabase = ApplicationContext.getInstance().getFlightInformation()
				.getAirlineDatabase();
		final Map<String, ArrayList<String>> airlineNames = airlineDatabase.airlineListByFields(parameters);

		final Gson gson = new Gson();
		final String jsonString = gson.toJson(airlineNames);

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
