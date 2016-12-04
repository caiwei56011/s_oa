package com.jsoft.oa.admin.identity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jsoft.oa.admin.AdminConstant;
import com.jsoft.oa.admin.identity.dao.DeptDao;
import com.jsoft.oa.admin.identity.dao.JobDao;
import com.jsoft.oa.admin.identity.dao.ModuleDao;
import com.jsoft.oa.admin.identity.dao.PopedomDao;
import com.jsoft.oa.admin.identity.dao.RoleDao;
import com.jsoft.oa.admin.identity.dao.UserDao;
import com.jsoft.oa.admin.identity.entity.Dept;
import com.jsoft.oa.admin.identity.entity.Job;
import com.jsoft.oa.admin.identity.entity.Module;
import com.jsoft.oa.admin.identity.entity.Popedom;
import com.jsoft.oa.admin.identity.entity.Role;
import com.jsoft.oa.admin.identity.entity.User;
import com.jsoft.oa.admin.identity.service.IdentityService;
import com.jsoft.oa.core.action.VerifyAction;
import com.jsoft.oa.core.common.email.EmailSender;
import com.jsoft.oa.core.common.security.MD5_32;
import com.jsoft.oa.core.common.web.CookieTools;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.GeneratorDao;
import com.jsoft.oa.core.exception.OAException;
import com.opensymphony.xwork2.ActionContext;

/**
 * 权限管理业务处理接口实现类
 * @author Administrator
 * @date 2016年8月16日 下午9:00:50 
 * @version V1.0
 */
public class IdentityServiceImpl implements IdentityService {
	/**整合该模块下的所有数据访问接口,实现业务处理*/
	@Resource
	private DeptDao deptDao;
	@Autowired
	private JobDao jobDao;
	@Resource
	private ModuleDao moduleDao;
	@Resource
	private PopedomDao popedomDao;
	@Resource
	private RoleDao roleDao;
	@Resource
	private UserDao userDao;
	@Resource
	private GeneratorDao generatorDao;
	@Resource
	private EmailSender emailSender;
	
	
	/** TODO ################## 登录功能业务处理 ###############*/ 
	/**
	 * 登陆方法
	 * @param userId 用户名
	 * @param password 密码
	 * @param vcode 验证码
	 * @param key 是否记住用户，1表示记住
	 * @return Map集合
	 */
	@Override
	public Map<String, Object> login(String userId, String password,
			String vcode, int key) {
		try {
			Map<String, Object> responseData = new HashMap<String, Object>();
			/** 从session中获取已生成的正确验证码 */
			String verifyCode = (String) ServletActionContext.
					getRequest().getSession().getAttribute(VerifyAction.VERIFY_CODE);
			/** 判断验证码是否正确 */
			if(!StringUtils.isEmpty(vcode) && vcode.equalsIgnoreCase(verifyCode))
			{
				/** 判断用户名密码是否为空 */
				if(!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(password))
				{
					/** 调用dao层，在数据库中按用户名查找 */
					User user = this.getUser(userId, false);
					/** 判断用户名的密码是否正确 */
					if( user != null && MD5_32.encryption(password).equalsIgnoreCase(user.getPassWord()) )
					{
						/** 判断用户状态,1为审核通过 */
						if(user.getStatus() == 1)
						{
							/** 登录成功，将user存入session*/
							ActionContext.getContext().getSession().put(AdminConstant.SESSION_USER, user);
							/** 判断是否记住用户 */
							if(key == 1)
							{
								/** 将userId添加到浏览器cookie中,最大存活时间为7天 */
								CookieTools.addCookie(AdminConstant.COOKIE_NAME, userId, AdminConstant.COOKIE_MAX_AGE);
							}
							/** 登录成功，查询用户的所有操作权限url，存入到session中，做权限控制用 */
							Map<String, List<String>> userAllPopedom = this.getAllPopedomByUserId(user.getUserId());
							ServletActionContext.getRequest().getSession().setAttribute(AdminConstant.USER_ALL_POPEDOM, userAllPopedom);
							
							/** 返回登录成功提示信息 */
							responseData.put("tip", "登录成功");
							responseData.put("status", 0);
						}
						else
						{
						 	//0新建,1审核,2不通过审核,3冻结 
							String[] arr = {"新建","审核","不通过审核","冻结"};
							responseData.put("tip", "登录失败，当前用户处于【" + arr[user.getStatus()] + "】状态");
							responseData.put("status", 4);
						}
							
						
					}
					else
					{
						responseData.put("tip", "用户名或密码不正确");
						responseData.put("status", 3);
					}
				}
				else
				{
					responseData.put("tip", "用户名或密码不能为空");
					responseData.put("status", 2);
				}
			}
			else
			{
				responseData.put("tip", "验证码不正确");
				responseData.put("status", 1);
			}
			return responseData;
			
		} catch (Exception e) {
			throw new OAException("登录方法出现异常",e);
		}
		
	}

	
	/**
	 * 根据userId查询用户
	 * @param userId 用户id
	 * @param isMD5 用户id是否进行了md5加密
	 * @return 用户
	 * @throws Exception 
	 */
	@Override
	public User getUser(String userId, boolean isMD5) {
		try {
			User user = null;
			if(isMD5)
			{
				user = userDao.getUser(userId);
			}
			else
			{
				user = userDao.get(User.class, userId);
			}
			/** 加载修改用户页面需要用到的部门和职位数据 */
			if(user != null && user.getDept() != null) user.getDept().getId();
			if(user != null && user.getJob() != null) user.getJob().getCode();
			
			return user;
		} catch (Exception e) {
			throw new OAException("根据userId查询用户时出现异常",e);
		}
		
	}
	/** TODO ##################用户管理业务处理 ###############*/ 
	/**
	 * 多条件分页查询用户
	 * @param user 用户查询条件
	 * @param pageModel  分页参数
	 * @return 用户集合
	 */
	@Override
	public List<User> getUserByPage(User user, PageModel pageModel) {
		try {
			//int i = 1 / 0; //手动引发算术异常，测试日志切面
			
			List<User> users =  userDao.getUserByPage(user, pageModel);
			/** 手动加载懒加载的属性中数据,供jsp页面显示 */
			for (User u : users) {
				/** 加载部门 */
				if(u.getDept() != null) u.getDept().getName();
				/** 加载职位 */
				if(u.getJob() != null) u.getJob().getName();
				/** 加载创建人 */
				if(u.getCreater() != null) u.getCreater().getName();
				/** 加载审核人 */
				if(u.getChecker() != null) u.getChecker().getName();
			}
			return users;
		} catch (Exception e) {
			throw new OAException("多条件分页查询用户方法出现异常",e);
		}
		
	}

