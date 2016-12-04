package com.jsoft.oa.admin.identity.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jsoft.oa.admin.identity.dao.ModuleDao;
import com.jsoft.oa.admin.identity.entity.Module;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.impl.HibernateDaoImpl;
/**
 * 模块表的访问接口实现类
 * @author Administrator
 * @date 2016年8月16日 下午8:32:55 
 * @version V1.0
 */
@SuppressWarnings("unchecked")
public class ModuleDaoImpl extends HibernateDaoImpl implements ModuleDao{

	/**
	 * 获取操作的code和name
	 * @return list集合
	 */
	@Override
	public List<Map<String, Object>> getModuleByCodeAndName() {
		String hql = "select new Map(code as id, name as name) from Module order by code asc";
		return this.find(hql);
	}

	/**
	 * 分页获取module
	 * @param parentCode 父级code
	 * @param pageModel 分页实体
	 * @param codeLength code长度
	 * @return list集合
	 */
	@Override
	public List<Module> getModuleByPage(String parentCode, PageModel pageModel,
			int codeLength) {
		/*
		 * select * from oa_id_module where length(code)=4; //未传parentCode
		   select * from oa_id_module where length(code)=12 and code like '00010001%'; //传入parentCode
		 */
		StringBuilder hql = new StringBuilder();
		hql.append("select m from Module m where length(m.code)=?");
		parentCode = StringUtils.isEmpty(parentCode) ? "" : parentCode;
		/** 定义list集合封装查询参数值 */
		List<Object> params = new ArrayList<Object>();
		params.add(parentCode.length() + codeLength);
		if(!StringUtils.isEmpty(parentCode))
		{
			hql.append(" and m.code like ?");
			params.add(parentCode + "%");
		}
		return this.findByPage(hql.toString(), pageModel, params);
	}
	
	/**
	 * 获取操作的code和name
	 * @param codeLen code长度
	 */
	
	public List<Map<String, Object>>  getModuleByCodeAndName(int codeLen)
	{
		String hql = "select new Map(code as id, name as name) from Module where length(code) <= ? order by code asc";
		return (List<Map<String, Object>>) this.find(hql,new Object[]{codeLen});
	}
	/**
	 * 根据8位的模块code获取12位的操作
	 * @param i 操作code的位数
	 * @param moduleCode 模块code
	 * @return 操作实体集合
	 */
	public List<Module> getModuleByCode8(int i, String moduleCode)
	{
		String hql = "select m from Module m where length(m.code) = ? and m.code like ? order by m.code asc";
		return (List<Module>) this.find(hql, new Object[]{i, moduleCode + "%"});
	}
	
}