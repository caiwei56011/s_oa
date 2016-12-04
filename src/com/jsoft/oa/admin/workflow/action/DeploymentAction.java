package com.jsoft.oa.admin.workflow.action;

import java.io.File;
import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

/**
 * 流程部署控制器
 * 
 * @author jack
 * @version V1.0
 */
public class DeploymentAction extends WorkflowAction {
	private static final long serialVersionUID = 7217051139339694258L;

	private String name;
	/** 文件上传参数 */
	private File bpmn;
	private String bpmnFileName;
	private List<Deployment> deployments;
	private String id;

	/** 部署流程 */
	public String processDeploy() {
		try {
			workflowService.processDeploy(name, bpmn, bpmnFileName);
			setTip("部署成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("部署失败");
		}
		return SUCCESS;
	}

	/** 分页查询流程部署 */
	public String selectDeployment() {
		try {
			/** 处理分页标签get方式传入的中文参数乱码问题 */
			if(ServletActionContext.getRequest().getMethod().equalsIgnoreCase("get") 
					&& !StringUtils.isEmpty(name))
			{
				name = new String(name.getBytes("iso8859-1"),"utf-8");
			}
			deployments = workflowService.getDeploymentByPage(name, pageModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/** 删除流程部署 */
	public String deleteDeployment()
	{
		try {
			workflowService.deleteDeployment(id);
			setTip("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("删除失败");
		}
		return SUCCESS;
	}
	
	
	
	/** getter and setter method */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public File getBpmn() {
		return bpmn;
	}

	public void setBpmn(File bpmn) {
		this.bpmn = bpmn;
	}

	public String getBpmnFileName() {
		return bpmnFileName;
	}

	public void setBpmnFileName(String bpmnFileName) {
		this.bpmnFileName = bpmnFileName;
	}
	public List<Deployment> getDeployments() {
		return deployments;
	}
	public void setDeployments(List<Deployment> deployments) {
		this.deployments = deployments;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
