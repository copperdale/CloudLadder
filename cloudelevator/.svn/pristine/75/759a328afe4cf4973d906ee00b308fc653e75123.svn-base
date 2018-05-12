Namespace.register("app.media");

app.media.editView=function(id){
    id = id||0;
    var title="上传资源";
    if(id) {
        title = "编辑信息";
    }
    var data = '{"id":'+id+'}';
    var editUrl = basePath+'/media/editDialog.htm';
    app.common.editView(title, editUrl, data, '600');
}

app.media.deleteView = function(id) {
    var deleteUrl = basePath+'/media/delete.htm';
    app.common.deleteView(id, deleteUrl);
}
