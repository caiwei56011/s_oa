package com.jsoft.oa.core.action;

import java.util.UUID;

import com.jsoft.oa.core.common.sms.SmsUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 短信验证码控制器
 * @author Administrator
 * @date 2016年8月22日 下午6:44:23 
 * @version V1.0
 */
public class SmsVerifyAction extends ActionSupport {
	
	private static final long serialVersionUID = -44222702564571717L;
	/** 定义存入在Session短信验证码 */
	public static final String SMS_VERIFY_CODE = "sms_verify_code";
	/** 定义手机号码 */
	private String phone;
	/** 定义是否发信成功的Field */
	private boolean smsSuccess;
	

	@Override
	public String execute() {
		try{
			/** 随机生成四位数字的验证码 */
			String code = UUID.randomUUID().toString().replaceAll("[a-z|-]", "").substring(0,4);
			smsSuccess = SmsUtils.send(code, phone);
			if (smsSuccess){
				/** 存入Session */
				ActionContext.getContext().getSession().put(SMS_VERIFY_CODE, code);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	

	/** setter and getter method */
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public boolean getSmsSuccess() {
		return smsSuccess;
	}
}
