package com.jsoft.oa.admin.leave.dao;

import java.util.List;

import com.jsoft.oa.admin.leave.entity.LeaveItem;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.HibernateDao;

/**
 * 假期明细的访问接口
 * @version V1.0
 */
public interface LeaveItemDao extends HibernateDao{

	/**
	 * 分页查询当前登录用户的请假单
	 * @param leaveItem 请假单类型
	 * @param pageModel 分页实体
	 * @return
	 */
	List<LeaveItem> getUserLeaveByPage(LeaveItem leaveItem, PageModel pageModel);
	
}