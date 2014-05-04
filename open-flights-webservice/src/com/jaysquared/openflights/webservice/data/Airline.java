/**
 * 
 */
package com.jaysquared.openflights.webservice.data;

/**
 * @author michaelkuck
 * 
 */
public class Airline {

	final private int airlineId;
	final private String name;
	final private String alias;
	final private String iata;
	final private String icao;
	final private String callsign;
	final private String country;
	final private boolean active;

	public Airline(final int airlineId, final String name, final String alias, final String iata, final String icao,
			final String callsign, final String country, final boolean active)
	{
		this.airlineId = airlineId;
		this.name = name;
		this.alias = alias;
		this.iata = iata;
		this.icao = icao;
		this.callsign = callsign;
		this.country = country;
		this.active = active;
	}

	@Override
	public String toString()
	{
		final String description = this.name + " (" + this.iata + "/" + this.icao + ") from " + this.country + " (id: "
				+ this.airlineId + ")";
		return description;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * @return the alias
	 */
	public String getAlias()
	{
		return this.alias;
	}

	/**
	 * @return the iata
	 */
	public String getIata()
	{
		return this.iata;
	}

	/**
	 * @return the icao
	 */
	public String getIcao()
	{
		return this.icao;
	}

	/**
	 * @return the callsign
	 */
	public String getCallsign()
	{
		return this.callsign;
	}

	/**
	 * @return the country
	 */
	public String getCountry()
	{
		return this.country;
	}

	/**
	 * @return the active
	 */
	public boolean isActive()
	{
		return this.active;
	}

	/**
	 * @return the airlineId
	 */
	public int getAirlineId()
	{
		return this.airlineId;
	}

}