	/**
	 * 加载部门
	 * @return list集合 
	 */
	@Override
	public List<Map<String, Object>> loadDept() {
		try {
			return deptDao.getDeptByIdAndName();
		} catch (Exception e) {
			throw new OAException("加载部门方法出现异常",e);
		}
	}

	/**
	 * 加载联想(模糊查询)的用户name
	 * @param userName
	 * @return list集合
	 */
	@Override
	public List<String> loadUserName(String userName) {
		try {
			return userDao.getUserName(userName);
		} catch (Exception e) {
			throw new OAException("加载用户name方法出现异常",e);
		}
	}

	/**
	 * 加载部门和职位数据
	 * @return map集合
	 */
	@Override
	public Map<String, List<Map<String, Object>>> loadDeptAndJob() {
		try {
			Map<String, List<Map<String, Object>>> responseDeptAndJobData = new HashMap<String, List<Map<String, Object>>>();
			List<Map<String, Object>> deptMap =  deptDao.getDeptByIdAndName();
			List<Map<String, Object>> jobMap = jobDao.getJobByCodeAndName();
			responseDeptAndJobData.put("depts", deptMap);
			responseDeptAndJobData.put("jobs", jobMap);
			return responseDeptAndJobData;
		} catch (Exception e) {
			throw new OAException("加载部门和职位数据方法出现异常",e);
		}
	}

	/**
	 * 添加用户
	 * @param user 用户实体
	 */
	@Override
	public void addUser(User user) {
		try {
			user.setCreateDate(new Date());
			user.setCreater(AdminConstant.getSessionUser());
			user.setPassWord( MD5_32.encryption(user.getPassWord()) );
			userDao.save(user);
		} catch (Exception e) {
			throw new OAException("添加用户方法出现异常",e);
		}
	}

	/**
	 * 获取用户登录名,验证用户名是否重复 
	 * @param userId 表单中填写的登录名
	 * @return
	 */
	@Override
	public String loadUserId(String userId) {
		try {
			
			return userDao.getUserId(userId);
		} catch (Exception e) {
			throw new OAException("获取用户登录名方法出现异常",e);
		}
	}

