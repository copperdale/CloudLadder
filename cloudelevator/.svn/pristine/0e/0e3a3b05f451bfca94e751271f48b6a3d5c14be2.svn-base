package com.laixusoft.cloudelevator.web.action;

import com.laixusoft.cloudelevator.biz.ao.MediaAO;
import com.laixusoft.cloudelevator.biz.query.MediaQuery;
import com.laixusoft.cloudelevator.web.common.base.BaseAction;
import wint.help.biz.result.Result;
import wint.mvc.flow.FlowData;
import wint.mvc.template.Context;

/**
 * 版权所有: 杭州睐旭信息科技有限公司
 * Created by User: apple
 * Date: 15/5/22
 * Time: 下午2:01
 */
public class Media extends BaseAction {

    private MediaAO mediaAO;

    public void editDialog(FlowData flowData, Context context) {
        int id = flowData.getParameters().getInt("id");
        Result result = mediaAO.viewMediaForEdit(id);
        this.proccessResult(result, flowData, context);
    }

    public void listDialog(FlowData flowData, Context context) {
        String key = flowData.getParameters().getString("key");
        MediaQuery query = new MediaQuery();
        query.setKey(key);
        Result result = mediaAO.viewList(query);
        super.proccessResult(result, flowData, context);
    }

    public void index(FlowData flowData, Context context) {
        String key = flowData.getParameters().getString("key");
        MediaQuery query = new MediaQuery();
        query.setKey(key);
        Result result = mediaAO.viewList(query);
        super.proccessResult(result, flowData, context);
    }

    public void json(FlowData flowData, Context context) {
        flowData.setLayout("dialog");
        flowData.setContentType(JSON_TYPE);
        int pageIndex=flowData.getParameters().getInt("pageIndex");
        int limit=flowData.getParameters().getInt("limit");
        MediaQuery query = new MediaQuery();
        query.setPageNo(pageIndex+1);
        query.setPageSize(limit);
        Result result = mediaAO.viewList(query);
        super.result2Context(result, context);
    }

    public void delete(FlowData flowData, Context context) {
        int id = flowData.getParameters().getInt("id");
        Result result = mediaAO.deleteMedia(id);
        this.proccessJsonResult(result, flowData, context);
    }

    public void doSave(FlowData flowData, Context context) {
        Result result = mediaAO.createMedia(flowData);
        this.proccessJsonResult(result, flowData, context);
    }

    public void setMediaAO(MediaAO mediaAO) {
        this.mediaAO = mediaAO;
    }
}
