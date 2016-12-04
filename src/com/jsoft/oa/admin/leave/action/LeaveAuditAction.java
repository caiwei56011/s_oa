package com.jsoft.oa.admin.leave.action;

import java.util.List;
import java.util.Stack;

import com.jsoft.oa.admin.leave.entity.LeaveAudit;

/**
 * 请假单审批控制器
 * @author jack
 * @version V1.0
 */
public class LeaveAuditAction extends LeaveAction {
	private static final long serialVersionUID = 5836330253956087438L;
	private LeaveAudit leaveAudit;
	private String taskId;
	private List<LeaveAudit> leaveAudits;
	/** 审批请假单 */
	public String audit()
	{
		try {
			leaveService.audit(taskId, leaveAudit);
			setTip("审批成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("审批失败");
		}
		return SUCCESS;
	}
	/** 查看审批意见 */
	public String selectAuditResult()
	{
		try {
			leaveAudits = leaveService.getLeaveAuditByLeaveItemId(leaveAudit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	
	
	
	
	
	
	
	
	
	/** getter and setter method */
	public LeaveAudit getLeaveAudit() {
		return leaveAudit;
	}
	public void setLeaveAudit(LeaveAudit leaveAudit) {
		this.leaveAudit = leaveAudit;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public List<LeaveAudit> getLeaveAudits() {
		return leaveAudits;
	}
	public void setLeaveAudits(List<LeaveAudit> leaveAudits) {
		this.leaveAudits = leaveAudits;
	}

}
