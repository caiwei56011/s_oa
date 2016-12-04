package com.jsoft.oa.admin.addressbook.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.jsoft.oa.admin.addressbook.dao.ContactDao;
import com.jsoft.oa.admin.addressbook.dao.ContactGroupDao;
import com.jsoft.oa.admin.addressbook.entity.Contact;
import com.jsoft.oa.admin.addressbook.entity.ContactGroup;
import com.jsoft.oa.admin.addressbook.service.AddressbookService;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.exception.OAException;
import com.jsoft.oa.core.pojo.ContactBean;

/**
 * 通讯录管理业务处理接口实现类
 * @author jack
 * @version V1.0
 */
public class AddressbookServiceImpl implements AddressbookService{
	/**整合该模块下的所有数据访问接口,实现业务处理*/
	@Resource
	private ContactDao contactDao;
	@Resource
	private ContactGroupDao contactGroupDao;
	
	/** TODO ######################联系组管理业务处理########################## */
	/**
	 * 分页查询联系组
	 * @param pageModel 分页实体
	 * @return list集合
	 */
	@Override
	public List<ContactGroup> getContactGroupByPage(PageModel pageModel) {
		try {
			pageModel.setPageSize(4);
			return contactGroupDao.getContactGroupByPage(pageModel);
		} catch (Exception e) {
			throw new OAException("分页查询联系组时出现异常",e);
		}
	}
	
	/**
	 * 添加联系组
	 * @param contactGroup 联系组实体
	 */
	public void addContactGroup(ContactGroup contactGroup){
		try {
			contactGroupDao.save(contactGroup);
		} catch (Exception e) {
			throw new OAException("添加联系组时出现异常",e);
		}
	}
	/**
	 * 主键查询联系组
	 * @param contactGroup 联系组实体
	 * @return 
	 */
	public ContactGroup getContactGroup(ContactGroup contactGroup){
		try {
			if(contactGroup != null && contactGroup.getId() != null){
				return contactGroupDao.get(ContactGroup.class, contactGroup.getId());
			}else{
				return null;
			}
		} catch (Exception e) {
			throw new OAException("主键查询联系组时出现异常",e);
		}
	}
	/**
	 * 修改联系组
	 * @param contactGroup 联系组实体
	 * @return
	 */
	public void updateContactGroup(ContactGroup contactGroup){
		try {
			if(contactGroup != null && contactGroup.getId() != null)
			contactGroupDao.update(contactGroup);
		} catch (Exception e) {
			throw new OAException("修改联系组时出现异常",e);
		}
	}
	/**
	 * 删除联系组
	 * @param contactGroupIds 
	 */
	public void deleteContactGroup(String[] contactGroupIds){
		try {
			for (String id : contactGroupIds) {
				/** 配置了级联删除联系人，必须获取持久化对象进行删除 */
				ContactGroup contactGroup = contactGroupDao.get(ContactGroup.class, (long)Integer.valueOf(id));
				contactGroupDao.delete(contactGroup);
			}
		} catch (Exception e) {
			throw new OAException("删除联系组时出现异常",e);
		}
	}
	
	/**
	 * 获取联系组列表,只id和name字段
	 * @return list集合
	 */
	public List<Map<String, Object>> getContactGroupByIdAndName(){
		try {
			List<Map<String, Object>> list = contactGroupDao.getContactGroupByIdAndName();
			/** 添加创建dtree时需要的参数pid */
			for (Map<String, Object> map : list) {
				map.put("pid", 0);
			}
			return list;
		} catch (Exception e) {
			throw new OAException("获取联系组列表时出现异常",e);
		}
	}
	/**
	 * 分页查询联系人
	 * @param pageModel 分页实体
	 * @param contact 联系组实体
	 * @return 联系人集合
	 */
	public List<Contact> getContactByPage(PageModel pageModel, Contact contact)
	{
		try {
			List<Contact> contacts = contactDao.getContactByPage(pageModel, contact);
			/** 加载延迟加载的数据 */
			for (Contact c : contacts) {
				c.getContactGroup().getName();
			}
			return contacts;
		} catch (Exception e) {
			throw new OAException("分页查询联系人时出现异常", e);
		}
	}
	/**
	 * 添加联系人
	 * @param contact 联系人实体
	 */
	public void addContact(Contact contact)
	{
		try {
			contactDao.save(contact);
		} catch (Exception e) {
			throw new OAException("添加联系人时出现异常", e);
		}
	}
	/**
	 * 主键查询联系人
	 * @param contact 联系人实体
	 * @return
	 */
	public Contact getContact(Contact contact)
	{
		try {
			 Contact result = contactDao.get(Contact.class, contact.getId());
			/** 加载延迟加载的属性 */
			 result.getContactGroup().getId();
			 return result;
		} catch (Exception e) {
			throw new OAException("主键查询联系人时出现异常", e);
		}
	}
	/**
	 * 修改联系人
	 * @param contact
	 */
	public void updateContact(Contact contact)
	{
		try {
			contactDao.update(contact);
		} catch (Exception e) {
			throw new OAException("修改联系人时出现异常", e);
		}
	}
	/**
	 * 删除联系人
	 * @param contactIds
	 */
	public void deleteContact(String[] contactIds)
	{
		try {
			for (String id : contactIds) {
				Contact contact = new Contact();
				contact.setId((long)Integer.valueOf(id));
				contactDao.delete(contact);
			}
		} catch (Exception e) {
			throw new OAException("删除联系人时出现异常", e);
		}
	}
	/**
	 * 查询需要导出的联系人
	 * @param contact 联系人实体（contact.contactGroup.id）
	 * @return
	 */
	public List<ContactBean> getContacts(Contact contact)
	{
		try {
			return contactDao.getContacts(contact);
		} catch (Exception e) {
			throw new OAException("查询需要导出的联系人时出现异常", e);
		}
	}
	/**
	 * 保存Excel联系人数据到数据库
	 * @param contactGroup 
	 * @param excelData
	 */
	public void saveExcel(ContactGroup contactGroup, List<List<Object>> excelData)
	{
		try {
			/** 循环Excel中的数据 */
			for (List<Object> rowData : excelData){
				// rowData --> Contact
				Contact contact = new Contact();
				contact.setName(rowData.get(0).toString());
				contact.setSex(rowData.get(1).toString().equals("男") ? (short)1 : (short)2);
				contact.setPhone(rowData.get(2).toString());
				contact.setEmail(rowData.get(3).toString());
				contact.setQqNum(rowData.get(4).toString());
				contact.setBirthday((Date)rowData.get(5));
				contact.setContactGroup(contactGroup);
				this.addContact(contact);
			}
		} catch (Exception e) {
			throw new OAException("保存Excel数据到数据库时出现异常", e);
		}
	}
}