	/**
	 * 修改用户
	 * @param user
	 */
	@Override
	public void updateUser(User user) {
		try {
			/** 获取持久化状态对象 */
			User u = userDao.get(User.class, user.getUserId());
			u.setModifier(AdminConstant.getSessionUser());
			u.setModifyDate(new Date());
			u.setAnswer(user.getAnswer());
			u.setDept(user.getDept());
			u.setEmail(user.getEmail());
			u.setJob(user.getJob());
			u.setName(user.getName());
			u.setPhone(user.getPhone());
			u.setQqNum(user.getQqNum());
			u.setQuestion(user.getQuestion());
			u.setSex(user.getSex());
			u.setTel(user.getTel());
			/** 
			 * 此处不用userDao.save(u),事物提交session关闭时,
			 * hibernate会检查一级缓存中的对象和数据库是否一致，不一致则自动更新
			 * */
		} catch (Exception e) {
			throw new OAException("修改用户方法出现异常",e);
		}
		
	}

	@Override
	public void deleteUser(String[] userIds) {
		try {
			/** 第一种方式: 获取持久化对象
			for (String userId : userIds) {
				User user = userDao.get(User.class, userId);
				userDao.delete(user);
			}
			*/
			/** 第二种方式 : 手动模拟游离对象
			for (String userId : userIds) {
				User user = new User();
				user.setUserId(userId);
				userDao.delete(user);
			}
			 */
			
			/** 第三种方式：自己写批量删除sql语句 */
			userDao.deleteUser(userIds);
		} catch (Exception e) {
			throw new OAException("删除用户方法出现异常",e);
		}
	}
	/**
	 * 审核用户
	 * @param userIds 用户的登录名
	 * @param status 用户状态
	 */
	@Override
	public void checkUser(String[] userIds, Short status) {
		try {
			 /** 方式一：获取持久化对象 */
			for (String userId : userIds) {
				User user = userDao.get(User.class, userId);
				user.setStatus(status);
				user.setCheckDate(new Date());
				user.setChecker(AdminConstant.getSessionUser());
			}
			/** 方式二： 手动写hql: update from User set status=? where userId in(?,?) */
		} catch (Exception e) {
			throw new OAException("审核用户方法出现异常",e);
		}
	}

	/** TODO ##################角色管理业务处理 ########################*/
	
	/**
	 * 分页查询角色列表
	 * @param pageModel 分页实体
	 * @return
	 */
	@Override
	public List<Role> getRoleByPage(PageModel pageModel) {
		try {
			List<Role> roles = roleDao.getRoleByPage(pageModel);
			/** 加载懒加载的数据 */
			for (Role role : roles) {
				if(role.getCreater() != null) role.getCreater().getName();
				if(role.getModifier() != null) role.getModifier().getName();
			}
			return roles;
		} catch (Exception e) {
			throw new OAException("查询角色列表方法出现异常",e);
		}
	}

	/**
	 * 添加角色
	 * @param role 角色实体
	 */
	@Override
	public void addRole(Role role) {
		try {
			role.setCreateDate(new Date());
			role.setCreater(AdminConstant.getSessionUser());
			roleDao.save(role);
		} catch (Exception e) {
			throw new OAException("添加角色方法出现异常",e);
		}
	}

	/**
	 * 根据主键查询角色
	 * @param id 主键
	 * @return 角色对象
	 */
	@Override
	public Role getRole(Long id) {
		try {
			return roleDao.get(Role.class, id);
		} catch (Exception e) {
			throw new OAException("根据主键查询角色方法出现异常",e);
		}
	}
	
	/**
	 * 修改角色
	 * @param role
	 */
	@Override
	public void updateRole(Role role) {
		try {
			Role r = this.getRole(role.getId());
			r.setName(role.getName());
			r.setRemark(role.getRemark());
			r.setModifier(AdminConstant.getSessionUser());
			r.setModifyDate(new Date());
		} catch (Exception e) {
			throw new OAException("更新角色方法出现异常",e);
		}
	}

	/**
	 * 删除角色
	 */
	@Override
	public void deleteRole(String[] roleIds) {
		try {
			for (String roleId : roleIds) {
				Role r = new Role();
				r.setId(Long.valueOf(roleId));
				roleDao.delete(r);
			}
		} catch (Exception e) {
			throw new OAException("删除角色方法出现异常",e);
		}
	}

