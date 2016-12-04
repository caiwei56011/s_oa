package com.jsoft.oa.admin;

import org.apache.struts2.ServletActionContext;

import com.jsoft.oa.admin.identity.entity.User;

/**
 * amdin子系统的常量类
 * @version V1.0
 */
public class AdminConstant {
	/** 定义session中SESSION_USER常量*/
	public final static  String SESSION_USER = "session_user";
	/** 定义cookie中COOKIE_NAME常量 */
	public final static String COOKIE_NAME = "oa_user_id";
	/** 定义cookie的最大存活时间常量(7天，按秒计算) */
	public final static int COOKIE_MAX_AGE = 7 * 24 * 60 * 60;
	/** 定义登录用户的所有权限常量 */
	public static final String USER_ALL_POPEDOM = "user_all_popedom";
	/** 定义登录用户的对应模块中权限常量 */
	public static final String USER_MODULE_POPEDOM = "user_module_popedom";
	
	/** 获取session中保存的用户 */
	public static User getSessionUser() {
		return (User) ServletActionContext.getRequest().getSession().getAttribute(SESSION_USER);
	}
}
