package com.jsoft.oa.admin.identity.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jsoft.oa.admin.identity.dao.JobDao;
import com.jsoft.oa.admin.identity.entity.Job;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.impl.HibernateDaoImpl;
/**
 * 职位表的访问接口实现类
 * @author Administrator
 * @date 2016年8月16日 下午8:32:55 
 * @version V1.0
 */
public class JobDaoImpl extends HibernateDaoImpl implements JobDao{

	/**
	 * 获取职位code和name
	 * @return list集合
	 */
	@Override
	public List<Map<String, Object>> getJobByCodeAndName() {
		String hql = "select new Map(code as code, name as name) from Job";
		return this.find(hql);
	}
	/**
	 * 分页查询职位
	 * @param pageModel
	 * @return
	 */
	public List<Job> getJobByPage(PageModel pageModel)
	{
		String hql = "select j from Job j order by j.code asc";
		return this.findByPage(hql, pageModel, null);
	}
	/**
	 * 批量删除职位
	 * @param ids
	 */
	public void deleteJob(String[] codes)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("delete from Job where code in (");
		List<String> jobCodes = new ArrayList<String>();
		for(int i = 0; i < codes.length; i++)
		{
			hql.append(i == 0 ? "?" : ",?");
			jobCodes.add(codes[i]);
		}
		hql.append(")");
		
		this.bulkUpdate(hql.toString(), jobCodes.toArray());
	}
}