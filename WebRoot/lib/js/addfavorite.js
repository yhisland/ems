LG.addfavorite = function (success)
{
    $(document).bind('keydown.addfavorite', function (e)
    {
        if (e.keyCode == 13)
        {
            doAddFavorite();
        }
    });

    if (!window.addfavoriteWin)
    {
        var addfavoritePanle = $("#mainform");

        var menusTree = {
            id: 'addfavoriteMenusTree',
            url: '../index/getUserMenusTreeJSON',
            checkbox: false,
            isExpand:1,
            nodeWidth: 220
        };

        addfavoritePanle.ligerForm({
			inputWidth: 170, labelWidth: 90, space: 40,
            fields: [
                 { display: "页面", name: "menuid", newline: true,  type: "select", comboboxName: "MyMenusMenuID",
                     options: { id: 'MyMenusMenuID', treeLeafOnly: true, tree: menusTree, valueFieldID: "MenuID", valueField: "id" },
                     validate: { required: true, messages: { required: '请选择页面'} }
                 },
                 { display: "收藏别名", name: "FavoriteTitle", newline: true, type: "text"  }

            ]
        });


        //验证
        jQuery.metadata.setType("attr", "validate");
        LG.validate(addfavoritePanle);

       

        window.addfavoriteWin = $.ligerDialog.open({
            width: 400, height: 180, top: 150, left: 230,
            isResize: true,
            title: '增加收藏',
            target: $("#detail"),
            buttons: [
            { text: '确定', onclick: function ()
            {
                doAddFavorite();
            }
            },
            { text: '取消', onclick: function ()
            {
                window.addfavoriteWin.hide();
                $(document).unbind('keydown.addfavorite');
            }
            }
            ]
        });
    }
    else
    {
        window.addfavoriteWin.show();
    }

    function doAddFavorite()
    { 
        var manager = $.ligerui.get("MyMenusMenuID"); 
        if (addfavoritePanle.valid() && manager)
        {
            LG.ajax({
                type: 'favorite',
                method: 'addMyFavorite',
                data: { MenuID: manager.getValue(), FavoriteTitle: $.ligerui.get("FavoriteTitle").getValue() }, 
                success: function ()
                {
                    LG.showSuccess('收藏成功');
                    window.addfavoriteWin.hide();
                    $(document).unbind('keydown.addfavorite');
                    if (success)
                    {
                        success();
                    }
                },
                error: function (message)
                {
                    LG.showError(message);
                }
            });
        }

    }

};