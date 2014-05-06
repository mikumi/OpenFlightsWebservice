/**
 * 
 */
package com.jaysquared.openflights.webservice;

import com.jaysquared.openflights.webservice.data.FlightInformation;
import com.jaysquared.openflights.webservice.dbconnection.DatabaseInformation;

/**
 * @author michaelkuck
 * 
 */
public class ApplicationContext {

	private FlightInformation flightInformation;
	private DatabaseInformation databaseInformation;

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

	/**
	 * @param flightInformation
	 * @param databaseInformation
	 */
	public void init(final FlightInformation flightInformation, final DatabaseInformation databaseInformation)
	{
		this.flightInformation = flightInformation;
		this.databaseInformation = databaseInformation;
	}

	/**
	 * @return the flightInformation
	 */
	public FlightInformation getFlightInformation()
	{
		return this.flightInformation;
	}

	/**
	 * @return the databaseInformation
	 */
	public DatabaseInformation getDatabaseInformation()
	{
		return this.databaseInformation;
	}
}
