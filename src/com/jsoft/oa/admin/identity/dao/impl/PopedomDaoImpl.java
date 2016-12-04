package com.jsoft.oa.admin.identity.dao.impl;

import java.util.List;
import java.util.Map;

import com.jsoft.oa.admin.identity.dao.PopedomDao;
import com.jsoft.oa.core.dao.impl.HibernateDaoImpl;
/**
 * 权限表的访问接口实现类
 * @author Administrator
 * @date 2016年8月16日 下午8:32:55 
 * @version V1.0
 */
@SuppressWarnings("unchecked")
public class PopedomDaoImpl extends HibernateDaoImpl implements PopedomDao{
	/**
	 * 查询当前角色在当前模块下已经绑定的操作
	 * @param id 角色id
	 * @param moduleCode 模块code
	 * @return 操作code集合
	 */
	
	public List<String> getOperaCodeByRoleIdAndModuleCode(Long id, String moduleCode)
	{
		String hql = "select opera.code from Popedom where role.id = ? and module.code= ? order by opera.code asc";
		return (List<String>) this.find(hql, new Object[]{id, moduleCode});
	}
	/**
	 * 根据角色id和模块code(8位)删除权限
	 * @param roleId 角色id
	 * @param moduleCode 模块code
	 */
	public void delete(Long roleId, String moduleCode)
	{
		String hql = "delete from Popedom where role.id = ? and module.code = ?";
		this.bulkUpdate(hql, new Object[]{roleId, moduleCode});
	}
	/**
	 * 根据用户id查询对应角色id，再根据角色id查询对应的8位模块code
	 * @param userId
	 * @return
	 */
	public List<String> getModuleCodeByUserId(String userId)
	{
		StringBuilder hql = new StringBuilder();
		/** set集合类型的属性需要显式的写join语句 */
		hql.append("select a.module.code from Popedom a where a.role.id in ")
		   .append("(select b.id from Role b inner join b.users c where c.userId = ?) ")
		   .append("group by a.module.code ")
		   .append("order by a.module.code asc");
		return (List<String>) this.find(hql.toString(), new Object[]{userId});
	}
	/**
	 * 根据用户id查询所有的角色id，再根据角色id查询所有的12位权限code
	 * @param userId
	 * @return list集合
	 */
	public List<String> getOperaCodeByUserId(String userId)
	{
		StringBuilder hql = new StringBuilder();
		/** set集合类型的属性需要显式的写join语句 */
		hql.append("select a.opera.code from Popedom a where a.role.id in ")
		   .append("(select b.id from Role b inner join b.users c where c.userId = ?) ")
		   .append("group by a.opera.code ")
		   .append("order by a.opera.code asc");
		return (List<String>) this.find(hql.toString(), new Object[]{userId});
	}
}