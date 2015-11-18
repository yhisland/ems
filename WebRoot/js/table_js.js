function selectAll() {
		if (this.checked == true) {
			checkAll('test');
		} else {
			clearAll('test');
		}
	}
	function checkAll(name) {
		var el = document.getElementsByTagName('input');
		var len = el.length;
		for ( var i = 0; i < len; i++) {
			if ((el[i].type == "checkbox") && (el[i].name == name)) {
				el[i].checked = true;
			}
		}
	}
	function clearAll(name) {
		var el = document.getElementsByTagName('input');
		var len = el.length;
		for ( var i = 0; i < len; i++) {
			if ((el[i].type == "checkbox") && (el[i].name == name)) {
				el[i].checked = false;
			}
		}
	}

	/**删除所有选中项*/
	function deleteSelected(action,url) {
		var idArr = new Array();
		var el = document.getElementsByTagName('input');
		var len = el.length;
		for ( var i = 0; i < len; i++) {
			if ((el[i].type == "checkbox") && el[i].checked) {
				idArr.push(el[i].value);
			}
		}
		if (idArr.length == 0) {
			alert("请选择删除项!");
			return;
		} else {
			$.post(action, {
				"idList" : idArr
			}, function(result) {
				if (result) {
					window.open(url, "_self");
				}
			}, "json");
		}
	}
	
	var Ptr = document.getElementById("tab").getElementsByTagName("tr");
	function init() {
		for ( var i = 1; i < Ptr.length + 1; i++) {
			Ptr[i - 1].style.backgroundColor = (i % 2 > 0) ? "#fff"
					: "#eee";
		}
	}
	window.onload = init;
	for ( var i = 0; i < Ptr.length; i++) {
		Ptr[i].onmouseover = function() {
			this.tmpClass = this.className;
			this.style.backgroundColor = "#ccc";

		};
		Ptr[i].onmouseout = function() {
			for ( var i = 1; i < Ptr.length + 1; i++) {
				Ptr[i - 1].style.backgroundColor = (i % 2 > 0) ? "#eee"
						: "#fff";
			}
		};
	}