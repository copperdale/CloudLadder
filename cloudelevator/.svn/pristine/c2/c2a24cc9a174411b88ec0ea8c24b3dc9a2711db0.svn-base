package com.laixusoft.cloudelevator.web.action;

import com.laixusoft.cloudelevator.biz.ao.UserAO;
import com.laixusoft.cloudelevator.biz.dto.UserDto;
import com.laixusoft.cloudelevator.biz.query.UserQuery;
import com.laixusoft.cloudelevator.web.common.base.BaseAction;
import wint.help.biz.result.Result;
import wint.mvc.flow.FlowData;
import wint.mvc.template.Context;

/**
 * 版权所有: 杭州睐旭信息科技有限公司
 * Created by User: apple
 * Date: 15/5/20
 * Time: 下午6:02
 */
public class User extends BaseAction {

    private UserAO userAO;


    public void editDialog(FlowData flowData, Context context) {
        int id = flowData.getParameters().getInt("id");
        Result result = userAO.viewUserForEdit(id);
        this.proccessResult(result, flowData, context);
    }

    public void index(FlowData flowData, Context context) {
        String key = flowData.getParameters().getString("key");
        UserQuery query = new UserQuery();
        query.setKey(key);
        Result result = userAO.viewList(query);
        super.proccessResult(result, flowData, context);
    }

    public void delete(FlowData flowData, Context context) {
        int id = flowData.getParameters().getInt("id");
        Result result = userAO.deleteUser(id);
        this.proccessJsonResult(result, flowData, context);
    }

    public void doSave(FlowData flowData, Context context) {

        String username = flowData.getParameters().getString("username");
        String password = flowData.getParameters().getString("password");

        UserDto dto= new UserDto();
        dto.setUsername(username);
        dto.setPassword(password);

        Result result = userAO.saveUser(dto);
        this.proccessJsonResult(result, flowData, context);
    }

    public void setUserAO(UserAO userAO) {
        this.userAO = userAO;
    }
}
