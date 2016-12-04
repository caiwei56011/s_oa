package com.jsoft.oa.admin.leave.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jsoft.oa.admin.AdminConstant;
import com.jsoft.oa.admin.leave.dao.LeaveItemDao;
import com.jsoft.oa.admin.leave.entity.LeaveItem;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.impl.HibernateDaoImpl;

/**
 * 假期明细的访问接口实现类
 * @version V1.0
 */
public class LeaveItemDaoImpl extends HibernateDaoImpl implements LeaveItemDao{
	/**
	 * 分页查询当前登录用户的请假单
	 * @param leaveItem 请假单类型
	 * @param pageModel 分页实体
	 * @return
	 */
	public List<LeaveItem> getUserLeaveByPage(LeaveItem leaveItem, PageModel pageModel)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select le from LeaveItem le where le.creater.userId = ? ");
		List<Object> params = new ArrayList<Object>();
		params.add(AdminConstant.getSessionUser().getUserId());
		System.out.println(AdminConstant.getSessionUser().getUserId());
		if(leaveItem != null && leaveItem.getLeaveType() != null
				&& !StringUtils.isEmpty(leaveItem.getLeaveType().getCode()))
		{
			hql.append("and le.leaveType.code = ? ");
			params.add(leaveItem.getLeaveType().getCode());
		}
		return this.findByPage(hql.toString(), pageModel, params);
	}
}