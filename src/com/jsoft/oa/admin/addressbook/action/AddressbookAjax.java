package com.jsoft.oa.admin.addressbook.action;

import java.util.List;
import java.util.Map;

/**
 * 通讯录管理模块的异步请求控制器
 * @author jack
 * @email wujunwei6@163.com
 * @date 2016年8月27日 下午10:26:25 
 * @version V1.0
 */
public class AddressbookAjax extends AddressbookAction {
	private static final long serialVersionUID = -229841127919109264L;
	private List<Map<String,Object>> responseData;
	
	/** 异步加载联系组树 */
	public String loadContactGroupTreeAjax()
	{
		try {
			responseData = addressbookService.getContactGroupByIdAndName();
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
