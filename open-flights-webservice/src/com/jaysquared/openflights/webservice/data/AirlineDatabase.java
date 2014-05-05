/**
 * 
 */
package com.jaysquared.openflights.webservice.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.jaysquared.openflights.webservice.dbconnection.MySqlConnectionManager;
import com.jaysquared.openflights.webservice.dbconnection.MySqlSelectStatementBuilder;
import com.michael_kuck.commons.Log;

/**
 * @author michaelkuck
 * 
 */
public class AirlineDatabase {

	final private static String TABLE_AIRLINES = "of_airlines";
	final public static String FIELD_AIRLINE_ID = "airline_id";
	final public static String FIELD_NAME = "name";
	final public static String FIELD_ALIAS = "alias";
	final public static String FIELD_IATA = "iata";
	final public static String FIELD_ICAO = "icao";
	final public static String FIELD_CALLSIGN = "callsign";
	final public static String FIELD_COUNTRY = "country";
	final public static String FIELD_ACTIVE = "active";

	final private MySqlConnectionManager connectionManager;

	/**
	 * 
	 */
	public AirlineDatabase(final MySqlConnectionManager connectionManager)
	{
		this.connectionManager = connectionManager;
	}

	/**
	 * @param field
	 * @param value
	 */
	public Airline[] airlineByField(String field, String value)
	{
		final Connection connection = this.connectionManager.connectionFromPool();

		ArrayList<Airline> airlines = new ArrayList<Airline>();
		try {
			// Prepare statement
			final MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder(TABLE_AIRLINES);
			statementBuilder.addSelectFields(new String[] { FIELD_AIRLINE_ID, FIELD_NAME, FIELD_ALIAS, FIELD_IATA,
					FIELD_ICAO, FIELD_CALLSIGN, FIELD_COUNTRY, FIELD_ACTIVE });
			Log.verbose(statementBuilder.getStatement());
			statementBuilder.addWhereFieldWithPlaceholder(field);
			final PreparedStatement statement = connection.prepareStatement(statementBuilder.getStatement());
			statement.setString(1, value);
			Log.verbose("Executing SQL statement: " + statement.toString());
			
			// Execute statement and parse results
			final ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				final int resultAirlineId = resultSet.getInt(FIELD_AIRLINE_ID);
				final String resultName = resultSet.getString(FIELD_NAME);
				final String resultAlias = resultSet.getString(FIELD_ALIAS);
				final String resultIata = resultSet.getString(FIELD_IATA);
				final String resultIcao = resultSet.getString(FIELD_ICAO);
				final String resultCallsign = resultSet.getString(FIELD_CALLSIGN);
				final String resultCountry = resultSet.getString(FIELD_COUNTRY);
				final boolean resultActive = (resultSet.getString(FIELD_ACTIVE) == "Y") ? true : false;
				Airline airline = new Airline(resultAirlineId, resultName, resultAlias, resultIata, resultIcao, resultCallsign,
						resultCountry, resultActive);
				airlines.add(airline);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}

		this.connectionManager.returnToPool(connection);
		return airlines.toArray(new Airline[airlines.size()]);
	}

	/**
	 * @param fields
	 * @return
	 */
	public Airline airlineByFields(HashMap<String, String> fields)
	{

		return null;
	}

}
