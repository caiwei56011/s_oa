package com.jsoft.oa.admin.identity.service;

import java.util.List;
import java.util.Map;

import com.jsoft.oa.admin.identity.entity.Dept;
import com.jsoft.oa.admin.identity.entity.Job;
import com.jsoft.oa.admin.identity.entity.Module;
import com.jsoft.oa.admin.identity.entity.Role;
import com.jsoft.oa.admin.identity.entity.User;
import com.jsoft.oa.core.common.web.PageModel;

/**
 * 权限管理业务处理接口
 * @author Administrator
 * @date 2016年8月16日 下午8:59:28 
 * @version V1.0
 */
public interface IdentityService {
	/** 定义操作树code长度的常量(接口中属性默认为public static final,常量声明时必须赋初值)*/
	int CODE_LENGTH = 4;
	
	/** TODO ##################用户管理业务处理 ###############*/ 

	/**
	 * 登陆方法
	 * @param userId 用户名
	 * @param password 密码
	 * @param vcode 验证码
	 * @param key 是否记住用户，1表示记住
	 * @return Map集合
	 */
	Map<String, Object> login(String userId, String password, String vcode,
			int key);

	/**
	 * 根据userId获取用户
	 * @param userId 用户id
	 * @param isMD5 用户id是否进行了md5加密
	 * @return 用户
	 * @throws Exception 
	 */
	User getUser(String userId, boolean isMD5);

	/**
	 * 多条件分页查询用户
	 * @param user 用户查询条件
	 * @param pageModel  分页参数
	 * @return 用户集合
	 */
	List<User> getUserByPage(User user, PageModel pageModel);

	/**
	 * 加载部门
	 * @return list集合 
	 */
	List<Map<String, Object>> loadDept();

	/**
	 * 加载联想(模糊查询)的用户name
	 * @param userName
	 * @return list集合
	 */
	List<String> loadUserName(String userName);
	
	/**
	 * 加载部门和职位数据
	 * @return map集合
	 */
	Map<String, List<Map<String, Object>>> loadDeptAndJob();

	/**
	 * 添加用户
	 * @param user 用户实体
	 */
	void addUser(User user);

	/**
	 * 获取用户登录名(用于检查是否已存在)
	 * @param userId 表单中填写的登录名
	 * @return
	 */
	String loadUserId(String userId);
	
	/**
	 * 修改用户
	 * @param user 用户实体
	 */
	void updateUser(User user);

	/**
	 * 删除用户
	 * @param userIds 用户的登录名
	 */
	void deleteUser(String[] userIds);

	/**
	 * 审核用户
	 * @param userIds 用户的登录名
	 * @param status 用户状态
	 */
	void checkUser(String[] userIds, Short status);

	/** TODO ##################角色管理业务处理 ###############*/ 
	/**
	 * 分页查询角色列表
	 * @param pageModel 分页实体
	 * @return
	 */
	List<Role> getRoleByPage(PageModel pageModel);
	
	/**
	 * 添加角色
	 * @param role 角色实体
	 */
	void addRole(Role role);

	/**
	 * 根据主键查询角色
	 * @param id 主键
	 * @return 角色对象
	 */
	Role getRole(Long id);
	
	/**
	 * 修改角色
	 * @param role 角色实体
	 */
	void updateRole(Role role);

	/**
	 * 删除角色
	 * @param 角色id
	 */
	void deleteRole(String[] roleIds);

	/**
	 * 加载操作树数据
	 * @return list集合
	 */
	List<Map<String, Object>> loadModuleTree();

	/**
	 * 分页获取操作
	 * @param parentCode 父级code
	 * @param pageModel	分页实体
	 * @return
	 */
	List<Module> getModuleByPage(String parentCode, PageModel pageModel);

	/**
	 * 添加操作
	 * @param parentCode 父级code
	 * @param module 操作实体
	 */
	void addModule(String parentCode, Module module);

	/**
	 * 根据主键获取操作
	 * @param code 主键code
	 * @return 操作实体
	 */
	Module getModule(String code);

