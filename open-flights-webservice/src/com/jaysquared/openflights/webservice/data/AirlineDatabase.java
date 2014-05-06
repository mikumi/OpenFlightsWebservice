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
import java.util.Map;

import com.jaysquared.openflights.webservice.dbconnection.MySqlConnectionManager;
import com.jaysquared.openflights.webservice.dbconnection.MySqlSelectStatementBuilder;
import com.michael_kuck.commons.Log;

/**
 * @author michaelkuck
 * 
 */
public class AirlineDatabase {

	final public static String TABLE_AIRLINES = "of_airlines";

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

	public int[] airlineIdsByField(String field, String value)
	{
		HashMap<String, String> fields = new HashMap<String, String>();
		fields.put(field, value);
		return airlineIdsByFields(fields);
	}

	/**
	 * @param fields
	 * @return
	 */
	public int[] airlineIdsByFields(Map<String, String> fields)
	{
		Log.verbose("airlineIdsByField: " + fields.toString());

		ArrayList<Integer> airlineIds = new ArrayList<Integer>();
		final Connection connection = this.connectionManager.connectionFromPool();
		if (connection == null) {
			Log.error("Could not search for airlines, no database connection.");
		} else {
			try {
				// Prepare statement
				final MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder(TABLE_AIRLINES);
				statementBuilder.addSelectField(FIELD_AIRLINE_ID);
				Log.verbose(statementBuilder.getStatement());
				for (Map.Entry<String, String> entry : fields.entrySet()) {
					statementBuilder.addWhereField(entry.getKey(), entry.getValue());
				}
				final PreparedStatement statement = connection.prepareStatement(statementBuilder.getStatement());
				Log.verbose("Executing SQL statement: " + statement.toString());

				// Execute statement and parse results
				final ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					final int resultAirlineId = resultSet.getInt(FIELD_AIRLINE_ID);
					airlineIds.add(resultAirlineId);
				}
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		}
		this.connectionManager.returnToPool(connection);

		int ids[] = new int[airlineIds.size()];
		for (int i = 0; i < airlineIds.size(); i++) {
			ids[i] = airlineIds.get(i).intValue();
		}
		return ids;
	}
	
	/**
	 * @param field
	 * @param value
	 * @return
	 */
	public Map<String, ArrayList<String>> airlineListByField(String field, String value) {
		HashMap<String, String> fields = new HashMap<String, String>();
		fields.put(field, value);
		return airlineListByFields(fields);
	}
	
	/**
	 * @param fields
	 * @return
	 */
	public Map<String, ArrayList<String>> airlineListByFields(Map<String,String> fields) {
		Log.verbose("airlineListByField: " + fields.toString());

		HashMap<String,ArrayList<String>> airlineList = new HashMap<String,ArrayList<String>>();
		final Connection connection = this.connectionManager.connectionFromPool();
		if (connection == null) {
			Log.error("Could not search for airlines, no database connection.");
		} else {
			try {
				// Prepare statement
				final MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder(TABLE_AIRLINES);
				statementBuilder.addSelectField(FIELD_NAME);
				statementBuilder.addSelectField(FIELD_IATA);
				Log.verbose(statementBuilder.getStatement());
				for (Map.Entry<String, String> entry : fields.entrySet()) {
					statementBuilder.addWhereField(entry.getKey(), entry.getValue());
				}
				final PreparedStatement statement = connection.prepareStatement(statementBuilder.getStatement());
				Log.verbose("Executing SQL statement: " + statement.toString());

				// Execute statement and parse results
				final ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					final String resultAirlineName = resultSet.getString(FIELD_NAME);
					final String resultAirlineIata = resultSet.getString(FIELD_IATA);
					ArrayList<String> airlines = airlineList.get(resultAirlineIata);
					if (airlines == null) {
						airlines = new ArrayList<String>();
						airlineList.put(resultAirlineIata, airlines);
					}
					airlines.add(resultAirlineName);
				}
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		}
		this.connectionManager.returnToPool(connection);

		return airlineList;
	}

	/**
	 * @param field
	 * @param value
	 */
	public Airline[] airlinesByField(String field, String value)
	{
		HashMap<String, String> fields = new HashMap<String, String>();
		fields.put(field, value);
		return airlinesByFields(fields);
	}

	/**
	 * @param fields
	 * @return
	 */
	public Airline[] airlinesByFields(Map<String, String> fields)
	{
		Log.verbose("airlinesByField: " + fields.toString());

		ArrayList<Airline> airlines = new ArrayList<Airline>();
		final Connection connection = this.connectionManager.connectionFromPool();
		if (connection == null) {
			Log.error("Could not search for airlines, no database connection.");
		} else {
			try {
				// Prepare statement
				final MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder(TABLE_AIRLINES);
				statementBuilder.addSelectFields(new String[] { FIELD_AIRLINE_ID, FIELD_NAME, FIELD_ALIAS, FIELD_IATA,
						FIELD_ICAO, FIELD_CALLSIGN, FIELD_COUNTRY, FIELD_ACTIVE });
				Log.verbose(statementBuilder.getStatement());
				for (Map.Entry<String, String> entry : fields.entrySet()) {
					statementBuilder.addWhereField(entry.getKey(), entry.getValue());
				}
				final PreparedStatement statement = connection.prepareStatement(statementBuilder.getStatement());
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
					Airline airline = new Airline(resultAirlineId, resultName, resultAlias, resultIata, resultIcao,
							resultCallsign, resultCountry, resultActive);
					airlines.add(airline);
				}
			} catch (final SQLException e) {
				Log.error("Error executing SQL Statement: " + e.getLocalizedMessage());
			}
		}

		this.connectionManager.returnToPool(connection);
		return airlines.toArray(new Airline[airlines.size()]);
	}

}
