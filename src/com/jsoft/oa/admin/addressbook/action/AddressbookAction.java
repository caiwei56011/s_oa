package com.jsoft.oa.admin.addressbook.action;

import javax.annotation.Resource;

import com.jsoft.oa.admin.addressbook.service.AddressbookService;
import com.jsoft.oa.core.common.web.PageModel;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 通讯录管理模块的基控制器
 * @author jack
 * @Email wujunwei6@163.com
 * @date 2016年8月27日 下午7:41:05 
 * @version V1.0
 */
public class AddressbookAction extends ActionSupport {
	private static final long serialVersionUID = -2615792399782747278L;
	/** 封装分页参数 */
	protected PageModel pageModel = new PageModel();
	/** 保存提示信息 */
	protected String tip;
	/** 注入AddressbookService接口 */
	@Resource
	protected AddressbookService addressbookService;
	
	
	
	
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
