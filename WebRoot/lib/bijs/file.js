//导出数据
function exportData(isall) {
	if (grid.data.Total == 0 || grid.data.Total == null) {
		$.ligerDialog.success('温馨提示：当前没有数据导出!');
		return;
	}
	if (isall == 1) {
		// 只导出当前页
		$("#inputexportwhere").val(liger.toJSON(sData));
		$("#inputexportpage").val(grid.options.page);
		$("#inputexportpagesize").val(grid.options.pageSize);
	} else {
		$("#inputexportwhere").val(liger.toJSON(sData));
		$("#inputexportpage").val(1);
		$("#inputexportpagesize").val(grid.data.Total);
	}
	$("#exportdata").submit();
}
// 工具条图标
function down() {
	$("#toptoolbar").ligerToolBar({
		items : [ {
			text : '导出Excel',
			menu : {
				width : 100,
				items : [
				// { text: '导入', click: getFields },
				{
					text : '导出本页',
					click : function() {
						exportData(1);
					}
				}, {
					text : '导出所有',
					click : function() {
						exportData(2);
					}
				}, {
					line : true
				} ]
			},
			icon : 'excel'
		} ]
	});
}
// 获取月份
function f_showMonth() 
{ 
	var uom = new Date(); 
	return (uom.getMonth()+1);
} 
// 获取日期
function f_showdate() 
{ 
	var uom = new Date(); 
	return (uom.getMonth()+1) + "-" + uom.getDate(); 
} 
//获取星期
function f_showweek(n)
{
   var uom = new Date(); 
	uom.setDate(uom.getDate()+n);
	switch(uom.getDay()) 
	{ 
		case 0:xingqi="星期日";break; 
		case 1:xingqi="星期一";break; 
		case 2:xingqi="星期二";break; 
		case 3:xingqi="星期三";break; 
		case 4:xingqi="星期四";break; 
		case 5:xingqi="星期五";break; 
		case 6:xingqi="星期六";break; 
		default:xingqi="系统错误！" ;
	}
	return xingqi;
}

//获取时间Date型
	function getDateTime(a) {
		var newdate = new Date();
		var newtimems = newdate.getTime() + (24 * 60 * 60 * 1000 * a);
		newdate.setTime(newtimems);
		return newdate;
}
	  // 获取格式化时间 
	  function f_showtime(a) { 
	 	var date = new Date(); 
	 	var format = "yyyy-MM-dd hh:mm:ss";
	 	date.setDate(date.getDate()+a);
	   	var o = {
	   		"M+" : date.getMonth()+1,
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
//回车查询
$(document).keydown(function (e){
	if (e.keyCode == 13){
		f_Search();
	}
});		    
//表单查询的按钮事件
  function f_Search(){
	  sData = s.getData();	 
	  if(sData.DATE1 != null) sData.DATE1 = getFormatDate(sData.DATE1);
	  if(sData.DATE2 != null) sData.DATE2 = getFormatDate(sData.DATE2);	 
	  grid.setOptions({newPage:1});
	  grid.set('parms', {where : liger.toJSON(sData)});
//	  grid.set('url',"/wms/warehouse_inventory/getInventoryData");
	  grid.loadData();
}  

//前台日期转换
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
	//获得两个时间的天数
	function GetDateRegion(sDate1,sDate2)
      {
          var aDate=null;
          var bDate=null;
          var oDate1=null;
          var oDate2=null;
          var i=0;
          aDate = sDate1.split(" ");  
          bDate = aDate[0].split("-");
          oDate1 = new Date(bDate[1] + '/' + bDate[2] + '/' + bDate[0]);  
		  aDate = sDate2.split(" ");
          bDate = aDate[0].split("-");
          oDate2 = new Date(bDate[1] + '/' + bDate[2] + '/' + bDate[0]);
          i=(oDate2 - oDate1) / 1000 / 60 / 60 /24;
          return i;
      }	   
	//型号查询下拉框
	function specsGridOptions(checkbox) {
		var options = {
			columns: [
			  { display: "产品型号", name: "SPECS",width: 150}
			], 		
			url:'/demand_planning/getItem_specs',
			pageSize: 10, 
			width: '98%', 
			height: '98%',
//			delayLoad:true,
			//usePager:false, 
			rownumbers: true
		};
		return options;
	}	
	//查询商品名称和编码下拉框
	function getGridOptions() {
		var options = {
			columns: [
//			  { display: '主键', name: 'ITEM_ID', align: 'left',hide:1} ,
			  { display: '产品名称', name: 'ITEM_NAME',width: 170},
			  { display: '产品编码', name: 'ITEM_CODE',width: 100}  
			], 		
			url:'/demand_planning/getItemNameJson', 
			pageSize: 10, 
			width: '99%', 
			height: '98%', 
//			delayLoad : true,
			rownumbers: true
		};
		return options;
	}	