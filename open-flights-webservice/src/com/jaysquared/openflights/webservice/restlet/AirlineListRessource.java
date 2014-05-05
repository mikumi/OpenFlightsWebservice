package com.jaysquared.openflights.webservice.restlet;

/**
 * 
 */

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
public class AirlineListRessource extends ServerResource {

	@Get
	public String getRessource()
	{
		Map<String, String> parameters = getQuery().getValuesMap();
		AirlineDatabase airlineDatabase = ApplicationContext.getInstance().getFlightInformation().getAirlineDatabase();
		Airline[] airlines = airlineDatabase.airlinesByFields(parameters);
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(airlines);
		
		return jsonString;
	}

}
