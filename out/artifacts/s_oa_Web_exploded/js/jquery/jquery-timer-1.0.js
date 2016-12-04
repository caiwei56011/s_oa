/** jQuery 显示当前日期时间插件*/

(function($){
	/** 为jQuery添加对象的方法,方法名为test */
	$.prototype.test = function(){
		alert("test!!!!")
	};
	/** 为jQuery批量添加对象的方法 */
	$.fn.extend({
		/** 在jQuery对象上显示当前系统时间的方法 */
		runtime: function(){
			/** 创建日期对象 */
			var d = new Date();
			/** 创建数组，用于保存和拼接日期 */
			var arr = new Array();
			
			var year = d.getFullYear();
			arr.push(year + "年");
			var month = d.getMonth();
			arr.push((month + 1) + "月");
			var date = d.getDate();
			arr.push($.calc(date) + "日&nbsp;");
			var day = d.getDay();
			/** day:0-6,对应星期日-星期六 */
			arr.push($.weeks[day] + "&nbsp");
			var hour = d.getHours();
			arr.push($.calc(hour) + ":");
			var minutes = d.getMinutes();
			arr.push($.calc(minutes) + ":");
			var seconds = d.getSeconds();
			arr.push($.calc(seconds));
			/** 把数组按空字符串拼接，并显示到jquery对象上 */
			this.html(arr.join(""));
			/** 每隔一秒调用runtime()重新获取一次时间 */
			var t = this;
			window.setTimeout(function(){
				t.runtime();
			}, 1000);
		},
		/**
		 * 倒计时的方法
		 * text : 显示的文本
		 * seconds : 秒数
		 */
		countDown : function(text, seconds){
			if (seconds > 1){
				/** 自减 */
				seconds--;
				/** 替换掉text中的占位符{0} */
				var res = text.replace("{0}", seconds);
				/** 为按钮添加value属性值 */
				this.val(res);
				var obj = this;
				/** 开启延迟的定时器 */
				setTimeout(function(){
					obj.countDown(text, seconds);
				}, 1000);
			}else{
				$(this).attr("disabled", false).val("重新获取验证码");
			}
		}
	});
	
	/** 为jQuery添加静态属性和方法*/
	$.extend({
		weeks: ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"],
		calc: function(num){
			return num > 9 ? num : "0" + num;
		}
	});
	
})(jQuery);




/* 此种写法可能导致函数被覆盖，推荐使用匿名函数
function ext($){
	// 为jQuery添加对象的方法,方法名为test1
	$.prototype.test1 = function(){
		alert("test1!!!!")
	}
}
//调用为jquery添加对象方法的函数ext
ext(jQuery);
*/