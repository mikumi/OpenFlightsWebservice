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

import com.michael_kuck.commons.Log;
import com.michael_kuck.commons.mysql.MySqlConnectionManager;
import com.michael_kuck.commons.mysql.MySqlSelectStatementBuilder;

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
	
	private static final String NOT_NULL = "!null";

	final private Map<String, ArrayList<Airline>> airlineCache;
	final private Map<Integer, Airline> airlineIdCache;

	final private MySqlConnectionManager connectionManager;

	/**
	 * 
	 */
	public AirlineDatabase(final MySqlConnectionManager connectionManager)
	{
		this.connectionManager = connectionManager;
		this.airlineCache = new HashMap<String, ArrayList<Airline>>();
		this.airlineIdCache = new HashMap<Integer, Airline>();
	}

	public int[] airlineIdsByField(final String field, final String value)
	{
		final HashMap<String, String> fields = new HashMap<String, String>();
		fields.put(field, value);
		return this.airlineIdsByFields(fields);
	}

	/**
	 * @param fields
	 * @return
	 */
	public int[] airlineIdsByFields(final Map<String, String> fields)
	{
		Log.verbose("airlineIdsByField: " + fields.toString());

		final ArrayList<Integer> airlineIds = new ArrayList<Integer>();
		final Connection connection = this.connectionManager.connectionFromPool();
		if (connection == null) {
			Log.error("Could not search for airlines, no database connection.");
		} else {
			try {
				// Prepare statement
				final MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder(TABLE_AIRLINES);
				statementBuilder.addSelectField(FIELD_AIRLINE_ID);
				for (final Map.Entry<String, String> entry : fields.entrySet()) {
					if (entry.getValue().equals(NOT_NULL)) {
						statementBuilder.addWhereNotField(entry.getKey(), "");
					} else {
						statementBuilder.addWhereField(entry.getKey(), entry.getValue());
					}
				}
				final PreparedStatement statement = statementBuilder.getPreparedStatement(connection);
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

		final int ids[] = new int[airlineIds.size()];
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
	public Map<String, ArrayList<String>> airlineListByField(final String field, final String value)
	{
		final HashMap<String, String> fields = new HashMap<String, String>();
		fields.put(field, value);
		return this.airlineIndexByFields(fields);
	}

	/**
	 * @param fields
	 * @return
	 */
	public Map<String, ArrayList<String>> airlineIndexByFields(final Map<String, String> fields)
	{
		Log.verbose("airlineListByField: " + fields.toString());

		final HashMap<String, ArrayList<String>> airlineList = new HashMap<String, ArrayList<String>>();
		final Connection connection = this.connectionManager.connectionFromPool();
		if (connection == null) {
			Log.error("Could not search for airlines, no database connection.");
		} else {
			try {
				// Prepare statement
				final MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder(TABLE_AIRLINES);
				statementBuilder.addSelectField(FIELD_NAME);
				statementBuilder.addSelectField(FIELD_IATA);
				for (final Map.Entry<String, String> entry : fields.entrySet()) {
					if (entry.getValue().equals(NOT_NULL)) {
						statementBuilder.addWhereNotField(entry.getKey(), "");
					} else {
						statementBuilder.addWhereField(entry.getKey(), entry.getValue());
					}
				}
				final PreparedStatement statement = statementBuilder.getPreparedStatement(connection);
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
	public Airline[] airlinesByField(final String field, final String value)
	{
		final HashMap<String, String> fields = new HashMap<String, String>();
		fields.put(field, value);
		return this.airlinesByFields(fields);
	}

	/**
	 * @param fields
	 * @return
	 */
	public Airline[] airlinesByFields(final Map<String, String> fields)
	{
		Log.verbose("airlinesByField: " + fields.toString());

		final ArrayList<Airline> airlinesMatched = new ArrayList<Airline>();
		// Search Cache
		boolean foundMatch = false;
		for (final Map.Entry<String, String> entry : fields.entrySet()) {
			// Check airline id first
			if (entry.getKey().equals(FIELD_AIRLINE_ID)) {
				final Integer searchAirlineId = Integer.valueOf(entry.getValue());
				if (this.airlineIdCache.containsKey(searchAirlineId)) {
					final Airline airline = this.airlineIdCache.get(searchAirlineId);
					airlinesMatched.add(airline);
					foundMatch = true;
					break;
				}
			}
//			// Check rest of the fields
//			ArrayList<Airline> airlinesForKey = this.airlineCache.get(entry.getValue());
//			if (this.airlineCache.containsKey(entry.getValue())) {
//				// TODO: implement
//			} 
		}
		// Search SQL
		if (!foundMatch) {
			final Connection connection = this.connectionManager.connectionFromPool();
			if (connection == null) {
				Log.error("Could not search for airlines, no database connection.");
			} else {
				try {
					// Prepare statement
					final MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder(TABLE_AIRLINES);
					statementBuilder.addSelectFields(new String[] { FIELD_AIRLINE_ID, FIELD_NAME, FIELD_ALIAS,
							FIELD_IATA, FIELD_ICAO, FIELD_CALLSIGN, FIELD_COUNTRY, FIELD_ACTIVE });
					for (final Map.Entry<String, String> entry : fields.entrySet()) {
						if (entry.getValue().equals(NOT_NULL)) {
							statementBuilder.addWhereNotField(entry.getKey(), "");
						} else {
							statementBuilder.addWhereField(entry.getKey(), entry.getValue());
						}
					}
					statementBuilder.setRowLimit(100);
					final PreparedStatement statement = statementBuilder.getPreparedStatement(connection);
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
						final Airline airline = new Airline(resultAirlineId, resultName, resultAlias, resultIata,
								resultIcao, resultCallsign, resultCountry, resultActive);
						airlinesMatched.add(airline);
						this.addAirlineToCache(airline);
					}
				} catch (final SQLException e) {
					Log.error("Error executing SQL Statement: " + e.getLocalizedMessage());
				}
			}
			this.connectionManager.returnToPool(connection);
		}

		return airlinesMatched.toArray(new Airline[airlinesMatched.size()]);
	}

//	/**
//	 * @param airline
//	 * @param propertyName
//	 * @return
//	 */
//	private String getPropertyFromAirlineAsString(Airline airline, String propertyName) {
//		String result = null;
//		if (propertyName.equals(FIELD_ACTIVE)) {
//			result = airline.isActive() ? "1" : "0";
//		} else if (propertyName.equals(FIELD_AIRLINE_ID)) {
//			result = String.valueOf(airline.getAirlineId());
//		} else if (propertyName.equals(FIELD_ALIAS)) {
//			result = airline.getAlias();
//		} else if (propertyName.equals(FIELD_CALLSIGN)) {
//			result = airline.getCallsign();
//		} else if (propertyName.equals(FIELD_COUNTRY)) {
//			result = airline.getCountry();
//		} else if (propertyName.equals(FIELD_IATA)) {
//			result = airline.getIata();
//		} else if (propertyName.equals(FIELD_ICAO)) {
//			result = airline.getIcao();
//		} else if (propertyName.equals(FIELD_NAME)) {
//			result = airline.getName();
//		}
//		return result;
//	}

	/**
	 * @param airline
	 */
	private synchronized void addAirlineToCache(final Airline airline)
	{
		// Cache id
		final Integer id = airline.getAirlineId();
		this.airlineIdCache.put(id, airline);
		// Cache major keys
		final ArrayList<String> keys = new ArrayList<String>();
		keys.add(airline.getName());
		keys.add(airline.getIata());
		keys.add(airline.getIcao());
		keys.add(airline.getCountry());
		for (final String key : keys) {
			ArrayList<Airline> airlines = this.airlineCache.get(key);
			if (airlines == null) {
				airlines = new ArrayList<Airline>();
				this.airlineCache.put(key, airlines);
				Log.debug("Airline cache now containing " + this.airlineCache.size() + " keywords.");
			}
			if (!airlines.contains(airline)) {
				Log.debug("Added " + airline.getName() + " added to keyword " + key + ", which now contains "
						+ airlines.size() + " airlines.");
				airlines.add(airline);
			}
		}
	}

}
