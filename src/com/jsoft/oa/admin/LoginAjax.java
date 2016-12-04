package com.jsoft.oa.admin;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.json.annotations.JSON;

import com.jsoft.oa.admin.identity.service.IdentityService;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAjax extends ActionSupport{
	private static final long serialVersionUID = -8465601407986263392L;
	
	/** 接收页面的请求参数 */
	private String userId;
	private String password;
	private String vcode;
	private int key;
	/** 保存响应数据 */
	private Map<String,Object> responseData;
	/** 注入identityService */
	@Resource
	private IdentityService identityService;
	
	public String execute()
	{
		try {
			//{"tip":"登录成功","status": 0}
			/*responseData = new HashMap<String,Object>();
			responseData.put("tip","登录成功");
			responseData.put("status", 0);*/
			//String str = JSONUtil.serialize(responseData); 将对象序列化为json格式字符串
			
			/** 调用identityService的login方法获取返回的map集合 */
			responseData = identityService.login(userId, password, vcode, key);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	
	
	







	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
	public void setKey(int key) {
		this.key = key;
	}



	/** 使用struts2-json-plugin-xx.jar插件
	 *  调用getResponseData方法返回响应数据
	 *  */
	public Map<String, Object> getResponseData() {
		return responseData;
	}
	@JSON(serialize=false) //struts2-json-plugin-xx.jar插件,userId不作为响应数据返回
	public String getUserId() {
		return userId;
	}
	
}
