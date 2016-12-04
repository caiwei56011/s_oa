package com.jsoft.oa.admin.interceptor;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import org.springframework.util.StringUtils;

import com.jsoft.oa.admin.AdminConstant;
import com.jsoft.oa.admin.identity.entity.User;
import com.jsoft.oa.admin.identity.service.IdentityService;
import com.jsoft.oa.core.common.web.CookieTools;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * 登录拦截器
 * @author Administrator
 * @date 2016年8月20日 下午1:52:15 
 * @version V1.0
 */
public class LoginInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = -4434781806621445132L;
	/** 注入IdentityService */
	@Resource
	private IdentityService identityService;
	

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		/** 定义变量标识用户是否已登录,默认为已登录*/
		boolean isLogined = true;
		/** 获取session中的用户 */
		ActionContext invocationContext = invocation.getInvocationContext();
		User user = (User) invocationContext.getSession().get(AdminConstant.SESSION_USER);
		if(user == null)
		{
			/** 当session中用户为空时，再判断cookie中是否记住了用户 */
			//Cookie cookie = CookieTools.getCookie(AdminConstant.COOKIE_NAME);
			Cookie cookie = CookieTools.getCookie(AdminConstant.COOKIE_NAME);
			if(cookie != null && !StringUtils.isEmpty(cookie.getValue()))
			{
				/** cookie中记住了用户时，帮助用户登录*/
				String userId = cookie.getValue();
				/** cookie中的userId进行了md5加密，查询时需要数据库中用加密后的userId来对比 */
				User loginUser = identityService.getUser(userId, true);
				/** 登录用户存入session中 */
				invocationContext.getSession().put(AdminConstant.SESSION_USER, loginUser);
				/** 登录用户的所有权限url也存入session中 */
				Map<String, List<String>> userAllPopedom = identityService.getAllPopedomByUserId(loginUser.getUserId());
				invocationContext.getSession().put(AdminConstant.USER_ALL_POPEDOM, userAllPopedom);
			}
			else
			{
				/** session中用户为空，cookie中userId也为空时，需要登录*/
				isLogined = false;
			}
		}
		/** 已登录则放行，否则进入登录页面 */
		return isLogined ? invocation.invoke() : Action.LOGIN;
	}

}
