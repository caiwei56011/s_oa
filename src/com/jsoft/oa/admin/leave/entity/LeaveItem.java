package com.jsoft.oa.admin.leave.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jsoft.oa.admin.identity.entity.User;

/**
 * 假期明细实体
 * @version V1.0
 */
@Entity @Table(name="OA_LEAVE_ITEM")
public class LeaveItem implements Serializable {

	private static final long serialVersionUID = 5750687352942763975L;
	@Id @Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	/** 假期明细与假期类型存在多对一关联 */
	@ManyToOne(targetEntity=LeaveType.class, fetch=FetchType.LAZY)
	@JoinColumn(name="TYPE_CODE", referencedColumnName="CODE", 
			foreignKey=@ForeignKey(name="FK_LEAVE_ITEM_TYPE"))
	private LeaveType leaveType;
	/** 开始日期 */
	@Column(name="BEGIN_DATE")
	private Date beginDate;
	/** 结束日期 */
	@Column(name="END_DATE")
	private Date endDate;
	/** 请假小时 */
	@Column(name="LEAVE_HOUR")
	private Float leaveHour;
	/** 请假原因 */
	@Column(name="LEAVE_CASE", length=300)
	private String leaveCase;
	/** 状态  0:新建1:审核通过2:不通过*/
	@Column(name="STATUS")
	private Short status = 0;
	/** 创建日期 */
	@Column(name="CREATE_DATE")
	private Date createDate;
	/** 创建人 */
	@ManyToOne(targetEntity=User.class, fetch=FetchType.LAZY)
	@JoinColumn(name="CREATER", referencedColumnName="USER_ID",
			foreignKey=@ForeignKey(name="FK_LEAVE_ITEM_CREATER"))
	private User creater;
	/** PROC_instance_ID	Varchar2(100)	流程实例ID	*/
	@Column(name="PROC_INSTANCE_ID", length=100)
	private String procInstanceId;
	
	/** 定义任务ID */
	@Transient // 不是持久化属性，不会生成表中的列名
	private String taskId;
	
	/** setter and getter method */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LeaveType getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Float getLeaveHour() {
		return leaveHour;
	}
	public void setLeaveHour(Float leaveHour) {
		this.leaveHour = leaveHour;
	}
	public String getLeaveCase() {
		return leaveCase;
	}
	public void setLeaveCase(String leaveCase) {
		this.leaveCase = leaveCase;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public User getCreater() {
		return creater;
	}
	public void setCreater(User creater) {
		this.creater = creater;
	}
	public String getProcInstanceId() {
		return procInstanceId;
	}
	public void setProcInstanceId(String procInstanceId) {
		this.procInstanceId = procInstanceId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}