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
	private int werbservicePortHTTP = 8814;
	private int webservicePortHTTPS = 8815;
	
	// SQL connection settings
	private String sqlHost = "localhost";
	private int sqlPort = 3306;
	private String sqlUser = "root";
	private String sqlPassword = "";
	private String sqlDatabase = "boarding_pass";

	public Settings() {
	}
	
	/**
	 * @return the webservicePortHTTP
	 */
	public int getWebservicePortHTTP() {
		return werbservicePortHTTP;
	}

	/**
	 * @param webservicePortHTTP
	 *            the webservicePortHTTP to set
	 */
	public void setWebservicePortHTTP(final int webservicePortHTTP) {
		this.werbservicePortHTTP = webservicePortHTTP;
	}

	/**
	 * @return the webservicePortHTTPS
	 */
	public int getWebservicePortHTTPS() {
		return webservicePortHTTPS;
	}

	/**
	 * @param webservicePortHTTPS
	 *            the webservicePortHTTPS to set
	 */
	public void setWebservicePortHTTPS(final int webservicePortHTTPS) {
		this.webservicePortHTTPS = webservicePortHTTPS;
	}

	/**
	 * @return the sqlHost
	 */
	public String getSqlHost() {
		return sqlHost;
	}

	/**
	 * @param sqlHost
	 *            the sqlHost to set
	 */
	public void setSqlHost(final String sqlHost) {
		this.sqlHost = sqlHost;
	}

	/**
	 * @return the sqlPort
	 */
	public int getSqlPort() {
		return sqlPort;
	}

	/**
	 * @param port
	 *            the sqlPort to set
	 */
	public void setSqlPort(final int sqlPort) {
		this.sqlPort = sqlPort;
	}

	/**
	 * @return the sqlUser
	 */
	public String getSqlUser() {
		return sqlUser;
	}

	/**
	 * @param sqlUser
	 *            the sqlUser to set
	 */
	public void setSqlUser(final String sqlUser) {
		this.sqlUser = sqlUser;
	}

	/**
	 * @return the sqlPassword
	 */
	public String getSqlPassword() {
		return sqlPassword;
	}

	/**
	 * @param sqlPassword
	 *            the sqlPassword to set
	 */
	public void setSqlPassword(final String sqlPassword) {
		this.sqlPassword = sqlPassword;
	}

	/**
	 * @return the sqlDatabase
	 */
	public String getSqlDatabase() {
		return sqlDatabase;
	}

	/**
	 * @param sqlDatabase
	 *            the sqlDatabase to set
	 */
	public void setSqlDatabase(final String sqlDatabase) {
		this.sqlDatabase = sqlDatabase;
	}

}
