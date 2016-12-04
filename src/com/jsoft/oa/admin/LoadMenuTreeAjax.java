package com.jsoft.oa.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.jsoft.oa.admin.identity.service.IdentityService;
import com.opensymphony.xwork2.ActionSupport;

public class LoadMenuTreeAjax extends ActionSupport{
	private static final long serialVersionUID = -3930561978966897677L;
	
	/** 保存响应数据 */
	private List<Map<String,Object>> responseData;
	/** 注入identityService */
	@Resource
	private IdentityService identityService;
	
	public String execute()
	{
		try {
			responseData = identityService.loadMenuTree();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	
	/** getter and setter method */
	public List<Map<String, Object>> getResponseData() {
		return responseData;
	}
	
	







	
}
