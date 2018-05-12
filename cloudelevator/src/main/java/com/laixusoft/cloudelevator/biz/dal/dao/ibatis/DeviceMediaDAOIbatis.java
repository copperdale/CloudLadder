package com.laixusoft.cloudelevator.biz.dal.dao.ibatis;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import com.laixusoft.cloudelevator.biz.dal.dao.DeviceMediaDAO;
import com.laixusoft.cloudelevator.biz.dal.domain.DeviceMediaDO;
import wint.dal.ibatis.ReadWriteSqlMapClientDaoSupport;
import wint.help.biz.query.BaseQuery;
import wint.lang.utils.CollectionUtil;
import wint.lang.utils.MapUtil;

/**
* this file is auto generate.
*/
public class DeviceMediaDAOIbatis extends ReadWriteSqlMapClientDaoSupport implements DeviceMediaDAO {


    @Override
	public int create(DeviceMediaDO deviceMedia) {
		return (Integer)this.getSqlMapClientTemplate().insert("DeviceMediaDAO.create", deviceMedia);
	}

    @Override
	public int delete(int id) {
		return this.getSqlMapClientTemplate().update("DeviceMediaDAO.delete", id);
	}

    @Override
	public int update(DeviceMediaDO deviceMedia) {
		return this.getSqlMapClientTemplate().update("DeviceMediaDAO.update", deviceMedia);
	}

    @Override
	public DeviceMediaDO queryById(int id) {
	    return (DeviceMediaDO)this.getSqlMapClientTemplate().queryForObject("DeviceMediaDAO.queryById", id);
	}

    @Override
    public Map<Integer, DeviceMediaDO> queryByIds(List<Integer> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return MapUtil.newHashMap();
        }
        List<DeviceMediaDO> resultList = (List<DeviceMediaDO>) this.getSqlMapClientTemplate().queryForList("DeviceMediaDAO.queryByIds", ids);
        Map<Integer, DeviceMediaDO> ret = MapUtil.newHashMap();
        for (DeviceMediaDO deviceMedia : resultList) {
            ret.put(deviceMedia.getId(), deviceMedia);
        }
        return ret;
    }

    @Override
    public List<DeviceMediaDO> queryForPage(BaseQuery query) {
        int count = (Integer)this.getSqlMapClientTemplate().queryForObject("DeviceMediaDAO.queryForPageCount", query);
        if (count == 0) {
            return new ArrayList<DeviceMediaDO>(0);
        }
        query.setTotalResultCount(count);
        return (List<DeviceMediaDO>)this.getSqlMapClientTemplate().queryForList("DeviceMediaDAO.queryForPage", query);
    }

    @Override
    public List<DeviceMediaDO> queryByDeviceId(int deviceId){
        return (List<DeviceMediaDO>)this.getSqlMapClientTemplate().queryForList("DeviceMediaDAO.queryByDeviceId", deviceId);
    }

}
