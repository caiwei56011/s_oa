package com.jsoft.oa.core.common.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
/**
 * 日期格式化标签处理类
 * @author jack
 * @email wujunwei6@163.com
 * @date 2016年9月5日 下午10:22:45 
 * @version V1.0
 */
public class DateTag extends SimpleTagSupport {
	/** 定义时间毫秒数 */
	private Long value;
	
	@Override
	public void doTag() throws JspException, IOException {
		// 1小时2分钟3秒
		StringBuilder str = new StringBuilder();
		if (value > 0){
			/** 计算出秒数 */
			long seconds = value / 1000;
			/** 计算出分钟 */
			long minutes = seconds / 60;
			/** 计算出小时 */
			long hours = minutes / 60;
			if (hours > 0){
				str.append(hours + "小时");
			}
			if (minutes > 0){
				str.append((minutes - (hours * 60)) + "分");
			}
			if (seconds >= 0){
				str.append((seconds - (minutes * 60)) + "秒");
			}
		}else{
			str.append(value + "秒");
		}
		this.getJspContext().getOut().print(str.toString());
	}

	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value == null ? 0L : value;
	}
}
