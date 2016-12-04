package com.jsoft.oa.admin.leave.action;

import java.util.List;

import com.jsoft.oa.admin.leave.entity.LeaveItem;

public class LeaveItemAction extends LeaveAction {
	private static final long serialVersionUID = 1244277422874928372L;
	
	private LeaveItem leaveItem;
	private List<LeaveItem> leaveItems;
	
	/** 分页查询当前登录用户的请假单 */
	public String selectUserLeave()
	{
		try {
			leaveItems = leaveService.getUserLeaveByPage(leaveItem, pageModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 填写用户请假单 */
	public String addLeaveItem(){
		try{
			leaveService.saveLeaveItem(leaveItem);
			setTip("添加成功！");
		}catch(Exception ex){
			setTip("添加失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	/** 查询当前登录用户需要审批的请假单  */
	public String selectAuditLeave()
	{
		try{
			leaveItems = leaveService.queryAuditLeave();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	/** 显示审批窗口 */
	public String showAudit()
	{
		try{
			/** 缓存auditLeave.jsp页面传入的任务id */
			String taskId = leaveItem.getTaskId();
			leaveItem = leaveService.getLeaveItem(leaveItem.getId());
			/** 将任务id设置回查询到的单个请假单中 */
			leaveItem.setTaskId(taskId);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	public LeaveItem getLeaveItem() {
		return leaveItem;
	}
	public void setLeaveItem(LeaveItem leaveItem) {
		this.leaveItem = leaveItem;
	}
	public List<LeaveItem> getLeaveItems() {
		return leaveItems;
	}
	public void setLeaveItems(List<LeaveItem> leaveItems) {
		this.leaveItems = leaveItems;
	}
	
}
