package com.jsoft.oa.admin.identity.action;

import java.util.List;

import org.apache.struts2.json.JSONUtil;

import com.jsoft.oa.admin.identity.entity.Module;
import com.jsoft.oa.admin.identity.entity.Role;
import com.jsoft.oa.admin.identity.entity.User;

/**
 * 角色绑定控制器
 * @author jack
 * @email wujunwei6@163.com
 * @date 2016年8月29日 下午3:27:32 
 * @version V1.0
 */
public class RoleBindAction extends IdentityAction {
	private static final long serialVersionUID = 7445345205346000931L;
	private Role role;
	private List<User> users;
	private String userIds;
	private List<Module> modules;
	private String moduleCode;
	/** 保存查询到的已绑定操作code */
	private String operateCodes;
	/** 保存选中的需要绑定的操作code */
	private String codes;
	
	/**  根据角色id查询用户（当前角色已绑定的用户） */
	public String selectRoleUser()
	{
		try {
			users = identityService.getUserByPageAndRoleId(pageModel, role.getId());
			role = identityService.getRole(role.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/** 显示准备绑定(未绑定)到当前角色的用户 */
	public String showBindUser()
	{
		try {
			/** 设置每页显示6行 */
			pageModel.setPageSize(6);
			users = identityService.getUserByPageAndNotRoleId(pageModel, role.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/** 为角色绑定用户 */
	public String bindUser()
	{
		try {
			identityService.bindUser(role.getId(), userIds.split(","));
			setTip("绑定成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("绑定失败");
		}
		return SUCCESS;
	}
	/** 为角色解除用户 */
	public String unBindUser()
	{
		try {
			identityService.unBindUser(role.getId(), userIds.split(","));
			setTip("解除成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("解除失败");
		}
		return SUCCESS;
	}
	/** 显示绑定操作右边页面：操作列表 */
	public String selectPopedom()
	{
		try {
			/** 根据8位的模块code获取12位的操作 */
			modules = identityService.getModuleByCode8(moduleCode);
			/** 主键查询角色 */
			role = identityService.getRole(role.getId());
			/** 查询当前角色在当前模块下已经绑定的操作,返回值示例:[000100010001, 000100010002, 000100010003, 000100010004, 000100010005] */
			List<String> list = identityService.getOperaCodeByRoleIdAndModuleCode(role.getId(),moduleCode);
			/** 把list集合转换为js中数组格式的字符串,返回值示例：["000100010001","000100010002","000100010003","000100010004","000100010005"] */
			operateCodes = JSONUtil.serialize(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 角色绑定操作 */
	public String bindModule()
	{
		try {
			identityService.bindModule(codes, moduleCode, role);
			setTip("绑定成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("绑定失败");
		}
		return SUCCESS;
	}
	
	
	/** getter and setter method */
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public List<Module> getModules() {
		return modules;
	}
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	public String getOperateCodes() {
		return operateCodes;
	}
	public void setOperateCodes(String operateCodes) {
		this.operateCodes = operateCodes;
	}
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	
	
	
	
	

}
