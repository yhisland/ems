// 新的提交数据(异步)
function do_ajaxSubmit1(fieldName, formName, url) {

	var eurl = encodeURI(url);
	var x = $("#" + formName + "").serializeArray();
	var jsonStr = [];
	$.each(x, function(i, field) {
		if (field.name != 'undefined') {
			if ((field.name).indexOf("2") >= 0) {
				jsonStr.push((field.name).replace('2', '') + ":" + "'"
						+ stripscript(field.value) + "'");
			} else {
				jsonStr.push(field.name + ":" + "'" + stripscript(field.value)
						+ "'");
			}
		}
	});
  
	$.ajax({
		type : "POST",
		url : eurl,
		data : "json={" + jsonStr + "}",
		dataType : "json",
		error : function(XMLHttpRequest, textStatus, thrownError) {
			var sessionstatus = XMLHttpRequest
					.getResponseHeader("sessionstatus");// 通过XMLHttpRequest取得响应头，sessionstatus，
			if (sessionstatus == "timeout") {
				$.ligerDialog.confirm('Session失效!是否重新登录!', function(yes) {
					if (yes == true) {
						parent.location.reload();// 如果超时就处理 ，指定要跳转的页面
					}
					;
				});
			}
		},
		success : function(data) {
			$.ligerDialog.success(data.msg);
			g.loadData();
			if ((data.id > 0) && (fieldName != ''))
				$.ligerui.get(fieldName).setValue(data.id);
		}
	});
}

/**
 * 创 建 人 ： LiHongYuan 日 期 ： 2013-12-11 上午9:42:52 描 述 ： 新的提交数据(同步), fieldName字段名：
 * 1.保存时传入 2.需要返回时传入
 * 
 */
function do_ajaxSubmit2(fieldName, formName, url, jsonStr) {
	var eurl = encodeURI(url);
	var x = $("#" + formName + "").serializeArray();
	$.each(x, function(i, field) {
		if (field.name != 'undefined') {
			if ((field.name).indexOf("2") >= 0) {
				jsonStr.push((field.name).replace('2', '') + ":" + "'"
						+ stripscript(field.value) + "'");
			} else {
				jsonStr.push(field.name + ":" + "'" + stripscript(field.value)
						+ "'");
			}
		}
	});

	$.ajax({
		type : "POST",
		url : eurl,
		data : "json={" + jsonStr + "}",
		dataType : "json",
		async : false,
		error : function(XMLHttpRequest, textStatus, thrownError) {
			// 通过XMLHttpRequest取得响应头，sessionstatus，
			var sessionstatus = XMLHttpRequest
					.getResponseHeader("sessionstatus");
			if (sessionstatus == "timeout") {
				$.ligerDialog.confirm('Session失效!是否重新登录!', function(yes) {
					if (yes == true) {
						parent.location.reload();// 如果超时就处理 ，指定要跳转的页面
					}
					;
				});
			}
			$.ligerDialog.error(thrownError);
		},
		success : function(data) {
			$.ligerDialog.success(data.msg);
			g.loadData();
			if ((data.id > 0) && (fieldName != ''))
				$.ligerui.get(fieldName).setValue(data.id);
		}
	});
}

/**
 * 创 建 人 ： LiHongYuan 日 期 ： 2013-12-11 上午9:42:52 描 述 ： 过滤特殊字符
 * 
 */
function stripscript(s) {
	var pattern = new RegExp("[\%&'{}]");
	var rs = "";
	for ( var i = 0; i < s.length; i++) {
		rs = rs + s.substr(i, 1).replace(pattern, '');
	}
	return rs;
}
/**
 * @param param
 * @param url
 *            不根据Form提交
 */
var manager = null;
function do_ajaxSubmit(param, url) {
	var eurl = encodeURI(url);
	$.ajax({
		type : "POST",
		url : eurl,
		timeout : 3000,
		async : false,
		data : "json=" + liger.toJSON(param) + "",
		dataType : "json",
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.ligerDialog.error("请求错误..." + errorThrown);
		},
		beforeSend : function(XMLHttpRequest) {
			// $.ligerDialog.success("正在执行……");
			manager = $.ligerDialog.waitting('正在执行,请稍候...');
		},
		success : function(data, textStatus) {
			$.ligerDialog.success(data.msg);
			g.loadData();
		},
		complete : handleResponse
	});
}

// 成功返回的绑定函数
function handleResponse(XMLHttpRequest, textStatus) {
	manager.close();
}

// js去空格
function jsTrim(str) {
	return str.replace(/\ /g, "");
}

/**
 * HEYONGJIAN 2014-01-21 去掉字符串左右空格
 */
