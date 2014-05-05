/**
 * 
 */
package com.jaysquared.openflights.webservice.dbconnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author michaelkuck
 * 
 */
public class MySqlSelectStatementBuilder {

	final private static String PLACEHOLDER = "?";
	final private ArrayList<String> selectFields;
	final private String tableName;
	final private HashMap<String, String> whereFields;

	/**
	 * @param tableName
	 */
	public MySqlSelectStatementBuilder(String tableName)
	{
		this.selectFields = new ArrayList<String>();
		this.tableName = tableName;
		this.whereFields = new HashMap<String, String>();
	}

	/**
	 * @param fieldName
	 */
	public void addSelectField(String fieldName)
	{
		selectFields.add(fieldName);
	}

	/**
	 * @param fieldNames
	 */
	public void addSelectFields(String[] fieldNames)
	{
		for (String fieldName : fieldNames) {
			selectFields.add(fieldName);
		}
	}
	
	/**
	 * @param fieldName
	 * @param fieldValue
	 */
	public void addWhereField(String fieldName, String fieldValue)
	{
		whereFields.put(fieldName, "'" + fieldValue + "'");
	}

	/**
	 * @param fieldName
	 */
	public void addWhereFieldWithPlaceholder(String fieldName)
	{
		whereFields.put(fieldName, PLACEHOLDER);
	}

	public void addWhereFieldsWithPlaceholder(String[] fieldNames)
	{
		for (String fieldname : fieldNames) {
			whereFields.put(fieldname, PLACEHOLDER);
		}
	}

	/**
	 * @return
	 */
	public String getStatement()
	{
		StringBuilder statementStringBuilder = new StringBuilder(100);
		// select
		statementStringBuilder.append("SELECT ");
		if (selectFields.size() > 0) {
			Iterator<String> it = this.selectFields.iterator();
			while (it.hasNext()) {
				String fieldName = it.next();
				statementStringBuilder.append(fieldName);
				if (it.hasNext()) {
					statementStringBuilder.append(",");
				}
			}
		} else {
			statementStringBuilder.append("*");
		}
		// from
		statementStringBuilder.append(" FROM ");
		statementStringBuilder.append(tableName);
		// where
		if (whereFields.size() > 0) {
			statementStringBuilder.append(" WHERE ");
			Iterator<Map.Entry<String, String>> it = whereFields.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				statementStringBuilder.append(entry.getKey());
				statementStringBuilder.append("=");
				statementStringBuilder.append(entry.getValue());
				if (it.hasNext()) {
					statementStringBuilder.append(" AND ");
				}

			}
		}
		return statementStringBuilder.toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getStatement();
	}

}
