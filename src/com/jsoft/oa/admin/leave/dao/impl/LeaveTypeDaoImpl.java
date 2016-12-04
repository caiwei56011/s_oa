package com.jsoft.oa.admin.leave.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jsoft.oa.admin.leave.dao.LeaveTypeDao;
import com.jsoft.oa.admin.leave.entity.LeaveType;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.impl.HibernateDaoImpl;

/**
 * 假期类型的访问接口实现类
 * @version V1.0
 */
public class LeaveTypeDaoImpl extends HibernateDaoImpl implements LeaveTypeDao{
	/**
	 *  异步加载假期类型(code和name)
	 * @return List
	 */
	public List<Map<String, Object>> getLeaveTypeByCodeAndName()
	{
		String hql = "select new Map(lt.code as code, lt.name as name) from LeaveType lt";
		return this.find(hql);
	}
	/**
	 * 分页查询假期类型
	 * @param leaveType
	 * @param pageModel
	 * @return
	 */
	public List<LeaveType> getLeaveTypeByPage(LeaveType leaveType, PageModel pageModel){
		StringBuffer hql = new StringBuffer();
		hql.append("select lt from LeaveType as lt");
		List<String> params = null;
		if(leaveType != null && !StringUtils.isEmpty(leaveType.getCode()))
		{
			hql.append(" where lt.code = ?");
			params = new ArrayList<String>();
			params.add(leaveType.getCode());
		}
		return this.findByPage(hql.toString(), pageModel, params);
	}
	/**
	 * 添加假期类型
	 * @param leaveType
	 */
	public void saveLeaveType(LeaveType leaveType)
	{
		this.save(leaveType);
	}
	/**
	 * 主键查询假期类型
	 * @param code
	 * @return
	 */
	public LeaveType getLeaveTypeByCode(String code)
	{
		return this.get(LeaveType.class, code);
	}
}