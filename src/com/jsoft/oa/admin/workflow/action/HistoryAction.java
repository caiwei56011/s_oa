package com.jsoft.oa.admin.workflow.action;

import java.util.List;

import org.activiti.engine.history.HistoricTaskInstance;

/**
 * 历史流程控制器
 * @author jack
 * @version V1.0
 */
public class HistoryAction extends WorkflowAction {
	private static final long serialVersionUID = -6594020398145748255L;
	private String processInstanceId;
	private List<HistoricTaskInstance>  historicTaskInstances;
	
	
	/** 查看历史任务 */
	@Override
	public String execute() throws Exception {
		try {
			historicTaskInstances = workflowService.getHistoricTaskInstance(processInstanceId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	
	/** getter and setter method */
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public List<HistoricTaskInstance> getHistoricTaskInstances() {
		return historicTaskInstances;
	}

	public void setHistoricTaskInstances(
			List<HistoricTaskInstance> historicTaskInstances) {
		this.historicTaskInstances = historicTaskInstances;
	}

	
	
	
}
