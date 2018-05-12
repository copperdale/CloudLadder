package com.laixusoft.cloudelevator.biz.dal.dao.ibatis;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import com.laixusoft.cloudelevator.biz.dal.dao.DeviceDAO;
import com.laixusoft.cloudelevator.biz.dal.domain.DeviceDO;
import wint.dal.ibatis.ReadWriteSqlMapClientDaoSupport;
import wint.help.biz.query.BaseQuery;
import wint.lang.utils.CollectionUtil;
import wint.lang.utils.MapUtil;

/**
* this file is auto generate.
*/
public class DeviceDAOIbatis extends ReadWriteSqlMapClientDaoSupport implements DeviceDAO {


    @Override
	public int create(DeviceDO device) {
		return (Integer)this.getSqlMapClientTemplate().insert("DeviceDAO.create", device);
	}

    @Override
	public int delete(int id) {
		return this.getSqlMapClientTemplate().update("DeviceDAO.delete", id);
	}

    @Override
	public int update(DeviceDO device) {
		return this.getSqlMapClientTemplate().update("DeviceDAO.update", device);
	}

    @Override
	public DeviceDO queryById(int id) {
	    return (DeviceDO)this.getSqlMapClientTemplate().queryForObject("DeviceDAO.queryById", id);
	}

    @Override
    public Map<Integer, DeviceDO> queryByIds(List<Integer> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return MapUtil.newHashMap();
        }
        List<DeviceDO> resultList = (List<DeviceDO>) this.getSqlMapClientTemplate().queryForList("DeviceDAO.queryByIds", ids);
        Map<Integer, DeviceDO> ret = MapUtil.newHashMap();
        for (DeviceDO device : resultList) {
            ret.put(device.getId(), device);
        }
        return ret;
    }

    @Override
    public List<DeviceDO> queryForPage(BaseQuery query) {
        int count = (Integer)this.getSqlMapClientTemplate().queryForObject("DeviceDAO.queryForPageCount", query);
        if (count == 0) {
            return new ArrayList<DeviceDO>(0);
        }
        query.setTotalResultCount(count);
        return (List<DeviceDO>)this.getSqlMapClientTemplate().queryForList("DeviceDAO.queryForPage", query);
    }

}
