package com.jaysquared.openflights.webservice.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return new HashCodeBuilder(17, 31).append(airline).append(sourceAirport).append(destinationAirport)
				.append(codeshare).append(stops).append(equipment).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Airline))
			return false;

		Route route = (Route) obj;
		return new EqualsBuilder().append(airline, route.airline).append(sourceAirport, route.sourceAirport)
				.append(destinationAirport, route.destinationAirport).append(codeshare, route.codeshare)
				.append(stops, route.stops).append(equipment, route.equipment).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final String description = airline.getCallsign() + " from " + sourceAirport.getName() + " to "
				+ destinationAirport.getName();
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
