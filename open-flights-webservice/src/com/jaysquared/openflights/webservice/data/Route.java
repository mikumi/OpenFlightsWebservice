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

	public Route(final Airline airline, final Airport sourceAirport, final Airport destinationAirport,
			final boolean codeshare, final int stops, final String equipment)
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
	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(17, 31).append(this.airline).append(this.sourceAirport)
				.append(this.destinationAirport).append(this.codeshare).append(this.stops).append(this.equipment)
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Airline))
			return false;

		final Route route = (Route) obj;
		return new EqualsBuilder().append(this.airline, route.airline).append(this.sourceAirport, route.sourceAirport)
				.append(this.destinationAirport, route.destinationAirport).append(this.codeshare, route.codeshare)
				.append(this.stops, route.stops).append(this.equipment, route.equipment).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final String description = this.airline.getCallsign() + " from " + this.sourceAirport.getName() + " to "
				+ this.destinationAirport.getName();
		return description;
	}

	/**
	 * @return the airline
	 */
	public Airline getAirline()
	{
		return this.airline;
	}

	/**
	 * @return the sourceAirport
	 */
	public Airport getSourceAirport()
	{
		return this.sourceAirport;
	}

	/**
	 * @return the destinationAirport
	 */
	public Airport getDestinationAirport()
	{
		return this.destinationAirport;
	}

	/**
	 * @return the codeshare
	 */
	public boolean isCodeshare()
	{
		return this.codeshare;
	}

	/**
	 * @return the stops
	 */
	public int getStops()
	{
		return this.stops;
	}

	/**
	 * @return the equipment
	 */
	public String getEquipment()
	{
		return this.equipment;
	}

}