// 去掉空格
function trim(str) { // 删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
function ltrim(str) { // 删除左边的空格
	return str.replace(/(^\s*)/g, "");
}
function rtrim(str) { // 删除右边的空格
	return str.replace(/(\s*$)/g, "");
}

// 删除LigerUI产生的分页信息
function delPage(data) {
	delete data['__id'];
	delete data['__previd'];
	delete data['__index'];
	delete data['__status'];
	delete data['__nextid'];
	delete data['ROWNUM_'];
	return data;
}
// 格式化数字列
function formatCurrency(num) {
	if (!num)
		return "0.00";
	num = num.toString().replace(/\$|\,/g, '');
	if (isNaN(num))
		num = "0.00";
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num * 100 + 0.50000000001);
	cents = num % 100;
	num = Math.floor(num / 100).toString();
	if (cents < 10)
		cents = "0" + cents;
	for ( var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
		num = num.substring(0, num.length - (4 * i + 3)) + ','
				+ num.substring(num.length - (4 * i + 3));
	return (((sign) ? '' : '-') + '' + num + '.' + cents);
}

// 前台日期转换
function getFormatDate(date) {
	if (date == "NaN")
		return null;
	var format = "yyyy-MM-dd hh:mm:ss";
	var o = {
		"M+" : date.getMonth() + 1,
		"d+" : date.getDate(),
		"h+" : date.getHours(),
		"m+" : date.getMinutes(),
		"s+" : date.getSeconds(),
		"q+" : Math.floor((date.getMonth() + 3) / 3),
		"S" : date.getMilliseconds()
	};
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (date.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

/* heyongjian清空表格 */
function cleanGrid(grid) {
	// 清空表格
	var i = grid.recordNumber;
	while (i > 0) {
		grid.deleteRow(i - 1);
		i--;
	}
}
/* heyongjian根据列名返回列索引 */
function ColumnsIndex(grid,ColumnName) {
	for(var i=0;i<grid.columns.length;i++){
		if(grid.columns[i].columnname == ColumnName){
			return i;
		}
	}
	return -1;
}

/* 获取当月第一天 */
function getCurrFirstDate() {
	var myDate = new Date();
	var year = myDate.getFullYear();
	var month = myDate.getMonth() + 1;
	if (month < 10) {
		month = "0" + month;
	}
	return year + "-" + month + "-" + "01" + " 00:00:00";
}
/* 获取当月最后一天 */
function getCurrLastDate() {
	var myDate = new Date();
	var year = myDate.getFullYear();
	var month = myDate.getMonth() + 1;
	if (month < 10) {
		month = "0" + month;
	}
	myDate = new Date(year, month, 0);
	return year + "-" + month + "-" + myDate.getDate() + " 23:59:59";
}
/* 获取当前日期的前30天 */
function getUpMonthDate() {
	// var now=new Date();
	var newdate = new Date();
	var newtimems = newdate.getTime() - (24 * 60 * 60 * 1000 * 30);
	newdate.setTime(newtimems);
	newdate.setHours("23");
	newdate.setMinutes("59");
	newdate.setSeconds("59");
	// var newdate=newdate.toLocaleString();
	return newdate;
}
// -------------------------------------------------------------------------------
// 结合SHIFT,CTRL,ALT键实现单选或多选
// -------------------------------------------------------------------------------
var KEY = {
	SHIFT : 16,
	CTRL : 17,
	ALT : 18,
	DOWN : 40,
	RIGHT : 39,
	UP : 38,
	LEFT : 37
};

var selectIndexs = {
	firstSelectRowIndex : 0,
	lastSelectRowIndex : 0
};
var inputFlags = {
	isShiftDown : false,
	isCtrlDown : false,
	isAltDown : false
};

function keyPress(event) {// 响应键盘按下事件
	var e = event || window.event;
	var code = e.keyCode | e.which | e.charCode;
	switch (code) {
	case KEY.SHIFT:
		inputFlags.isShiftDown = true;
		break;
	default:
	}
}

function keyRelease(event) { // 响应键盘按键放开的事件
	var e = event || window.event;
	var code = e.keyCode | e.which | e.charCode;
	switch (code) {
	case KEY.SHIFT:
		inputFlags.isShiftDown = false;
		selectIndexs.firstSelectRowIndex = 0;
		break;
	default:
	}
}
//创建人：yzj 功能描述：手机号码保密，长度大于6的字符串，最后三位的前三从位变为‘*’号，其他的是最后一位的前一位。
function textSwitch(text){
	if(!(text == null || text == "" || text =="null")){
		var vArray = text.split("");
		var size = vArray.length;
		if(size > 6){
			for(var i=0;i < 3;i++){
				vArray[size-4-i] = "*";
			}
		}else{
			vArray[size-2] = "*";
		}
		var result = vArray.join("");
		return result;
	}else {
		return "";
	}
}

//克隆对象 add by heyongjian
function clone(myObj){ 
	if(typeof(myObj) != 'object') return myObj; 
	if(myObj == null) return myObj; 
	var myNewObj = new Object(); 
	for(var i in myObj) 
	myNewObj[i] = clone(myObj[i]); 
	return myNewObj; 
} 

//密码对话框  add by heyongjian
$.ligerDialog.promptPassWord = function (title, value, multi, callback)
{
    var target = $('<input type="password" class="l-dialog-inputtext"/>');
    if (typeof (multi) == "function")
    {
        callback = multi;
    }
    if (typeof (value) == "function")
    {
        callback = value;
    }
    else if (typeof (value) == "boolean")
    {
        multi = value;
    }
    if (typeof (multi) == "boolean" && multi)
    {
        target = $('<textarea class="l-dialog-textarea"></textarea>');
    }
    if (typeof (value) == "string" || typeof (value) == "int")
    {
        target.val(value);
    }
    var btnclick = function (item, Dialog, index)
    {
        Dialog.close();
        if (callback)
        {
            callback(item.type == 'yes', target.val());
        }
    };
    p = {
        title: title,
        target: target,
        width: 320,
        buttons: [{ text: $.ligerDefaults.DialogString.ok, onclick: btnclick, type: 'yes' }, { text: $.ligerDefaults.DialogString.cancel, onclick: btnclick, type: 'cancel' }]
    };
    return $.ligerDialog(p);
};

//add by heyongjian 带表格参数提交方法，数据freshFlag = 2时需要grid参数
function gridAjax(grid,param,url,freshFlag){
	var manager = null;
	var URL = encodeURI(url);
	$.ajax({
		type : "post",
		url : URL,
		timeout : 3000,
		async : false,
		data : "json=" + liger.toJSON(param) + "",
		dataType : "json",
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.ligerDialog.error("请求错误..." + errorThrown);
		},
		beforeSend : function(XMLHttpRequest) {
			manager = $.ligerDialog.waitting('正在执行,请稍候...');
		},
		success : function(data, textStatus) {
			$.ligerDialog.success(data.msg);
			if(freshFlag == 2){
				grid.loadData();
			}
		},
		complete : function(){
			manager.close();
		}
	});		
}

//add by heyongjian  修改状态条，调用这个方法时，后台除了要返回total，pagesize还要返回totalqty
function gridBuildPager(grid) {
    var g = grid,
        p = grid.options;
    $('.pcontrol input', g.toolbar).val(p.page);
    if (!p.pageCount) p.pageCount = 1;
    $('.pcontrol span', g.toolbar).html(p.pageCount);
    var r1 = parseInt((p.page - 1) * p.pageSize) + 1.0;
    var r2 = parseInt(r1) + parseInt(p.pageSize) - 1;
    if (!p.total) p.total = 0;
    if (p.total < r2) r2 = p.total;
    if (!p.total) r1 = r2 = 0;
    if (r1 < 0) r1 = 0;
    if (r2 < 0) r2 = 0;
    var totalqty = g.data.TotalQty || 0;
    var stat = "显示从{from}到{to}，总 {total} 条，商品总数: {totalqty}。每页显示：{pagesize}";
    stat = stat.replace(/{from}/, r1);
    stat = stat.replace(/{to}/, r2);
    stat = stat.replace(/{total}/, p.total);
    stat = stat.replace(/{totalqty}/, totalqty);
    stat = stat.replace(/{pagesize}/, p.pageSize);
    $('.l-bar-text', g.toolbar).html(stat);
    if (!p.total) {
        $(".l-bar-btnfirst span,.l-bar-btnprev span,.l-bar-btnnext span,.l-bar-btnlast span", g.toolbar)
            .addClass("l-disabled");
    }
    if (p.page == 1) {
        $(".l-bar-btnfirst span", g.toolbar).addClass("l-disabled");
        $(".l-bar-btnprev span", g.toolbar).addClass("l-disabled");
    } else if (p.page > p.pageCount && p.pageCount > 0) {
        $(".l-bar-btnfirst span", g.toolbar).removeClass("l-disabled");
        $(".l-bar-btnprev span", g.toolbar).removeClass("l-disabled");
    }
    if (p.page == p.pageCount) {
        $(".l-bar-btnlast span", g.toolbar).addClass("l-disabled");
        $(".l-bar-btnnext span", g.toolbar).addClass("l-disabled");
    } else if (p.page < p.pageCount && p.pageCount > 0) {
        $(".l-bar-btnlast span", g.toolbar).removeClass("l-disabled");
        $(".l-bar-btnnext span", g.toolbar).removeClass("l-disabled");
    }
}
/**创建：yzj*/
//时间格式化
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth() + 3) / 3), //quarter
		"S" : this.getMilliseconds()//millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}
