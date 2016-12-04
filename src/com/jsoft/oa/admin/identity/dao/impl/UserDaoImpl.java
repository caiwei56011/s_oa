package com.jsoft.oa.admin.identity.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jsoft.oa.admin.identity.dao.UserDao;
import com.jsoft.oa.admin.identity.entity.Role;
import com.jsoft.oa.admin.identity.entity.User;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.impl.HibernateDaoImpl;

/**
 * 用户表的访问接口实现类
 * @author Administrator
 * @date 2016年8月16日 下午8:32:55 
 * @version V1.0
 */
@SuppressWarnings("unchecked")
public class UserDaoImpl extends HibernateDaoImpl implements UserDao{

	/**
	 * 根据md5加密后的用户id获取用户
	 * @param userId md5加密后的userId
	 * @return 用户
	 */
	@Override
	public User getUser(String userId) {
		String hql = "select u from User u where MD5(userId) =?";
		return this.findUniqueEntity(hql, userId);
	}

	/**
	 * 多条件分页查询用户
	 * @param user 用户查询条件
	 * @param pageModel  分页参数
	 * @return 用户集合
	 */
	@Override
	public List<User> getUserByPage(User user, PageModel pageModel) {
		/** 定义StringBuilder拼接hql语句 */
		StringBuilder hql = new StringBuilder();
		/** 定义list集合保存查询条件参数 */
		List<Object> params = new ArrayList<Object>();
		hql.append("select u from User u where 1=1 ");
		if(user != null)
		{
			/** 姓名 */
			if(!StringUtils.isEmpty(user.getName()))
			{
				hql.append(" and u.name like ?");
				params.add("%" + user.getName() + "%");
			}
			/** 手机号 */
			if(!StringUtils.isEmpty(user.getPhone()))
			{
				hql.append(" and u.phone like ?");
				params.add("%" + user.getPhone() + "%");
			}
			/** 部门id */
			if(user.getDept() != null && user.getDept().getId() != null && user.getDept().getId() > 0)
			{
				hql.append(" and u.dept.id=?");
				params.add(user.getDept().getId());
			}
		}
		/** 按创建时间升序排序*/
		hql.append(" order by u.createDate asc");
		
		return this.findByPage(hql.toString(), pageModel,params);
	}

	
	/**
	 * 获取联想(模糊查询)的用户name
	 * @param userName
	 * @return list集合
	 */
	@Override
	public List<String> getUserName(String userName) {
		String hql = "select u.name from User u where u.name like ?";
		return (List<String>) this.find(hql, new Object[]{"%" + userName + "%"});
	}

	/**
	 * 获取用户登录名
	 * @param userId 表单中填写的登录名
	 * @return
	 */
	@Override
	public String getUserId(String userId) {
		String hql = "select userId from User where userId = ?";
		return this.findUniqueEntity(hql, userId);
	}

	/**
	 * 批量删除用户
	 * @param userIds 用户登录名
	 */
	@Override
	public void deleteUser(String[] userIds) {
		StringBuilder hql = new StringBuilder();
		hql.append("delete from User where userId in(");
		for(int i = 0; i < userIds.length; i++)
		{
			hql.append(i == 0 ? "?" : ",?");
		}	
		hql.append(")");
		this.bulkUpdate(hql.toString(), userIds);
	}
	
	/**
	 * 根据角色id查询用户（当前角色已绑定的用户）
	 * @param pageModel 分页实体
	 * @param roleId 角色id
	 * @return 用户集合
	 */
	public List<User> getUserByPageAndRoleId(PageModel pageModel, Long roleId)
	{
		String hql = "select u from User u inner join u.roles r where r.id=? order by u.createDate asc";
		List<Object> params = new ArrayList<Object>();
		params.add(roleId);
		return this.findByPage(hql, pageModel, params);
	}
	/**
	 * 获取未绑定到当前角色的用户
	 * @param pageModel 分页实体
	 * @param roleId 角色id
	 * @return
	 */
	public List<User> getUserByPageAndNotRoleId(PageModel pageModel, Long roleId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select u from User u where u.userId not in ")
		   .append("(select u2.userId from User u2 inner join u2.roles r where r.id=?)")
		   .append("order by u.createDate asc");
		List<Object> params = new ArrayList<Object>();
		params.add(roleId);
		return this.findByPage(hql.toString(), pageModel, params);
	}

}