package com.jsoft.oa.admin.identity.dao;

import java.util.List;
import java.util.Map;

import com.jsoft.oa.core.dao.HibernateDao;
/**
 * 权限表的访问接口
 * @author Administrator
 * @date 2016年8月16日 下午8:32:55 
 * @version V1.0
 */
public interface PopedomDao extends HibernateDao{

	/**
	 * 查询当前角色在当前模块下已经绑定的操作
	 * @param id 角色id
	 * @param moduleCode 模块code
	 * @return 操作code集合
	 */
	List<String> getOperaCodeByRoleIdAndModuleCode(Long id, String moduleCode);

	/**
	 * 根据角色id和模块code(8位)删除权限
	 * @param roleId 角色id
	 * @param moduleCode 模块code
	 */
	void delete(Long roleId, String moduleCode);

	/**
	 * 根据用户id查询对应角色id，再根据角色id查询对应的8位模块code
	 * @param userId
	 * @return
	 */
	List<String> getModuleCodeByUserId(String userId);

	/**
	 * 根据用户id查询所有的角色id，再根据角色id查询所有的12位权限code
	 * @param userId
	 * @return list集合
	 */
	List<String> getOperaCodeByUserId(String userId);
	
}