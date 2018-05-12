Namespace.register("app.account");

app.account.editView=function(id){
    id = id||0;
    var title="新增用户";
    if(id) {
        title = "编辑用户信息";
    }
    var data = '{"id":'+id+'}';
    var editUrl = basePath+'/user/editDialog.htm';
    app.common.editView(title, editUrl, data, '600');
}

app.account.deleteView = function(id) {
    var deleteUrl = basePath+'/user/delete.htm';
    app.common.deleteView(id, deleteUrl);
}