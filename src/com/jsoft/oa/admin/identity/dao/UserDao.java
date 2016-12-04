package com.jsoft.oa.admin.identity.dao;

import java.util.List;
import java.util.Map;

import com.jsoft.oa.admin.identity.entity.Role;
import com.jsoft.oa.admin.identity.entity.User;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.HibernateDao;
/**
 * 用户表的访问接口
 * @author Administrator
 * @date 2016年8月16日 下午8:32:55 
 * @version V1.0
 */
public interface UserDao extends HibernateDao{

	/**
	 * 根据md5加密后的用户id获取用户
	 * @param userId md5加密后的userId
	 * @return 用户
	 */
	User getUser(String userId);

	/**
	 * 多条件分页查询用户
	 * @param user 用户查询条件
	 * @param pageModel  分页参数
	 * @return 用户集合
	 */
	List<User> getUserByPage(User user, PageModel pageModel);

	/**
	 * 获取联想(模糊查询)的用户name
	 * @param userName
	 * @return list集合
	 */
	List<String> getUserName(String userName);

	/**
	 * 获取用户登录名
	 * @param userId 表单中填写的登录名
	 * @return
	 */
	String getUserId(String userId);

	/**
	 * 批量删除用户
	 * @param userIds 用户登录名
	 */
	void deleteUser(String[] userIds);

	/**
	 * 根据角色id查询用户（当前角色已绑定的用户）
	 * @param pageModel 分页实体
	 * @param roleId 角色id
	 * @return 用户集合
	 */
	List<User> getUserByPageAndRoleId(PageModel pageModel, Long roleId);

	/**
	 * 获取未绑定到当前角色的用户
	 * @param pageModel 分页实体
	 * @param roleId 角色id
	 * @return
	 */
	List<User> getUserByPageAndNotRoleId(PageModel pageModel, Long roleId);

	
}