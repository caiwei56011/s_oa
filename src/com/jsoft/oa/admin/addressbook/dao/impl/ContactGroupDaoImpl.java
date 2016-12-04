package com.jsoft.oa.admin.addressbook.dao.impl;

import java.util.List;
import java.util.Map;

import com.jsoft.oa.admin.addressbook.dao.ContactGroupDao;
import com.jsoft.oa.admin.addressbook.entity.ContactGroup;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.impl.HibernateDaoImpl;

/**
 * 联系组表的访问接口实现类
 * @author jack
 * @version V1.0
 */
public class ContactGroupDaoImpl extends HibernateDaoImpl implements ContactGroupDao{

	/**
	 * 分页查询联系组
	 * @param pageModel 分页实体
	 * @return list集合
	 */
	@Override
	public List<ContactGroup> getContactGroupByPage(PageModel pageModel) {
		String hql = "select cg from ContactGroup cg";
		return this.findByPage(hql, pageModel, null);
	}
	/**
	 * 获取联系组列表,只id和name字段
	 * @return list集合
	 */
	public List<Map<String, Object>> getContactGroupByIdAndName(){
		String hql = "select new Map(id as id, name as name) from ContactGroup";
		return this.find(hql);
	}
}