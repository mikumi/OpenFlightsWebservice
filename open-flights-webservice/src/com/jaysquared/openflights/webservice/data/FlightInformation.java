package com.jaysquared.openflights.webservice.data;

import com.jaysquared.openflights.webservice.dbconnection.MySqlConnectionManager;

public class FlightInformation {

	private final AirlineDatabase airlineDatabase;
	private final AirportDatabase airportDatabase;
	private final RouteDatabase routeDatabase;
	
	public FlightInformation(MySqlConnectionManager connectionManager) {
		this.airlineDatabase = new AirlineDatabase(connectionManager);
		this.airportDatabase = new AirportDatabase(connectionManager);
		this.routeDatabase = new RouteDatabase(connectionManager);
	}

	/**
	 * @return the airlineDatabase
	 */
	public AirlineDatabase getAirlineDatabase()
	{
		return airlineDatabase;
	}

	/**
	 * @return the airportDatabase
	 */
	public AirportDatabase getAirportDatabase()
	{
		return airportDatabase;
	}

	/**
	 * @return the routeDatabase
	 */
	public RouteDatabase getRouteDatabase()
	{
		return routeDatabase;
	}
	
}
