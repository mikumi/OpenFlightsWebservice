package com.jaysquared.openflights.webservice.restlet;

/**
 * 
 */

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.jaysquared.openflights.webservice.ReleaseConfiguration;
import com.jaysquared.openflights.webservice.Version;

/**
 * @author michaelkuck
 * 
 */
public class AirlineRessource extends ServerResource {

	@Get
	public String getRessource()
	{
		final String line1 = ReleaseConfiguration.APPLICATION_NAME + " v" + Version.fullVersionAsString() + "\n";
		final String line2 = "\n";
		final String line3 = "Source: http://openflights.org/data.html#license\n";
		final String line4 = "\n";
		final String line5 = "The OpenFlights Airport, Airline and Route Databases are made available under the Open Database License. Any rights in individual contents of the database are licensed under the Database Contents License.\n";
		final String page = line1 + line2 + line3 + line4 + line5;
		return page;
	}

}
