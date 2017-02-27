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
 * Title:
 * <p>Description:</p>
 * Copyright (c) feinno 2013-2016
 */
@Repository
public class QueryPageExt {
	
	/** ORACLE 分页查询SQL模版 */
	private static final String ORACLE_PAGESQL_TEMPLATE = "SELECT * FROM (SELECT XX.*,rownum ROW_NUM FROM (${sql}) XX ) ZZ where ZZ.ROW_NUM BETWEEN ${startNum} AND ${endNum}";
	
	public <T> void query(String sql, Map<String, Object> keyword, Map<String, Boolean> orderby, PageInfo<T> pageinfo,
			Class<T> dtoEntity, PagedJdbcTemplate pagedJdbcTemplate) {

		Condition conditon = getQuery(sql, keyword, orderby);
		String sqls = "select count(*) from (" + conditon.getSql() + ") A";
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
		
		String pageSql = ORACLE_PAGESQL_TEMPLATE ;
		pageSql = StringUtils.replace(pageSql, "${sql}", corentsql);
		pageSql = StringUtils.replace(pageSql, "${startNum}", String.valueOf(startNum));
		pageSql = StringUtils.replace(pageSql, "${endNum}", String.valueOf(endNum));
		
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
	private Condition getQuery(String sql, Map<String, Object> keyword, Map<String, Boolean> orderby) {
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
					} else if ("LE".equals(oparation)) {
						querysql.append(" and ").append(columName).append(" <= ? ");
						params.add(columValue);
					} else if ("GE".equals(oparation)) {
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
			// 做排序
			if (!orderby.isEmpty() && orderby.size() > 0) {
				querysql.append(" order by ");
				int i = 1;
				for (String key : orderby.keySet()) {
					boolean orders = orderby.get(key);
					querysql.append(coverdColumName(key) + " ");
					querysql.append(orders ? "ASC" : "DESC");
					if (i < orderby.size()) {
						querysql.append(",");
					}
					i++;
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

}
