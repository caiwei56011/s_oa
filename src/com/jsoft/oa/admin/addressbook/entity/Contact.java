package com.jsoft.oa.admin.addressbook.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * 联系人实体
 * @author Administrator
 * @date 2016年8月16日 下午7:32:54 
 * @version V1.0
 */
@Entity @Table(name="OA_AB_CONSTACT")
public class Contact implements Serializable {

	private static final long serialVersionUID = -6012306594204509328L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	@Column(name="NAME", length=50)
	private String name;
	@Column(name="SEX")
	private Short sex = 1;
	@Column(name="BIRTHDAY")
	private Date birthday;
	@Column(name="PHONE", length=50)
	private String phone;
	@Column(name="EMAIL", length=50)
	private String email;
	@Column(name="QQ_NUM", length=50)
	private String qqNum;
	/** 联系人与联系组存在多对一关联(N-1) */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=ContactGroup.class)
	@JoinColumn(name="GROUP_ID", referencedColumnName="ID")
	private ContactGroup contactGroup;
	
	/** setter and getter method */
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
	public Short getSex() {
		return sex;
	}
	public void setSex(Short sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQqNum() {
		return qqNum;
	}
	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}
	public ContactGroup getContactGroup() {
		return contactGroup;
	}
	public void setContactGroup(ContactGroup contactGroup) {
		this.contactGroup = contactGroup;
	}
}