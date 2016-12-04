package com.jsoft.oa.admin.addressbook.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 * 联系组实体
 * @author Administrator
 * @date 2016年8月16日 下午7:33:01 
 * @version V1.0
 */
@Entity @Table(name="OA_AB_GROUP")
public class ContactGroup implements Serializable {
	
	private static final long serialVersionUID = 6540897700449966067L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	@Column(name="NAME", length=50)
	private String name;
	@Column(name="REMARK", length=500)
	private String remark;
	@OneToMany(fetch=FetchType.LAZY, targetEntity=Contact.class, 
				mappedBy="contactGroup", cascade=CascadeType.REMOVE)
	private Set<Contact> contacts = new HashSet<Contact>();
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Set<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}
}