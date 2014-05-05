package com.jaysquared.openflights.webservice.restlet;

/**
 * 
 */

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
public class AirportListRessource extends ServerResource {

	@Get
	public String getRessource()
	{
		Map<String, String> parameters = getQuery().getValuesMap();
		AirportDatabase airportDatabase = ApplicationContext.getInstance().getFlightInformation().getAirportDatabase();
		Airport[] airports = airportDatabase.airportsByFields(parameters);

		Gson gson = new Gson();
		String jsonString = gson.toJson(airports);

		return jsonString;
	}

}
