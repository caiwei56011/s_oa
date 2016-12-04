package com.jsoft.oa.admin.identity.action;

import javax.annotation.Resource;

import com.jsoft.oa.admin.identity.service.IdentityService;
import com.jsoft.oa.core.common.web.PageModel;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 权限管理模块的基础控制器
 * @author Administrator
 * @date 2016年8月20日 下午5:05:56 
 * @version V1.0
 */
public class IdentityAction extends ActionSupport {

	private static final long serialVersionUID = -1616617260305682513L;
	
	/** 定义PageModel类型的属性封装分页参数,
	 * 第一次访问的时候未传入分页参数，无法自动封装，需要手动创建pageModel对象
	 *  */
	protected PageModel pageModel = new PageModel();
	/** 定义添加、修改、删除、审核成功后的提示信息*/
	private String tip;
	
	/** 注入IdentityService */
	@Resource
	protected IdentityService identityService;
	
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
