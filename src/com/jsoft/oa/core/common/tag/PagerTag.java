package com.jsoft.oa.core.common.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 分页标签处理类
 * @author jack
 * @email wujunwei6@163.com
 * @date 2016年8月5日 下午10:22:45 
 * @version V1.0
 */
public class PagerTag extends SimpleTagSupport {
	
	/** 定义请求URL中的占位符常量 */
	private static final String TAG = "{0}";
	
	/** 当前页码 */
	private int pageIndex;
	/** 每页显示的数量 */
	private int pageSize;
	/** 总记录条数 */
	private int recordCount;
	/** 请求URL page.action?pageIndex={0}*/
	private String submitUrl;
	/** 样式 */
	private String style = "sabrosus";
	
	/** 定义总页数 */
	private int totalPage = 0;
	
	@Override
	public void doTag() throws JspException, IOException {
		
		/** 定义它拼接是终的结果 */
		StringBuilder res = new StringBuilder();
		/** 定义它拼接中间的页码 */
		StringBuilder str = new StringBuilder();
		/** 判断总记录条数 */
		if (recordCount > 0){
			/** 需要显示分页标签，计算出总页数 */
			totalPage = (this.recordCount - 1) / this.pageSize + 1; 
			/** 判断上一页或下一页需不需要加a标签 */
			if (this.pageIndex == 1){ // 首页
				str.append("<span class='disabled'>上一页</span>");
				
				/** 计算中间的页码 */
				this.calcPage(str);
				
				/** 下一页需不需要a标签 */
				if (this.pageIndex == totalPage){
					/** 只有一页 */
					str.append("<span class='disabled'>下一页</span>");
				}else{
					String tempUrl = this.submitUrl.replace(TAG, String.valueOf(pageIndex + 1));
					str.append("<a href='"+ tempUrl +"'>下一页</a>");
				}
			}else if (this.pageIndex == totalPage){ // 尾页
				String tempUrl = this.submitUrl.replace(TAG, String.valueOf(pageIndex - 1));
				str.append("<a href='"+ tempUrl +"'>上一页</a>");
				
				/** 计算中间的页码 */
				this.calcPage(str);
				
				str.append("<span class='disabled'>下一页</span>");
			}else{ // 中间
				String tempUrl = this.submitUrl.replace(TAG, String.valueOf(pageIndex - 1));
				str.append("<a href='"+ tempUrl +"'>上一页</a>");
				
				/** 计算中间的页码 */
				this.calcPage(str);
				
				tempUrl = this.submitUrl.replace(TAG, String.valueOf(pageIndex + 1));
				str.append("<a href='"+ tempUrl +"'>下一页</a>");
			}
			
			/** 拼接其它的信息 */
			res.append("<table width='98%' align='center' style='font-size:13px;' class='"+ style +"'>");
			res.append("<tr><td>" + str.toString());
			res.append("跳转到<input type='text' size='2' id='pager_jump_page_size'/>");
			res.append("<input type='button' value='确定' id='pager_jump_btn'/>");
			res.append("</td></tr>");
			res.append("<tr align='center'><td>");
			/** 开始条数 */
			int startNum = (this.pageIndex - 1) * this.pageSize + 1;
			/** 结束条数 */
			int endNum = (this.pageIndex == this.totalPage) ? this.recordCount : this.pageIndex * this.pageSize;
			
			res.append("总共<font color='red'>"+ this.recordCount +"</font>条记录，当前显示"+ startNum +"-"+ endNum +"条记录。");
			res.append("</td></tr>");
			res.append("</table>");
			res.append("<script type='text/javascript'>");
			res.append("   document.getElementById('pager_jump_btn').onclick = function(){");
			res.append("      var page_size = document.getElementById('pager_jump_page_size').value;");
			res.append("      if (!/^[1-9]\\d*$/.test(page_size) || page_size < 1 || page_size > "+ this.totalPage +"){");
			res.append("          alert('请输入[1-"+ this.totalPage +"]之间的页码！');");
			res.append("      }else{");
			res.append("         var submit_url = '" + this.submitUrl + "';");
			res.append("         window.location = submit_url.replace('"+ TAG +"', page_size);");
			res.append("      }");
			res.append("}");
			res.append("</script>");
			
			
		}else{
			res.append("<table align='center' style='font-size:13px;'><tr><td>总共<font color='red'>0</font>条记录，当前显示0-0条记录。</td></tr></table>");
		}
		this.getJspContext().getOut().print(res.toString());
	}
	
	/** 计算中间页码的方法 */
	private void calcPage(StringBuilder str) {
		/** 判断总页数 */
		if (this.totalPage <= 11){
			/** 一次性显示全部的页码 */
			for (int i = 1; i <= this.totalPage; i++){
				if (this.pageIndex == i){
					/** 当前页码 */
					str.append("<span class='current'>"+ i +"</span>");
				}else{
					String tempUrl = this.submitUrl.replace(TAG, String.valueOf(i));
					str.append("<a href='"+ tempUrl +"'>"+ i +"</a>");
				}
			}
		}else{
			/** 靠首页近些：12345678910...100 */
			if (this.pageIndex <= 8){
				for (int i = 1; i <= 10; i++){
					if (this.pageIndex == i){
						/** 当前页码 */
						str.append("<span class='current'>"+ i +"</span>");
					}else{
						String tempUrl = this.submitUrl.replace(TAG, String.valueOf(i));
						str.append("<a href='"+ tempUrl +"'>"+ i +"</a>");
					}
				}
				str.append("...");
				String tempUrl = this.submitUrl.replace(TAG, String.valueOf(this.totalPage));
				str.append("<a href='"+ tempUrl +"'>"+ this.totalPage +"</a>");
			}
			/** 靠尾页近些：1...90919293949596979899100 */
			else if (this.pageIndex + 8 >= this.totalPage){
				String tempUrl = this.submitUrl.replace(TAG, String.valueOf(1));
				str.append("<a href='"+ tempUrl +"'>1</a>");
				str.append("...");
				
				for (int i = this.totalPage - 10; i <= this.totalPage; i++){
					if (this.pageIndex == i){
						/** 当前页码 */
						str.append("<span class='current'>"+ i +"</span>");
					}else{
						tempUrl = this.submitUrl.replace(TAG, String.valueOf(i));
						str.append("<a href='"+ tempUrl +"'>"+ i +"</a>");
					}
				}
			}
			/** 在中间： 1...464748495051525354...100*/
			else{
				String tempUrl = this.submitUrl.replace(TAG, String.valueOf(1));
				str.append("<a href='"+ tempUrl +"'>1</a>");
				str.append("...");
				
				for (int i = this.pageIndex - 4; i <= this.pageIndex + 4; i++){
					if (this.pageIndex == i){
						/** 当前页码 */
						str.append("<span class='current'>"+ i +"</span>");
					}else{
						tempUrl = this.submitUrl.replace(TAG, String.valueOf(i));
						str.append("<a href='"+ tempUrl +"'>"+ i +"</a>");
					}
				}
				
				str.append("...");
				tempUrl = this.submitUrl.replace(TAG, String.valueOf(this.totalPage));
				str.append("<a href='"+ tempUrl +"'>"+ this.totalPage +"</a>");
			}
		}
	}

	/** setter method */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	public void setSubmitUrl(String submitUrl) {
		this.submitUrl = submitUrl;
	}
	public void setStyle(String style) {
		this.style = style;
	}
}