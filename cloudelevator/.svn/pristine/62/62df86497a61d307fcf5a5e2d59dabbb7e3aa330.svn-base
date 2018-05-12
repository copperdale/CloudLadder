package com.laixusoft.cloudelevator.web.action;

import com.laixusoft.cloudelevator.biz.ao.NoticeAO;
import com.laixusoft.cloudelevator.biz.dto.NoticeDto;
import com.laixusoft.cloudelevator.web.common.base.BaseAction;
import wint.help.biz.query.BaseQuery;
import wint.help.biz.result.Result;
import wint.mvc.flow.FlowData;
import wint.mvc.template.Context;

/**
 * 版权所有: 杭州睐旭信息科技有限公司
 * Created by User: apple
 * Date: 15/5/21
 * Time: 上午10:57
 */
public class Notice extends BaseAction {

    private NoticeAO noticeAO;

    public void editDialog(FlowData flowData, Context context) {
        int id = flowData.getParameters().getInt("id");
        Result result = noticeAO.viewNoticeForEdit(id);
        this.proccessResult(result, flowData, context);
    }

    public void index(FlowData flowData, Context context) {
        BaseQuery query = new BaseQuery();
        Result result = noticeAO.viewList(query);
        super.proccessResult(result, flowData, context);
    }

    public void delete(FlowData flowData, Context context) {
        int id = flowData.getParameters().getInt("id");
        Result result = noticeAO.deleteNotice(id);
        this.proccessJsonResult(result, flowData, context);
    }

    public void doSave(FlowData flowData, Context context) {

        String content = flowData.getParameters().getString("content");
        NoticeDto dto=new NoticeDto();
        dto.setContent(content);

        Result result = noticeAO.createNotice(dto);
        this.proccessJsonResult(result, flowData, context);
    }

    public void setNoticeAO(NoticeAO noticeAO) {
        this.noticeAO = noticeAO;
    }
}
