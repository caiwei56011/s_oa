package com.jsoft.oa.admin.workflow.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.apache.commons.lang3.StringUtils;

import com.jsoft.oa.admin.workflow.service.WorkflowService;
import com.jsoft.oa.core.common.web.PageModel;
import com.jsoft.oa.core.exception.OAException;
/**
 * 工作流业务处理接口实现类
 * @author jack
 * @version V1.0
 */
public class WorkflowServiceImpl implements WorkflowService {
	/** 注入activiti5中的仓储业务服务 */
	@Resource
	private RepositoryService repositoryService;
	/** 注入历史服务 */
	@Resource
	private HistoryService historyService;

	/**
	 * 流程部署
	 * @param name 流程定义名称
	 * @param bpmn 流程定义文件
	 * @param bpmnFileName 流程定义文件名
	 */
	public void processDeploy(String name, File bpmn, String bpmnFileName) {
		try {
			/** 创建流程部署构建器 */
			DeploymentBuilder builder = repositoryService.createDeployment();
			/** 添加说明名称 */
			builder.name(name);
			/** 添加分类 */
			builder.category(name);
			if(bpmnFileName.endsWith(".bpmn"))
			{
				/** 部署单个bpmn文件时，添加文件输入流 */
				builder.addInputStream(bpmnFileName, new FileInputStream(bpmn));
			}
			else /** 批量部署，多个bpmn文件打成zip压缩包时 */
			{
				builder.addZipInputStream(new ZipInputStream(new FileInputStream(bpmn)));
			}
			
			/** 调用部署方法 */
			builder.deploy();
		} catch (Exception e) {
			throw new OAException("流程部署时出现异常");
		}
	}
	/**
	 * 分页查询流程部署
	 * @param name 流程部署名称
	 * @param pageModel 分页实体
	 * @return list集合
	 */
	public List<Deployment> getDeploymentByPage(String name, PageModel pageModel)
	{
		try {
			/** 创建流程部署查询对象 */
			DeploymentQuery dq = repositoryService.createDeploymentQuery();
			/** name不为空时，加入name查询条件 */
			if(!StringUtils.isEmpty(name))
			{
				dq.deploymentNameLike("%" + name + "%");
			}
			/** 获取总记录数 */
			long recordCount = dq.count();
			if(recordCount == 0)
			{
				return null;
			}
			/** 将总记录数设置到分页实体 */
			pageModel.setRecordCount((int)recordCount);
			/** 从分页实体中获取分页的两个参数 */
			int startRow = pageModel.getStartRow();
			int pageSize = pageModel.getPageSize();
			/** 调用部署查询对象的分页查询方法(按照部署时间排序) */
			return dq.orderByDeploymenTime().asc().listPage(startRow, pageSize);
		} catch (Exception e) {
			throw new OAException("分页查询流程部署时出现异常");
		}
	}
	/**
	 * 删除流程部署 
	 * @param id 流程部署id
	 */
	public void deleteDeployment(String id)
	{
		try {
			/**
			 * 第二个参数：是否级联删除 act_ge_bytearray、act_re_procdef表数据
			 */
			repositoryService.deleteDeployment(id, true);
		} catch (Exception e) {
			throw new OAException("删除流程部署 时出现异常");
		}
	}
	
	/**
	 * 获取历史任务记录
	 * @param processInstanceId 流程实例id
	 * @return List
	 */
	public List<HistoricTaskInstance> getHistoricTaskInstance(String processInstanceId)
	{
		List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
											.processInstanceId(processInstanceId).finished()
											.orderByTaskCreateTime().asc().list();
		return list;
	}
}
