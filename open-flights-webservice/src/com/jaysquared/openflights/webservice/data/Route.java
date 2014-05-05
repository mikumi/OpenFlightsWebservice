package com.jaysquared.openflights.webservice.data;

public class Route {

	private final Airline airline;
	private final Airport sourceAirport;
	private final Airport destinationAirport;
	private final boolean codeshare;
	private final int stops;
	private final String equipment;

	public Route(Airline airline, Airport sourceAirport, Airport destinationAirport, boolean codeshare, int stops,
			String equipment)
	{
		this.airline = airline;
		this.sourceAirport = sourceAirport;
		this.destinationAirport = destinationAirport;
		this.codeshare = codeshare;
		this.stops = stops;
		this.equipment = equipment;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final String description = airline.getCallsign() + " from " + sourceAirport.getName() + " to " + destinationAirport.getName();
		return description;
	}

	/**
	 * @return the airline
	 */
	public Airline getAirline()
	{
		return airline;
	}

	/**
	 * @return the sourceAirport
	 */
	public Airport getSourceAirport()
	{
		return sourceAirport;
	}

	/**
	 * @return the destinationAirport
	 */
	public Airport getDestinationAirport()
	{
		return destinationAirport;
	}

	/**
	 * @return the codeshare
	 */
	public boolean isCodeshare()
	{
		return codeshare;
	}

	/**
	 * @return the stops
	 */
	public int getStops()
	{
		return stops;
	}

	/**
	 * @return the equipment
	 */
	public String getEquipment()
	{
		return equipment;
	}
	
	

}
