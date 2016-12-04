package com.jsoft.oa.admin.addressbook.dao;

import java.util.List;
import java.util.Map;

import com.jsoft.oa.admin.addressbook.entity.ContactGroup;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.HibernateDao;

/**
 * 联系组的访问接口
 * @author Administrator
 * @date 2016年8月16日 下午7:33:01 
 * @version V1.0
 */
public interface ContactGroupDao extends HibernateDao{

	/**
	 * 分页查询联系组
	 * @param pageModel 分页实体
	 * @return list集合
	 */
	List<ContactGroup> getContactGroupByPage(PageModel pageModel);

	/**
	 * 获取联系组列表,只id和name字段
	 * @return list集合
	 */
	List<Map<String, Object>> getContactGroupByIdAndName();
	
}