/**
 *
 */
package com.jaysquared.openflights.webservice.data;

import com.jolbox.bonecp.BoneCP;
import com.michael_kuck.commons.Log;
import com.michael_kuck.commons.mysql.MySqlHelper;
import com.michael_kuck.commons.mysql.MySqlSelectStatementBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author michaelkuck
 */
public class AirportDatabase {

    final public static String TABLE_AIRPORTS = "of_airports";

    final public static String FIELD_AIRPORT_ID = "airport_id";
    final public static String FIELD_NAME       = "name";
    final public static String FIELD_CITY       = "city";
    final public static String FIELD_COUNTRY    = "country";
    final public static String FIELD_IATA_FAA   = "iata_faa";
    final public static String FIELD_ICAO       = "icao";
    final public static String FIELD_LATITUDE   = "latitude";
    final public static String FIELD_LONGITUDE  = "longitude";
    final public static String FIELD_ALTITUDE   = "altitude";
    final public static String FIELD_TIMEZONE   = "timezone";
    final public static String FIELD_DST        = "dst";

    private static final String NOT_NULL = "!null";

    final private Map<String, ArrayList<Airport>> airportCache;
    final private Map<Integer, Airport>           airportIdCache;

    final private BoneCP connectionPool;

    /**
     *
     */
    public AirportDatabase(final BoneCP connectionPool) {
        this.connectionPool = connectionPool;
        this.airportCache = new HashMap<String, ArrayList<Airport>>();
        this.airportIdCache = new HashMap<Integer, Airport>();
    }

    public int[] airportIdsByField(final String field, final String value) {
        final HashMap<String, String> fields = new HashMap<String, String>();
        fields.put(field, value);
        return this.airportIdsByFields(fields);
    }

    /**
     * @param fields
     * @return
     */
    public int[] airportIdsByFields(final Map<String, String> fields) {
        Log.verbose("airportIdsByField: " + fields.toString());

        final ArrayList<Integer> airportIds = new ArrayList<Integer>();
        Connection connection = null;
        try {
            connection = this.connectionPool.getConnection();
            // Prepare statement
            final MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder(TABLE_AIRPORTS);
            statementBuilder.addSelectField(FIELD_AIRPORT_ID);
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
                final int resultAirportId = resultSet.getInt(FIELD_AIRPORT_ID);
                airportIds.add(resultAirportId);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            MySqlHelper.closeConnection(connection);
        }

        final int ids[] = new int[airportIds.size()];
        for (int i = 0; i < airportIds.size(); i++) {
            ids[i] = airportIds.get(i).intValue();
        }
        return ids;
    }

    /**
     * @param field
     * @param value
     * @return
     */
    public Map<String, ArrayList<String>> airportIndexByField(final String field, final String value) {
        final HashMap<String, String> fields = new HashMap<String, String>();
        fields.put(field, value);
        return this.airportIndexByFields(fields);
    }

    /**
     * @param fields
     * @return
     */
    public Map<String, ArrayList<String>> airportIndexByFields(final Map<String, String> fields) {
        Log.verbose("airportIndexByField: " + fields.toString());

        final HashMap<String, ArrayList<String>> airportList = new HashMap<String, ArrayList<String>>();
        Connection connection = null;
        try {
            connection = this.connectionPool.getConnection();
            // Prepare statement
            final MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder(TABLE_AIRPORTS);
            statementBuilder.addSelectField(FIELD_NAME);
            statementBuilder.addSelectField(FIELD_IATA_FAA);
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
                final String resultAirportName = resultSet.getString(FIELD_NAME);
                final String resultAirportIataFaa = resultSet.getString(FIELD_IATA_FAA);
                ArrayList<String> airports = airportList.get(resultAirportIataFaa);
                if (airports == null) {
                    airports = new ArrayList<String>();
                    airportList.put(resultAirportIataFaa, airports);
                }
                airports.add(resultAirportName);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            MySqlHelper.closeConnection(connection);
        }

        return airportList;
    }

    /**
     * @param field
     * @param value
     */
    public Airport[] airportsByField(final String field, final String value) {
        final HashMap<String, String> fields = new HashMap<String, String>();
        fields.put(field, value);
        return this.airportsByFields(fields);
    }

