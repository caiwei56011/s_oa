package com.jsoft.oa.admin.leave.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.jsoft.oa.admin.AdminConstant;
import com.jsoft.oa.admin.identity.dao.UserDao;
import com.jsoft.oa.admin.identity.entity.User;
import com.jsoft.oa.admin.leave.dao.LeaveAuditDao;
import com.jsoft.oa.admin.leave.dao.LeaveItemDao;
import com.jsoft.oa.admin.leave.dao.LeaveTypeDao;
import com.jsoft.oa.admin.leave.entity.LeaveAudit;
import com.jsoft.oa.admin.leave.entity.LeaveItem;
import com.jsoft.oa.admin.leave.entity.LeaveType;
import com.jsoft.oa.admin.leave.service.LeaveService;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.dao.GeneratorDao;
import com.jsoft.oa.core.exception.OAException;
/**
 * 请假管理业务处理接口的实现类
 * @version V1.0
 */
public class LeaveServiceImpl implements LeaveService{
	/** 整合该模块下的所有数据访问接口,实现业务处理 */
	@Resource
	private LeaveAuditDao leaveAuditDao;
	@Resource
	private LeaveItemDao leaveItemDao;
	@Resource
	private LeaveTypeDao leaveTypeDao;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;
	@Resource
	private HistoryService historyService;
	@Resource
	private UserDao userDao;
	@Resource
	private GeneratorDao generatorDao;
	
