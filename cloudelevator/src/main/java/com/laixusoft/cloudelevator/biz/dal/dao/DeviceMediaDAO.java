package com.laixusoft.cloudelevator.biz.dal.dao;

import java.util.List;
import java.util.Map;
import com.laixusoft.cloudelevator.biz.dal.domain.DeviceMediaDO;
import wint.help.biz.query.BaseQuery;

/**
* this file is auto generate.
*/
public interface DeviceMediaDAO {

	int create(DeviceMediaDO deviceMedia);

	int delete(int id);

    int update(DeviceMediaDO deviceMedia);

	DeviceMediaDO queryById(int id);

    Map<Integer, DeviceMediaDO> queryByIds(List<Integer> ids);

    List<DeviceMediaDO> queryForPage(BaseQuery query);

    List<DeviceMediaDO> queryByDeviceId(int deviceId);
}
