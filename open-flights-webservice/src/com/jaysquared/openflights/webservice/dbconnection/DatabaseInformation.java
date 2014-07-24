/**
 *
 */
package com.jaysquared.openflights.webservice.dbconnection;

import com.jolbox.bonecp.BoneCP;
import com.michael_kuck.commons.Log;
import com.michael_kuck.commons.mysql.MySqlHelper;
import com.michael_kuck.commons.mysql.MySqlSelectStatementBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author michaelkuck
 */
public class DatabaseInformation {

    final public static String TABLE_INFO             = "of_info";
    final public static String FIELD_DATABASE_VERSION = "database_version";

    private int databaseVersion = -1;
    private long lastDatabaseVersionCheck = 0;
    private final BoneCP connectionPool;

    /**
     *
     */
    public DatabaseInformation(final BoneCP connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * @return
     */
    public int databaseVersion() {
        final long timeSinceLastCheck = System.currentTimeMillis() - lastDatabaseVersionCheck;
        if ((this.databaseVersion < 0) || (timeSinceLastCheck > 1000 * 60)) {
            Connection connection = null;
            try {
                connection = this.connectionPool.getConnection();
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
                this.lastDatabaseVersionCheck = System.currentTimeMillis();
                statement.close();
            } catch (final SQLException e) {
                e.printStackTrace();
            } finally {
                MySqlHelper.closeConnection(connection);
            }
        }

        return this.databaseVersion;
    }

}
