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

	public Airport(int airportId, String name, String city, String country, String iata_faa, String icao,
			float latitude, float longitude, int altitude, float timezone, char dst)
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
	public int hashCode()
	{
		return new HashCodeBuilder(17, 31).append(airportId).append(name).append(city).append(country).append(iata_faa)
				.append(icao).append(latitude).append(longitude).append(altitude).append(timezone).append(dst)
				.toHashCode();
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

		Airport airport = (Airport) obj;
		return new EqualsBuilder().append(airportId, airport.airportId).append(name, airport.name)
				.append(city, airport.city).append(country, airport.country).append(iata_faa, airport.iata_faa)
				.append(icao, airport.icao).append(latitude, airport.latitude).append(longitude, airport.longitude)
				.append(altitude, airport.altitude).append(timezone, airport.timezone).append(dst, airport.dst)
				.isEquals();
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
		return airportId;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the city
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * @return the country
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * @return the iata_faa
	 */
	public String getIata_faa()
	{
		return iata_faa;
	}

	/**
	 * @return the icao
	 */
	public String getIcao()
	{
		return icao;
	}

	/**
	 * @return the latitude
	 */
	public float getLatitude()
	{
		return latitude;
	}

	/**
	 * @return the longitude
	 */
	public float getLongitude()
	{
		return longitude;
	}

	/**
	 * @return the altitude
	 */
	public int getAltitude()
	{
		return altitude;
	}

	/**
	 * @return the timezone
	 */
	public float getTimezone()
	{
		return timezone;
	}

	/**
	 * @return the dst
	 */
	public char getDst()
	{
		return dst;
	}

}
