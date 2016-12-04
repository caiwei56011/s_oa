package com.jsoft.oa.admin.workflow.action;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.struts2.ServletActionContext;

/**
 * 显示流程图控制器
 * @author jack
 * @version V1.0
 */
public class ProcessDiagramAction extends WorkflowAction {
	private static final long serialVersionUID = -7394614808564439721L;
	private String processInstanceId;
	/** 注入运行时服务接口 */
	@Resource
	private RuntimeService runtimeService;
	/** 注入仓储服务接口 */
	@Resource
	private RepositoryService repositoryService;
	
	public String processDiagram() throws Exception
	{
		/** 根据流程实例id查询单条流程实例 */
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
					.processInstanceId(processInstanceId).singleResult();
		/** 根据流程实例表中保存的流程定义id查询对应的流程定义文件的图片 */
		InputStream inputStream = repositoryService.getProcessDiagram(pi.getProcessDefinitionId());
		/** 将流程图片的输入流转换为缓冲流图片 */
		BufferedImage image = ImageIO.read(inputStream);
		/** 通过缓冲流图片获取2D画笔 */
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		/** 设置画笔粗细、颜色、消除锯齿 */
		graphics.setStroke(new BasicStroke(3.0f));
		graphics.setColor(Color.RED);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		/** 根据流程定义id查询流程定义文件的bpmn文件（文件中保存了节点的坐标信息） */
		BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
		/** 根据流程实例id查询流程实例中所有的活动节点id */
		List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
		/** 迭代所有活动节点id */
		for (String activeActivityId : activeActivityIds) {
			/** 根据活动节点id在bpmnModel中获取活动节点的坐标信息 */
			GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(activeActivityId);
			/** 在图片上活动节点环绕画一个圆角矩形 */
			graphics.drawRoundRect((int)graphicInfo.getX(), (int)graphicInfo.getY(), 
					(int)graphicInfo.getWidth(), (int)graphicInfo.getHeight(), 10, 10);
		}
		/** 获取响应输出流 */
		HttpServletResponse response = ServletActionContext.getResponse();
		/** 设置响应内容类型 */
		response.setContentType("images/png");
		/** 向浏览器输出图片 */
		ImageIO.write(image, "png", response.getOutputStream());
		return NONE;
	}
	
	
	
	
	
	
	/** getter and setter method */
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	

}
