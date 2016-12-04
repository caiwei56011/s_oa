package com.jsoft.oa.admin.leave.service;

import java.util.List;
import java.util.Map;

import com.jsoft.oa.admin.leave.entity.LeaveAudit;
import com.jsoft.oa.admin.leave.entity.LeaveItem;
import com.jsoft.oa.admin.leave.entity.LeaveType;
import com.jsoft.oa.core.common.web.PageModel;

/**
 * 请假管理业务处理接口
 * @version V1.0
 */
public interface LeaveService {

	/**
	 * 分页查询当前登录用户的请假单
	 * @param leaveItem 请假单类型
	 * @param pageModel 分页实体
	 * @return
	 */
	List<LeaveItem> getUserLeaveByPage(LeaveItem leaveItem, PageModel pageModel);

	/**
	 *  异步加载假期类型(code和name)
	 * @return List
	 */
	List<Map<String, Object>> getLeaveTypeByCodeAndName();

	/**
	 * 异步加载假期类型 与 流程定义
	 * @return
	 */
	List<List<Map<String, Object>>> loadLeaveTypeAndProcess();

	/**
	 * 用户添加请假单
	 * @param leaveItem
	 */
	void saveLeaveItem(LeaveItem leaveItem);
	/**
	 * 查询当前登录用户需要审批的假期 
	 * @return
	 */
	List<LeaveItem> queryAuditLeave();

	/**
	 * 根据请假单id获取单个请假单
	 * @param id 请假单id
	 * @return 请假单实体
	 */
	LeaveItem getLeaveItem(Long id);

	/**
	 * 审批请假单
	 * @param taskId 任务id
	 * @param leaveAudit 审批实体(包含请假单id和审批status)
	 */
	void audit(String taskId, LeaveAudit leaveAudit);

	/**
	 * 根据请假单id获取请假单审批记录
	 * @param leaveAudit 
	 * @return List 
	 */
	List<LeaveAudit> getLeaveAuditByLeaveItemId(LeaveAudit leaveAudit);
	/**
	 * 分页查询假期类型
	 * @param leaveType
	 * @param pageModel
	 * @return
	 */
	List<LeaveType> getLeaveTypeByPage(LeaveType leaveType, PageModel pageModel);

	/**
	 * 添加假期类型
	 * @param leaveType
	 */
	void saveLeaveType(LeaveType leaveType);

	/**
	 * 主键查询假期类型
	 * @param leaveType
	 * @return
	 */
	LeaveType getLeaveTypeByCode(LeaveType leaveType);
	/**
	 * 修改假期类型
	 * @param leaveType
	 */
	void updateLeaveType(LeaveType leaveType);

	/**
	 * 删除假期类型
	 * @param codes
	 */
	void deleteLeaveType(String[] codes);


}
