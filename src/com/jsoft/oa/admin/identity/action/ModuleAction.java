package com.jsoft.oa.admin.identity.action;

import java.util.List;

import com.jsoft.oa.admin.identity.entity.Module;

/**
 * 操作管理控制器
 * @author Administrator
 * @date 2016年8月26日 下午2:38:23 
 * @version V1.0
 */
public class ModuleAction extends IdentityAction {
	private static final long serialVersionUID = 1L;
	
	private Module module;
	private List<Module> modules;
	private String parentCode;
	private String moduleCodes;
	
	/** 分页显示操作 */
	public String selectModule()
	{
		try {
			modules = identityService.getModuleByPage(parentCode, pageModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 添加操作  */
	public String addModule()
	{
		try {
			identityService.addModule(parentCode, module);
			setTip("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("添加失败");
		}
		return SUCCESS;
	}
	
	/** 显示修改操作页面  */
	public String showUpdateModule()
	{
		try {
			if(module != null)
			{
				module = identityService.getModule(module.getCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 修改操作  */
	public String updateModule()
	{
		try {
			if(module != null)
			{
				identityService.updateModule(module);
				setTip("修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			setTip("修改失败");
		}
		return SUCCESS;
	}
	/** 删除操作 */
	public String deleteModule()
	{
		try {
			identityService.deleteModule(moduleCodes.split(","));
			setTip("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("删除失败");
		}
		return SUCCESS;
	}
	
	
	
	
	
	
	/** getter and setter method */
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public List<Module> getModules() {
		return modules;
	}
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getModuleCodes() {
		return moduleCodes;
	}
	public void setModuleCodes(String moduleCodes) {
		this.moduleCodes = moduleCodes;
	}
	
	
}

