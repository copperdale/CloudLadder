Namespace.register("app.notice");

app.notice.editView=function(id){
    id = id||0;
    var title="新增通知";
    if(id) {
        title = "编辑信息";
    }
    var data = '{"id":'+id+'}';
    var editUrl = basePath+'/notice/editDialog.htm';
    app.common.editView(title, editUrl, data, '600');
}

app.notice.deleteView = function(id) {
    var deleteUrl = basePath+'/notice/delete.htm';
    app.common.deleteView(id, deleteUrl);
}