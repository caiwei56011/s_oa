package com.jsoft.oa.admin.identity.dao.impl;

import java.util.List;

import com.jsoft.oa.admin.identity.dao.RoleDao;
import com.jsoft.oa.admin.identity.entity.Role;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.impl.HibernateDaoImpl;
/**
 * 角色表的访问接口实现类
 * @author Administrator
 * @date 2016年8月16日 下午8:32:55 
 * @version V1.0
 */
public class RoleDaoImpl extends HibernateDaoImpl implements RoleDao{

	/**
	 * 分页查询用户列表
	 * @param pageModel
	 * @return
	 */
	@Override
	public List<Role> getRoleByPage(PageModel pageModel) {
		String hql = "select r from Role r";
		return this.findByPage(hql, pageModel, null);
	}
	
}