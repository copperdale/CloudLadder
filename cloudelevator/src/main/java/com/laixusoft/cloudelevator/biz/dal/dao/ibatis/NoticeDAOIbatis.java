package com.laixusoft.cloudelevator.biz.dal.dao.ibatis;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import com.laixusoft.cloudelevator.biz.dal.dao.NoticeDAO;
import com.laixusoft.cloudelevator.biz.dal.domain.NoticeDO;
import wint.dal.ibatis.ReadWriteSqlMapClientDaoSupport;
import wint.help.biz.query.BaseQuery;
import wint.lang.utils.CollectionUtil;
import wint.lang.utils.MapUtil;

/**
* this file is auto generate.
*/
public class NoticeDAOIbatis extends ReadWriteSqlMapClientDaoSupport implements NoticeDAO {


    @Override
	public int create(NoticeDO notice) {
		return (Integer)this.getSqlMapClientTemplate().insert("NoticeDAO.create", notice);
	}

    @Override
	public int delete(int id) {
		return this.getSqlMapClientTemplate().update("NoticeDAO.delete", id);
	}

    @Override
	public int update(NoticeDO notice) {
		return this.getSqlMapClientTemplate().update("NoticeDAO.update", notice);
	}

    @Override
	public NoticeDO queryById(int id) {
	    return (NoticeDO)this.getSqlMapClientTemplate().queryForObject("NoticeDAO.queryById", id);
	}

    @Override
    public Map<Integer, NoticeDO> queryByIds(List<Integer> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return MapUtil.newHashMap();
        }
        List<NoticeDO> resultList = (List<NoticeDO>) this.getSqlMapClientTemplate().queryForList("NoticeDAO.queryByIds", ids);
        Map<Integer, NoticeDO> ret = MapUtil.newHashMap();
        for (NoticeDO notice : resultList) {
            ret.put(notice.getId(), notice);
        }
        return ret;
    }

    @Override
    public List<NoticeDO> queryForPage(BaseQuery query) {
        int count = (Integer)this.getSqlMapClientTemplate().queryForObject("NoticeDAO.queryForPageCount", query);
        if (count == 0) {
            return new ArrayList<NoticeDO>(0);
        }
        query.setTotalResultCount(count);
        return (List<NoticeDO>)this.getSqlMapClientTemplate().queryForList("NoticeDAO.queryForPage", query);
    }

}
