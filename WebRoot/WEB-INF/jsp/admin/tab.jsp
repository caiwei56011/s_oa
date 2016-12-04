<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<title>办公管理系统</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
	<meta name="Keywords" content="keyword1,keyword2,keyword3"/>
	<meta name="Description" content="网页信息的描述" />
	<meta name="Author" content="gdcct.gov.cn" />
	<meta name="Copyright" content="All Rights Reserved." />
	<link href="${path }/logo.ico" rel="shortcut icon" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="${path }/js/jqeasyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${path }/js/jqeasyui/themes/icon.css">
	<script type="text/javascript" src="${path }/js/jquery/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${path }/js/jqeasyui/jquery.easyui.min.js"></script>
	
  	<style type="text/css">
		html, body {
		width : 100%;
		height : 100%;
		padding : 0;
		margin : 0;
		overflow : hidden;
	</style>
	
	<script type="text/javascript">
		$(function(){
			/** 向div中添加选项卡容器,并设置全局属性 */
			$("#tab").tabs({
				fit: true, //自适应父容器
				//tabWidth: 80, //每个选项卡的宽度（不指定时根据内容自适应）
				//tabHeight: 30,
				//border: false
				onAdd: function(){
					window.isAdd = true; //定义全局变量标识是否是添加选项卡					
				},
				onSelect: function(title, index){
					/**选项卡首次添加时会先执行add方法，再执行select方法,
					 * 所以在ie浏览器中，请求一次，后又刷新一次，导致发送两次请求 */
					if(!window.isAdd){	
						/** 当选项卡面板被选中时，刷新其内容 */
						var tab = $("#tab").tabs("getSelected");
						tab.panel("refresh");
					}
					window.isAdd = false;
				},
				/** 添加选项卡右键点击事件 */
				onContextMenu: function(e, title, index){
					e.preventDefault(); // 取消事件的默认行为
					$("#mm").show();	
					$("#mm").menu({
						left: e.pageX,
						top: e.pageY,
						onClick: function(item){
							if(item.name == "close"){
								//关闭当前选项卡
								$("#tab").tabs("close", title);
							}else if(item.name == "closeAll"){
								//获取所有选项卡的标题所在span标签							
								var titles = $("#tab")
									.find(".tabs-header:first").find(".tabs-title");
								for(var i = 0; i < titles.length; i++){
									//获取指定span标签内的标题文本
									var tempTitle = titles.eq(i).text();
									//关闭选项卡
									$("#tab").tabs("close", tempTitle);
								}
							}
						}
					});
				}
			});
			/** 添加选项卡面板 */
			$("#tab").tabs("add",{
				title: "用户信息",	//标题
				content: "<iframe width='100%' height='100%' src='${path}/admin/self.jspx' frameborder='0'></iframe>", //内容
				closable: false	//是否可关闭
			});
		});
		/** 自定义向此本页面中添加选项卡面板的方法，供其它页面调用 */
		var addTab = function(title,url){
			/** 根据标题判断当前需要添加的选项卡面板是否已经存在 */
			var isExists = $("#tab").tabs("exists",title);
			if(isExists){
				/** 如果已存在，则根据标题将其选中*/
				 $("#tab").tabs("select",title);
			}else{
				/** 如果不存在，则添加 */
				$("#tab").tabs("add",{
					title: title,
					content: "<iframe width='100%' height='100%' src='${path}" + url + "' frameborder='0'></iframe>",
					closable: true
				});
			}
		};
		/** 显示修改密码div */
		var showUpdatePwd = function(){
			$("#dialogDiv").dialog({
				title: "修改密码",
				width: "360",
				height: "260",
				collapsible: true,
				minimizable: true,
				maximizable: true,
				modal: true
				
			});
			$("#iframe").attr("src","${path}/admin/showUpdatePwd.jspx").show();
		};
	</script>
</head>
<body>
	<div id="tab">
		<!-- <iframe src="" frameborder="0" width="100%" height="100%"></iframe> -->
	</div>
	<!-- 修改密码弹出div -->
	<div id="dialogDiv" style="overflow: hidden;">
		<iframe id="iframe" width="100%" height="100%" style="display:none;" frameborder="0" ></iframe>
	</div>
	
	<!-- 选项卡上点击右键，弹出的菜单 -->
	<div id="mm" class="easyui-menu" style="width:120px;">   
    	<div data-options="name:'close', iconCls:'icon-cancel'">关闭</div>   
    	<div data-options="name:'closeAll', iconCls:'icon-cancel'">关闭全部</div>   
	</div>  
	
</body>
</html>