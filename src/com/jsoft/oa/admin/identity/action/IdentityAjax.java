package com.jsoft.oa.admin.identity.action;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;

/**
 * 系统管理模块的异步请求控制器
 * @version V1.0
 */
public class IdentityAjax extends IdentityAction{
	private static final long serialVersionUID = 4135851442953579647L;
	
	private List<Map<String,Object>> responseData;
	private List<String> responseUserNameData;
	private Map<String,List<Map<String,Object>>> responseDeptAndJobData;
	private String responseUserIdData;
	/** 封装联想的用户名参数 */
	private String userName;
	/** 封装添加用户时填写的用户登录名 */
	private String userId;
	
	/** 异步加载部门 */
	public String loadDeptByIdAndNameAjax()
	{
		try {
			responseData = identityService.loadDept();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	

	/** 异步加载联想(模糊查询)的用户name*/
	public String loadUserNameAjax()
	{
		try {
			responseUserNameData = identityService.loadUserName(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/** 异步加载部门和职位数据  */
	public String loadDeptAndJobAjax()
	{
		try {
			responseDeptAndJobData = identityService.loadDeptAndJob();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 异步获取用户登录名,验证用户名是否重复  */
	public String loadUserIdAjax()
	{
		try {
			responseUserIdData = identityService.loadUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 异步加载用于创建操作管理页面操作树节点的数据  */
	public String loadModuleTreeAjax()
	{
		try {
			responseData = identityService.loadModuleTree();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 异步加载用于创建绑定操作页面权限树节点的数据  */
	public String loadPopdomTreeAjax()
	{
		try {
			responseData = identityService.loadPopdomTree();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/** getter and setter method */
	public List<Map<String, Object>> getResponseData() {
		return responseData;
	}
	public List<String> getResponseUserNameData() {
		return responseUserNameData;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Map<String, List<Map<String, Object>>> getResponseDeptAndJobData() {
		return responseDeptAndJobData;
	}
	public String getResponseUserIdData() {
		return responseUserIdData;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	

	
}
