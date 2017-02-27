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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.feinno.framework.common.domain.AbstractEntity;

/**
 * 系统资源表 Entity
 *
 * Date: 2015-06-01 13:59:43
 *
 * @author Acooly Code Generator
 */
@Entity
@Table(name = "sys_resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysResource extends AbstractEntity {
	/** 主键ID */
	private Long id;

	/** 父ID */
	private Long parentId;

	/** 资源名称 */
	private String title;

	/** 资源类型 */
	private String restype;

	/** 资源值 */
	private String resString;

	/** 权限值 */
	private String permissionValue;

	/** 描述 */
	private String descn;

	/** 状态 */
	private Integer status;

	/** 排序 */
	private Long sortNum;

	/** 创建时间 */
	private Date createDate;

	/** 子资源 */
	private Set<SysResource> subResource;

	private Long classify;

	public Long getClassify() {
		return classify;
	}

	public void setClassify(Long classify) {
		this.classify = classify;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRestype() {
		return this.restype;
	}

	public void setRestype(String restype) {
		this.restype = restype;
	}

	public String getResString() {
		return this.resString;
	}

	public void setResString(String resString) {
		this.resString = resString;
	}

	public String getPermissionValue() {
		return this.permissionValue;
	}

	public void setPermissionValue(String permissionValue) {
		this.permissionValue = permissionValue;
	}

	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getSortNum() {
		return sortNum;
	}

	public void setSortNum(Long sortNum) {
		this.sortNum = sortNum;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId")
	@OrderBy("sortNum ASC")
	public Set<SysResource> getSubResource() {
		return subResource;
	}

	public void setSubResource(Set<SysResource> subResource) {
		this.subResource = subResource;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
