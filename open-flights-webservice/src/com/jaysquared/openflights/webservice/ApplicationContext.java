/**
 * 
 */
package com.jaysquared.openflights.webservice;

import com.jaysquared.openflights.webservice.data.FlightInformation;

/**
 * @author michaelkuck
 * 
 */
public class ApplicationContext {

	private FlightInformation flightInformation;

	/**
	 * Private constructor for Singleton
	 */
	private ApplicationContext()
	{
	}

	/**
	 * Singleton holder
	 */
	private static class SingletonHolder {
		private static final ApplicationContext INSTANCE = new ApplicationContext();
	}

	/**
	 * Return the lazyly initialized singleton instance of ApplicationContext
	 * 
	 * @return Exactly the same instance of ApplicationContext at all times.
	 */
	public static ApplicationContext getInstance()
	{
		return SingletonHolder.INSTANCE;
	}

	public void init(final FlightInformation flightInformation)
	{
		this.flightInformation = flightInformation;
	}

	/**
	 * @return the flightInformation
	 */
	public FlightInformation getFlightInformation()
	{
		return this.flightInformation;
	}
}
