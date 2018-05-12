package com.laixusoft.cloudelevator.biz.dal.dao;

import java.util.Arrays;
import java.util.Map;
import com.laixusoft.cloudelevator.biz.dal.domain.UserDO;
import com.laixusoft.cloudelevator.BaseTest;
import junit.framework.Assert;

/**
* this file is auto generate.
*/
public class UserDAOTests extends BaseTest {

	private UserDAO userDAO;

    public void testCreate() {
        UserDO userDO = new UserDO();
		userDO.setGmtModified(new java.util.Date());
		userDO.setUsername("a");
		userDO.setGmtCreate(new java.util.Date());
		userDO.setPassword("a");
		userDO.setSalt("a");
		int id = userDAO.create(userDO);
		Assert.assertTrue(id > 0);
	}

    public void testQueryById() {
        UserDO userDO = new UserDO();
		userDO.setGmtModified(new java.util.Date());
		userDO.setUsername("a");
		userDO.setGmtCreate(new java.util.Date());
		userDO.setPassword("a");
		userDO.setSalt("a");
        int id = userDAO.create(userDO);
        Assert.assertTrue(id > 0);
        UserDO userDO_2 = userDAO.queryById(id);
        Assert.assertNotNull(userDO_2);
        Map<Integer, UserDO> userDO_3 = userDAO.queryByIds(Arrays.asList(id));
        Assert.assertNotNull(userDO_3);
        Assert.assertTrue(!userDO_3.isEmpty());
    }

    public void testDelete() {
        UserDO userDO = new UserDO();
		userDO.setGmtModified(new java.util.Date());
		userDO.setUsername("a");
		userDO.setGmtCreate(new java.util.Date());
		userDO.setPassword("a");
		userDO.setSalt("a");
        int id = userDAO.create(userDO);
        Assert.assertTrue(id > 0);
        UserDO userDO_2 = userDAO.queryById(id);
        Assert.assertNotNull(userDO_2);
        userDAO.delete(id);
        userDO_2 = userDAO.queryById(id);
        Assert.assertNull(userDO_2);
    }

    public void testUpdate() {
        UserDO userDO = new UserDO();
		userDO.setGmtModified(new java.util.Date());
		userDO.setUsername("a");
		userDO.setGmtCreate(new java.util.Date());
		userDO.setPassword("a");
		userDO.setSalt("a");
        int id = userDAO.create(userDO);
        Assert.assertTrue(id > 0);
        UserDO userDO_2 = userDAO.queryById(id);
        // TODO finish update


        userDAO.update(userDO_2);
        userDO_2 = userDAO.queryById(id);
        // TODO finish your asserts

    }


    public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

}
