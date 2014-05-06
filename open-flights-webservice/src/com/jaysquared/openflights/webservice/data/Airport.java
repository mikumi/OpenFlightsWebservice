/**
 * 
 */
package com.jaysquared.openflights.webservice.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author michaelkuck
 * 
 */
public class Airport {

	private final int airportId;
	private final String name;
	private final String city;
	private final String country;
	private final String iata_faa;
	private final String icao;
	private final float latitude;
	private final float longitude;
	private final int altitude;
	private final float timezone;
	private final char dst;

	public Airport(final int airportId, final String name, final String city, final String country,
			final String iata_faa, final String icao, final float latitude, final float longitude, final int altitude,
			final float timezone, final char dst)
	{
		this.airportId = airportId;
		this.name = name;
		this.city = city;
		this.country = country;
		this.iata_faa = iata_faa;
		this.icao = icao;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.timezone = timezone;
		this.dst = dst;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(17, 31).append(this.airportId).append(this.name).append(this.city)
				.append(this.country).append(this.iata_faa).append(this.icao).append(this.latitude)
				.append(this.longitude).append(this.altitude).append(this.timezone).append(this.dst).toHashCode();
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

		final Airport airport = (Airport) obj;
		return new EqualsBuilder().append(this.airportId, airport.airportId).append(this.name, airport.name)
				.append(this.city, airport.city).append(this.country, airport.country)
				.append(this.iata_faa, airport.iata_faa).append(this.icao, airport.icao)
				.append(this.latitude, airport.latitude).append(this.longitude, airport.longitude)
				.append(this.altitude, airport.altitude).append(this.timezone, airport.timezone)
				.append(this.dst, airport.dst).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final String description = this.name + " (" + this.iata_faa + "/" + this.icao + ") in " + this.country
				+ " (id: " + this.airportId + ")";
		return description;
	}

	/**
	 * @return the airportId
	 */
	public int getAirportId()
	{
		return this.airportId;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * @return the city
	 */
	public String getCity()
	{
		return this.city;
	}

	/**
	 * @return the country
	 */
	public String getCountry()
	{
		return this.country;
	}

	/**
	 * @return the iata_faa
	 */
	public String getIata_faa()
	{
		return this.iata_faa;
	}

	/**
	 * @return the icao
	 */
	public String getIcao()
	{
		return this.icao;
	}

	/**
	 * @return the latitude
	 */
	public float getLatitude()
	{
		return this.latitude;
	}

	/**
	 * @return the longitude
	 */
	public float getLongitude()
	{
		return this.longitude;
	}

	/**
	 * @return the altitude
	 */
	public int getAltitude()
	{
		return this.altitude;
	}

	/**
	 * @return the timezone
	 */
	public float getTimezone()
	{
		return this.timezone;
	}

	/**
	 * @return the dst
	 */
	public char getDst()
	{
		return this.dst;
	}

}
