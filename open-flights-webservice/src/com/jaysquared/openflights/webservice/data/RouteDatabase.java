/**
 *
 */
package com.jaysquared.openflights.webservice.data;

import com.jaysquared.openflights.webservice.ApplicationContext;
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
public class RouteDatabase {

    final public static String TABLE_ROUTES = "of_routes";

    final public static String FIELD_AIRLINE                = "airline";
    final public static String FIELD_AIRLINE_ID             = "airline_id";
    final public static String FIELD_SOURCE_AIRPORT         = "source_airport";
    final public static String FIELD_SOURCE_AIRPORT_ID      = "source_airport_id";
    final public static String FIELD_DESTINATION_AIRPORT    = "destination_airport";
    final public static String FIELD_DESTINATION_AIRPORT_ID = "destination_airport_id";
    final public static String FIELD_CODESHARE              = "codeshare";
    final public static String FIELD_STOPS                  = "stops";
    final public static String FIELD_EQUIPMENT              = "equipment";

    private static final String NOT_NULL = "!null";

    final private BoneCP connectionPool;

    /**
     *
     */
    public RouteDatabase(final BoneCP connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * @param field
     * @param value
     */
    public Route[] routesByField(final String field, final String value) {
        final HashMap<String, String> fields = new HashMap<String, String>();
        fields.put(field, value);
        return this.routesByFields(fields);
    }

    /**
     * @param fields
     * @return
     */
    public Route[] routesByFields(final Map<String, String> fields) {
        Log.verbose("routesByField: " + fields.toString());

        final ArrayList<Route> routes = new ArrayList<Route>();
        Connection connection = null;
        try {
            connection = this.connectionPool.getConnection();
            // Prepare statement
            final MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder(TABLE_ROUTES);
            statementBuilder.addSelectFields(
                    new String[]{FIELD_AIRLINE_ID, FIELD_SOURCE_AIRPORT_ID, FIELD_DESTINATION_AIRPORT_ID,
                                 FIELD_CODESHARE, FIELD_STOPS, FIELD_EQUIPMENT,}
                                            );
            for (final Map.Entry<String, String> entry : fields.entrySet()) {
                if (entry.getValue().equals(NOT_NULL)) {
                    statementBuilder.addWhereNotField(entry.getKey(), "");
                } else {
                    statementBuilder.addWhereField(entry.getKey(), entry.getValue());
                }
            }
            statementBuilder.setRowLimit(10);
            final PreparedStatement statement = statementBuilder.getPreparedStatement(connection);
            Log.verbose("Executing SQL statement: " + statement.toString());

            // Execute statement and parse results
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final int resultAirlineId = resultSet.getInt(FIELD_AIRLINE_ID);
                final int resultSourceAirportId = resultSet.getInt(FIELD_SOURCE_AIRPORT_ID);
                final int resultDestinationAirportId = resultSet.getInt(FIELD_DESTINATION_AIRPORT_ID);
                final boolean resultCodeShare = resultSet.getBoolean(FIELD_CODESHARE);
                final int resultStops = resultSet.getInt(FIELD_STOPS);
                final String resultEquipment = resultSet.getString(FIELD_EQUIPMENT);
                // TOOD: Improve this. this should be done with sql joins!!
                final AirlineDatabase airlineDatabase =
                        ApplicationContext.getInstance().getFlightInformation().getAirlineDatabase();
                final Airline[] airlines = airlineDatabase
                        .airlinesByField(AirlineDatabase.FIELD_AIRLINE_ID, String.valueOf(resultAirlineId));
                final AirportDatabase airportDatabase =
                        ApplicationContext.getInstance().getFlightInformation().getAirportDatabase();
                final Airport[] sourceAirports = airportDatabase
                        .airportsByField(AirportDatabase.FIELD_AIRPORT_ID, String.valueOf(resultSourceAirportId));
                final Airport[] destinationAirports = airportDatabase
                        .airportsByField(AirportDatabase.FIELD_AIRPORT_ID, String.valueOf(resultDestinationAirportId));
                if ((airlines.length >= 1) && (sourceAirports.length >= 1) && (destinationAirports.length >= 1)) {
                    final Route route =
                            new Route(airlines[0], sourceAirports[0], destinationAirports[0], resultCodeShare,
                                      resultStops, resultEquipment);
                    routes.add(route);
                } else {
                    Log.error("Couldn't parse route information");
                }
            }
        } catch (final SQLException e) {
            Log.error("Error executing SQL Statement: " + e.getLocalizedMessage());
        } finally {
            MySqlHelper.closeConnection(connection);
        }

        return routes.toArray(new Route[routes.size()]);
    }

    /**
     * @param fields
     * @return
     */
    public ArrayList<Map<String, String>> routeIndexByFields(final Map<String, String> fields) {
        Log.verbose("routeIndexByField: " + fields.toString());

        final ArrayList<Map<String, String>> routesList = new ArrayList<Map<String, String>>();
        Connection connection = null;
        try {
            connection = this.connectionPool.getConnection();
            // Prepare statement
            final MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder(TABLE_ROUTES);
            statementBuilder
                    .addSelectFields(new String[]{FIELD_AIRLINE, FIELD_SOURCE_AIRPORT, FIELD_DESTINATION_AIRPORT});
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
                final String resultAirline = resultSet.getString(FIELD_AIRLINE);
                final String resultSourceAirport = resultSet.getString(FIELD_SOURCE_AIRPORT);
                final String resultDestinationAirport = resultSet.getString(FIELD_DESTINATION_AIRPORT);
                Map<String, String> route = new HashMap<String, String>();
                route.put(FIELD_AIRLINE, resultAirline);
                route.put(FIELD_SOURCE_AIRPORT, resultSourceAirport);
                route.put(FIELD_DESTINATION_AIRPORT, resultDestinationAirport);
                routesList.add(route);
            }
        } catch (final SQLException e) {
            Log.error("Error executing SQL Statement: " + e.getLocalizedMessage());
        } finally {
            MySqlHelper.closeConnection(connection);
        }

        return routesList;
    }

}
