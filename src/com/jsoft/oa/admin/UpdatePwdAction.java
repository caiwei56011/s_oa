package com.jsoft.oa.admin;

import javax.annotation.Resource;

import com.jsoft.oa.admin.identity.service.IdentityService;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 修改密码控制器
 * @author jack
 * @version V1.0
 */
public class UpdatePwdAction extends ActionSupport {
	private static final long serialVersionUID = -5111820473346751576L;
	private String oldpwd;
	private String newpwd;
	private String okpwd;
	private String tip;
	@Resource
	private IdentityService identityService;
	
	@Override
	public String execute() throws Exception {
		try {
			boolean isSuccess = identityService.updatePassword(oldpwd, newpwd, okpwd);
			if(isSuccess)
			{
				setTip("密码修改成功");
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTip("密码修改失败");
		return INPUT;
	}
	
	/** getter and setter method */
	public String getOldpwd() {
		return oldpwd;
	}
	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}
	public String getNewpwd() {
		return newpwd;
	}
	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}
	public String getOkpwd() {
		return okpwd;
	}
	public void setOkpwd(String okpwd) {
		this.okpwd = okpwd;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	
}
