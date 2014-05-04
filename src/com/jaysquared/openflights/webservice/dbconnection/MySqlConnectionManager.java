/**
 * 
 */
package com.jaysquared.openflights.webservice.dbconnection;

import java.sql.Connection;
import java.util.ArrayList;

import com.michael_kuck.commons.Log;

/**
 * @author michaelkuck
 * 
 */
public class MySqlConnectionManager {

	final private static int poolSizeLimit = 100;

	final private String host;
	final private int port;
	final private String username;
	final private String password;
	final private String database;

	final private ArrayList<Connection> connectionPool;

	/**
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @param database
	 */
	public MySqlConnectionManager(final String host, final int port, final String username, final String password,
			final String database)
	{
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.database = database;

		this.connectionPool = new ArrayList<Connection>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable
	{
		Log.verbose("Closing " + this.connectionPool.size() + " connections");
		for (final Connection connection : this.connectionPool) {
			connection.close();
		}
		super.finalize();
	}

	/**
	 * @return
	 */
	public synchronized Connection connectionFromPool()
	{
		final Connection connection;
		if (this.connectionPool.size() > 0) {
			connection = this.connectionPool.get(0);
			this.connectionPool.remove(0);
		} else {
			connection = MySqlHelper
					.getNewConnection(this.host, this.port, this.username, this.password, this.database);
		}
		return connection;
	}

	/**
	 * @param connection
	 */
	public synchronized void returnToPool(final Connection connection)
	{
		if (this.connectionPool.size() >= poolSizeLimit) {
			this.connectionPool.remove(0);
		}
		this.connectionPool.add(connection);
		Log.verbose("Connections in pool: " + this.connectionPool.size());
	}

}
