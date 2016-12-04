package com.jsoft.oa.core.common.web;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

import com.jsoft.oa.core.common.security.MD5_32;

/***
 * 操作Cookie的工具类
 * @author Administrator
 * @date 2016年8月19日 下午9:00:09 
 * @version V1.0
 */
public class CookieTools {
	
	/**
	 * 根据cookieName获取cookie
	 * @param cookieName cookie名称
	 * @return
	 */
	public static Cookie getCookie(String cookieName)
	{
		/** 获取浏览器发送的所有cookie */
		Cookie[] cookies = ServletActionContext.getRequest().getCookies();
		/** 遍历所有cookie，查找指定名称的cookie */
		if(cookies != null && cookies.length > 0)
		{
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals(cookieName))
				{
					return cookie;
				}
			}
		}
		return null;
	}
	
	/**
	 * 向浏览器添加cookie
	 * @param cookieName cookie名称
	 * @param cookieValue cookie值
	 * @param cookieMaxAge cookie最大存活时间
	 */
	public static void addCookie(String cookieName, String cookieValue, int cookieMaxAge)
	{
		/** 根据cookieName获取cookie */
		Cookie cookie = getCookie(cookieName);
		if(cookie == null)
		{
			/** cookie为空时才创建新cookie，此时在有效时间内只能保存最早的一个cookie,
			 * 将cookie的值进行md5加密
			 *  */
			cookie = new Cookie(cookieName, MD5_32.encryption(cookieValue));
		}
		else
		{
			/**
			 * cookie不为空时，设置cookie的值，覆盖原来的值
			 */
			cookie.setValue(MD5_32.encryption(cookieValue));
		}
		/** 设置cookie的最大存活时间为7天 */
		cookie.setMaxAge(cookieMaxAge);
		/** 设置cookie的访问路径,/表示当前项目 */
		cookie.setPath("/");
		/** 设置cookie跨域*/
		//cookie.setDomain("www.xxx.com");
		/** 将cookie发送到浏览器 */
		ServletActionContext.getResponse().addCookie(cookie);
	}
	
	/**
	 * 删除浏览器的cookie
	 * @param cookieName cookie名称
	 */
	public static void removeCookie(String cookieName)
	{
		/** 根据cookieName获取cookie */
		Cookie cookie = getCookie(cookieName);
		if(cookie != null)
		{
			/** 设置cookie的最大存活时间为0 */
			cookie.setMaxAge(0);
			/** 设置cookie的访问路径,/表示当前项目 */
			cookie.setPath("/");
			/** 将cookie发送到浏览器,覆盖原cookie,浏览器会自动删除cookie  */
			ServletActionContext.getResponse().addCookie(cookie);
		}
		
	}
	
}
