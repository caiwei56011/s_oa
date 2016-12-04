package com.jsoft.oa.admin.leave.action;

import javax.annotation.Resource;

import com.jsoft.oa.admin.leave.service.LeaveService;
import com.jsoft.oa.core.common.web.PageModel;
import com.opensymphony.xwork2.ActionSupport;

public class LeaveAction extends ActionSupport {
	private static final long serialVersionUID = -19691213628750948L;
	@Resource
	protected LeaveService leaveService;
	protected PageModel pageModel = new PageModel();
	private String tip;
	
	
	
	/** getter and setter method */
	public PageModel getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	
	
	

}
