package com.jaysquared.openflights.webservice;

/**
 * 
 */

/**
 * @author michaelkuck
 * 
 */
public class Version {

	final public static int MAJOR = 0;
	final public static int MINOR = 5;
	final public static int PATCH = 0;

	/**
	 * Returns the full version information as a string. Version numbers separated by ".".
	 * 
	 * @return Full version number as String.
	 */
	final public static String fullVersionAsString()
	{
		return MAJOR + "." + MINOR + "." + PATCH;
	}

}
