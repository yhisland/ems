LG.login = function ()
{
    $(document).bind('keydown.login', function (e)
    {
        if (e.keyCode == 13)
        {
            dologin();
        }
    });

    if (!window.loginWin)
    {
        var loginPanle = $("#userform");
        loginPanle.ligerForm({
            fields: [
                { display: '用户名', name: 'LoginUserName' },
                { display: '密码', name: 'LoginPassword', type: 'password' }
            ]
        });

        window.loginWin = $.ligerDialog.open({
            width: 400,
            height: 140, top: 200,
            isResize: true,
            title: '用户登录',
            target: $("#detail_user"),
            buttons: [
            { text: '登录', onclick: function ()
            {
                dologin();
            } 
            },
            { text: '取消', onclick: function ()
            {
                window.loginWin.hide();
                $(document).unbind('keydown.login');
            } 
            }
            ]
        });
    }
    else
    {
        window.loginWin.show();
    }

    $("#LoginUserName").focus();
    //$("#LoginUserName,#LoginPassword").val("");
    $.ligerui.get("LoginUserName").setValue("");
    $.ligerui.get("LoginPassword").setValue("");

    function dologin()
    {
        //var username = $("#LoginUserName").val();
        //var password = $("#LoginPassword").val();
        var username = $.ligerui.get("LoginUserName").getValue();
        var password = $.ligerui.get("LoginPassword").getValue();

        $.ajax({
            type: 'post', cache: false, dataType: 'json',
            url: '../loginSubmit',
            data: [
                    { name: 'username', value: username },
                    { name: 'password', value: hex_md5(password) }
                  ],
            success: function (result)
            {
                if (!result)
                {
                    LG.showError('登陆失败,账号或密码有误!');
                    $("#LoginUserName").focus();
                    return;
                } else
                {
                    location.href = location.href;
                }
            },
            error: function ()
            {
                LG.showError('发送系统错误,请与系统管理员联系!');
            },
            beforeSend: function ()
            {
                LG.showLoading('正在登录中...');
            },
            complete: function ()
            {
                LG.hideLoading();
            }
        });
    }

};