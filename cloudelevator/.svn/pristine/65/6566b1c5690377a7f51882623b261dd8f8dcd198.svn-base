package com.laixusoft.cloudelevator.web.action;

import com.laixusoft.cloudelevator.biz.ao.NoticeAO;
import com.laixusoft.cloudelevator.web.common.base.BaseAction;
import wint.help.biz.query.BaseQuery;
import wint.help.biz.result.Result;
import wint.mvc.flow.FlowData;
import wint.mvc.template.Context;

/**
 * 版权所有: 杭州睐旭信息科技有限公司
 * Created by User: apple
 * Date: 15/5/21
 * Time: 下午1:40
 */
public class Api extends BaseAction {

    private NoticeAO noticeAO;

    public void notices(FlowData flowData, Context context) {
        flowData.setLayout("dialog");
        flowData.setContentType(JSON_TYPE);
        BaseQuery query = new BaseQuery();
        query.setPageSize(50);
        Result result = noticeAO.viewList(query);
        super.result2Context(result, context);
    }


    public void setNoticeAO(NoticeAO noticeAO) {
        this.noticeAO = noticeAO;
    }
}
