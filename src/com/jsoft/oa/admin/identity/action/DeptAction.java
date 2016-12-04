package com.jsoft.oa.admin.identity.action;

import java.util.List;

import com.jsoft.oa.admin.identity.entity.Dept;

/**
 * 部门处理器
 * @author jack
 * @version 1.0
 */
public class DeptAction extends IdentityAction{

	private static final long serialVersionUID = 6821912002003171121L;
	private List<Dept> depts;
	private Dept dept;
	private String ids;
	/** 分页查询部门 */
	public String selectDept()
	{
		try {
			depts = identityService.getDeptByPage(pageModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/** 添加部门 */
	public String addDept()
	{
		try {
			identityService.addDept(dept);
			setTip("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("添加失败");
		}
		return SUCCESS;
	}
	/** 显示修改部门页面 */
	public String showUpdateDept()
	{
		try {
			dept = identityService.getDept(dept.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/** 修改部门 */
	public String updateDept()
	{
		try {
			identityService.updateDept(dept);
			setTip("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("修改失败");
		}
		return SUCCESS;
	}
	/** 删除部门 */
	public String deleteDept()
	{
		try {
			identityService.deleteDept(ids.split(","));
			setTip("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("删除失败");
		}
		return SUCCESS;
	}
	
	
	
	
	
	
	
	
	/*测试事务控制
	public String execute() throws Exception
	{
		identityService.run();
		identityService.getDept();
		System.out.println(identityService);
		return SUCCESS;
	}
	*/
	/** getter and setter method */
	public List<Dept> getDepts() {
		return depts;
	}
	public void setDepts(List<Dept> depts) {
		this.depts = depts;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
	
}