/**创建：yzj*/
//得到第几周
function getWeek(year, month, day) {
	month += 1;
	var a = Math.floor((14 - (month)) / 12);
	var y = year + 4800 - a;
	var m = (month) + (12 * a) - 3;
	var jd = day + Math.floor(((153 * m) + 2) / 5) + (365 * y)
			+ Math.floor(y / 4) - Math.floor(y / 100) + Math.floor(y / 400)
			- 32045;
	var d4 = (jd + 31741 - (jd % 7)) % 146097 % 36524 % 1461;
	var L = Math.floor(d4 / 1460);
	var d1 = ((d4 - L) % 365) + L;
	NumberOfWeek = Math.floor(d1 / 7) + 1;
	return NumberOfWeek;
}
/**创建：yzj*/
//从当天开始，得到前20周的周范围
function getWeekFromToday() {
	var result = [];
	var d = new Date();
	var day = d.getDay();
	var n = 1000 * 60 * 60 * 24;
	for ( var i = 0; i < 20; i++) {
		var weekNum = getWeek(d.getYear(), d.getMonth(), d.getDate());
		if(day == 0){
			day = 7;
		}
		var first = new Date(d.getTime() - (day - 1) * n);
		var last =  new Date(d.getTime() + (7 - day) * n);
		d = new Date(first.getTime() - 3 * n);
		day = d.getDay();
		var data = first.format("yyyy/MM/dd") + "-"+ last.format("yyyy/MM/dd") + "第" + weekNum + "周"
		result.push({text:data,value:data});
		//document.write(first.format("yyyy/MM/dd") + "-"
		//		+ last.format("yyyy/MM/dd") + "第" + weekNum + "周<br>");
	}
	return result;
}
/**创建：yzj*/
//从当月开始得到前12个月 
function getYearFromToday() {
	var result = [];
	var day = new Date();
	var month = day.getMonth();
	var year = day.getFullYear();
	for ( var i = 0; i < 12; i++) {
		var date = new Date();
		date.setFullYear(year);
		date.setMonth(month);
		var data = date.format("yyyy年MM月");
		result.push({text:data,value:data});
		if (month > 0) {
			month = month - 1;
		} else {
			year = year - 1;
			month = 11;
		}
	}
	return result;
}

