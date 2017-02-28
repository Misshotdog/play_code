package org.mi.core.domain.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.feinno.framework.common.domain.AbstractEntity;

/** 
 * 
 * File Name:SysUserRole.java 
 * Package Name:com.feinno.bms.core.domain.sys 
 * Date:2016年7月24日下午1:15:31 
 * Copyright (c) BeiJinghtdf 2016, All Rights Reserved. 
 * @author miss_hotdog
*/
@Entity
@Table(name = "sys_user_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysUserRole extends AbstractEntity{
	
	private Long roleId;
	private Long userId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="role_id")
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	

}
