package com.jsoft.oa.admin.leave.action;

import java.util.List;
import java.util.Map;

public class LeaveAjax extends LeaveAction {
	private static final long serialVersionUID = -1843729411528763305L;
	
	private List<Map<String, Object>> responseData;
	private List<List<Map<String, Object>>> responseLists;
	/** 异步加载假期类型 */
	public String loadLeaveTypeAjax()
	{
		try {
			responseData = leaveService.getLeaveTypeByCodeAndName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/** 异步加载假期类型 与 流程定义 */
	public String loadLeaveTypeAndProcessAjax(){
		try{
			responseLists = leaveService.loadLeaveTypeAndProcess();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	
	/** getter and setter method */
	public List<Map<String, Object>> getResponseData() {
		return responseData;
	}
	public void setResponseData(List<Map<String, Object>> responseData) {
		this.responseData = responseData;
	}
	public List<List<Map<String, Object>>> getResponseLists() {
		return responseLists;
	}
	public void setResponseLists(List<List<Map<String, Object>>> responseLists) {
		this.responseLists = responseLists;
	}
	
	
	
}