	/** TODO ##################操作管理业务处理 ########################*/
	/**
	 * 加载操作树数据
	 * @return list集合
	 */
	@Override
	public List<Map<String, Object>> loadModuleTree() {
		try {
			List<Map<String, Object>> data  = moduleDao.getModuleByCodeAndName();
			for (Map<String, Object> map : data) {
				/** 计算父级code,添加到map集合中 */
				String code = map.get("id").toString();
				String parentCode = code.length() == CODE_LENGTH ? "0" : code.substring(0, code.length() - CODE_LENGTH);
				map.put("pid", parentCode);
				/** 将name中的中划线去掉 */
				String name = map.get("name").toString();
				map.put("name", name.replaceAll("-",	""));
			}
			return data;
		} catch (Exception e) {
			throw new OAException("加载操作树数据时出现异常",e);
		}
	}

	/**
	 * 分页显示操作
	 * @param parentCode 父级code
	 * @param pageModel	分页实体
	 * @return
	 */
	@Override
	public List<Module> getModuleByPage(String parentCode, PageModel pageModel) {
		try {
			List<Module> modules = moduleDao.getModuleByPage(parentCode, pageModel, CODE_LENGTH);
			for (Module module : modules) {
				/** 加载延迟加载的属性数据 */
				if(module.getCreater() != null) module.getCreater().getName();
				if(module.getModifier() != null) module.getModifier().getName();
			}
			return modules;
		} catch (Exception e) {
			throw new OAException("分页显示操作时出现异常",e);
		}
	}

	/**
	 * 添加操作
	 * @param parentCode 父级code
	 * @param module 操作实体
	 */
	@Override
	public void addModule(String parentCode, Module module) {
		try {
			/** 判断父级Code是否为空 */
			parentCode = StringUtils.isEmpty(parentCode) ? "" : parentCode;
			/** 根据父级code生成主键列的值 */
			String code = generatorDao.generatorCode(Module.class, "code", CODE_LENGTH, parentCode);
			module.setCode(code);
			module.setName(parentCode.replaceAll(".", "-") + module.getName());
			module.setUrl(module.getUrl());
			module.setRemark(module.getRemark());
			module.setCreater(AdminConstant.getSessionUser());
			module.setCreateDate(new Date());
			moduleDao.save(module);
		} catch (Exception e) {
			throw new OAException("添加操作时出现异常", e);
		}
		
	}

	/**
	 * 根据主键获取操作
	 * @param code 主键code
	 * @return 操作实体
	 */
	@Override
	public Module getModule(String code) {
		try {
			Module module =  moduleDao.get(Module.class, code);
			module.setName(module.getName().replaceAll("-", ""));
			return module;
		} catch (Exception e) {
			throw new OAException("根据主键获取操作时出现异常", e);
		}
		
	}

	/**
	 * 修改操作
	 * @param 操作实体
	 */
	@Override
	public void updateModule(Module module) {
		try {
			Module m = this.getModule(module.getCode());
			m.setModifier(AdminConstant.getSessionUser());
			m.setModifyDate(new Date());
			
			String parentCode = module.getCode().substring(0, module.getCode().length() - CODE_LENGTH);
			m.setName(parentCode.replaceAll(".", "-") + module.getName());
			m.setUrl(module.getUrl());
			m.setRemark(module.getRemark());
		} catch (Exception e) {
			throw new OAException("修改操作时出现异常", e);
		}
	}

	/**
	 * 删除操作
	 * @param moduleCodes 主键数组
	 */
	@Override
	public void deleteModule(String[] moduleCodes) {
		try {
			for (String code : moduleCodes) {
				Module module = new Module();
				module.setCode(code);
				moduleDao.delete(module);
			}
			
		} catch (Exception e) {
			throw new OAException("删除操作时出现异常", e);
		}
		
	}

	/** TODO ######################## 角色绑定业务处理 ########################### */
	/**
	 * 根据角色id查询用户（当前角色已绑定的用户）
	 * @param pageModel 分页实体
	 * @param roleId 角色id
	 * @return 用户集合
	 */
	public List<User> getUserByPageAndRoleId(PageModel pageModel, Long roleId)
	{
		try {
			List<User> users = userDao.getUserByPageAndRoleId(pageModel, roleId);
			/** 加载延迟加载的属性 */
			for (User user : users) {
				user.getDept().getName();
				user.getJob().getName();
				user.getCreater().getName();
				user.getChecker().getName();
			}
			return users;
		} catch (Exception e) {
			throw new OAException("根据角色id查询用户（当前角色已绑定的用户）时出现异常", e);
		}
	}

