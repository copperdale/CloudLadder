Namespace.register("app.device");

app.device.editView=function(id){
    id = id||0;
    var title="新增设备";
    if(id) {
        title = "编辑设备信息";
    }
    var data = '{"id":'+id+'}';
    var editUrl = basePath+'/device/editDialog.htm';
    app.common.editView(title, editUrl, data, '600');
}

app.device.mediaListView=function(){
    var title="选择资源";
    var editUrl = basePath+'/media/listDialog.htm';
    app.device.View(title, editUrl, null, '600');
}

app.device.deleteView = function(id) {
    var deleteUrl = basePath+'/device/delete.htm';
    app.common.deleteView(id, deleteUrl);
}

app.device.View = function (t, u, d, w) {
    jQuery.ajax({
        data: d ? $.parseJSON(d) : '',
        url: u,
        success: function (data) {
            dialog.baseBar(t, data, w || 400, function () {
                var api = this;
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


app.device.deleteMediaView=function(id){
    var deleteUrl = basePath+'/device/deleteMedia.htm';
    app.common.deleteView(id, deleteUrl);
}