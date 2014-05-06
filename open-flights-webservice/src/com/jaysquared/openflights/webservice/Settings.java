package com.jaysquared.openflights.webservice;

/**
 * 
 */

/**
 * @author michaelkuck
 * 
 */
public class Settings {

	// General Settings
	private int werbservicePortHTTP;
	private int webservicePortHTTPS;

	// SQL connection settings
	private String sqlHost;
	private int sqlPort;
	private String sqlUser;
	private String sqlPassword;
	private String sqlDatabase;

	public Settings()
	{
		this.werbservicePortHTTP = 8891;
		this.webservicePortHTTPS = 8892;

		this.sqlHost = "localhost";
		this.sqlPort = 3306;
		this.sqlUser = "root";
		this.sqlPassword = "";
		this.sqlDatabase = "open_flights";
	}

	/**
	 * @return the webservicePortHTTP
	 */
	public int getWebservicePortHTTP()
	{
		return this.werbservicePortHTTP;
	}

	/**
	 * @param webservicePortHTTP
	 *            the webservicePortHTTP to set
	 */
	public void setWebservicePortHTTP(final int webservicePortHTTP)
	{
		this.werbservicePortHTTP = webservicePortHTTP;
	}

	/**
	 * @return the webservicePortHTTPS
	 */
	public int getWebservicePortHTTPS()
	{
		return this.webservicePortHTTPS;
	}

	/**
	 * @param webservicePortHTTPS
	 *            the webservicePortHTTPS to set
	 */
	public void setWebservicePortHTTPS(final int webservicePortHTTPS)
	{
		this.webservicePortHTTPS = webservicePortHTTPS;
	}

	/**
	 * @return the sqlHost
	 */
	public String getSqlHost()
	{
		return this.sqlHost;
	}

	/**
	 * @param sqlHost
	 *            the sqlHost to set
	 */
	public void setSqlHost(final String sqlHost)
	{
		this.sqlHost = sqlHost;
	}

	/**
	 * @return the sqlPort
	 */
	public int getSqlPort()
	{
		return this.sqlPort;
	}

	/**
	 * @param port
	 *            the sqlPort to set
	 */
	public void setSqlPort(final int sqlPort)
	{
		this.sqlPort = sqlPort;
	}

	/**
	 * @return the sqlUser
	 */
	public String getSqlUser()
	{
		return this.sqlUser;
	}

	/**
	 * @param sqlUser
	 *            the sqlUser to set
	 */
	public void setSqlUser(final String sqlUser)
	{
		this.sqlUser = sqlUser;
	}

	/**
	 * @return the sqlPassword
	 */
	public String getSqlPassword()
	{
		return this.sqlPassword;
	}

	/**
	 * @param sqlPassword
	 *            the sqlPassword to set
	 */
	public void setSqlPassword(final String sqlPassword)
	{
		this.sqlPassword = sqlPassword;
	}

	/**
	 * @return the sqlDatabase
	 */
	public String getSqlDatabase()
	{
		return this.sqlDatabase;
	}

	/**
	 * @param sqlDatabase
	 *            the sqlDatabase to set
	 */
	public void setSqlDatabase(final String sqlDatabase)
	{
		this.sqlDatabase = sqlDatabase;
	}

}
