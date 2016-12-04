package com.jsoft.oa.admin.leave.action;

import java.util.List;

import com.jsoft.oa.admin.leave.entity.LeaveType;

public class LeaveTypeAction extends LeaveAction {
	private static final long serialVersionUID = 1244277422874928372L;
	
	private LeaveType leaveType;
	private List<LeaveType> leaveTypes;
	private String codes;
	
	/** 分页查询假期类型 */
	public String selectLeaveType()
	{
		try {
			leaveTypes = leaveService.getLeaveTypeByPage(leaveType, pageModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 添加假期类型 */
	public String addLeaveType(){
		try{
			leaveService.saveLeaveType(leaveType);
			setTip("添加成功！");
		}catch(Exception ex){
			setTip("添加失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	/** 显示修改假期类型页面 */
	public String showUpdateLeaveType()
	{
		try{
			leaveType = leaveService.getLeaveTypeByCode(leaveType);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	/** 修改假期类型  */
	public String updateLeaveType()
	{
		try{
			leaveService.updateLeaveType(leaveType);
			setTip("修改成功！");
		}catch(Exception ex){
			ex.printStackTrace();
			setTip("修改失败！");
		}
		return SUCCESS;
	}
	/** 删除假期类型  */
	public String deleteLeaveType()
	{
		try{
			leaveService.deleteLeaveType(codes.split(","));
			setTip("删除成功！");
		}catch(Exception ex){
			ex.printStackTrace();
			setTip("删除失败！");
		}
		return SUCCESS;
	}
	
	
	
	/** getter and setter method */

	public List<LeaveType> getLeaveTypes() {
		return leaveTypes;
	}

	public LeaveType getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	public void setLeaveTypes(List<LeaveType> leaveTypes) {
		this.leaveTypes = leaveTypes;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}
	
}
