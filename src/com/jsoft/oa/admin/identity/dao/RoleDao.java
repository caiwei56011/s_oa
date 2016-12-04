package com.jsoft.oa.admin.identity.dao;

import java.util.List;

import com.jsoft.oa.admin.identity.entity.Role;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.HibernateDao;
/**
 * 角色表的访问接口
 * @author Administrator
 * @date 2016年8月16日 下午8:32:55 
 * @version V1.0
 */
public interface RoleDao extends HibernateDao{

	/**
	 * 分页查询用户列表
	 * @param pageModel
	 * @return
	 */
	List<Role> getRoleByPage(PageModel pageModel);
	
}