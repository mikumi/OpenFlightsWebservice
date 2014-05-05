/**
 * 
 */
package com.jaysquared.openflights.webservice.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jaysquared.openflights.webservice.data.Airline;
import com.jaysquared.openflights.webservice.data.AirlineDatabase;
import com.jaysquared.openflights.webservice.dbconnection.MySqlConnectionManager;
import com.michael_kuck.commons.Log;

/**
 * @author michaelkuck
 *
 */
public class AirlineDatabaseTest {

	MySqlConnectionManager connectionManager;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		Log.setLogLevel(Log.Level.VERBOSE);
		final String host = MySqlDatabaseSettings.host;
		final int port = MySqlDatabaseSettings.port;
		final String username = MySqlDatabaseSettings.username;
		final String password = MySqlDatabaseSettings.password;
		final String database = MySqlDatabaseSettings.database;
		connectionManager = new MySqlConnectionManager(host, port, username, password, database);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}
	
	/**
	 * Test method for {@link com.jaysquared.openflights.webservice.data.AirlineDatabase#airlineByIata(java.lang.String)}.
	 */
	@Test
	public final void testAirlineIdsByField()
	{	
		AirlineDatabase airlineDatabase = new AirlineDatabase(connectionManager);
		int[] airlineIds = airlineDatabase.airlineIdsByField(AirlineDatabase.FIELD_IATA, "LH");
		Log.verbose("Ids found: " + airlineIds.length);
		for (int airlineId : airlineIds) {
			assertTrue("Airline ID not valid", (airlineId == 3320 ) || (airlineId == 3321));
			Log.debug("Airline id: " + airlineId);
		}
	}

	/**
	 * Test method for {@link com.jaysquared.openflights.webservice.data.AirlineDatabase#airlineByIata(java.lang.String)}.
	 */
	@Test
	public final void testAirlinesByField()
	{	
		AirlineDatabase airlineDatabase = new AirlineDatabase(connectionManager);
		Airline[] airlines = airlineDatabase.airlinesByField(AirlineDatabase.FIELD_IATA, "LH");
		Log.verbose("Airlines found: " + airlines.length);
		for (Airline airline : airlines) {
			assertTrue("IATA code didn't match.", airline.getIata().equals("LH"));
			Log.debug("Airline: " + airline.toString());
		}
	}

}
