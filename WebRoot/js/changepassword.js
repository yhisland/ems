LG.changepassword = function ()
{
    $(document).bind('keydown.changepassword', function (e)
    {
        if (e.keyCode == 13)
        {
            doChangePassword();
        }
    });

    if (!window.changePasswordWin)
    {
        var changePasswordPanle = $("#pwdform");
        
        changePasswordPanle.ligerForm({
            fields: [
                { display: '旧密码', name: 'OldPassword', type: 'password', validate: { maxlength: 50, required: true, messages: { required: '请输入密码'}} },
                { display: '新密码', name: 'NewPassword', type: 'password', validate: { maxlength: 50, required: true, messages: { required: '请输入密码'}} },
                { display: '确认密码', name: 'NewPassword2', type: 'password', validate: { maxlength: 50, required: true, equalTo:'[name=NewPassword]', messages: { required: '请输入密码', equalTo: '两次密码输入不一致'}} }
            ]
        });

        //验证
        jQuery.metadata.setType("attr", "validate");
        LG.validate(changePasswordPanle);

        window.changePasswordWin = $.ligerDialog.open({
            width: 400,
            height: 190, top: 170,
            isResize: true,
            title: '用户修改密码',
            target: $("#detail_pwd"),
            buttons: [
            { text: '确定', onclick: function ()
            {
                doChangePassword();
            }
            },
            { text: '取消', onclick: function ()
            {
                window.changePasswordWin.hide();
                $(document).unbind('keydown.changepassword');
            }
            }
            ]
        });
    }
    else
    {
        window.changePasswordWin.show();
    }

    $("#OldPassword").focus();
    //$.ligerui.get("OldPassword").focus();
    //$("#OldPassword,#NewPassword,#NewPassword2").val("");
    $.ligerui.get("OldPassword").setValue("");
    $.ligerui.get("NewPassword").setValue("");
    $.ligerui.get("NewPassword2").setValue("");
    

    function doChangePassword()
    {        
    	var OldPassword = $.ligerui.get("OldPassword").getValue();
    	var LoginPassword = $.ligerui.get("NewPassword").getValue();
        if (changePasswordPanle.valid())
        {
            LG.ajax({
                type: 'index',
                method: 'changePassword',
                data: { OldPassword: hex_md5(OldPassword), LoginPassword: hex_md5(LoginPassword) },
                success: function (data)
                {
                    //LG.showSuccess('密码修改成功');
                	LG.showSuccess(data.message);
                    window.changePasswordWin.hide();
                    $(document).unbind('keydown.changepassword');
                },
                error: function (message)
                {
                    LG.showError(message);
                }
            });
		}
	}

};