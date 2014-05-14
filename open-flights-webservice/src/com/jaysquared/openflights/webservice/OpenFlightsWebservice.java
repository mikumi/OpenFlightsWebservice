package com.jaysquared.openflights.webservice;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.michael_kuck.commons.mysql.MySqlHelper;
import org.restlet.Component;
import org.restlet.data.Protocol;

import com.jaysquared.openflights.webservice.data.FlightInformation;
import com.jaysquared.openflights.webservice.dbconnection.DatabaseInformation;
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

    static BoneCP connectionPool = null;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception
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

        // Set up MySQL connection pool
        BoneCPConfig config = new BoneCPConfig();
        final String url = MySqlHelper.getUrl(settings.getSqlHost(), settings.getSqlPort(), settings.getSqlDatabase());
        config.setJdbcUrl(url);
        config.setUsername(settings.getSqlUser());
        config.setPassword(settings.getSqlPassword());
        config.setMinConnectionsPerPartition(5);
        config.setMaxConnectionsPerPartition(10);
        config.setPartitionCount(1);
        connectionPool = new BoneCP(config);

		final FlightInformation flightInformation = new FlightInformation(connectionPool);
		final DatabaseInformation databaseInformation = new DatabaseInformation(connectionPool);
		ApplicationContext.getInstance().init(flightInformation, databaseInformation);

		// Add a new HTTP server listening on port xxx and attach the boarding pass web service
		final Component component = new Component();
		component.getServers().add(Protocol.HTTP, settings.getWebservicePortHTTP());
		component.getDefaultHost().attach("/rest", new OpenFlightsRestletApplication());
		component.start();

	}

    @Override
    protected void finalize() throws Throwable {
        // Shutdown server
        Log.info("Server is shutting down...");
        connectionPool.shutdown();
    }
}
