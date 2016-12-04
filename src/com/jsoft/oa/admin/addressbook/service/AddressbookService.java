package com.jsoft.oa.admin.addressbook.service;

import java.util.List;
import java.util.Map;

import com.jsoft.oa.admin.addressbook.entity.Contact;
import com.jsoft.oa.admin.addressbook.entity.ContactGroup;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.pojo.ContactBean;

/**
 * 通讯录管理业务处理接口
 * @author Administrator
 * @date 2016年8月16日 下午9:04:48 
 * @version V1.0
 */
public interface AddressbookService {

	/** TODO ###################### 联系组管理业务处理 ######################## */
	/**
	 * 分页查询联系组
	 * @param pageModel 分页实体
	 * @return list集合
	 */
	List<ContactGroup> getContactGroupByPage(PageModel pageModel);

	/**
	 * 添加联系组
	 * @param contactGroup 联系组实体
	 */
	void addContactGroup(ContactGroup contactGroup);

	/**
	 * 主键查询联系组
	 * @param contactGroup 联系组实体
	 * @return 
	 */
	ContactGroup getContactGroup(ContactGroup contactGroup);

	/**
	 * 修改联系组
	 * @param contactGroup 联系组实体
	 * @return
	 */
	void updateContactGroup(ContactGroup contactGroup);

	/**
	 * 删除联系组
	 * @param contactGroupIds
	 */
	void deleteContactGroup(String[] contactGroupIds);

	/** TODO ###################### 联系人管理业务处理 ######################## */
	/**
	 * 获取联系组列表（id,name）
	 * @return list集合
	 */
	List<Map<String, Object>> getContactGroupByIdAndName();

	/**
	 * 分页查询联系人
	 * @param pageModel 分页实体
	 * @param contact 联系组实体
	 * @return 联系人集合
	 */
	List<Contact> getContactByPage(PageModel pageModel, Contact contact);

	/**
	 * 添加联系人
	 * @param contact 联系人实体
	 */
	void addContact(Contact contact);

	/**
	 * 主键查询联系人
	 * @param contact 联系人实体
	 * @return
	 */
	Contact getContact(Contact contact);

	/**
	 * 修改联系人
	 * @param contact
	 */
	void updateContact(Contact contact);

	/**
	 * 删除联系人
	 * @param contactIds 联系人id
	 */
	void deleteContact(String[] contactIds);

	/**
	 * 查询需要导出的联系人
	 * @param contact 联系人实体（contact.contactGroup.id）
	 * @return
	 */
	List<ContactBean> getContacts(Contact contact);

	/**
	 * 保存Excel数据到数据库
	 * @param contactGroup 联系组(id)
	 * @param excelData Excel中数据
	 */
	void saveExcel(ContactGroup contactGroup, List<List<Object>> excelData);

}
