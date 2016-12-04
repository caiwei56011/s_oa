package com.jsoft.oa.admin.identity.action;

import java.util.List;

import com.jsoft.oa.admin.identity.entity.Job;


public class JobAction extends IdentityAction{

	private static final long serialVersionUID = 6821912002003171121L;
	private List<Job> jobs;
	private Job job;
	private String codes;
	/** 分页查询职位 */
	public String selectJob()
	{
		try {
			jobs = identityService.getJobByPage(pageModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/** 添加职位 */
	public String addJob()
	{
		try {
			identityService.addJob(job);
			setTip("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("添加失败");
		}
		return SUCCESS;
	}
	/** 显示修改职位页面 */
	public String showUpdateJob()
	{
		try {
			job = identityService.getJob(job.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/** 修改职位 */
	public String updateJob()
	{
		try {
			identityService.updateJob(job);
			setTip("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("修改失败");
		}
		return SUCCESS;
	}
	/** 删除职位 */
	public String deleteJob()
	{
		try {
			identityService.deleteJob(codes.split(","));
			setTip("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("删除失败");
		}
		return SUCCESS;
	}
	
	
	
	
	
	
	

	/** getter and setter method */
	public List<Job> getJobs() {
		return jobs;
	}
	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	
	
	
	
}
