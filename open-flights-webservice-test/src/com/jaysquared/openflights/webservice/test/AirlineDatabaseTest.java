/**
 *
 */
package com.jaysquared.openflights.webservice.test;

import com.jaysquared.openflights.webservice.data.Airline;
import com.jaysquared.openflights.webservice.data.AirlineDatabase;
import com.jolbox.bonecp.BoneCP;
import com.michael_kuck.commons.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author michaelkuck
 */
public class AirlineDatabaseTest {

    BoneCP connectionManager;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        Log.setLogLevel(Log.Level.VERBOSE);
        final String host = MySqlDatabaseSettings.host;
        final int port = MySqlDatabaseSettings.port;
        final String username = MySqlDatabaseSettings.username;
        final String password = MySqlDatabaseSettings.password;
        final String database = MySqlDatabaseSettings.database;
//        connectionManager = new MySqlConnectionManager(host, port, username, password, database);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for .
     */
    @Test
    public final void testAirlineIdsByField() {
        AirlineDatabase airlineDatabase = new AirlineDatabase(connectionManager);
        int[] airlineIds = airlineDatabase.airlineIdsByField(AirlineDatabase.FIELD_IATA, "LH");
        Log.verbose("Ids found: " + airlineIds.length);
        assertTrue("No airlineIds returned", (airlineIds.length >= 1));
        for (int airlineId : airlineIds) {
            assertTrue("Airline ID not valid", (airlineId == 3320) || (airlineId == 3321));
            Log.debug("Airline id: " + airlineId);
        }
    }

    /**
     * Test method for .
     */
    @Test
    public final void testAirlinesByField() {
        AirlineDatabase airlineDatabase = new AirlineDatabase(connectionManager);
        Airline[] airlines = airlineDatabase.airlinesByField(AirlineDatabase.FIELD_IATA, "LH");
        Log.verbose("Airlines found: " + airlines.length);
        assertTrue("No airlines returned", (airlines.length >= 1));
        for (Airline airline : airlines) {
            assertTrue("IATA code didn't match.", airline.getIata().equals("LH"));
            Log.debug("Airline: " + airline.toString());
        }
    }

}
