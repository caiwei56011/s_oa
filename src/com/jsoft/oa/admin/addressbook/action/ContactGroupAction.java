package com.jsoft.oa.admin.addressbook.action;

import java.util.List;

import com.jsoft.oa.admin.addressbook.entity.ContactGroup;

/**
 * 联系组管理控制器
 * @author jack
 * @email wujunwei6@163.com
 * @date 2016年8月27日 下午8:02:12 
 * @version V1.0
 */
public class ContactGroupAction extends AddressbookAction {
	private static final long serialVersionUID = 5849831376414881506L;
	private ContactGroup contactGroup;
	private List<ContactGroup> contactGroups;
	private String contactGroupIds;
	
	/** 分页查询联系组 */
	public String selectContactGroup()
	{
		try {
			contactGroups = addressbookService.getContactGroupByPage(pageModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/** 添加联系组 */
	public String addContactGroup()
	{
		try{
			addressbookService.addContactGroup(contactGroup);
			setTip("添加成功");
		}catch (Exception e) {
			e.printStackTrace();
			setTip("添加失败");
		}
		return SUCCESS;
	}
	/** 显示修改联系组页面 */
	public String showUpdateContactGroup()
	{
		try{
			contactGroup = addressbookService.getContactGroup(contactGroup);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/** 修改联系组 */
	public String updateContactGroup()
	{
		try{
			addressbookService.updateContactGroup(contactGroup);
			setTip("修改成功");
		}catch (Exception e) {
			e.printStackTrace();
			setTip("修改失败");
		}
		return SUCCESS;
	}
	
	/** 删除联系组 */
	public String deleteContactGroup()
	{
		try{
			addressbookService.deleteContactGroup(contactGroupIds.split(","));
			setTip("删除成功");
		}catch (Exception e) {
			e.printStackTrace();
			setTip("删除失败");
		}
		return SUCCESS;
	}
	
	
	/** getter and setter method */
	public ContactGroup getContactGroup() {
		return contactGroup;
	}
	public void setContactGroup(ContactGroup contactGroup) {
		this.contactGroup = contactGroup;
	}
	public List<ContactGroup> getContactGroups() {
		return contactGroups;
	}
	public void setContactGroups(List<ContactGroup> contactGroups) {
		this.contactGroups = contactGroups;
	}
	public String getContactGroupIds() {
		return contactGroupIds;
	}
	public void setContactGroupIds(String contactGroupIds) {
		this.contactGroupIds = contactGroupIds;
	}
	
	
}
