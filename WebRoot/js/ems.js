/**
 *JS正则表达式，提供了验证价格和联系电话的方法
 */
var Ems={
	
	/**
	 * 验证是否为正实数
	 * @param num
	 */
	validateNumber:function(num){
		var re=new RegExp("^[\\d]*\\.?[\\d]*$");
		if(re.test(num))
		{
			return(!isNaN(parseFloat(num)));
		}
		else
		{
			return false;
		}
	},
	
	/**
	 * 验证是否为电话号码
	 * @param tel
	 */
	validateTelNumber:function(tel){
		var charcode;
		for (var i=0; i<tel.length; i++)	
		{
			charcode = tel.charCodeAt(i);
			if (charcode < 48 && charcode != 45 || charcode > 57)	
			{
				return false;
			}
		}
		return true;
	}
};