package org.mi.core.common.jdbc;
/**
 * Title:
 * <p>Description:</p>
 * Copyright (c) feinno 2013-2016
 * @author
 */
public class Condition {
	private String sql;
	private Object[] params;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
}
