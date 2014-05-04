/**
 * 
 */
package com.jaysquared.openflights.webservice.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		return "jdbc:mysql://" + host + ":" + port + "/" + database;
	}

	/**
	 * @return
	 */
	public static Connection getNewConnection(final String host, final int port, final String user,
			final String password, final String database)
	{
		Connection connection = null;
		Log.debug("Trying to connect to " + database + " on " + host + ":" + port + " with " + user);
		try {
			connection = DriverManager.getConnection(getUrl(host, port, database), user, password);
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		validateConnection(connection);

		return connection;
	}

	/**
	 *
	 */
	private static void validateConnection(final Connection connection)
	{
		Statement statement;
		ResultSet resultSet;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT VERSION()");
			if (resultSet.next()) {
				Log.debug("Successfully connected to MySQL Server v" + resultSet.getString(1));
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}

}
