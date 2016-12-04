package com.jsoft.oa.admin.identity.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jsoft.oa.admin.identity.dao.DeptDao;
import com.jsoft.oa.admin.identity.entity.Dept;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.impl.HibernateDaoImpl;
/**
 * 部门表的访问接口实现类
 * @author Administrator
 * @date 2016年8月16日 下午8:32:55 
 * @version V1.0
 */
public class DeptDaoImpl extends HibernateDaoImpl implements DeptDao{

	/**
	 * 获取所有部门id和name
	 * @return list集合
	 */
	@Override
	public List<Map<String, Object>> getDeptByIdAndName() {
		String hql = "select new Map(id as id, name as name) from Dept";
		return this.find(hql);
	}
	/**
	 * 分页查询部门
	 * @param pageModel 分页实体
	 * @return List
	 */
	public List<Dept> getDeptByPage(PageModel pageModel)
	{
		String hql = "select d from Dept d";
		return this.findByPage(hql, pageModel, null);
	}
	/**
	 * 批量删除部门
	 * @param ids
	 */
	public void deleteDept(String[] ids)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("delete from Dept where id in (");
		List<Long> deptIds = new ArrayList<Long>();
		for(int i = 0; i < ids.length; i++)
		{
			hql.append(i == 0 ? "?" : ",?");
			/** 对ids进行类型转换 */
			Long deptId = Long.parseLong(ids[i]);
			deptIds.add(deptId);
		}
		hql.append(")");
		
		this.bulkUpdate(hql.toString(), deptIds.toArray());
	}
	
}