package com.jsoft.oa.admin.addressbook.dao;

import java.util.List;

import com.jsoft.oa.admin.addressbook.entity.Contact;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.HibernateDao;
import com.jsoft.oa.core.pojo.ContactBean;

/**
 * 联系人的访问接口
 * @author Administrator
 * @date 2016年8月16日 下午7:32:54 
 * @version V1.0
 */
public interface ContactDao extends HibernateDao{

	/**
	 * 分页查询联系人
	 * @param pageModel 分页实体
	 * @param contact 联系组实体
	 * @return 联系人集合
	 */
	List<Contact> getContactByPage(PageModel pageModel, Contact contact);

	/**
	 * 查询需要导出的联系人
	 * @param contact 联系人实体（contact.contactGroup.id）
	 * @return
	 */
	List<ContactBean> getContacts(Contact contact);
	
}