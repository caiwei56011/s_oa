package com.jsoft.oa.admin.identity.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * 部门实体类
 * @author Administrator
 * @date 2016年8月15日 下午8:59:22 
 * @version V1.0
 */
@Entity @Table(name="OA_ID_DEPT") 
public class Dept implements Serializable{
	
	private static final long serialVersionUID = 6515325640675044252L;
	/*ID	NUMBER	编号	PK主键自增长

	NAME	VARCHAR2(50)	部门名称	
	REMARK	VARCHAR2(500)	备注	
	MODIFIER	VARCHAR2(50)	修改人	FK(OA_ID_USER)
	MODIFY_DATE	DATE	修改时间	
	CREATER	VARCHAR2(50)	创建人	FK(OA_ID_USER)
	CREATE_DATE	DATE	创建时间	*/
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)//主键、自增长
	@Column(name="id")	//列名
	private Long id;
	@Column(name="name",length=50)
	private String name;
	@Column(name="remark",length=500)
	private String remark;
	/*部门修改人和用户之间是多对一关系*/
	@ManyToOne(fetch=FetchType.LAZY, targetEntity = User.class)
	/*生成外键列*/
	@JoinColumn(name="modifier",referencedColumnName="user_id")
	private User modifier;
	@Column(name="modify_date")
	private Date modifyDate;
	/*部门创建人和用户之间是多对一关系*/
	@ManyToOne(fetch=FetchType.LAZY, targetEntity = User.class)
	/*生成外键列*/
	@JoinColumn(name="creater",referencedColumnName="user_id")
	private User creater;
	@Column(name="create_date")
	private Date createDate;
	
	
	
	/** getter and setter method*/
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
