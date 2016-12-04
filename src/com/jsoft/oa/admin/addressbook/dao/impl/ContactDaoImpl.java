package com.jsoft.oa.admin.addressbook.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jsoft.oa.admin.addressbook.dao.ContactDao;
import com.jsoft.oa.admin.addressbook.entity.Contact;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.impl.HibernateDaoImpl;
import com.jsoft.oa.core.pojo.ContactBean;

/**
 * 联系人表的访问接口实现类
 * @author jack
 * @version V1.0
 */
@SuppressWarnings("unchecked")
public class ContactDaoImpl extends HibernateDaoImpl implements ContactDao{
	/**
	 * 分页查询联系人
	 * @param pageModel 分页实体
	 * @param contact 联系组实体
	 * @return 联系人集合
	 */
	public List<Contact> getContactByPage(PageModel pageModel, Contact contact)
	{
		StringBuilder hql = new StringBuilder(); 
		hql.append("select c from Contact c where 1=1");
		List<Object> params = new ArrayList<Object>();
		if( contact != null && contact.getContactGroup() != null &&contact.getContactGroup().getId() != null)
		{
			hql.append(" and c.contactGroup.id=?");
			params.add(contact.getContactGroup().getId());
		}
		return this.findByPage(hql.toString(), pageModel, params);
	}
	/**
	 * 查询需要导出的联系人
	 * @param contact 联系人实体（contact.contactGroup.id）
	 * @return
	 */
	public List<ContactBean> getContacts(Contact contact)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new com.jsoft.oa.core.pojo.ContactBean(c) from Contact c where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		/** 添加查询条件 */
		if (contact != null){
			if (contact.getContactGroup() != null && contact.getContactGroup().getId() != null
					&& contact.getContactGroup().getId() > 0){
				hql.append(" and c.contactGroup.id = ?");
				params.add(contact.getContactGroup().getId());
			}
		}
		hql.append(" order by c.id asc");
		return (List<ContactBean>)this.find(hql.toString(), params.toArray());
	}
}