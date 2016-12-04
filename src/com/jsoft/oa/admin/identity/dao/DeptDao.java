package com.jsoft.oa.admin.identity.dao;

import java.util.List;
import java.util.Map;

import com.jsoft.oa.admin.identity.entity.Dept;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.HibernateDao;
/**
 * 部门表的访问接口
 * @author Administrator
 * @date 2016年8月16日 下午8:32:55 
 * @version V1.0
 */
public interface DeptDao extends HibernateDao{

	/**
	 * 获取所有部门id和name
	 * @return list集合
	 */
	List<Map<String, Object>> getDeptByIdAndName();

	/**
	 * 分页查询部门
	 * @param pageModel 分页实体
	 * @return List
	 */
	List<Dept> getDeptByPage(PageModel pageModel);

	/**
	 * 批量删除部门
	 * @param ids
	 */
	void deleteDept(String[] ids);

	
}
