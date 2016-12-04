package com.jsoft.oa.admin.addressbook.action;

import java.io.File;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.jsoft.oa.admin.addressbook.entity.Contact;
import com.jsoft.oa.core.common.excel.ExcelUtils;
import com.jsoft.oa.core.pojo.ContactBean;

/**
 * 联系人管理控制器
 * @author jack
 * @email wujunwei6@163.com
 * @date 2016年8月27日 下午10:09:22 
 * @version V1.0
 */
public class ContactAction extends AddressbookAction {
	private static final long serialVersionUID = 3840137951987906777L;
	private Contact contact;
	private List<Contact> contacts;
	private String contactIds;
	/** 定义文件上传属性 */
	private File excel;
	
	/** 分页查询联系人 */
	public String selectContact()
	{
		try {
			contacts = addressbookService.getContactByPage(pageModel, contact);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 添加联系人 */
	public String addContact()
	{
		try {
			addressbookService.addContact(contact);
			setTip("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("添加失败");
		}
		return SUCCESS;
	}
	/** 显示修改联系人页面  */
	public String showUpdateContact()
	{
		try {
			contact  = addressbookService.getContact(contact);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/** 修改联系人 */
	public String updateContact()
	{
		try {
			addressbookService.updateContact(contact);
			setTip("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("修改失败");
		}
		return SUCCESS;
	}
	/** 删除联系人 */
	public String deleteContact()
	{
		try {
			addressbookService.deleteContact(contactIds.split(","));
			setTip("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			setTip("删除失败");
		}
		return SUCCESS;
	}
	/** 导出联系人到excel  */
	public String exportContact()
	{
		try {
			/** 查询需要导出的联系人 */
			List<ContactBean> contacts = addressbookService.getContacts(contact);
			/** 调用ExcelUtils工具类将联系人数据写入到excel，并输出到浏览器 */
			/** 定义表格标题数组 */
			String[] titles = {"编号","姓名","性别","手机号码","邮箱", "QQ号码","生日","组名"};
			ExcelUtils.exportExcel("联系人信息", "通讯录", titles, contacts,ServletActionContext.getResponse(), ServletActionContext.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	/** 从Excel导入联系人 */
	public String importContact()
	{
		try {
			/** 调用ExcelUtils工具类读取excel文件数据并返回封装结果 */
			List<List<Object>> excelData = ExcelUtils.readExcel(excel);
			/** 保存Excel数据到数据库 */
			addressbookService.saveExcel(contact.getContactGroup(), excelData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	
	
	
	
	
	
	/** getter and setter method */
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	public String getContactIds() {
		return contactIds;
	}
	public void setContactIds(String contactIds) {
		this.contactIds = contactIds;
	}
	public File getExcel() {
		return excel;
	}
	public void setExcel(File excel) {
		this.excel = excel;
	}

}