	/** TODO ################## 用户请假业务处理 ############### */ 
	/**
	 * 分页查询当前登录用户的请假单
	 * @param leaveItem 请假单类型
	 * @param pageModel 分页实体
	 * @return
	 */
	public List<LeaveItem> getUserLeaveByPage(LeaveItem leaveItem, PageModel pageModel)
	{
		try {
			List<LeaveItem> list = leaveItemDao.getUserLeaveByPage(leaveItem, pageModel);
			/** 加载延迟加载的属性 */
			for (LeaveItem item : list) {
				if(item.getCreater() != null) item.getCreater().getName();
				if(item.getLeaveType() != null) item.getLeaveType().getName();
			}
			return list;
		} catch (Exception e) {
			throw new OAException("分页查询当前登录用户的请假单时出现异常", e);
		}
	}
	/**
	 *  异步加载假期类型(code和name)
	 * @return List
	 */
	public List<Map<String, Object>> getLeaveTypeByCodeAndName()
	{
		try {
			return leaveTypeDao.getLeaveTypeByCodeAndName();
		} catch (Exception e) {
			throw new OAException("异步加载假期类型(code和name)时出现异常", e);
		}
	}
	/**
	 * 加载流程定义
	 * @return List
	 */
	public List<Map<String, Object>> loadProcess(){
		try{
			/** 创建流程定义查询对象 */
			List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
				             .latestVersion()
				             .orderByDeploymentId()
				             .asc()
				             .list();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			for (ProcessDefinition pd : processDefinitions){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", pd.getId());
				map.put("name", pd.getName());
				data.add(map);
			}
			return data;
		}catch(Exception ex){
			throw new OAException("加载假期类型时出现异常！", ex);
		}
	}
	/**
	 * 加载假期类型 与 流程定义
	 * @return List
	 */
	public List<List<Map<String, Object>>> loadLeaveTypeAndProcess(){
		try{
			// [[{},{}],[{},{}]] : List<List<Map>>
			List<List<Map<String, Object>>> data = new ArrayList<List<Map<String, Object>>>();
			/** 添加假期类型 */
			data.add(getLeaveTypeByCodeAndName());
			/** 添加流程定义 */
			data.add(loadProcess());
			return data;
		}catch(Exception ex){
			throw new OAException("加载假期类型 与 流程定义时出现异常！", ex);
		}
	}
	/**
	 * 保存用户填写的请假单
	 * @param leaveItem
	 */
	public void saveLeaveItem(LeaveItem leaveItem)
	{
		try{
			/** 获取流程定义id */
			String processDefId = leaveItem.getProcInstanceId();
			/** 获取当前登录用户 */
			User user = AdminConstant.getSessionUser();
			/** 将user转换为持久化状态对象 */
			user = userDao.get(User.class, user.getUserId());
			/** 封装流程变量 jobCode deptId */
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("jobCode", Integer.valueOf(user.getJob().getCode()));
			map.put("deptId", user.getDept().getId());
			/** 根据流程定义id开启流程实例，同时传入流程变量 */
			ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefId, map);
			/** 设置请假单创建日期、创建人 */
			leaveItem.setCreateDate(new Date());
			leaveItem.setCreater(user);
			/** 设置请假单的流程实例id */
			leaveItem.setProcInstanceId(processInstance.getId());
			/** 保存请假单，同时产生请假单id */
			leaveItemDao.save(leaveItem);
			/** 添加流程变量leaveId，记录流程实例属于哪个请假单 */
			runtimeService.setVariable(processInstance.getId(), "leaveId", leaveItem.getId());
		}catch(Exception ex){
			throw new OAException("用户添加请假单时出现异常！", ex);
		}
	}
	
	/** TODO ################## 假期审批业务处理 ############### */ 
	/**
	 * 查询当前登录用户需要审批的请假单 
	 * @return
	 */
	public List<LeaveItem> queryAuditLeave()
	{
		try {
			/** 获取当前登录的用户 */
			User user = AdminConstant.getSessionUser();
			/** 将user转换为持久化对象，用来获取延迟加载的属性（职位code和部门id） */
			user = userDao.get(User.class, user.getUserId());
			/** 获取用户的职位 */
			String userJobCode = user.getJob().getCode();
			/** 查询任务候选组id等于用户的职位code的所有可以领取的任务 */
			List<Task> tasklist = taskService.createTaskQuery()
				.taskCandidateGroup(userJobCode)
				.orderByTaskCreateTime().asc()
				.list();
			/** 获取用户的部门 */
			long userDeptId = user.getDept().getId();
			/** 遍历可领取的任务，再通过用户部门做一次判断是否确定领取 */
			for (Task task : tasklist) {
				/** 通过TaskService获取对应的流程实例中的deptId流程变量 */
				long deptId = taskService.getVariable(task.getId(), "deptId", Long.class);
				/** 若用户的部门和deptId流程变量相同，则领取该任务
				 * (或者当前登录用户是总经理,用户职位等于0009,直接领取任务)
				 *  */
				if(userDeptId == deptId || "0009".equals(userJobCode))
				{
					/**
					 * 领取任务
					 * 参数1：任务id
					 * 参数2：任务受托人 
					 * */
					taskService.claim(task.getId(), user.getUserId());
				}
			}

			/** 根据任务受托人查询当前用户所有已领取的任务（待完成的任务） */
			tasklist = taskService.createTaskQuery()
					.taskAssignee(user.getUserId()).list();
			/** 定义List集合存储所有需要审批的请假单 */
			List<LeaveItem> leaveItems = new ArrayList<LeaveItem>();
			/** 遍历所有已领取的任务,转换为请求单用于前台页面显示 */
			for (Task task : tasklist) {
				/** 通过任务对应的流程实例获取流程实例中存储的流程变量leaveId */
				long leaveId = taskService.getVariable(task.getId(), "leaveId", Long.class);
				/** 通过leaveId获取对应的请假单*/
				LeaveItem leaveItem = leaveItemDao.get(LeaveItem.class, leaveId);
				
				/** 加载延迟加载的属性 */
				if(leaveItem.getCreater() != null) 
					leaveItem.getCreater().getName();
				if(leaveItem.getLeaveType() != null)
					leaveItem.getLeaveType().getName();
				
				/** 为获取到的请假单设置任务id，(任务id时实体类中临时字段，未存入表)
				 * 用于假期审批时，完成对应的任务。
				 * */
				leaveItem.setTaskId(task.getId());
				/** 添加到需要审批的请假单集合 */
				leaveItems.add(leaveItem);
			}
			/** 返回要审批的请假单集合 */
			return leaveItems;
		} catch (Exception e) {
			throw new OAException("查询当前登录用户需要审批的请假单时出现异常！", e);
		}
		
	}
	/**
	 * 根据请假单id获取单个请假单
	 * @param id 请假单id
	 * @return 请假单实体
	 */
	public LeaveItem getLeaveItem(Long id)
	{
		try {
			LeaveItem leaveItem = leaveItemDao.get(LeaveItem.class, id);
			/** 加载延迟加载的属性 */
			if(leaveItem != null && leaveItem.getCreater() != null) 
				leaveItem.getCreater().getName();
			return leaveItem;
		} catch (Exception e) {
			throw new OAException("根据请假单id获取单个请假单时出现异常！", e);
		}
	}
	/**
	 * 审批请假单
	 * @param taskId 任务id
	 * @param leaveAudit 审批实体(包含请假单id和审批status)
	 */
	public void audit(String taskId, LeaveAudit leaveAudit)
	{
		try {
			/** 封装stauts流程变量 */
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", leaveAudit.getStatus());
			/** 根据任务id完成任务，同时传入status流程变量 */
			taskService.complete(taskId, map);
			/** 保存审批实体 */
			leaveAudit.setChecker(AdminConstant.getSessionUser());
			leaveAudit.setCheckDate(new Date());
			leaveAuditDao.save(leaveAudit);
			/** 根据请假单id查询请假单 */
			LeaveItem leaveItem = this.getLeaveItem(leaveAudit.getLeaveItem().getId());
			/** 根据请假单中保存的流程实例id查询历史流程实例 */
			HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
										.processInstanceId(leaveItem.getProcInstanceId()).singleResult();
			/** 根据历史流程实例的结束时间是否为null判断流程实例是否已结束 */
			if(hpi.getEndTime() != null)
			{
				/** 流程实例已结束则更新请假单表的状态  0:新建1:审核通过2:不通过 */
				leaveItem.setStatus(leaveAudit.getStatus() == 1 ? (short)1 : (short)2);
			}
			
		} catch (Exception e) {
			throw new OAException("审批请假单时出现异常！", e);
		}
	}

	/**
	 * 根据请假单id获取请假单审批记录
	 * @param leaveAudit 
	 * @return List 
	 */
	public List<LeaveAudit> getLeaveAuditByLeaveItemId(LeaveAudit leaveAudit)
	{
		try {
			List<LeaveAudit> list = leaveAuditDao.getLeaveAuditByLeaveItemId(leaveAudit);
			/** 加载延迟加载的属性 */
			for (LeaveAudit la : list) {
				if(la.getChecker() != null) 
				{
					la.getChecker().getName();
					if(la.getChecker().getJob() != null)
					{
						la.getChecker().getJob().getName();
					}
				}
				
			}
			return list;
		} catch (Exception e) {
			throw new OAException("根据请假单id获取请假单审批记录时出现异常！", e);
		}
	}
	
	/** TODO ################## 假期类型管理业务处理 ############### */ 
	/**
	 * 分页查询假期类型
	 * @param leaveType
	 * @param pageModel 
	 * @return
	 */
	public List<LeaveType> getLeaveTypeByPage(LeaveType leaveType, PageModel pageModel){
		try {
			List<LeaveType> list = leaveTypeDao.getLeaveTypeByPage(leaveType, pageModel);
			/** 加载延迟加载的属性 */
			for (LeaveType lt : list) {
				if(lt.getCreater() != null) lt.getCreater().getName();
				if(lt.getModifier() != null) lt.getModifier().getName();
			}
			return list;
		} catch (Exception e) {
			throw new OAException("分页查询假期类型时出现异常！", e);
		}
	}
	/**
	 * 添加假期类型
	 * @param leaveType
	 */
	public void saveLeaveType(LeaveType leaveType)
	{
		try {
			/** 生成新的code */
			String code = generatorDao.generatorCode(LeaveType.class, "code", 3, null);
			leaveType.setCode(code);
			leaveType.setCreateDate(new Date());
			leaveType.setCreater(AdminConstant.getSessionUser());
			leaveTypeDao.saveLeaveType(leaveType);
		} catch (Exception e) {
			throw new OAException("添加假期类型时出现异常！", e);
		}
	}
	/**
	 * 主键查询假期类型
	 * @param leaveType
	 * @return
	 */
	public LeaveType getLeaveTypeByCode(LeaveType leaveType)
	{
		try {
			
			return leaveTypeDao.getLeaveTypeByCode(leaveType.getCode());
		} catch (Exception e) {
			throw new OAException("主键查询假期类型时出现异常！", e);
		}
	}
	/**
	 * 修改假期类型
	 * @param leaveType
	 */
	public void updateLeaveType(LeaveType leaveType)
	{
		try {
			LeaveType lt = leaveTypeDao.getLeaveTypeByCode(leaveType.getCode());
			lt.setName(leaveType.getName());
			lt.setRemark(leaveType.getRemark());
			lt.setModifyDate(new Date());
			lt.setModifier(AdminConstant.getSessionUser());
		} catch (Exception e) {
			throw new OAException("修改假期类型时出现异常！", e);
		}
	}
	
	/**
	 * 删除假期类型
	 * @param codes
	 */
	public void deleteLeaveType(String[] codes)
	{
		try {
			for (String code : codes) {
				LeaveType leaveType = leaveTypeDao.getLeaveTypeByCode(code);
				leaveTypeDao.delete(leaveType);
			}
		} catch (Exception e) {
			throw new OAException("删除假期类型时出现异常！", e);
		}
	}
}

