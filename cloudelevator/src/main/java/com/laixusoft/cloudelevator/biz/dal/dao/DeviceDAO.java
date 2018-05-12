package com.laixusoft.cloudelevator.biz.dal.dao;

import java.util.List;
import java.util.Map;
import com.laixusoft.cloudelevator.biz.dal.domain.DeviceDO;
import wint.help.biz.query.BaseQuery;

/**
* this file is auto generate.
*/
public interface DeviceDAO {

	int create(DeviceDO device);

	int delete(int id);

    int update(DeviceDO device);

	DeviceDO queryById(int id);

    Map<Integer, DeviceDO> queryByIds(List<Integer> ids);

    List<DeviceDO> queryForPage(BaseQuery query);
}
