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
public class Airline {

	final private int airlineId;
	final private String name;
	final private String alias;
	final private String iata;
	final private String icao;
	final private String callsign;
	final private String country;
	final private boolean active;

	/**
	 * @param airlineId
	 * @param name
	 * @param alias
	 * @param iata
	 * @param icao
	 * @param callsign
	 * @param country
	 * @param active
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(17, 31).append(this.airlineId).append(this.name).append(this.alias)
				.append(this.iata).append(this.icao).append(this.callsign).append(this.country).append(this.active)
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

		final Airline airline = (Airline) obj;
		return new EqualsBuilder().append(this.airlineId, airline.airlineId).append(this.name, airline.name)
				.append(this.alias, airline.alias).append(this.iata, airline.iata).append(this.icao, airline.icao)
				.append(this.callsign, airline.callsign).append(this.country, airline.country)
				.append(this.active, airline.active).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
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
