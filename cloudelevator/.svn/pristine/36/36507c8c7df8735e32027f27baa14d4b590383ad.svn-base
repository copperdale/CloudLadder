package com.laixusoft.cloudelevator.biz.dal.dao;

import java.util.Arrays;
import java.util.Map;
import com.laixusoft.cloudelevator.biz.dal.domain.MediaDO;
import com.laixusoft.cloudelevator.BaseTest;
import junit.framework.Assert;

/**
* this file is auto generate.
*/
public class MediaDAOTests extends BaseTest {

	private MediaDAO mediaDAO;

    public void testCreate() {
        MediaDO mediaDO = new MediaDO();
		mediaDO.setGmtModified(new java.util.Date());
		mediaDO.setTitle("a");
		mediaDO.setGmtCreate(new java.util.Date());
		mediaDO.setUrl("a");
        mediaDO.setFileSize(1);
		int id = mediaDAO.create(mediaDO);
		Assert.assertTrue(id > 0);
	}

    public void testQueryById() {
        MediaDO mediaDO = new MediaDO();
		mediaDO.setGmtModified(new java.util.Date());
		mediaDO.setTitle("a");
		mediaDO.setGmtCreate(new java.util.Date());
		mediaDO.setUrl("a");
        mediaDO.setFileSize(1);
        int id = mediaDAO.create(mediaDO);
        Assert.assertTrue(id > 0);
        MediaDO mediaDO_2 = mediaDAO.queryById(id);
        Assert.assertNotNull(mediaDO_2);
        Map<Integer, MediaDO> mediaDO_3 = mediaDAO.queryByIds(Arrays.asList(id));
        Assert.assertNotNull(mediaDO_3);
        Assert.assertTrue(!mediaDO_3.isEmpty());
    }

    public void testDelete() {
        MediaDO mediaDO = new MediaDO();
		mediaDO.setGmtModified(new java.util.Date());
		mediaDO.setTitle("a");
		mediaDO.setGmtCreate(new java.util.Date());
		mediaDO.setUrl("a");
        mediaDO.setFileSize(1);
        int id = mediaDAO.create(mediaDO);
        Assert.assertTrue(id > 0);
        MediaDO mediaDO_2 = mediaDAO.queryById(id);
        Assert.assertNotNull(mediaDO_2);
        mediaDAO.delete(id);
        mediaDO_2 = mediaDAO.queryById(id);
        Assert.assertNull(mediaDO_2);
    }

    public void testUpdate() {
        MediaDO mediaDO = new MediaDO();
		mediaDO.setGmtModified(new java.util.Date());
		mediaDO.setTitle("a");
		mediaDO.setGmtCreate(new java.util.Date());
		mediaDO.setUrl("a");
        mediaDO.setFileSize(1);
        int id = mediaDAO.create(mediaDO);
        Assert.assertTrue(id > 0);
        MediaDO mediaDO_2 = mediaDAO.queryById(id);
        // TODO finish update


        mediaDAO.update(mediaDO_2);
        mediaDO_2 = mediaDAO.queryById(id);
        // TODO finish your asserts

    }


    public void setMediaDAO(MediaDAO mediaDAO) {
		this.mediaDAO = mediaDAO;
	}

}
