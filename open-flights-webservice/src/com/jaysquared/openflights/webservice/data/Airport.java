/**
 * 
 */
package com.jaysquared.openflights.webservice.data;

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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final String description = this.name + " (" + this.iata_faa + "/" + this.icao + ") in " + this.country + " (id: "
				+ this.airportId + ")";
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