	/**
	 * 获取未绑定到当前角色的用户
	 * @param pageModel 分页实体
	 * @param roleId 角色id
	 * @return
	 */
	public List<User> getUserByPageAndNotRoleId(PageModel pageModel, Long roleId)
	{
		try {
			List<User> users = userDao.getUserByPageAndNotRoleId(pageModel, roleId);
			/** 加载延迟加载的属性 */
			for (User user : users) {
				user.getJob().getName();
				user.getCreater().getName();
			}
			return users;
		} catch (Exception e) {
			throw new OAException("获取未绑定到当前角色的用户时出现异常", e);
		}
	}
	/**
	 * 为角色绑定用户
	 * @param roleId 角色id
	 * @param userIds 用户id
	 */
	public void bindUser(Long roleId, String[] userIds)
	{
		try {
			/** entity中配置了由Role一方维护关系 */
			/** 获取持久化角色对象 */
			Role role = roleDao.get(Role.class, roleId);
			/** 获取已绑定的用户集合 */
			Set<User> users = role.getUsers();
			/** 迭代向用户集合中添加新绑定的用户  */
			for (String userId : userIds) {
				User user = new User();
				user.setUserId(userId);
				users.add(user);
			}
			/** 将用户集合设置回角色 */
			role.setUsers(users);
		} catch (Exception e) {
			throw new OAException("为角色绑定用户时出现异常", e);
		}
	}
	/**
	 * 为角色解除用户
	 * @param roleId 角色id
	 * @param userIds 用户id
	 */
	public void unBindUser(Long roleId, String[] userIds)
	{
		try {
			/** entity中配置了由Role一方维护关系 */
			/** 获取持久化角色对象 */
			Role role = roleDao.get(Role.class, roleId);
			/** 获取已绑定的用户集合 */
			Set<User> users = role.getUsers();
			/** 迭代从用户集合中移除解除的用户  */
			for (String userId : userIds) {
				User user = new User();
				user.setUserId(userId);
				/** 需要重写User实体类的hashCode和equals方法 */
				users.remove(user);
			}
			/** 将用户集合设置回角色 */
			role.setUsers(users);
		} catch (Exception e) {
			throw new OAException("为角色绑定用户时出现异常", e);
		}
	}
	/**
	 * 加载用于创建绑定操作页面权限树节点的数据
	 * @return
	 */
	public List<Map<String, Object>> loadPopdomTree()
	{
		try {
			/** 4位和8位code的是模块，12位code的是具体操作, 只要8位和4位code的 */
			List<Map<String, Object>> list = moduleDao.getModuleByCodeAndName(CODE_LENGTH * 2);
			/** 向map中添加父级code */
			for (Map<String, Object> map : list) {
				String code = map.get("id").toString();
				String parentCode = 
						code.length() == CODE_LENGTH ? "0" : code.substring(0, (code.length() - CODE_LENGTH));
				map.put("pid", parentCode);
				/** 处理name中的中划线 */
				String name = map.get("name").toString();
				map.put("name", name.replaceAll("-", ""));
			}
			return list;
			
		} catch (Exception e) {
			throw new OAException("加载用于创建绑定操作页面权限树时出现异常", e);
		}
	}
	
	/**
	 * 根据8位的模块code获取12位的操作
	 * @param moduleCode
	 * @return 操作实体集合
	 */
	public List<Module> getModuleByCode8(String moduleCode)
	{
		try {
			return moduleDao.getModuleByCode8(CODE_LENGTH * 3, moduleCode);
		} catch (Exception e) {
			throw new OAException("根据8位的模块code获取12位的操作时出现异常", e);
		}
	}
	
