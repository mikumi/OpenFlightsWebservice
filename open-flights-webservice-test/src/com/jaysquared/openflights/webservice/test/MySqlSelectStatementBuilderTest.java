/**
 * 
 */
package com.jaysquared.openflights.webservice.test;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jaysquared.openflights.webservice.dbconnection.MySqlSelectStatementBuilder;
import com.michael_kuck.commons.Log;

/**
 * @author michaelkuck
 * 
 */
public class MySqlSelectStatementBuilderTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public final void testSelectAllFields()
	{
		MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder("test_table");
		String statement = statementBuilder.getStatement();
		Log.verbose(statement);
		assertTrue("Not a correct statement", statement.equals("SELECT * FROM test_table"));
	}

	@Test
	public final void testSelectOneField()
	{
		MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder("test_table");
		statementBuilder.addSelectField("field1");
		String statement = statementBuilder.getStatement();
		Log.verbose(statement);
		assertTrue("Not a correct statement", statement.equals("SELECT field1 FROM test_table"));
	}

	@Test
	public final void testSelectMultipleFields()
	{
		MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder("test_table");
		statementBuilder.addSelectFields(new String[] { "field1", "field2", "field3" });
		String statement = statementBuilder.getStatement();
		Log.verbose(statement);
		assertTrue("Not a correct statement", statement.equals("SELECT field1,field2,field3 FROM test_table"));
	}

	@Test
	public final void testWhereOneFieldWithPlaceholder()
	{
		MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder("test_table");
		statementBuilder.addWhereFieldWithPlaceholder("field1");
		String statement = statementBuilder.getStatement();
		Log.verbose(statement);
		assertTrue("Not a correct statement", statement.equals("SELECT * FROM test_table WHERE field1=?"));
	}

	@Test
	public final void testWhereOneFieldWithValue()
	{
		MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder("test_table");
		statementBuilder.addWhereField("field1", "value1");
		String statement = statementBuilder.getStatement();
		Log.verbose(statement);
		assertTrue("Not a correct statement", statement.equals("SELECT * FROM test_table WHERE field1='value1'"));
	}

	@Test
	public final void testWhereMultipleFields()
	{
		MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder("test_table");
		statementBuilder.addWhereField("field1", "value1");
		statementBuilder.addWhereFieldWithPlaceholder("field2");
		statementBuilder.addWhereField("field3", "value3");
		statementBuilder.addWhereFieldsWithPlaceholder(new String[] { "field4", "field5" });
		String statement = statementBuilder.getStatement();
		Log.verbose(statement);
		assertTrue(
				"Not a correct statement",
				statement
						.equals("SELECT * FROM test_table WHERE field5=? AND field4=? AND field3='value3' AND field2=? AND field1='value1'"));
	}

	@Test
	public final void testToString()
	{
		MySqlSelectStatementBuilder statementBuilder = new MySqlSelectStatementBuilder("test_table");
		assertTrue("toString() should return the same as getStatement()",
				statementBuilder.getStatement().equals(statementBuilder.toString()));
	}

}
