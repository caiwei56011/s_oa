package com.jsoft.oa.admin.workflow.action;

import javax.annotation.Resource;

import com.jsoft.oa.admin.workflow.service.WorkflowService;
import com.jsoft.oa.core.common.web.PageModel;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 工作流的基控制器
 * @author jack
 * @version V1.0
 */
public class WorkflowAction extends ActionSupport {
	private static final long serialVersionUID = 379056772737514851L;
	@Resource
	protected WorkflowService workflowService;
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
