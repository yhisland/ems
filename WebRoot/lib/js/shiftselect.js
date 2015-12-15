//-------------------------------------------------------------------------------
//     结合SHIFT,CTRL,ALT键实现单选或多选
//-------------------------------------------------------------------------------
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
}

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

function f_onCheckRow(data) {
	// -------------for 结合SHIFT键实现单选或多选---------------
	if (!inputFlags.isShiftDown) {
		selectIndexs.firstSelectRowIndex = data.__index;
	}
	if (inputFlags.isShiftDown) {
		selectIndexs.lastSelectRowIndex = data.__index;
		var tempIndex = 0;
		if (selectIndexs.firstSelectRowIndex > selectIndexs.lastSelectRowIndex) {
			tempIndex = selectIndexs.firstSelectRowIndex;
			selectIndexs.firstSelectRowIndex = selectIndexs.lastSelectRowIndex;
			selectIndexs.lastSelectRowIndex = tempIndex;
		}
		for ( var i = selectIndexs.firstSelectRowIndex; i <= selectIndexs.lastSelectRowIndex; i++) {
			grid.select(i);
		}
	}
	// -------------for 结合SHIFT键实现单选或多选----------------
}