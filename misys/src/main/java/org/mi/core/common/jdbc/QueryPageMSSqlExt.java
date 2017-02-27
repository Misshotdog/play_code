/**
 * Copyright (c) 2009 FEINNO, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * FEINNO, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with FEINNO.
 */
package org.mi.core.common.jdbc;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.feinno.framework.common.dao.jdbc.PagedJdbcTemplate;
import com.feinno.framework.common.dao.support.PageInfo;


/**
 * Title: SQL Server 分页
 * <p>Description:</p>
 * @author  on 2015年11月21日
 */
@Repository
public class QueryPageMSSqlExt {
	
	/** SQL Server 分页查询SQL模版 */
	private static final String MSSQL_PAGESQL_TEMPLATE = "select w2.* from  (SELECT TOP ${endNum} row_number() OVER(${order}) n, w1.* FROM (${sql}) w1 ) w2 where w2.n >=${startNum} ORDER BY w2.n ASC";
	
	public <T> void query(String sql, Map<String, Object> keyword, Map<String, Boolean> orderby, PageInfo<T> pageinfo,
			Class<T> dtoEntity, PagedJdbcTemplate pagedJdbcTemplate) {

		Condition conditon = getQuery(sql, keyword);
		String sqls = "select count(*) from (" + conditon.getSql() + ") A";
		String orders = getOrderBy(orderby);
		Object[] params = conditon.getParams();

		// 总记录数
		long totalCount = pagedJdbcTemplate.queryForLong(sqls, params);
		long totalPage = (totalCount % pageinfo.getCountOfCurrentPage() == 0 ? totalCount
				/ pageinfo.getCountOfCurrentPage() : totalCount / pageinfo.getCountOfCurrentPage() + 1);

		String corentsql = conditon.getSql();
		/* 分页 */
		long currentPage = pageinfo.getCurrentPage();
		if(currentPage > totalPage){
			currentPage = totalPage;
		}
		if(currentPage <= 0){
			currentPage = 1;
		}
		
		int startNum = pageinfo.getCountOfCurrentPage() * (pageinfo.getCurrentPage() - 1) + 1;
		int endNum = pageinfo.getCountOfCurrentPage() * (pageinfo.getCurrentPage());
		if (endNum > totalCount) {
			endNum = (int) totalCount;
		}
		
		String pageSql = MSSQL_PAGESQL_TEMPLATE ;
		pageSql = StringUtils.replace(pageSql, "${sql}", corentsql);
		pageSql = StringUtils.replace(pageSql, "${startNum}", String.valueOf(startNum));
		pageSql = StringUtils.replace(pageSql, "${endNum}", String.valueOf(endNum));
		pageSql = StringUtils.replace(pageSql, "${order}", orders);
		
		// 查询结果列表
		List<T> result = query(pageSql, params, dtoEntity, pagedJdbcTemplate);

		// 设置分布对象
		pageinfo.setPageResults(result);
		pageinfo.setTotalCount(totalCount);
		pageinfo.setTotalPage(totalPage);
		pageinfo.setCurrentPage((int)currentPage);

	}

	private <T> List<T> query(String corentsql, Object[] params, Class<T> dtoEntity, PagedJdbcTemplate pagedJdbcTemplate) {
		return pagedJdbcTemplate.query(corentsql, params, BeanPropertyRowMapper.newInstance(dtoEntity));
	}