	/**
	 * 查询当前角色在当前模块下已经绑定的操作
	 * @param id 角色id
	 * @param moduleCode 模块code
	 * @return 操作code集合
	 */
	public List<String> getOperaCodeByRoleIdAndModuleCode(Long id, String moduleCode)
	{
		try {
			return popedomDao.getOperaCodeByRoleIdAndModuleCode(id, moduleCode);
		} catch (Exception e) {
			throw new OAException("查询当前角色在当前模块下已经绑定的操作时出现异常", e);
		}
	}
	/**
	 * 角色绑定操作
	 * @param codes 操作code 12位
	 * @param moduleCode 模块code 8位
	 * @param role 角色实体(id)
	 */
	public void bindModule(String codes, String moduleCode, Role role)
	{
		try {
			/** 先删除当前角色在此模块中已有的所有操作权限 */
			popedomDao.delete(role.getId(), moduleCode);
			/** 再保存新的操作权限 */
			if( !StringUtils.isEmpty(codes))
			{
				/** 创建模块,设置模块code */
				Module module = new Module();
				module.setCode(moduleCode);
				/**
				 *遍历选中的每一个操作code，
				 *每个选中的操作code和当前模块code和当前角色id构成一条popedom记录
				 */
				for (String code : codes.split(",")) {
					/** 创建一个popedom */
					Popedom popedom = new Popedom();
					popedom.setCreater(AdminConstant.getSessionUser());
					popedom.setCreateDate(new Date());
					/** 为popedom设置模块code */
					popedom.setModule(module);
					/** 为popedom设置角色id */
					popedom.setRole(role);
					/** 创建操作,设置操作code */
					Module opera = new Module();
					opera.setCode(code);
					/** 为popedom设置操作code*/
					popedom.setOpera(opera);
					/** 保存一条popedom记录 */
					popedomDao.save(popedom);
				}
			}
		} catch (Exception e) {
			throw new OAException("角色绑定操作时出现异常", e);
		}
	}
	
	
	/** TODO ######################## 当前登录用户的权限业务处理 ########################### */
	/**
	 * 根据当前登录用户加载对应的菜单树
	 * @return
	 */
	public List<Map<String, Object>> loadMenuTree()
	{
		try {
			/** 根据用户id查询对应角色id，再根据角色id查询对应的8位模块code */
			List<String> codeList = popedomDao.getModuleCodeByUserId(AdminConstant.getSessionUser().getUserId());
			//[00010001, 00010002, 00010003, 
			//00020001, 00020002, 00020003, 00020004, 
			//00030001, 00030002, 00040001, 00040002]
			/** 保存用来生成菜单树的数据 */
			List<Map<String, Object>> treeData = new ArrayList<Map<String, Object>>();
			/** 保存4位父级code */
			String parentCode = null;
			for(String code : codeList)
			{
				/** 为8位的code生成节点数据 */
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", code);
				map.put("pid", code.substring(0, code.length() - CODE_LENGTH));
				/** 根据code查询模块名 */
				Module module = moduleDao.get(Module.class, code);
				map.put("name", module.getName().replaceAll("-", ""));
				map.put("url", module.getUrl());
				treeData.add(map);
				
				/** 当父级code不为空，且本次code是以父级code开头时，不再生成4位父级code的节点数据，避免重复 */
				if(parentCode != null && code.startsWith(parentCode))
				{
					continue;
				}
				
				/** 截取4位父级code，生成节点数据 */
				parentCode = code.substring(0, code.length() - CODE_LENGTH);
				/** 此处要重新new，否则引用类型map的改变会导致上一个已存入treeData的map值也发生改变 */
				map = new HashMap<String, Object>();
				map.put("id", parentCode);
				map.put("pid", "0");
				module = moduleDao.get(Module.class, parentCode);
				map.put("name", module.getName());
				map.put("url", module.getUrl());
				treeData.add(map);
			}
			return treeData;
		} catch (Exception e) {
			throw new OAException("根据当前登录用户加载对应的菜单树时出现异常", e);
		}
	}
	
