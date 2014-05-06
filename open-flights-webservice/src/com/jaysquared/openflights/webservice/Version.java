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
	final public static int MINOR = 3;
	final public static int PATCH = 3;

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
