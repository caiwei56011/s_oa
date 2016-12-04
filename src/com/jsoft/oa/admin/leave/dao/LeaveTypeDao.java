package com.jsoft.oa.admin.leave.dao;

import java.util.List;
import java.util.Map;

import com.jsoft.oa.admin.leave.entity.LeaveType;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.HibernateDao;

/**
 * 假期类型的访问接口
 * @version V1.0
 */
public interface LeaveTypeDao extends HibernateDao{

	/**
	 *  异步加载假期类型(code和name)
	 * @return List
	 */
	List<Map<String, Object>> getLeaveTypeByCodeAndName();

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
	 * @param code
	 * @return
	 */
	LeaveType getLeaveTypeByCode(String code);
	
}