    /**
     * @param fields
     * @return
     */
    public Airport[] airportsByFields(final Map<String, String> fields) {
        Log.verbose("airportsByField: " + fields.toString());

        final ArrayList<Airport> airportsMatched = new ArrayList<Airport>();
        // Search Cache
        boolean foundMatch = false;
        for (final Map.Entry<String, String> entry : fields.entrySet()) {
            // Check airline id first
            if (entry.getKey().equals(FIELD_AIRPORT_ID)) {
                final Integer searchAirportId = Integer.valueOf(entry.getValue());
                if (this.airportIdCache.containsKey(searchAirportId)) {
                    final Airport airport = this.airportIdCache.get(searchAirportId);
                    airportsMatched.add(airport);
                    foundMatch = true;
                    break;
                }
            }
            // Check rest of the fields
//			ArrayList<Airport> airportsForKey = this.airportCache.get(entry.getValue());
//			if (this.airportCache.containsKey(entry.getValue())) {
//				// TODO: implement
//			}
        }
        // Search SQL
        if (!foundMatch) {
            Connection connection = null;
            try {
                connection = this.connectionPool.getConnection();
                // Prepare statement
                final MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder(TABLE_AIRPORTS);
                statementBuilder.addSelectFields(
                        new String[]{FIELD_AIRPORT_ID, FIELD_NAME, FIELD_CITY, FIELD_COUNTRY, FIELD_IATA_FAA,
                                     FIELD_ICAO, FIELD_LATITUDE, FIELD_LONGITUDE, FIELD_ALTITUDE, FIELD_TIMEZONE,
                                     FIELD_DST}
                                                );
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
                    final int resultAirportId = resultSet.getInt(FIELD_AIRPORT_ID);
                    final String resultName = resultSet.getString(FIELD_NAME);
                    final String resultCity = resultSet.getString(FIELD_CITY);
                    final String resultCountry = resultSet.getString(FIELD_COUNTRY);
                    final String resultIataFaa = resultSet.getString(FIELD_IATA_FAA);
                    final String resultIcao = resultSet.getString(FIELD_ICAO);
                    final float resultLatitude = resultSet.getFloat(FIELD_LATITUDE);
                    final float resultLongitude = resultSet.getFloat(FIELD_LONGITUDE);
                    final int resultAltitude = resultSet.getInt(FIELD_ALTITUDE);
                    final float resultTimezone = resultSet.getFloat(FIELD_TIMEZONE);
                    final String resultDst = resultSet.getString(FIELD_DST);
                    final Airport airport =
                            new Airport(resultAirportId, resultName, resultCity, resultCountry, resultIataFaa,
                                        resultIcao, resultLatitude, resultLongitude, resultAltitude, resultTimezone,
                                        resultDst.charAt(0));
                    airportsMatched.add(airport);
                    this.addAirportToCache(airport);
                }
            } catch (final SQLException e) {
                Log.error("Error executing SQL Statement: " + e.getLocalizedMessage());
            } finally {
                MySqlHelper.closeConnection(connection);
            }
        }

        return airportsMatched.toArray(new Airport[airportsMatched.size()]);
    }

//	/**
//	 * @param airline
//	 * @param propertyName
//	 * @return
//	 */
//	private String getPropertyFromAirlineAsString(Airport airport, String propertyName)
//	{
//		String result = null;
//		if (propertyName.equals(FIELD_AIRPORT_ID)) {
//			result = String.valueOf(airport.getAirportId());
//		} else if (propertyName.equals(FIELD_ALTITUDE)) {
//			result = String.valueOf(airport.getAltitude());
//		} else if (propertyName.equals(FIELD_CITY)) {
//			result = airport.getCity();
//		} else if (propertyName.equals(FIELD_COUNTRY)) {
//			result = airport.getCountry();
//		} else if (propertyName.equals(FIELD_DST)) {
//			result = String.valueOf(airport.getDst());
//		} else if (propertyName.equals(FIELD_IATA_FAA)) {
//			result = airport.getIata_faa();
//		} else if (propertyName.equals(FIELD_ICAO)) {
//			result = airport.getIcao();
//		} else if (propertyName.equals(FIELD_LATITUDE)) {
//			result = String.valueOf(airport.getLatitude());
//		} else if (propertyName.equals(FIELD_LONGITUDE)) {
//			result = String.valueOf(airport.getLongitude());
//		} else if (propertyName.equals(FIELD_NAME)) {
//			result = airport.getName();
//		} else if (propertyName.equals(FIELD_TIMEZONE)) {
//			result = String.valueOf(airport.getTimezone());
//		}
//		return result;
//	}

    /**
     * @param airline
     */
    private synchronized void addAirportToCache(final Airport airport) {
        // Cache id
        final Integer id = airport.getAirportId();
        this.airportIdCache.put(id, airport);
        // Cache major keys
        final ArrayList<String> keys = new ArrayList<String>();
        keys.add(airport.getCity());
        keys.add(airport.getCountry());
        keys.add(airport.getIata_faa());
        keys.add(airport.getIcao());
        keys.add(airport.getName());
        for (final String key : keys) {
            ArrayList<Airport> airports = this.airportCache.get(key);
            if (airports == null) {
                airports = new ArrayList<Airport>();
                this.airportCache.put(key, airports);
                Log.debug("Airport cache now containing " + this.airportCache.size() + " keywords.");
            }
            if (!airports.contains(airport)) {
                Log.debug("Added " + airport.getName() + " added to keyword " + key + ", which now contains " +
                          airports.size() + " airlines.");
                airports.add(airport);
            }
        }
    }

}
