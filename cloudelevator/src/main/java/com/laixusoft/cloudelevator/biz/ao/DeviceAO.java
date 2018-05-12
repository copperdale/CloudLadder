package com.laixusoft.cloudelevator.biz.ao;

import com.laixusoft.cloudelevator.biz.dto.DeviceDto;
import wint.help.biz.query.BaseQuery;
import wint.help.biz.result.Result;
import com.laixusoft.cloudelevator.biz.dal.domain.DeviceDO;
import wint.mvc.flow.FlowData;

/**
* this file is auto generate.
*/
public interface DeviceAO {

    Result createDevice(DeviceDto dto);

    Result updateDevice(DeviceDO deviceDO);

    Result viewDevice(int id);

    Result viewDeviceForEdit(int id);

    Result viewList(BaseQuery query);

    Result deleteDevice(int id);

    Result deleteDeviceMedia(int id);

    Result saveDeviceMedia(FlowData flowData);

}