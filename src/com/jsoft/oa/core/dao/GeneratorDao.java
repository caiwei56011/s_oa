package com.jsoft.oa.core.dao;

/**
 * 通用的主键生成器数据访问接口
 * @author Administrator
 * @date 2016年8月26日 下午4:54:53 
 * @version V1.0
 */
public interface GeneratorDao extends HibernateDao {

	/**
	 * 生成主键code的方法
	 * @param clazz 需要生成主键的实体类
	 * @param field 主键列对应的属性名称
	 * @param codeLength code(不包含父级code)的长度
	 * @param parentCode 父级code
	 * @return
	 */
	String generatorCode(Class<?> clazz, String field, int codeLength, String parentCode);

}
