package com.jaysquared.openflights.webservice;

/**
 * 
 */

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import com.michael_kuck.commons.Log;

/**
 * @author michaelkuck
 * 
 */
public class CmdLineParser {

	private static final String PARAM_HTTP = "http";
	private static final String PARAM_HTTPS = "https";
	private static final String PARAM_LOG = "log";
	private static final String PARAM_SQL = "sql";
	private static final String PARAM_SQLHOST = "sqlhost";
	private static final String PARAM_SQLPORT = "sqlport";
	private static final String PARAM_SQLUSER = "sqluser";
	private static final String PARAM_SQLDB = "sqldb";

	private static final String ENV_VAR_PASSWORD = "OPENFLIGHTS_SQL_PW";

	public static Settings parseArguments(final String[] args)
	{
		final Options options = new Options();
		options.addOption(PARAM_HTTP, true, "HTTP port for web service");
		options.addOption(PARAM_HTTPS, true, "HTTPS port for web service");
		options.addOption(PARAM_LOG, true, "Log level");
		options.addOption(PARAM_SQL, false, "SQL database");
		options.addOption(PARAM_SQLHOST, true, "SQL host");
		options.addOption(PARAM_SQLPORT, true, "SQL port");
		options.addOption(PARAM_SQLUSER, true, "SQL user");
		options.addOption(PARAM_SQLDB, true, "SQL database");

		final Settings settings = new Settings();
		final CommandLineParser cmdLineGnuParser = new PosixParser();
		try {
			final CommandLine cmdLine = cmdLineGnuParser.parse(options, args);
			if (cmdLine.hasOption(PARAM_HTTP)) {
				settings.setWebservicePortHTTP(Integer.valueOf(cmdLine.getOptionValue(PARAM_HTTP)));
			}
			if (cmdLine.hasOption(PARAM_HTTPS)) {
				settings.setWebservicePortHTTPS(Integer.valueOf(cmdLine.getOptionValue(PARAM_HTTPS)));
			}
			if (cmdLine.hasOption(PARAM_LOG)) {
				Log.setLogLevel(Log.Level.valueOf(cmdLine.getOptionValue(PARAM_LOG)));
			}
			if (cmdLine.hasOption(PARAM_SQL)) {

				// Check if password is set in environment variable. We dont want to set this using
				// a parameter (because of bash history, "ps" command etc)
				final String sqlPassword = System.getenv(ENV_VAR_PASSWORD);
				if (sqlPassword != null) {
					// System.out.println(sqlPassword);
					settings.setSqlPassword(sqlPassword);
				} else {
					Log.warning("SQL has been set to use, but no password is provided.");
					Log.warning("use 'export " + ENV_VAR_PASSWORD + "=password' first.");
				}
			}
			if (cmdLine.hasOption(PARAM_SQLHOST)) {
				settings.setSqlHost(cmdLine.getOptionValue(PARAM_SQLHOST));
			}
			if (cmdLine.hasOption(PARAM_SQLPORT)) {
				settings.setSqlPort(Integer.valueOf(cmdLine.getOptionValue(PARAM_SQLPORT)));
			}
			if (cmdLine.hasOption(PARAM_SQLUSER)) {
				settings.setSqlUser(cmdLine.getOptionValue(PARAM_SQLUSER));
			}
			if (cmdLine.hasOption(PARAM_SQLDB)) {
				settings.setSqlDatabase(cmdLine.getOptionValue(PARAM_SQLDB));
			}

		} catch (final ParseException e) {
			e.printStackTrace();
		}

		return settings;
	}

}