/*
*调用示例   可用点击事件也可以用鼠标移入事件
*表格文本中加入=>'销售量<img style="cursor:help;"    onmouseover="f_overlag(this,event,'+"'你好啊！'"+',100,100);"  src="${root}/Images/log0001.gif"   />'
*参数说明: o当前对象,e鼠标对像,text显示文本或HTML,widht宽,height高
*函数名:f_overlag(o,e,text,width,height)
*/
function f_overlag(o,e,text,width,height){
	if (e && e.stopPropagation){e.stopPropagation();}else{window.event.cancelBubble=true;}
	var timerluo=null;id='luo_lag';
	var obj = document.getElementById(id);
	if(obj){}else{$("body").append("<div  id='luo_lag' ></div>");}
	text="<p style='color:blue;font-size:15px;font-weight:bold;'>帮助说明？</p>"+text;
	var Y=null;var X=null;
	if(e.pageY){Y=e.pageY;X=e.pageX;
		if(X+parseInt(width)>document.body.offsetWidth  ){ X=document.body.offsetWidth-parseInt(width)-30;  }
		if(Y+parseInt(height)>document.body.offsetHeight ){Y=document.body.offsetHeight-parseInt(height)-30;}
	}else{Y=e.clientY;X=e.clientX;
		if(X+parseInt(width)>document.body.clientWidth  ){ X=document.body.clientWidth-parseInt(width)-30;  }
		if(Y+parseInt(height)>document.body.clientHeight ){Y=document.body.clientHeight-parseInt(height)-30;}
	}
	if(timerluo!=null){window.clearTimeout(timerluo);timerluo=null;}
	$("#"+id).html(text).css({'display':'block',"top":(Y+5)+"px","left":(X+5)+"px",'width':width+'px',"height":height+'px',"position":"absolute",
		"background-color":"#FFF3DA","border":"1px solid #FBCFAC","border-radius":"10px","z-index":19200,
		"-moz-box-shadow":"4px 4px 0 #FBCFAC","-webkit-box-shadow":"4px 4px 0 #FBCFAC","bottom":"52px",
		"font-size":"14px","font-family":"Arial, Helvetica, sans-serif","padding":"1px 12px 12px"});
	$("#"+id).mouseover(function(){
		if(timerluo!=null){window.clearTimeout(timerluo);timerluo=null;}   
		$('#'+id).css({'display':'block'});});
	$("#"+id).mouseout(function(){
		$('#'+id).css({'display':'none'});});
	$(o).mouseout(function(){
		timerluo=window.setTimeout(function(){
			$('#'+id).css({'display':'none'});
			if(timerluo!=null){window.clearTimeout(timerluo);timerluo=null;} 
		},600);}); 
}