	/**
	 * 修改操作
	 * @param 操作实体
	 */
	void updateModule(Module module);

	/**
	 * 删除操作
	 * @param moduleCodes 主键数组
	 */
	void deleteModule(String[] moduleCodes);

	/**
	 * 根据角色id查询用户（当前角色已绑定的用户）
	 * @param pageModel 分页实体
	 * @param roleId 角色id
	 * @return 用户集合
	 */
	public List<User> getUserByPageAndRoleId(PageModel pageModel, Long roleId);

	/**
	 * 获取未绑定到当前角色的用户
	 * @param pageModel 分页实体
	 * @param roleId 角色id
	 * @return
	 */
	List<User> getUserByPageAndNotRoleId(PageModel pageModel, Long roleId);

	/**
	 * 为角色绑定用户
	 * @param roleId 角色id
	 * @param userIds 用户id
	 */
	void bindUser(Long roleId, String[] userIds);

	/**
	 * 为角色解除用户
	 * @param roleId 角色id
	 * @param userIds 用户id
	 */
	void unBindUser(Long roleId, String[] userIds);
	
	/**
	 * 加载用于创建绑定操作页面权限树节点的数据
	 * @return
	 */
	List<Map<String, Object>> loadPopdomTree();

	/**
	 * 根据8位的模块code获取12位的操作
	 * @param moduleCode
	 * @return 操作实体集合
	 */
	List<Module> getModuleByCode8(String moduleCode);

	/**
	 * 查询当前角色在当前模块下已经绑定的操作
	 * @param id 角色id
	 * @param moduleCode 模块code
	 * @return 操作code集合
	 */
	List<String> getOperaCodeByRoleIdAndModuleCode(Long id, String moduleCode);

	/**
	 * 角色绑定操作
	 * @param codes 操作code 12位
	 * @param moduleCode 模块code 8位
	 * @param role 角色实体(id)
	 */
	void bindModule(String codes, String moduleCode, Role role);

	
	/**
	 * 根据当前登录用户加载对应的菜单树
	 * @return
	 */
	List<Map<String, Object>> loadMenuTree();

	/**
	 * 根据用户id查询所有的角色id，再根据角色id查询所有的12位权限code,
	 * 并将结果按照模块id分组,返回分组后的map集合
	 * @param userId
	 * @return map集合
	 */
	Map<String, List<String>> getAllPopedomByUserId(String userId);
	
	
	
	/**
	 * 找回密码
	 * @param userId 用户编号
	 * @param question 密保问题代号
	 * @param answer 密保问题答案
	 * @return 提示信息
	 */
	String findPassword(String userId, int question, String answer);

	/**
	 * 修改密码
	 * @param oldpwd 旧密码
	 * @param newpwd 新密码
	 * @param okpwd 确认新密码
	 * @return 是否修改成功
	 */
	boolean updatePassword(String oldpwd, String newpwd, String okpwd);

	/**
	 * 分页查询部门
	 * @param pageModel 分页实体
	 * @return List
	 */
	List<Dept> getDeptByPage(PageModel pageModel);

	/**
	 * 添加部门
	 * @param dept
	 */
	void addDept(Dept dept);

	/**
	 * 主键查询部门
	 * @param id
	 * @return
	 */
	Dept getDept(Long id);
	/**
	 * 修改部门
	 * @param dept
	 */
	void updateDept(Dept dept);
	/**
	 * 删除部门
	 * @param ids
	 */
	void deleteDept(String[] ids);

	/**
	 * 分页查询职位
	 * @param pageModel 分页实体
	 * @return List
	 */
	List<Job> getJobByPage(PageModel pageModel);

	/**
	 * 添加职位
	 * @param job
	 */
	void addJob(Job job);

	/**
	 * 主键查询职位
	 * @param code
	 * @return
	 */
	Job getJob(String code);
	/**
	 * 修改职位
	 * @param job
	 */
	void updateJob(Job job);
	/**
	 * 删除职位
	 * @param ids
	 */
	void deleteJob(String[] codes);


	
}
