package com.jsoft.oa.admin.identity.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * 用户实体类
 * @author Administrator
 * @date 2016年8月15日 下午8:58:04 
 * @version V1.0
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "jsoftCache")
@Entity @Table(name="OA_ID_USER")
public class User implements Serializable{
	
	private static final long serialVersionUID = -8119022745771606427L;
	/*
	USER_ID	VARCHAR2(50)	用户ID	PK，大小写英文和数字
	PASS_WORD	VARCHAR2(50)	密码	MD5加密
	NAME	VARCHAR2(50)	姓名	
	SEX	NUMBER	性别	1:男 2:女
	DEPT_ID	NUMBER	部门	FK(OA_ID_DEPT)
	JOB_CODE	VARCHAR2(100)	职位	FK(OA_ID_JOB)
	EMAIL	VARCHAR2(50)	邮箱	
	TEL	VARCHAR2(50)	电话号码	
	PHONE	VARCHAR2(50)	手机号码	
	QQ_NUM	VARCHAR2(50)	QQ号码	
	QUESTION	NUMBER	问题编号	
	ANSWER	VARCHAR2(200)	回答结果	
	STATUS	NUMBER	状态	0新建,1审核,2不通过审核,3冻结 
	CREATE_DATE	DATE	创建时间	
	CREATER	VARCHAR2(50)	创建人	FK(OA_ID_USER)
	MODIFIER	VARCHAR2(50)	修改人	FK(OA_ID_USER)
	MODIFY_DATE	DATE	修改时间	
	CHECKER	VARCHAR2(50)	审核人	FK(OA_ID_USER)
	CHECK_DATE	DATE	审核时间	
	*/
	@Id
	@Column(name="user_id", length=50)
	private String userId;
	@Column(name="pass_word", length=50)
	private String passWord;
	@Column(name="name", length=50)
	private String name;
	@Column(name="sex")
	private Short sex;
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Dept.class)
	@JoinColumn(name="dept_id", 
		referencedColumnName="id", 
		foreignKey=@ForeignKey(name="FK_USER_DEPT") //指定外键名称
	)
	private Dept dept;
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Job.class)
	@JoinColumn(name="job_code", referencedColumnName="code", foreignKey=@ForeignKey(name="FK_USER_JOB"))
	private Job job;
	@Column(name="email", length=50)
	private String email;
	@Column(name="tel", length=50)
	private String tel;
	@Column(name="phone", length=50)
	private String phone;
	@Column(name="qq_num", length=50)
	private String qqNum;
	@Column(name="question")
	private Short question;
	@Column(name="answer", length=200)
	private String answer;
	@Column(name="status")
	private Short status = 0;
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="creater",referencedColumnName="user_id")
	private User creater;
	@Column(name="create_date")
	private Date createDate;
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="modifier", referencedColumnName="user_id")
	private User modifier;
	@Column(name="modify_date")
	private Date modifyDate;
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="checker", referencedColumnName="user_id")
	private User checker;
	@Column(name="check_date")
	private Date checkDate;
	
	/** 用户和角色之间存在多对多关系*/
	@ManyToMany(fetch=FetchType.LAZY, targetEntity=Role.class, mappedBy="users") //控制反转，由另一方维护关系
	private Set<Role> roles = new HashSet<Role>();

	
	
	@Override
	public int hashCode() {
		return this.getUserId().hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof User){
			return ((User)obj).getUserId().equals(this.getUserId());
		}
		return false;
	}
	
	/** getter and setter method */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getSex() {
		return sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQqNum() {
		return qqNum;
	}

	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}

	public Short getQuestion() {
		return question;
	}

	public void setQuestion(Short question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public User getModifier() {
		return modifier;
	}

	public void setModifier(User modifier) {
		this.modifier = modifier;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public User getChecker() {
		return checker;
	}

	public void setChecker(User checker) {
		this.checker = checker;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
}
