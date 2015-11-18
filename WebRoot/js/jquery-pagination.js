// JavaScript Document
//分页js

(function($){
	$.fn.pager = function(opts){
		var rules = $.extend({
				nTotal : 0,   // 总数量
				sUrl : "",   //链接地址
				nPageSize : 10,  //每页显示的数量
				sOffsetFlag : "offset",  //offset索引
				nOffset : 1,  //查询的索引开始位置，默认从1
				nTheme : 1,  //分页主题
				nItems : 8, //主题2的每页显示的数量
				sPrevFlag : "&larr;", //上一页的显示标志
				sNextFlag : "&rarr;", //下一页的显示标志
				nWidth : 40, //样式大小  1，最大，2 次之  3， 最小
				bPre:true,  // 是否显示前一页导航（默认显示)  
				bNext:true, //是否显示后一页(默认不显示)
				bShowTotal:true, //是否显示总页数
				sDirection:"center", //分页组件的对其策略left,right,center
				sId:"", //是否锚链接要页面的某个ID处
				bGo:false,//是否前往某个页面
				sGo:"GO", //前往某一页的标志
				sNull:"暂且没有数据" //没有数据将显示该句话，可以使用HTML
			},opts?opts:{});
		
//		for(v in rules){
//			alert(rules[v]);	
//		}
		var data = [],
			offset = 1,
			url = rules.sUrl,
			total = rules.nTotal,
			theme = rules.nTheme,
			pageSize = rules.nPageSize,
			pages = Math.ceil(total/pageSize),
			prevFlag = rules.sPrevFlag,
			nextFlag = rules.sNextFlag,
			offsetFlag = rules.sOffsetFlag,
			items = rules.nItems,
			locArgs = getLocationArgs(),
			bGo = rules.bGo;
			sGo = rules.sGo;
			dirs = rules.sDirection;
		if(locArgs[offsetFlag]==undefined){
			offset = 1;
		}
		else{
			offset = locArgs[offsetFlag];
		}
		var curPage = 1 + Math.floor(offset/pageSize);
		//风格一
		if(theme == 1){
			if(curPage==1){
				 //type 为0 表示disabled,type为1表示是可链接，type为2表示为当前页
				 //bNum 为-1表示前一页，bNum为1表示下一页，bNum为0表示总也数，bNum为2表示数字
				data.push({"num":prevFlag,"url": "","type":"0","bNum":-1});
			}
			else{
				data.push({"num":prevFlag,"url": getUrl(curPage-1),"type":"1","bNum":-1});
			}
			if(pages<=10){
				data = pushData(data,1,pages);
			}
			else{
				if(curPage<=3){
					data = pushData(data,1,5);
					data.push({"num":"···","url": "","type":"0"});
				}
				else if(curPage>3&&curPage<=5){
					data = pushData(data,1,curPage+2);
					data.push({"num":"···","url": "","type":"0"});	
				}
				else if(curPage>5&&curPage<=pages-4){
					data.push({"num":"1","url": getUrl(1),"type":"1"});
					data.push({"num":"···","url": "","type":"0"});
					data = pushData(data,curPage-2,curPage+2);
					data.push({"num":"···","url": "","type":"0"});
					data.push({"num":pages,"url": getUrl(pages),"type":"1"});
				}
				else{
					data.push({"num":"1","url": getUrl(1),"type":"1"});
					data.push({"num":"···","url": "","type":"0"});
					data = pushData(data,pages-4,pages);
				}
			}
			if(curPage==pages){
				data.push({"num":nextFlag,"url": "","type":"0","bNum":1});
			}
			else{
				data.push({"num":nextFlag,"url": getUrl(curPage+1),"type":"1","bNum":1});
			}	
		}
		//风格二
		if(theme==2){
			var tempFlag = items%2;
			var half = Math.floor(items/2);
			if(curPage==1){
				data.push({"num":prevFlag,"url": "","type":"0","bNum":-1});
			}
			else{
				data.push({"num":prevFlag,"url": getUrl(curPage-1),"type":"1","bNum":-1});
			}
			if(pages<=items){
				data = pushData(data,1,pages,curPage);
			}
			else{
				if(curPage<=half){
					data = pushData(data,1,items,curPage);
				}
				else if(curPage>half&&curPage<=pages-half){
					if(tempFlag==0){
						data = pushData(data,curPage-half,curPage+half-1);
					}
					if(tempFlag==1){
						data = pushData(data,curPage-half,curPage+half);
					}
				}
				else{
					data = pushData(data,pages-items+1,pages);
				}
			}
			if(curPage==pages){
					data.push({"num":nextFlag,"url": "","type":"0","bNum":1});
				}
			else{
					data.push({"num":nextFlag,"url": getUrl(curPage+1),"type":"1","bNum":1});
			}	
		}
		function pushData(data,from,to){
			for(var i=from;i<=to;i++){
				if(curPage==i){
					data.push({"num":i,"url": "","type":"2"});
				}
				else{
					data.push({"num":i,"url": getUrl(i),"type":"1"});
				}	
			}
			return data;
		}
		
		function getUrl(page){
			var temp = "";
			if(url.indexOf("?")!=-1){
				temp = url + "&offset=" + (page-1)*pageSize;
			}
			else{
				temp = url + "?offset=" + (page-1)*pageSize;
			}
			if(rules.sId!=""){
				temp += rules.sId;
			}
			return temp;
		}
		if(rules.bShowTotal){
			data.push({"num":"共"+pages+"页","url":getUrl(pages),"type":"0","bNum":0});
		}
		if(bGo){
			data.push({"num":"i","url":"","type":"3","bNum":-2});
			data.push({"num":"b","url":"","type":"3","bNum":-3});
		}
		
		this.append("<ul class='mypage-list'>");
		var ul = this.find(".mypage-list").eq(0);
		for(var i=0;i<data.length;i++){
			//获取每个分页页码对象
			var pageObj = data[i];
			//添加一个li
			ul.append("<li class='mypage-li'>");	
			//为刚添加的li添加a标签，并赋予其href及文本值
			if(pageObj.bNum<2){
				$(ul.find(".mypage-li").get(i)).addClass("word");
				if(pageObj.bNum==-1){
					$(ul.find(".mypage-li").get(i)).addClass("prev");
				}
				if(pageObj.bNum==1){
					$(ul.find(".mypage-li").get(i)).addClass("next");
				}
				if(pageObj.bNum==0){
					$(ul.find(".mypage-li").get(i)).addClass("all");
				}
				if(pageObj.bNum==-2){
					$(ul.find(".mypage-li").get(i)).addClass("goinput");
				}
				if(pageObj.bNum==-3){
					$(ul.find(".mypage-li").get(i)).addClass("gobtn");
				}
			}
			else{
				$(ul.find(".mypage-li").get(i)).addClass("num");
			}
			if(pageObj.type==0){
				$(ul.find(".mypage-li").get(i)).addClass("disabled");
				$("<span>"+pageObj.num+"</span>").appendTo(ul.find(".mypage-li").get(i));
			}
			else if(pageObj.type==1){
				$("<a href='"+pageObj.url+"'>"+pageObj.num+"</a>").appendTo(ul.find(".mypage-li").get(i));
			}
			else if(pageObj.type==2){
				$(ul.find(".mypage-li").get(i)).addClass("active");
				$("<span>"+pageObj.num+"</span>").appendTo(ul.find(".mypage-li").get(i));
			}
			if(pageObj.num=="i"){
				$("<input>",{"class":"topage go"}).appendTo(ul.find(".mypage-li").get(i));
			}
			else if(pageObj.num=="b"){
				$("<a>",{"class":"pager-search go","text":sGo}).appendTo(ul.find(".mypage-li").get(i));
			}
		}
		var fo = ul.find(".mypage-li").find("span:contains('···')");
		fo.each(function(){
			$(fo).css({"font-size":"20px","border":"none","color":"#BBBBBB","font-family": "微软雅黑"});
		});
		
		var mywidth = rules.nWidth;
		$(this).find(".mypage-li a").css({width:mywidth+"px",height:mywidth+"px","line-height":mywidth+"px"});
		$(this).find(".mypage-li span").css({width:mywidth+"px",height:mywidth+"px","line-height":mywidth+"px"});
		$(this).find(".topage").css({width:mywidth+"px",height:mywidth+"px"});
		$(this).find(".mypage-li.word a").css({width:"auto"});
		$(this).find(".mypage-li.word span").css({width:"auto"});
		var ta = $(this).find(".mypage-li.word a");
		ta.each(function(index,value){
			var w = ta.eq(index).width()+20;
			if(w<mywidth){
				$(this).css({
					"width":mywidth-20
				});
			}
		});


		if(dirs=="center"){
			setUlWidth();
		}
		if(dirs=="right"){
			setUlWidth();
			ul.css("float","right");
		}
		if(dirs=="left"){
			setUlWidth();
			ul.css("float","left");
		}
		function setUlWidth(){
			var width = 0;
			$(".mypage-li").each(function(){
				width += $(this).outerWidth(true);
			});
			ul.width(width+30);			
		}
		
		if(pages==0){
			this.html("<p>"+rules.sNull+"</p>");
		}
		if(bGo){
			ul.find(".pager-search").click(function(){
				var page = ul.find(".topage").val();
				var totalPage = pages;
				var flag = totalPage-page;//定义一个变量保存两个数的差。小技巧比较
				if(!/^\d+/.test(page)){
					alert("您输入的格式不对哟");
				}
				else{
					var myurl = url+"?offset=";
					if(flag<0){
						window.location.href=changeUrl(myurl+(totalPage-1)*pageSize);
					}
					if(page-1<0){
						window.location.href=changeUrl(myurl+0);
					}
					if(page>0&&flag>=0){
						window.location.href=changeUrl(myurl+(page-1)*pageSize);
					}
				}
			});
		}
		function changeUrl(myurl){
			var new_value = "";
			var front_index = myurl.indexOf("?");
			var back_index = myurl.lastIndexOf("?");
			if(front_index!=back_index){
				new_value = myurl.slice(0,back_index) + "&" + myurl.slice(back_index+1);
			}
			else{
				new_value = myurl;
			}
			if(rules.sId!=""){
				new_value += rules.sId;
			}
			return new_value;
		}
		function getLocationArgs(){
			var qs = (location.search.length > 0 ? location.search.substring(1) : ""),
				args = {},
				items = qs.length ? qs.split("&") : [],
				item = null,
				name = null,
				value = null,
				i = 0,
				len = items.length;
			for(i = 0;i<len;i++){
				item = items[i].split("=");
				name = item[0];
				value= item[1];
				if(name.length>0){
					args[name] = value;
				}
			}
			return args;
		}
		initDataIndex();
		function initDataIndex(){
			var args = getLocationArgs();
			var offset  = args.offset;
			if(offset == undefined){
				offset = 0;
			}
			$(".data_index").each(function(index,value){
				$(this).text(offset-0+index+1);
			});
		}
	};
})(jQuery);