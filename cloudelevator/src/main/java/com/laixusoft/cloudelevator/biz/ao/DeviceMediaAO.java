package com.laixusoft.cloudelevator.biz.ao;

import wint.help.biz.query.BaseQuery;
import wint.help.biz.result.Result;
import com.laixusoft.cloudelevator.biz.dal.domain.DeviceMediaDO;

/**
* this file is auto generate.
*/
public interface DeviceMediaAO {

    Result createDeviceMedia(DeviceMediaDO deviceMediaDO);

    Result updateDeviceMedia(DeviceMediaDO deviceMediaDO);

    Result viewDeviceMedia(int id);

    Result viewDeviceMediaForEdit(int id);

    Result viewList(BaseQuery query);

    Result deleteDeviceMedia(int id);

}