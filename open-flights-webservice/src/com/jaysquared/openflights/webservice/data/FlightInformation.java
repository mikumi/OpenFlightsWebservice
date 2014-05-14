package com.jaysquared.openflights.webservice.data;

import com.jolbox.bonecp.BoneCP;

public class FlightInformation {

	private final AirlineDatabase airlineDatabase;
	private final AirportDatabase airportDatabase;
	private final RouteDatabase routeDatabase;

	public FlightInformation(final BoneCP connectionPool)
	{
		this.airlineDatabase = new AirlineDatabase(connectionPool);
		this.airportDatabase = new AirportDatabase(connectionPool);
		this.routeDatabase = new RouteDatabase(connectionPool);
	}

	/**
	 * @return the airlineDatabase
	 */
	public AirlineDatabase getAirlineDatabase()
	{
		return this.airlineDatabase;
	}

	/**
	 * @return the airportDatabase
	 */
	public AirportDatabase getAirportDatabase()
	{
		return this.airportDatabase;
	}

	/**
	 * @return the routeDatabase
	 */
	public RouteDatabase getRouteDatabase()
	{
		return this.routeDatabase;
	}

}
