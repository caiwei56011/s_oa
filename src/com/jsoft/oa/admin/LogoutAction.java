package com.jsoft.oa.admin;

import org.apache.struts2.ServletActionContext;

import com.jsoft.oa.core.common.web.CookieTools;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 安全退出控制器
 * @author jack
 * @email wujunwei6@163.com
 * @date 2016年9月1日 下午4:12:28 
 * @version V1.0
 */
public class LogoutAction extends ActionSupport {
	private static final long serialVersionUID = -4261151065132172762L;
	@Override
	public String execute() throws Exception {
		/** 使session失效 */
		ServletActionContext.getRequest().getSession().invalidate();
		/** 删除浏览器端记住用户的cookie */
		CookieTools.removeCookie(AdminConstant.COOKIE_NAME);
		/** 跳转到登录页面 */
		return LOGIN;
	}
}
