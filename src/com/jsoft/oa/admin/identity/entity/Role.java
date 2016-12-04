package com.jsoft.oa.admin.identity.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * 角色实体类
 * @author Administrator
 * @date 2016年8月15日 下午9:26:02 
 * @version V1.0
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity @Table(name="OA_ID_ROLE")
public class Role implements Serializable{

	private static final long serialVersionUID = -2685444084157531659L;
	/*
	ID	NUMBER	ID	PK
	NAME	VARCHAR2(50)	角色名字	
	REMARK	VARCHAR2(500)	备注	
	CREATER	VARCHAR2(50)	创建人	FK(OA_ID_USER)
	CREATE_DATE	DATE	创建时间	
	MODIFIER	VARCHAR2(50)	修改人	FK(OA_ID_USER)
	MODIFY_DATE	DATE	修改时间	
	*/
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="name",length=50)
	private String name;
	@Column(name="remark",length=500)
	private String remark;
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="creater",referencedColumnName="user_id")
	private User creater;
	@Column(name="create_date")
	private Date createDate;
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="modifier",referencedColumnName="user_id")
	private User modifier;
	@Column(name="modify_date")
	private Date modifyDate;
	
	/** 用户和角色的多对多关系*/
	/*
	ROLE_ID	NUMBER	角色ID	FK(OA_ID_ROLE)
	USER_ID	VARCHAR2(50)	管理员ID	FK(OA_ID_USER)
	*/
	@ManyToMany(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinTable(name="OA_ID_USER_ROLE",
				joinColumns=@JoinColumn(name="ROLE_ID",referencedColumnName="id"),
				inverseJoinColumns=@JoinColumn(name="USER_ID",referencedColumnName="user_id")//ManyToMany加了mappedBy()的一方
			)
	private Set<User> users = new HashSet<User>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public User getModifier() {
		return modifier;
	}

	public void setModifier(User modifier) {
		this.modifier = modifier;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	
	
	
}
