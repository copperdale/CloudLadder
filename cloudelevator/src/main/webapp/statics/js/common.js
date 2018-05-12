Namespace.register("app.common");


dialog.tips = function (content, time) {
    var d = dialog({
        fixed: true,
        quickClose: true,
        content: content
    });
    d.show();
    setTimeout(function () {
        d.close().remove();
    }, time || 2000);
}

dialog.tips.success = function (content, time) {
    var d = dialog({
        width: 300,
        skin: 'dialog-success',
        fixed: true,
        quickClose: true,
        content: content
    });
    d.show();
    setTimeout(function () {
        d.close().remove();
    }, time || 2000);
}

dialog.tips.error = function (content, time) {
    var d = dialog({
        width: 300,
        skin: 'dialog-error',
        fixed: true,
        quickClose: true,
        content: content
    });
    d.show();
    setTimeout(function () {
        d.close().remove();
    }, time || 2000);
}

dialog.confirm = function (content, yes, no) {
    var d = dialog({
        id: 'confirm',
        icon: 'question',
        fixed: true,
        title: "提示",
        content: content,
        width: 300,
        callback: yes,
        okValue: "确定",
        cancelValue: "取消",
        ok: function (here) {
            return yes.call(this, here);
        },
        cancel: function (here) {
            return no && no.call(this, here);
        }
    });
    d.showModal();
}

dialog.base = function (title, content, width, yes, no) {
    dialog.baseStatusBar(title, content, width, "", yes, no);
}

dialog.baseStatusBar = function (title, content, width, bar, yes, no) {
    var d = dialog({
        id: 'base',
        fixed: false,
        title: title,
        content: content,
        width: width || 400,
        callback: yes,
        okValue: "确定",
        cancelValue: "取消",
        ok: function (here) {
            yes.call(this, here);
            return false;
        },
        cancel: function (here) {
            return no && no.call(this, here);
        },
        statusbar: bar
    });
    d.showModal();
}

dialog.baseBar = function (title, content, width, yes, no) {
    var d = dialog({
        id: 'base',
        fixed: false,
        title: title,
        content: content,
        width: width || 400,
        callback: yes,
        okValue: "确定",
        cancelValue: "取消",
        ok: function (here) {
            yes.call(this, here);
            return false;
        },
        cancel: function (here) {
            return no && no.call(this, here);
        }
    });
    d.showModal();
}

dialog.baseNoFooter = function (title, content, width) {
    var d = dialog({
        id: 'base',
        fixed: false,
        title: title,
        content: content,
        width: width || 400
    });
    d.showModal();
}

app.common.editViewNoFooter = function (t, u, d, w) {
    jQuery.ajax({
        data: d ? $.parseJSON(d) : '',
        url: u,
        success: function (data) {
            dialog.baseNoFooter(t, data, w || 400);
        },
        cache: false
    });
}

app.common.editViewShowBar = function (t, u, d, w, b) {
    jQuery.ajax({
        data: d ? $.parseJSON(d) : '',
        url: u,
        success: function (data) {
            $("#loading-animation").fadeOut();
            dialog.baseStatusBar(t, data, w || 400, b, function () {
                var api = this;
                if (b) {
                    $('#visibility-id').val($('#visibility').val());
                    $('#visibility').change(function () {
                        var value = $(this).children('option:selected').val();
                        $('#visibility-id').val(value);
                    });
                }
                app.common.ajaxTokenSubmit(function (response) {
                    if (app.common.dialogBaseResponse(response, api) == true) {
                        window.location.reload();
                    }
                });
            }, function () {
            });
        },
        cache: false
    });
}


app.common.editView = function (t, u, d, w) {
    app.common.editViewShowBar(t, u, d, w);
}

app.common.confirmView=function (confirm,id, d_url,target_url) {
    var url = d_url;
    dialog.confirm("<div class='aui_icon'><div></div><span style='padding:20px;display: block'>"+confirm+"</span></div>", function () {
        jQuery.ajax({
            url: url,
            data: {"id": id},
            success: function (response) {
                if(app.common.baseFromResponse(response)){
                    if(target_url){
                        window.location=target_url;
                    }else{
                        window.location.reload();
                    }
                }
            },
            cache: false
        });
    }, function () {

    });
}


app.common.deleteView = function (id, d_url,target_url) {
    var url = d_url || deleteUrl;
    app.common.confirmView("你确定要删除吗?",id, url,target_url);
}


app.common.update = function (d_url, d) {
    var url = d_url || updateUrl;
    jQuery.ajax({
        url: url,
        data: d,
        success: function (response) {
            app.common.baseResponse(response);
            window.location.reload();
        },
        cache: false
    });
}

app.common.baseResponse = function (responseText) {
    if (responseText.status == true) {
        if (responseText.message) {
            dialog.tips.success(responseText.message);
        }
    } else {
        dialog.tips.error(responseText.message);
    }
};

app.common.baseFromResponse = function (responseText) {
    if (responseText.status == true) {
        if (responseText.message) {
            dialog.tips.success(responseText.message);
        }
        return true;
    } else {
        if (responseText.customCreate) {
            app.common.formError(responseText.customCreate);
        } else {
            dialog.tips.error(responseText.message);
        }
        return false;
    }
};

app.common.dialogBaseResponse = function (responseText, theDialog) {
    if (responseText.status == true) {
        if (responseText.message) {
            dialog.tips.success(responseText.message);
        }
        theDialog.close().remove();
        return true;
    } else {
        dialog.tips.error(responseText.message);
        return false;
    }
};

app.common.ajaxTokenCustomFormSubmit = function (form, callback) {
    form.ajaxSubmit(function (response) {
        callback.call(this, response);
        if (response.x_token) {
            $('input[name="x_token"]').val(response.x_token);
        }
    });
    return false;
}

app.common.ajaxTokenSubmit = function (callback) {
    $('#baseInfoForm').ajaxSubmit(function (response) {
        callback.call(this, response);
        if (response.x_token) {
            $('input[name="x_token"]').val(response.x_token);
        }
    });
    return false;
}


app.common.changePasswordView=function(){
    var editUrl=basePath+'/manager/changePasswordDialog.htm';
    app.common.editView("修改密码", editUrl,null, '600');
}

app.common.formError = function (formErrorDate) {
    jQuery.each(formErrorDate, function (i) {
        var error = $("#" + i + "");
        error.removeClass('hidden');
        error.text(formErrorDate[i]);
    });
}


app.common.loadContent = function (tabId) {
    var $this = tabId,
        loadurl = $this.attr('data-url'),
        target = $this.attr('data-target');
    jQuery.get(loadurl, function (data) {
        $(target).html(data);
    });
    $this.tab('show');
}


$(function () {
    $('.baseSubmit').click(function () {
        app.common.ajaxTokenSubmit(function (response) {
            if(app.common.baseFromResponse(response)){
                window.location.reload();
            }
        });
    });

});


