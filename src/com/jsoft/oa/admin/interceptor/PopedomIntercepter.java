package com.jsoft.oa.admin.interceptor;

import java.util.List;
import java.util.Map;

import com.jsoft.oa.admin.AdminConstant;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * 权限拦截器
 * @author jack
 * @version V1.0
 */
@SuppressWarnings("unchecked")
public class PopedomIntercepter extends AbstractInterceptor {
	private static final long serialVersionUID = -7114368240832346189L;
	
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		/** ################ 获取用户请求的url ########## */
		/** 获取Action代理对象 */
		ActionProxy proxy = invocation.getProxy();
		String namespace = proxy.getNamespace();
		String actionName = proxy.getActionName();
		String requestUrl = namespace + "/" + actionName;
		System.out.println("用户请求的url为：" + requestUrl);
		/** ################ 获取session中存储的用户有权限的url ########## */
		Map<String, List<String>> userPopedomMap = (Map<String, List<String>>) invocation.getInvocationContext()
				.getSession().get(AdminConstant.USER_ALL_POPEDOM);
		/** ########### 将用户请求的url与session中存储的用户有权限的url进行匹配 ######*/
		/** 标识是否有权限 */
		boolean hasPopedom = false;
		outer:
		for( Map.Entry<String, List<String>> item : userPopedomMap.entrySet() )
		{
			for(String url : item.getValue())
			{
				if( url.substring(0, url.lastIndexOf(".")).equals(requestUrl) )
				{
					/** 
					 * 为了控制页面上的按钮，需要把该模块所有的权限传到页面,
					 * 当前请求模块的所有有权限的url存储到seesion中 ,
					 * (这样再按模块分组，是为了减少前台页面匹配的数据量)
					 */
					invocation.getInvocationContext().getSession().put(AdminConstant.USER_MODULE_POPEDOM, item.getValue());
					/** 有权限 */
					hasPopedom = true;
					/** 终止内层和外层循环 */
					break outer;
				}
			}
		}
		if(hasPopedom)
		{
			/** 有权限则放行 */
			return invocation.invoke();
		}
		else
		{
			/** 无权限则返回error结果视图页面，在页面进行提示 */
			invocation.getInvocationContext().put("tip", "您还没有此操作的权限，请联系系统管理员");
			return Action.ERROR;
		}
		
	}

}