	/**
	 * Title:
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param sql
	 * @param keyword
	 * @param orderby
	 * @return
	 */
	private Condition getQuery(String sql, Map<String, Object> keyword) {
		Condition conditon = new Condition();
		try {
			StringBuffer querysql = new StringBuffer();
			querysql.append(" SELECT * FROM ( ").append(sql).append(" ) B ");
			querysql.append(" WHERE 1 = 1 ");
			List<Object> params = new ArrayList<Object>();
			// 封裝SQL
			if(null !=keyword && !keyword.isEmpty()){
				for (String key : keyword.keySet()) {
					String[] keys = key.split("_");
					String columName = coverdColumName(keys[1]);
					if(columName.startsWith("_")){
						columName = columName.substring(1, columName.length());
					}
					// 操作符
					String oparation = keys[0];
					Object columValue = keyword.get(key);
					
					if ("EQ".equals(oparation)) {
						querysql.append(" and ").append(columName).append(" = ? ");
						params.add(columValue);
					} else if ("LT".equals(oparation)) {
						querysql.append(" and ").append(columName).append(" < ? ");
						params.add(columValue);
					} else if ("GT".equals(oparation)) {
						querysql.append(" and ").append(columName).append(" > ? ");
						params.add(columValue);
					} else if ("LTE".equals(oparation)) {
						querysql.append(" and ").append(columName).append(" <= ? ");
						params.add(columValue);
					} else if ("GTE".equals(oparation)) {
						querysql.append(" and ").append(columName).append(" >= ? ");
						params.add(columValue);
					} else if ("LIKE".equals(oparation)) {
						querysql.append(" and ").append(columName).append(" like ? ");
						params.add("%" + columValue + "%");
					} else if ("LLIKE".equals(oparation)) {
						querysql.append(" and ").append(columName).append(" like  ? ");
						params.add("%" + columValue);
					} else if ("RLIKE".equals(oparation)) {
						querysql.append(" and ").append(columName).append(" like  ? ");
						params.add(columValue + "%");
					}
				}
				
			}

			Object[] paramso = params.toArray();
			conditon.setSql(querysql.toString());
			conditon.setParams(paramso);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return conditon;
	}

	/**
	 * Title:將字符串轉成數據庫字段
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param columName
	 * @return
	 */
	public String coverdColumName(String columName) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < columName.length(); i++) {
			char c = columName.charAt(i);
			if (!Character.isLowerCase(c) && c!='.') {
				str.append("_");
			}
			str.append(c);
		}
		String strco = str.toString().toLowerCase();
		return strco;
	}
	
	private String getOrderBy(Map<String, Boolean> orderby) {
		StringBuffer ordersql = new StringBuffer();
		try {
			if (!orderby.isEmpty() && orderby.size() > 0) {
				ordersql.append(" order by ");
				int i = 1;
				for (String key : orderby.keySet()) {
					boolean orders = orderby.get(key);
					ordersql.append(coverdColumName(key) + " ");
					ordersql.append(orders ? "DESC" : "ASC");
					if (i < orderby.size()) {
						ordersql.append(",");
					}
					i++;
				}
			}else {
				ordersql.append(" order by id asc ");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return ordersql.toString();
	}
	
	/**
	 * 根据SQL查询返回LIST<Map<String,Object>>对象
	 * @param sql
	 * @param keyword
	 * @param orderby
	 * @param pageinfo
	 * @param pagedJdbcTemplate
	 */
	public PageInfo query(String sql, Map<String, Object> keyword, Map<String, Boolean> orderby, PageInfo<Map<String,Object>> pageinfo,
			PagedJdbcTemplate pagedJdbcTemplate) {

		Condition conditon = getQuery(sql, keyword);
		String sqls = "select count(*) from (" + conditon.getSql() + ") A";
		String orders = getOrderBy(orderby);
		Object[] params = conditon.getParams();

		// 总记录数
		long totalCount = pagedJdbcTemplate.queryForLong(sqls, params);
		long totalPage = (totalCount % pageinfo.getCountOfCurrentPage() == 0 ? totalCount
				/ pageinfo.getCountOfCurrentPage() : totalCount / pageinfo.getCountOfCurrentPage() + 1);

		String corentsql = conditon.getSql();
		/* 分页 */
		long currentPage = pageinfo.getCurrentPage();
		if(currentPage > totalPage){
			currentPage = totalPage;
		}
		if(currentPage <= 0){
			currentPage = 1;
		}
		
		int startNum = pageinfo.getCountOfCurrentPage() * (pageinfo.getCurrentPage() - 1) + 1;
		int endNum = pageinfo.getCountOfCurrentPage() * (pageinfo.getCurrentPage());
		if (endNum > totalCount) {
			endNum = (int) totalCount;
		}
		
		String pageSql = MSSQL_PAGESQL_TEMPLATE ;
		pageSql = StringUtils.replace(pageSql, "${sql}", corentsql);
		pageSql = StringUtils.replace(pageSql, "${startNum}", String.valueOf(startNum));
		pageSql = StringUtils.replace(pageSql, "${endNum}", String.valueOf(endNum));
		pageSql = StringUtils.replace(pageSql, "${order}", orders);
		
		// 查询结果列表
		List<Map<String,Object>> result = pagedJdbcTemplate.queryForList(pageSql,params);
		
		// 设置分布对象
		pageinfo.setPageResults(result);
		pageinfo.setTotalCount(totalCount);
		pageinfo.setTotalPage(totalPage);
		pageinfo.setCurrentPage((int)currentPage);
		return pageinfo;
	}
}
