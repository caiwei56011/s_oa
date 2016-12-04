package com.jsoft.oa.admin.identity.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.jsoft.oa.admin.AdminConstant;
import com.jsoft.oa.admin.identity.entity.Dept;
import com.jsoft.oa.admin.identity.entity.Job;
import com.jsoft.oa.admin.identity.entity.User;
import com.jsoft.oa.core.common.web.PageModel;
import com.opensymphony.xwork2.ActionContext;

/**
 * 用户控制器
 * @version V1.0
 */
public class UserAction extends IdentityAction {
	private static final long serialVersionUID = -5900430889973008012L;
	/** 定义用户类型的属性封装请求参数 */
	private User user;
	/** 封装确认密码参数 */
	private String repwd;
	/** 封装要删除的用户userId */
	private String userIds;
	/** 定义list集合保存查询返回的用户列表 */
	private List<User> users;

	/**
	 * 多条件分页查询用户
	 * @return
	 */
	public String selectUser()
	{
		try {
			/** 处理点击分页标签提交查询请求时,get请求中文数据乱码问题 */
			String method = ServletActionContext.getRequest().getMethod();
			if("get".equalsIgnoreCase(method) && user != null)
			{
				String userName = new String(user.getName().getBytes("iso8859-1"),"utf-8");
				user.setName(userName);
			}
			users = identityService.getUserByPage(user, pageModel);
			//System.out.println(users);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 添加用户
	 * @return
	 */
	public String addUser()
	{
		try {
			identityService.addUser(user);
			/** 添加时，清空回显数据 */
			setUser(null);
			setTip("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("添加失败");
		}
		return SUCCESS;
	}
	/**
	 * 显示修改用户页面
	 * @return
	 */
	public String showUpdateUser()
	{
		try {
			user = identityService.getUser(user.getUserId(), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 修改用户
	 * @return
	 */
	public String updateUser()
	{
		try {
			/** 当userId为空时，表示修改的session中当前登录的用户 */
			if(StringUtils.isEmpty(user.getUserId()))
			{
				/** 设置当前修改用户的userId为sesson中用户的userId */
				user.setUserId(AdminConstant.getSessionUser().getUserId());
				/** 设置当前修改用户的部门和职位为sesson中用户的部门和职位 */
				Dept dept = new Dept();
				dept.setId(AdminConstant.getSessionUser().getDept().getId());
				user.setDept(dept);
				Job job = new Job();
				job.setCode(AdminConstant.getSessionUser().getJob().getCode());
				user.setJob(job);
			}
			/** 保存修改后的登录用户信息到数据库中 */
			identityService.updateUser(user);
			/***
			 * 如果当前修改的用户信息是登录用户的信息，则保存修改后的用户信息到session中，
			 * 如果当前修改的其他用户的信息，则不存入session
			 * */
			if(user.getUserId() == AdminConstant.getSessionUser().getUserId())
			{
				ActionContext.getContext().getSession()
					.put(AdminConstant.SESSION_USER, user);
			}
			setTip("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("修改失败");
		}
		return SUCCESS;
	}
	/**
	 * 删除用户
	 * @return
	 */
	public String deleteUser()
	{
		try {
			identityService.deleteUser(userIds.split(","));
			setTip("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("删除失败");
		}
		return SUCCESS;
	}
	/**
	 * 审核用户
	 * @return
	 */
	public String checkUser()
	{
		try {
			identityService.checkUser(userIds.split(","),user.getStatus());
			setTip("审核成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("审核失败");
		}
		return SUCCESS;
	}
	
	/** getter and setter method*/
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public String getRepwd() {
		return repwd;
	}
	public void setRepwd(String repwd) {
		this.repwd = repwd;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	

}
