package com.jsoft.oa.core.common.email.impl;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.jsoft.oa.core.common.email.EmailSender;
/**
 * 邮件发送接口的实现类
 * @author jack
 * @email wujunwei6@163.com
 * @date 2016年9月7日 下午1:15:26 
 * @version V1.0
 */
public class EmailSenderImpl implements EmailSender {
	/** 注入spring的邮件发送接口 */
	private JavaMailSender javaMailSender;
	/** 邮件发送人 */
	private String from;
	/**
	 * 发送邮件方法
	 * @param to 对方邮箱地址
	 * @param subject 主题
	 * @param message 内容
	 * @param isHtml 内容是否支持html
	 * @return 是否发送成功
	 */
	public boolean send(String to, String subject, String message, boolean isHtml)
	{	
		try {
			/** 创建邮件消息体 */
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			/** 创建邮件消息体的帮助类 */
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
			/** 设置邮件接收人 */
			mimeMessageHelper.setTo(to);
			/** 设置邮件发送人 */
			mimeMessageHelper.setFrom(from);
			/** 设置邮件主题 */
			mimeMessageHelper.setSubject(subject);
			/** 设置邮件内容 */
			mimeMessageHelper.setText(message, isHtml);
			/** 发送邮件 */
			javaMailSender.send(mimeMessage);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
}
