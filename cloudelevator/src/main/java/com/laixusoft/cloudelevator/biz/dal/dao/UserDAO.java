package com.laixusoft.cloudelevator.biz.dal.dao;

import com.laixusoft.cloudelevator.biz.dal.domain.UserDO;
import com.laixusoft.cloudelevator.biz.query.UserQuery;

import java.util.List;
import java.util.Map;

/**
 * this file is auto generate.
 */
public interface UserDAO {

    int create(UserDO user);

    int delete(int id);

    int update(UserDO user);

    UserDO queryById(int id);

    UserDO queryByName(String name);

    Map<Integer, UserDO> queryByIds(List<Integer> ids);

    List<UserDO> queryForPage(UserQuery query);
}
