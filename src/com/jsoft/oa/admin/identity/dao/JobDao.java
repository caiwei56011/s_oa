package com.jsoft.oa.admin.identity.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jsoft.oa.admin.identity.entity.Job;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.HibernateDao;
/**
 * 职位表的访问接口
 * @author Administrator
 * @date 2016年8月16日 下午8:32:55 
 * @version V1.0
 */
public interface JobDao extends HibernateDao{

	/**
	 * 获取职位code和name
	 * @return list集合
	 */
	List<Map<String, Object>> getJobByCodeAndName();

	/**
	 * 分页查询职位
	 * @param pageModel
	 * @return
	 */
	List<Job> getJobByPage(PageModel pageModel);

	/**
	 * 批量删除职位
	 * @param ids
	 */
	void deleteJob(String[] codes);
	
	
}