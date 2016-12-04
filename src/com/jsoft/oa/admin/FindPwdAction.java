package com.jsoft.oa.admin;

import javax.annotation.Resource;

import com.jsoft.oa.admin.identity.service.IdentityService;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 找回密码控制器
 * @author jack
 * @email wujunwei6@163.com
 * @date 2016年9月7日 下午12:45:25 
 * @version V1.0
 */
public class FindPwdAction extends ActionSupport {
	private static final long serialVersionUID = -8144733998476994774L;
	private String userId;
	private int question;
	private String answer;
	private String tip;
	@Resource
	private IdentityService identityService;
	@Override
	public String execute() throws Exception {
		try {
			tip = identityService.findPassword(userId, question, answer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return SUCCESS;
	}
	
	
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getQuestion() {
		return question;
	}
	public void setQuestion(int question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	
}
