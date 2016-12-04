package com.jsoft.oa.core.common.email;
/**
 * 邮件发送接口
 * @author jack
 * @email wujunwei6@163.com
 * @date 2016年9月7日 下午1:14:06 
 * @version V1.0
 */
public interface EmailSender {

	/**
	 * 发送邮件方法
	 * @param to 对方邮箱地址
	 * @param subject 主题
	 * @param message 内容
	 * @param isHtml 内容是否支持html
	 * @return 是否发送成功
	 */
	boolean send(String to, String subject, String message, boolean isHtml);
}
