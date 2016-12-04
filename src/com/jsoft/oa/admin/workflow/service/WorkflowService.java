package com.jsoft.oa.admin.workflow.service;

import java.io.File;
import java.util.List;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;

import com.jsoft.oa.core.common.web.PageModel;

/**
 * 工作流业务处理接口
 * @author jack
 * @version V1.0
 */
public interface WorkflowService {

	/**
	 * 流程部署
	 * @param name 流程定义名称
	 * @param bpmn 流程定义文件
	 * @param bpmnFileName 流程定义文件名
	 */
	void processDeploy(String name, File bpmn, String bpmnFileName);

	/**
	 * 分页查询流程部署
	 * @param name 流程部署名称
	 * @param pageModel 分页实体
	 * @return list集合
	 */
	List<Deployment> getDeploymentByPage(String name, PageModel pageModel);

	/**
	 * 删除流程部署 
	 * @param id 流程部署id
	 */
	void deleteDeployment(String id);

	/**
	 * 获取历史任务记录
	 * @param processInstanceId 流程实例id
	 * @return List
	 */
	List<HistoricTaskInstance> getHistoricTaskInstance(String processInstanceId);

}
