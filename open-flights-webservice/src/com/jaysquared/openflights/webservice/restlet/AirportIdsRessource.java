package com.jaysquared.openflights.webservice.restlet;

/**
 * 
 */

import java.util.Map;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;
import com.jaysquared.openflights.webservice.ApplicationContext;
import com.jaysquared.openflights.webservice.data.AirportDatabase;

/**
 * @author michaelkuck
 * 
 */
public class AirportIdsRessource extends ServerResource {

	@Get
	public String getRessource()
	{
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

}
