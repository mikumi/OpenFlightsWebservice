/**
 * 
 */
package com.jaysquared.openflights.webservice.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jaysquared.openflights.webservice.dbconnection.MySqlConnectionManager;
import com.michael_kuck.commons.Log;

/**
 * @author michaelkuck
 * 
 */
public class AirlineDatabase {

	final private static String TABLE_NAME_AIRLINES = "of_airlines";
	final private static String FIELD_NAME_AIRLINE_ID = "airline_id";
	final private static String FIELD_NAME_NAME = "name";
	final private static String FIELD_NAME_ALIAS = "alias";
	final private static String FIELD_NAME_IATA = "iata";
	final private static String FIELD_NAME_ICAO = "icao";
	final private static String FIELD_NAME_CALLSIGN = "callsign";
	final private static String FIELD_NAME_COUNTRY = "country";
	final private static String FIELD_NAME_ACTIVE = "active";

	final private MySqlConnectionManager connectionManager;

	/**
	 * 
	 */
	public AirlineDatabase(final MySqlConnectionManager connectionManager)
	{
		this.connectionManager = connectionManager;
	}

	public Airline airlineByIata(final String iata)
	{
		final Connection connection = this.connectionManager.connectionFromPool();

		Airline airline = null;
		try {
			final PreparedStatement statement = connection.prepareStatement("SELECT " + FIELD_NAME_AIRLINE_ID + ","
					+ FIELD_NAME_NAME + "," + FIELD_NAME_ALIAS + "," + FIELD_NAME_IATA + "," + FIELD_NAME_ICAO + ","
					+ FIELD_NAME_CALLSIGN + "," + FIELD_NAME_COUNTRY + "," + FIELD_NAME_ACTIVE + " FROM "
					+ TABLE_NAME_AIRLINES + " WHERE " + FIELD_NAME_IATA + "=?");
			statement.setString(1, iata);
			Log.verbose("Executing SQL statement: " + statement.toString());
			final ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				final int resultAirlineId = resultSet.getInt(FIELD_NAME_AIRLINE_ID);
				final String resultName = resultSet.getString(FIELD_NAME_NAME);
				final String resultAlias = resultSet.getString(FIELD_NAME_ALIAS);
				final String resultIata = resultSet.getString(FIELD_NAME_IATA);
				final String resultIcao = resultSet.getString(FIELD_NAME_ICAO);
				final String resultCallsign = resultSet.getString(FIELD_NAME_CALLSIGN);
				final String resultCountry = resultSet.getString(FIELD_NAME_COUNTRY);
				final boolean resultActive = (resultSet.getString(FIELD_NAME_ACTIVE) == "Y") ? true : false;
				airline = new Airline(resultAirlineId, resultName, resultAlias, resultIata, resultIcao, resultCallsign,
						resultCountry, resultActive);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}

		this.connectionManager.returnToPool(connection);
		return airline;
	}

}
