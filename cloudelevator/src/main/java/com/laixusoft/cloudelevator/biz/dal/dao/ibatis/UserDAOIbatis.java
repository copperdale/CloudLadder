package com.laixusoft.cloudelevator.biz.dal.dao.ibatis;

import com.laixusoft.cloudelevator.biz.dal.dao.UserDAO;
import com.laixusoft.cloudelevator.biz.dal.domain.UserDO;
import com.laixusoft.cloudelevator.biz.query.UserQuery;
import wint.dal.ibatis.ReadWriteSqlMapClientDaoSupport;
import wint.lang.utils.CollectionUtil;
import wint.lang.utils.MapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* this file is auto generate.
*/
public class UserDAOIbatis extends ReadWriteSqlMapClientDaoSupport implements UserDAO {


    @Override
	public int create(UserDO user) {
		return (Integer)this.getSqlMapClientTemplate().insert("UserDAO.create", user);
	}

    @Override
	public int delete(int id) {
		return this.getSqlMapClientTemplate().update("UserDAO.delete", id);
	}

    @Override
	public int update(UserDO user) {
		return this.getSqlMapClientTemplate().update("UserDAO.update", user);
	}

    @Override
	public UserDO queryById(int id) {
	    return (UserDO)this.getSqlMapClientTemplate().queryForObject("UserDAO.queryById", id);
	}

    @Override
	public UserDO queryByName(String name) {
	    return (UserDO)this.getSqlMapClientTemplate().queryForObject("UserDAO.queryByName", name);
	}

    @Override
    public Map<Integer, UserDO> queryByIds(List<Integer> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return MapUtil.newHashMap();
        }
        List<UserDO> resultList = (List<UserDO>) this.getSqlMapClientTemplate().queryForList("UserDAO.queryByIds", ids);
        Map<Integer, UserDO> ret = MapUtil.newHashMap();
        for (UserDO user : resultList) {
            ret.put(user.getId(), user);
        }
        return ret;
    }

    @Override
    public List<UserDO> queryForPage(UserQuery query) {
        int count = (Integer)this.getSqlMapClientTemplate().queryForObject("UserDAO.queryForPageCount", query);
        if (count == 0) {
            return new ArrayList<UserDO>(0);
        }
        query.setTotalResultCount(count);
        return (List<UserDO>)this.getSqlMapClientTemplate().queryForList("UserDAO.queryForPage", query);
    }

}
