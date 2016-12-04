package com.jsoft.oa.admin.leave.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jsoft.oa.admin.leave.dao.LeaveAuditDao;
import com.jsoft.oa.admin.leave.entity.LeaveAudit;
import com.jsoft.oa.core.dao.impl.HibernateDaoImpl;
/**
 * 假期审核说明的访问接口实现类
 * @version V1.0
 */
@SuppressWarnings("unchecked")
public class LeaveAuditDaoImpl extends HibernateDaoImpl implements LeaveAuditDao{
	/**
	 * 根据请假单id获取请假单审批记录
	 * @param leaveAudit 
	 * @return List 
	 */
	public List<LeaveAudit> getLeaveAuditByLeaveItemId(LeaveAudit leaveAudit)
	{
		if(leaveAudit.getLeaveItem() != null && leaveAudit.getLeaveItem().getId() != null 
				&& leaveAudit.getLeaveItem().getId() > 0)
		{
			String hql = "select la from LeaveAudit la where la.leaveItem.id = ? order by la.checkDate asc";
			return (List<LeaveAudit>) this.find(hql, new Object[]{leaveAudit.getLeaveItem().getId()});
		}
		else
		{
			return null;
		}
		
	}
}