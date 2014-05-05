/**
 * 
 */
package com.jaysquared.openflights.webservice.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.michael_kuck.commons.Log;

/**
 * @author michaelkuck
 * 
 */
public class MySqlHelper {

	/**
	 * 
	 */
	private MySqlHelper()
	{
	}

	/**
	 * @return
	 */
	private static String getUrl(final String host, final int port, final String database)
	{
		final String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
		return url;
	}

	/**
	 * @return
	 */
	public static Connection getNewConnection(final String host, final int port, final String user,
			final String password, final String database)
	{
		Connection connection = null;
		final String connectionUrl = getUrl(host, port, database);
		Log.debug("Trying to connect to " + connectionUrl + " with user: " + user + ", password: (hidden)");
		try {
			connection = DriverManager.getConnection(connectionUrl, user, password);
		} catch (final SQLException e) {
			Log.error("Error connecting to MySQL database: " + e.getLocalizedMessage());
		}

		return connection;
	}

}