	/**
	 * 根据用户id查询所有的角色id，再根据角色id查询所有的12位权限code,
	 * 并将结果按照模块id分组,返回分组后的map集合
	 * @param userId
	 * @return map集合
	 */
	public Map<String, List<String>> getAllPopedomByUserId(String userId) {
		try {
			/** 查询用户对应的所有12位操作code */
			List<String> popedomList = popedomDao.getOperaCodeByUserId(userId);
			//[000100010001, 000100010002, 000100010003, 000100010004, 000100020001,...
			//000200010001, 000200010002, 000200010003, 000200010004, 000200010005, ...
			//000300010001, 000300010002, 000300020001, 000300020002, ...
			//000400010001, 000400010002, 000400010003, 000400010004, 000400020001,...
			/** 定义map集合保存按模块code分组后的12位操作code对应的url */
			Map<String, List<String>> popedomMap = new HashMap<String, List<String>>();
			/** 保存模块code，作为map的key */
			String parentCode = null;
			/** 保存分组后的12位操作code对应的url，作为map的value */
			List<String> codeUrlList = new ArrayList<String>();
			/** 遍历用户对应的所有12位操作code，进行分组处理 */
			for (String code : popedomList) {
				/** 当遍历到下一组的第一个code时，将上一组list添加到map集合中 */
				if(parentCode != null && !code.startsWith(parentCode))
				{
					popedomMap.put(parentCode, codeUrlList);
					/** 置空list，准备向list中添加下一组数据 */
					codeUrlList = new ArrayList<String>();
				}
				parentCode = code.substring(0, code.length() - CODE_LENGTH); //0004
				codeUrlList.add(this.getModule(code).getUrl());
			}
			/** 将最后一组list，添加到map中 */
			if(parentCode != null && codeUrlList.size() > 0)
			{
				popedomMap.put(parentCode, codeUrlList);
			}
			//00010001=[/admin/identity/selectUser.jspx, ...
			//00010002=[/admin/identity/selectRole.jspx, ...
			//00010003=[/admin/identity/mgrModule.jspx, ...
			//00020001=[/admin/leave/selectLeaveType.jspx,...
			//00020002=[/admin/leave/mgrLeaveItem.jspx,...
			//00020003=[/admin/leave/selectUserLeave.jspx,...
			//00020004=[/admin/leave/selectAuditLeave.jspx,...
			//00030001=[/admin/workflow/workflowDiagarm.jspx,...
			//00030002=[/admin/workflow/selectDeployment.jspx,...
			//00040001=[/admin/addressbook/selectContactGroup.jspx,...
			//00040002=[/admin/addressbook/mgrContact.jspx, ...
			return popedomMap;
		} catch (Exception e) {
			throw new OAException("根据用户id查询所有的角色id，再根据角色id查询所有的12位权限code时出现异常", e);
		}
	}

	/** TODO ######################## 用户密码相关业务处理 ########################### */
	/**
	 * 找回密码
	 * @param userId 用户编号
	 * @param question 密保问题代号
	 * @param answer 密保问题答案
	 * @return 提示信息
	 */
	public String findPassword(String userId, int question, String answer)
	{
		try {
			String tip = "找回密码失败";
			if(!StringUtils.isEmpty(userId))
			{
				/** 判断用户编号是否存在 */
				User user = this.getUser(userId, false);
				/** 判断密保问题和答案是否正确 */
				if(user.getQuestion() == (short)question && user.getAnswer().equals(answer))
				{
					/** 生成新密码 */
					String newPwd = UUID.randomUUID().toString()
							.replaceAll("-", "").substring(0, 6);
					String message = "<h1>您好,</h1>你的新密码为:<font color='red'>" + newPwd + "</font>,请及时更改。";
					System.out.println(message);
					/** 将新密码发送邮件告知用户 */
					boolean isSuccess = emailSender.send(user.getEmail(), "办公管理系统-找回密码", message, true);
					if(isSuccess)
					{
						/** 将用户的新密码进行MD5加密并保存到数据库 */
						user.setPassWord(MD5_32.encryption(newPwd));
						/** 设置页面上的提示信息 */
						tip = "新密码已发送到您的邮箱" + user.getEmail();
					}
				}
			}
			return tip;
		} catch (Exception e) {
			throw new OAException("找回密码时出现异常", e);
		}
	}
	

	/**
	 * 修改密码
	 * @param oldpwd 旧密码
	 * @param newpwd 新密码
	 * @param okpwd 确认新密码
	 * @return 是否修改成功
	 */
	public boolean updatePassword(String oldpwd, String newpwd, String okpwd)
	{
		try {
			if(!StringUtils.isEmpty(newpwd) && newpwd.equals(okpwd))
			{
				/** 获取session中的用户 */
				User user = AdminConstant.getSessionUser();
				if(user.getPassWord().equals(MD5_32.encryption(oldpwd)))
				{
					/** 获取持久化状态用户 */
					user = this.getUser(user.getUserId(),false);
					/** 将新密码MD5加密，并保存到数据库 */
					user.setPassWord(MD5_32.encryption(newpwd));
					return true;
				}
			}
			
		} catch (Exception e) {
			throw new OAException("修改密码时出现异常", e);
		}
		return false;
	}
	
