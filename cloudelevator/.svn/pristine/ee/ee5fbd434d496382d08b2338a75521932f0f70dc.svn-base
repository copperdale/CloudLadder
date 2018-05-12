package com.laixusoft.cloudelevator.biz.dal.dao;

import java.util.Arrays;
import java.util.Map;
import com.laixusoft.cloudelevator.biz.dal.domain.NoticeDO;
import com.laixusoft.cloudelevator.BaseTest;
import junit.framework.Assert;

/**
* this file is auto generate.
*/
public class NoticeDAOTests extends BaseTest {

	private NoticeDAO noticeDAO;

    public void testCreate() {
        NoticeDO noticeDO = new NoticeDO();
		noticeDO.setContent("a");
		noticeDO.setGmtModified(new java.util.Date());
		noticeDO.setGmtCreate(new java.util.Date());
		int id = noticeDAO.create(noticeDO);
		Assert.assertTrue(id > 0);
	}

    public void testQueryById() {
        NoticeDO noticeDO = new NoticeDO();
		noticeDO.setContent("a");
		noticeDO.setGmtModified(new java.util.Date());
		noticeDO.setGmtCreate(new java.util.Date());
        int id = noticeDAO.create(noticeDO);
        Assert.assertTrue(id > 0);
        NoticeDO noticeDO_2 = noticeDAO.queryById(id);
        Assert.assertNotNull(noticeDO_2);
        Map<Integer, NoticeDO> noticeDO_3 = noticeDAO.queryByIds(Arrays.asList(id));
        Assert.assertNotNull(noticeDO_3);
        Assert.assertTrue(!noticeDO_3.isEmpty());
    }

    public void testDelete() {
        NoticeDO noticeDO = new NoticeDO();
		noticeDO.setContent("a");
		noticeDO.setGmtModified(new java.util.Date());
		noticeDO.setGmtCreate(new java.util.Date());
        int id = noticeDAO.create(noticeDO);
        Assert.assertTrue(id > 0);
        NoticeDO noticeDO_2 = noticeDAO.queryById(id);
        Assert.assertNotNull(noticeDO_2);
        noticeDAO.delete(id);
        noticeDO_2 = noticeDAO.queryById(id);
        Assert.assertNull(noticeDO_2);
    }

    public void testUpdate() {
        NoticeDO noticeDO = new NoticeDO();
		noticeDO.setContent("a");
		noticeDO.setGmtModified(new java.util.Date());
		noticeDO.setGmtCreate(new java.util.Date());
        int id = noticeDAO.create(noticeDO);
        Assert.assertTrue(id > 0);
        NoticeDO noticeDO_2 = noticeDAO.queryById(id);
        // TODO finish update


        noticeDAO.update(noticeDO_2);
        noticeDO_2 = noticeDAO.queryById(id);
        // TODO finish your asserts

    }


    public void setNoticeDAO(NoticeDAO noticeDAO) {
		this.noticeDAO = noticeDAO;
	}

}
