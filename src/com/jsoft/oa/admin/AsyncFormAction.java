package com.jsoft.oa.admin;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 表单异常上传（文件）控制器
 * @author jack
 * @email wujunwei6@163.com
 * @date 2016年9月7日 下午7:43:28 
 * @version V1.0
 */
public class AsyncFormAction extends ActionSupport {
	private static final long serialVersionUID = -6721102909388230085L;
	private String remark;
	private File pic;
	private String picFileName;
	private String imgUrl;
	@Override
	public String execute() throws Exception {
		System.out.println("文件说明：" + remark);
		/** 获取项目路径下的上传文件保存路径 */
		String path = ServletActionContext.getServletContext().getRealPath("/images/pic");
		File dir = new File(path);
		/** 判断目录是否存在 */
		if(!dir.exists())
		{
			/** 不存在则创建目录 */
			dir.mkdirs();
		}
		/** 修改上传文件名 */
		String newFileName = UUID.randomUUID().toString() 
				+ "." + FilenameUtils.getExtension(picFileName);
		/** 上传文件拷贝到新路径下 */
		FileUtils.copyFile(pic, new File(path + "/" + newFileName));
		
		imgUrl = "/images/pic/" + newFileName;
		//return SUCCESS;
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(imgUrl);
		return NONE;
		
	}
	
	
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public File getPic() {
		return pic;
	}
	public void setPic(File pic) {
		this.pic = pic;
	}
	public String getPicFileName() {
		return picFileName;
	}
	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
}
