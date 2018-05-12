package com.laixusoft.cloudelevator.biz.dal.dao.ibatis;

import com.laixusoft.cloudelevator.biz.dal.dao.MediaDAO;
import com.laixusoft.cloudelevator.biz.dal.domain.MediaDO;
import com.laixusoft.cloudelevator.biz.query.MediaQuery;
import wint.dal.ibatis.ReadWriteSqlMapClientDaoSupport;
import wint.lang.utils.CollectionUtil;
import wint.lang.utils.MapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* this file is auto generate.
*/
public class MediaDAOIbatis extends ReadWriteSqlMapClientDaoSupport implements MediaDAO {


    @Override
	public int create(MediaDO media) {
		return (Integer)this.getSqlMapClientTemplate().insert("MediaDAO.create", media);
	}

    @Override
	public int delete(int id) {
		return this.getSqlMapClientTemplate().update("MediaDAO.delete", id);
	}

    @Override
	public int update(MediaDO media) {
		return this.getSqlMapClientTemplate().update("MediaDAO.update", media);
	}

    @Override
	public MediaDO queryById(int id) {
	    return (MediaDO)this.getSqlMapClientTemplate().queryForObject("MediaDAO.queryById", id);
	}

    @Override
    public Map<Integer, MediaDO> queryByIds(List<Integer> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return MapUtil.newHashMap();
        }
        List<MediaDO> resultList = (List<MediaDO>) this.getSqlMapClientTemplate().queryForList("MediaDAO.queryByIds", ids);
        Map<Integer, MediaDO> ret = MapUtil.newHashMap();
        for (MediaDO media : resultList) {
            ret.put(media.getId(), media);
        }
        return ret;
    }

    @Override
    public List<MediaDO> queryForPage(MediaQuery query) {
        int count = (Integer)this.getSqlMapClientTemplate().queryForObject("MediaDAO.queryForPageCount", query);
        if (count == 0) {
            return new ArrayList<MediaDO>(0);
        }
        query.setTotalResultCount(count);
        return (List<MediaDO>)this.getSqlMapClientTemplate().queryForList("MediaDAO.queryForPage", query);
    }

}
