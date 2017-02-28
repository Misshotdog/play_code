package org.mi.web.controller.common;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.feinno.framework.common.dao.support.PageInfo;

/**
 * com.feinno.bms.web.controller. 
 * Copyright (c) BeiJinghtdf 2016
 * 
 */
public class CommonController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());


	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor dateEditor = new CustomDateEditor(format, true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}

	/**
	 * 分页
	 * 
	 * @param request
	 * @param pageSize
	 * @return
	 */
	protected PageInfo getPageInfo(HttpServletRequest request, int pageSize) {
		int currentPage = 1;
		if (StringUtils.isNotBlank(request.getParameter("pageNo"))) {
			try {
				currentPage = Integer.parseInt(request.getParameter("pageNo"));
			} catch (Exception ex) {
			}
		}
		if(StringUtils.isNotBlank(request.getParameter("pageSize"))){
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		PageInfo pageInfo = new PageInfo(pageSize, currentPage);
		return pageInfo;
	}

	/**
	 * 分页
	 * 
	 * @param request
	 * @return
	 */
	protected PageInfo getPageInfo(HttpServletRequest request) {
		return getPageInfo(request, 10);
	}

	/**
	 * <p>
	 * Description: 分页对象
	 * </p>
	 * 
	 * @param request
	 * @param pageSize
	 * @return
	 */
	protected Pageable getPageable(HttpServletRequest request, int pageSize) {
		int currentPage = 1;
		if (StringUtils.isNotBlank(request.getParameter("pageNo"))) {
			try {
				currentPage = Integer.parseInt(request.getParameter("pageNo"));
			} catch (Exception ex) {
			}
		}
		return new PageRequest((currentPage - 1), pageSize);
	}

	/**
	 * <p>
	 * Description:分页对象
	 * </p>
	 * 
	 * @param request
	 * @return
	 */
	protected Pageable getPageable(HttpServletRequest request) {
		return getPageable(request, 10);
	}

	protected <T> PageInfo<T> asPageInfo(Page<T> page) {
		PageInfo<T> pageInfo = new PageInfo<T>();
		pageInfo.setCountOfCurrentPage(page.getSize());
		pageInfo.setCurrentPage(page.getNumber() + 1);
		pageInfo.setTotalCount(page.getTotalElements());
		pageInfo.setPageResults(page.getContent());
		return pageInfo;
	}


}
