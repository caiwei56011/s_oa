package com.jsoft.oa.admin.leave.dao;

import java.util.List;

import com.jsoft.oa.admin.leave.entity.LeaveAudit;
import com.jsoft.oa.core.dao.HibernateDao;
/**
 * 假期审核的访问接口
 * @version V1.0
 */
public interface LeaveAuditDao extends HibernateDao{


	/**
	 * 根据请假单id获取请假单审批记录
	 * @param leaveAudit 
	 * @return List 
	 */
	List<LeaveAudit> getLeaveAuditByLeaveItemId(LeaveAudit leaveAudit);
	
}