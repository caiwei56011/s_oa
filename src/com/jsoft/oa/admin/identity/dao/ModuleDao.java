package com.jsoft.oa.admin.identity.dao;

import java.util.List;
import java.util.Map;

import com.jsoft.oa.admin.identity.entity.Module;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.HibernateDao;
/**
 * 模块表的访问接口
 * @author Administrator
 * @date 2016年8月16日 下午8:32:55 
 * @version V1.0
 */
public interface ModuleDao extends HibernateDao{

	/**
	 * 获取操作的code和name
	 * @return list集合
	 */
	List<Map<String, Object>> getModuleByCodeAndName();

	/**
	 * 分页获取module
	 * @param parentCode 父级code
	 * @param pageModel 分页实体
	 * @param codeLength code长度
	 * @return list集合
	 */
	List<Module> getModuleByPage(String parentCode, PageModel pageModel, int codeLength);

	/**
	 * 获取操作的code和name
	 * @param codeLen code长度
	 */
	List<Map<String, Object>>  getModuleByCodeAndName(int codeLen);

	/**
	 * 根据8位的模块code获取12位的操作
	 * @param i 操作code的位数
	 * @param moduleCode 模块code
	 * @return 操作实体集合
	 */
	List<Module> getModuleByCode8(int i, String moduleCode);

	
	
}