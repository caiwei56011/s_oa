package com.jsoft.oa.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jsoft.oa.core.dao.GeneratorDao;

/**
 * 通用的主键生成器数据访问接口的实现
 * @author Administrator
 * @date 2016年8月26日 下午4:54:53 
 * @version V1.0
 */
public class GeneratorDaoImpl extends HibernateDaoImpl implements GeneratorDao {

	/**
	 * 生成主键code的方法
	 * @param clazz 需要生成主键的实体类
	 * @param field 主键列对应的属性名称
	 * @param codeLength code(不包含父级code)的长度
	 * @param parentCode 父级code
	 * @return
	 */
	@Override
	public String generatorCode(Class<?> clazz, String field, int codeLength,
			String parentCode) {
		/*
		 * select max(code) from oa_id_module where length(code)=4; //未传父级code
		 * select max(code) from oa_id_module where length(code)=12 and code like '00010001%'; //传入父级code
		 */
		/** parentCode为空时，处理parentCode为空字符串 */
		parentCode = StringUtils.isEmpty(parentCode) ? "" : parentCode;
		/** 拼接hql语句,用于查询当前父级code的最大下级code */
		StringBuilder hql = new StringBuilder();
		hql.append("select max(" + field + ") from " + clazz.getSimpleName());
		hql.append(" where length(" + field + ") = ?");
		/** 封装参数占位符的值 */
		List<Object> params = new ArrayList<Object>();
		params.add(parentCode.length() + codeLength);
		if( !StringUtils.isEmpty(parentCode) )
		{
			hql.append(" and " + field + " like ?");
			params.add(parentCode + "%"); 
		}
		/** 调用hibernateDao查询出最大下级code*/
		String maxCode = this.findUniqueEntity(hql.toString(), params.toArray());
		/** 定义suffix保存下级code(不包含父级code) */
		String suffix = "";
		if(StringUtils.isEmpty(maxCode))
		{
			/**
			 * 当最大下级code为空时，表示当前父级code还没有下级, 
			 * 根据父级code生成第一个新的下级code 
			 * */
			for(int i = 1; i < codeLength; i++)
			{
				suffix += "0"; //生成0001 其中的000
			}
			suffix += 1; //加上0001 其中的1
		}
		else
		{
			/** 当最大下级code不为空时，根据最大下级code生成新的下级code */
			/** 截取maxCode的最后四位 */
			String tempCode = maxCode.substring(maxCode.length() - codeLength, maxCode.length());
			/** 将最后四位转换为数值，再加一 */
			suffix = String.valueOf(Integer.valueOf(tempCode) + 1);
			/** 当生成的suffix的长度大于codeLength时，抛出异常 */
			if(suffix.length() > codeLength)
			{
				throw new RuntimeException("生成的主键超过合法长度");
			}
			else
			{
				/** 小于合法长度时，根据数值的位数补齐 */
				suffix = tempCode.substring( 0, tempCode.length() - suffix.length() ) + suffix;
			}
		}
			
		/** 返回生成的完整主键值*/
		return parentCode + suffix;
	}

}
