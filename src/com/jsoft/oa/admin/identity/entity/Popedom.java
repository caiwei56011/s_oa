package com.jsoft.oa.admin.identity.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * 权限实体类
 * @author Administrator
 * @date 2016年8月16日 上午8:57:50 
 * @version V1.0
 */
@Entity @Table(name="OA_ID_POPEDOM")
public class Popedom implements Serializable{

	private static final long serialVersionUID = 4691579423622062370L;
	/*
	ID	NUMBER	ID	PK(自增长)
	MODULE_CODE	VARCHAR2(100)	模块代码	FK(OA_ID_MODULE)
	OPERA_CODE	VARCHAR2(100)	操作代码	FK(OA_ID_MODULE)
	ROLE_ID	NUMBER	角色	FK(OA_ID_ROLE)
	CREATER	VARCHAR2(50)	创建人	FK(OA_ID_USER)
	CREATE_DATE	DATE	创建时间	
	*/
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Module.class)
	@JoinColumn(name="module_code",referencedColumnName="code")
	private Module module;
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Module.class)
	@JoinColumn(name="opera_code",referencedColumnName="code")
	private Module opera;
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Role.class)
	@JoinColumn(name="role_id",referencedColumnName="id")
	private Role role;
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="creater",referencedColumnName="user_id")
	private User creater;
	@Column(name="create_date")
	private Date createDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public Module getOpera() {
		return opera;
	}
	public void setOpera(Module opera) {
		this.opera = opera;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
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
	
	
	
}
