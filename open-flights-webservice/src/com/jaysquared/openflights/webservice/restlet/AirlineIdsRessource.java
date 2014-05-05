package com.jaysquared.openflights.webservice.restlet;

/**
 * 
 */

import java.util.Map;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jaysquared.openflights.webservice.ApplicationContext;
import com.jaysquared.openflights.webservice.data.AirlineDatabase;

/**
 * @author michaelkuck
 * 
 */
public class AirlineIdsRessource extends ServerResource {

	@Get
	public String getRessource()
	{
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

}