	/** TODO ######################## 部门管理业务处理 ########################### */

	/**
	 * 分页查询部门
	 * @param pageModel 分页实体
	 * @return List
	 */
	public List<Dept> getDeptByPage(PageModel pageModel)
	{
		try {
			List<Dept> deptList = deptDao.getDeptByPage(pageModel);
			/** 加载延迟加载的属性 */
			for (Dept dept : deptList) {
				if(dept.getCreater() != null) dept.getCreater().getName();
				if(dept.getModifier() != null) dept.getModifier().getName();
			}
			return deptList;
		} catch (Exception e) {
			throw new OAException("分页查询部门时出现异常", e);
		}
	}
	/**
	 * 添加部门
	 * @param dept
	 */
	public void addDept(Dept dept)
	{
		try {
			dept.setCreateDate(new Date());
			dept.setCreater(AdminConstant.getSessionUser());
			deptDao.save(dept);
		} catch (Exception e) {
			throw new OAException("添加部门时出现异常", e);
		}
	}

	/**
	 * 主键查询部门
	 * @param id
	 * @return
	 */
	public Dept getDept(Long id)
	{
		try {
			return deptDao.get(Dept.class, id);
		} catch (Exception e) {
			throw new OAException("主键查询部门时出现异常", e);
		}
	}
	/**
	 * 修改部门
	 * @param dept
	 */
	public void updateDept(Dept dept)
	{
		try {
			/** 因为前台未传递创建时间和创建人，所以需要获取一个持久化对象 */
			Dept d = deptDao.get(Dept.class, dept.getId());
			d.setName(dept.getName());
			d.setRemark(dept.getRemark());
			d.setModifier(AdminConstant.getSessionUser());
			d.setModifyDate(new Date());
		} catch (Exception e) {
			throw new OAException("修改部门时出现异常", e);
		}
	}
	/**
	 * 删除部门
	 * @param ids
	 */
	public void deleteDept(String[] ids)
	{
		try {
			deptDao.deleteDept(ids);
		} catch (Exception e) {
			throw new OAException("删除部门时出现异常", e);
		}
	}
	
	/** TODO ######################## 职位管理业务处理 ########################### */

	/**
	 * 分页查询职位
	 * @param pageModel 分页实体
	 * @return List
	 */
	public List<Job> getJobByPage(PageModel pageModel)
	{
		try {
			return jobDao.getJobByPage(pageModel);
		} catch (Exception e) {
			throw new OAException("分页查询职位时出现异常", e);
		}
	}
	/**
	 * 添加职位
	 * @param job
	 */
	public void addJob(Job job)
	{
		try {
			/** 生成新的职位code */
			String code = generatorDao.generatorCode(Job.class, "code", CODE_LENGTH, null);
			job.setCode(code);
			jobDao.save(job);
		} catch (Exception e) {
			throw new OAException("添加职位时出现异常", e);
		}
	}

	/**
	 * 主键查询职位
	 * @param code
	 * @return
	 */
	public Job getJob(String code)
	{
		try {
			return jobDao.get(Job.class, code);
		} catch (Exception e) {
			throw new OAException("主键查询职位时出现异常", e);
		}
	}
	/**
	 * 修改职位
	 * @param job
	 */
	public void updateJob(Job job)
	{
		try {
			jobDao.update(job);
		} catch (Exception e) {
			throw new OAException("修改职位时出现异常", e);
		}
	}
	/**
	 * 删除职位
	 * @param ids
	 */
	public void deleteJob(String[] codes)
	{
		try {
			jobDao.deleteJob(codes);
		} catch (Exception e) {
			throw new OAException("删除职位时出现异常", e);
		}
	}
	
	
	
	
	
	
	
	
	
	/*测试事务控制
	public void run() 
	{
		try {
			Dept d = new Dept();
			d.setName("运维部");
			deptDao.save(d);
			
			int i = 1 / 0;
			
			Job j = new Job();
			j.setCode("0010");
			j.setName("运维工程师");
			jobDao.save(j);
		} catch (Exception e) {
			//e.printStackTrace();
			//throw new Exception(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void getDept() {
		Dept d = new Dept();
		d.setName("运维部222");
		deptDao.save(d);
		System.out.println("test运维部222");
	}
	*/
}
