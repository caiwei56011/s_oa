package com.jsoft.oa.admin.identity.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * 模块实体类
 * @author Administrator
 * @date 2016年8月16日 下午1:07:34 
 * @version V1.0
 */
@Entity @Table(name="OA_ID_MODULE")
public class Module implements Serializable{

	private static final long serialVersionUID = -1747814380560896329L;
	/*
	CODE	VARCHAR2(100)	代码	PK主键由系统自动生成
	(0001...0002)四位为模块;
	(00010001..)八位为操作
	NAME	VARCHAR2(50)	名称	
	URL	VARCHAR2(100)	操作链接	
	REMARK	VARCHAR2(500)	备注	
	MODIFIER	VARCHAR2(50)	修改人	FK(OA_ID_USER)
	MODIFY_DATE	DATE	修改时间	
	CREATER	VARCHAR2(50)	创建人	FK(OA_ID_USER)
	CREATE_DATE	DATE	创建时间	
	*/
	@Id 
	@Column(name="code", length=100)
	private String code;
	@Column(name="name", length=50)
	private String name;
	@Column(name="url", length=100)
	private String url;
	@Column(name="remark", length=500)
	private String remark;
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="modifier", referencedColumnName="user_id")
	private User modifier;
	@Column(name="modify_date")
	private Date modifyDate;
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="creater",referencedColumnName="user_id")
	private User creater;
	@Column(name="create_date")
	private Date createDate;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
