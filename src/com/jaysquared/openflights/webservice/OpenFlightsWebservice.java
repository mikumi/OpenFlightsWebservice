package com.jaysquared.openflights.webservice;


import org.restlet.Component;
import org.restlet.data.Protocol;

import com.jaysquared.openflights.webservice.restlet.OpenFlightsRestletApplication;
import com.michael_kuck.commons.Log;

/**
 * 
 */

/**
 * @author michaelkuck
 * 
 */
public class OpenFlightsWebservice {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		final Settings settings = CmdLineParser.parseArguments(args);

		final String applicationInfoLine1 = ReleaseConfiguration.APPLICATION_NAME + " v"
				+ Version.fullVersionAsString();
		final String applicationInfoLine2 = "Contact: " + ReleaseConfiguration.APPLICATION_SUPPORT_EMAIL;
		Log.info("**********************************************************************");
		Log.info("* " + applicationInfoLine1);
		Log.info("* " + applicationInfoLine2);
		Log.info("**********************************************************************");
		Log.info("");

		// Add a new HTTP server listening on port xxx and attach the boarding pass web service
		final Component component = new Component();
		component.getServers().add(Protocol.HTTP, settings.getWebservicePortHTTP());
		component.getDefaultHost().attach("/", new OpenFlightsRestletApplication());
		component.start();
	}

}
