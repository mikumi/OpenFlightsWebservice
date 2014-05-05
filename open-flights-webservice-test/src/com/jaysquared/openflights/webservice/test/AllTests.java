package com.jaysquared.openflights.webservice.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AirlineDatabaseTest.class, MySqlSelectStatementBuilderTest.class })
public class AllTests {

}
