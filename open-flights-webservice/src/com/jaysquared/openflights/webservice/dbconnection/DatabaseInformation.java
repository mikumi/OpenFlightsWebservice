/**
 * 
 */
package com.jaysquared.openflights.webservice.dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.michael_kuck.commons.Log;
import com.michael_kuck.commons.mysql.MySqlConnectionManager;
import com.michael_kuck.commons.mysql.MySqlSelectStatementBuilder;

/**
 * @author michaelkuck
 * 
 */
public class DatabaseInformation {

	final public static String TABLE_INFO = "of_info";
	final public static String FIELD_DATABASE_VERSION = "database_version";

	private int databaseVersion = -1; 
	private final MySqlConnectionManager connectionManager;

	/**
	 * 
	 */
	public DatabaseInformation(final MySqlConnectionManager connectionManager)
	{
		this.connectionManager = connectionManager;
	}

	/**
	 * @return
	 */
	public int databaseVersion()
	{
		if (this.databaseVersion < 0) {
			final Connection connection = this.connectionManager.connectionFromPool();
			if (connection == null) {
				Log.error("Could not get database version, no database connection.");
			} else {
				try {
					// Prepare statement
					final MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder(TABLE_INFO);
					statementBuilder.addSelectField(FIELD_DATABASE_VERSION);
					final PreparedStatement statement = statementBuilder.getPreparedStatement(connection);
					Log.verbose("Executing SQL statement: " + statement.toString());

					// Execute statement and parse results
					final ResultSet resultSet = statement.executeQuery();
					// Should only be one line anyway. If not, something else is seriously wrong
					while (resultSet.next()) {
						this.databaseVersion = resultSet.getInt(FIELD_DATABASE_VERSION);
					}
				} catch (final SQLException e) {
					e.printStackTrace();
				}
			}
			this.connectionManager.returnToPool(connection);
		}

		return this.databaseVersion;
	}

}
