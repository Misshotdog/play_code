package org.mi.core.domain.security;


import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OrderBy;

import com.feinno.framework.common.domain.AbstractEntity;

/**
 * 系统角色表 Entity
 *
 *
 * @author miss_hotdog
 */
@Entity
@Table(name = "sys_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysRole extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3590953684737984164L;

	/** 主键ID */
	private Long id;
	
	/** 角色名称 */
	private String roleName;
	
	/** 描述信息 */
	private String descn;
	
	/** 包含的用户 */
	private Set<SysUser> users;

	private Date createDate;
	private Date updateDate;

	
	/** 包含的资源 */
	private Set<SysResource> rescs;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId(){
		return this.id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	public String getRoleName(){
		return this.roleName;
	}
	
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}
	public String getDescn(){
		return this.descn;
	}
	
	public void setDescn(String descn){
		this.descn = descn;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@OneToMany(mappedBy = "role", targetEntity = SysUser.class, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@LazyCollection(value = LazyCollectionOption.EXTRA)
	public Set<SysUser> getUsers() {
		return users;
	}

	public void setUsers(Set<SysUser> users) {
		this.users = users;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY, targetEntity = SysResource.class)
	@JoinTable(name = "sys_role_resc", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = @JoinColumn(name = "RESC_ID"))
	@OrderBy(clause = "RESC_ID")
	@LazyCollection(value = LazyCollectionOption.EXTRA)
	public Set<SysResource> getRescs() {
		return rescs;
	}

	public void setRescs(Set<SysResource> rescs) {
		this.rescs = rescs;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}


}
