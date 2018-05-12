package com.laixusoft.cloudelevator.web.action;

import com.laixusoft.cloudelevator.biz.ao.DeviceAO;
import com.laixusoft.cloudelevator.biz.dto.DeviceDto;
import com.laixusoft.cloudelevator.web.common.base.BaseAction;
import wint.help.biz.query.BaseQuery;
import wint.help.biz.result.Result;
import wint.mvc.flow.FlowData;
import wint.mvc.template.Context;

/**
 * 版权所有: 杭州睐旭信息科技有限公司
 * Created by User: apple
 * Date: 15/5/21
 * Time: 下午12:38
 */
public class Device extends BaseAction{

    private DeviceAO deviceAO;

    public void editDialog(FlowData flowData, Context context) {
        int id = flowData.getParameters().getInt("id");
        Result result = deviceAO.viewDeviceForEdit(id);
        this.proccessResult(result, flowData, context);
    }

    public void index(FlowData flowData, Context context) {
        BaseQuery query = new BaseQuery();
        Result result = deviceAO.viewList(query);
        super.proccessResult(result, flowData, context);
    }

    public void delete(FlowData flowData, Context context) {
        int id = flowData.getParameters().getInt("id");
        Result result = deviceAO.deleteDevice(id);
        this.proccessJsonResult(result, flowData, context);
    }

    public void deleteMedia(FlowData flowData, Context context) {
        int id = flowData.getParameters().getInt("id");
        Result result = deviceAO.deleteDeviceMedia(id);
        this.proccessJsonResult(result, flowData, context);
    }

    public void doSave(FlowData flowData, Context context) {

        String deviceName = flowData.getParameters().getString("deviceName");
        String deviceNumber = flowData.getParameters().getString("deviceNumber");

        DeviceDto dto=new DeviceDto();
        dto.setDeviceName(deviceName);
        dto.setDeviceNumber(deviceNumber);

        Result result = deviceAO.createDevice(dto);
        this.proccessJsonResult(result, flowData, context);
    }

    public void doSaveMedias(FlowData flowData, Context context){
        Result result = deviceAO.saveDeviceMedia(flowData);
        this.proccessJsonResult(result, flowData, context);
    }

    public void execute(FlowData flowData, Context context, int id) {
        flowData.setTarget("device/detail");
        Result result = deviceAO.viewDevice(id);
        this.proccessResult(result,flowData,context);
    }

    public void setDeviceAO(DeviceAO deviceAO) {
        this.deviceAO = deviceAO;
    }
}
