package com.laixusoft.cloudelevator.biz.dal.dao;

import java.util.Arrays;
import java.util.Map;
import com.laixusoft.cloudelevator.biz.dal.domain.DeviceMediaDO;
import com.laixusoft.cloudelevator.BaseTest;
import junit.framework.Assert;

/**
* this file is auto generate.
*/
public class DeviceMediaDAOTests extends BaseTest {

	private DeviceMediaDAO deviceMediaDAO;

    public void testCreate() {
        DeviceMediaDO deviceMediaDO = new DeviceMediaDO();
		deviceMediaDO.setMediaId(2);
		deviceMediaDO.setGmtModified(new java.util.Date());
		deviceMediaDO.setGmtCreate(new java.util.Date());
		deviceMediaDO.setPlay(false);
		deviceMediaDO.setDeviceId(2);
		int id = deviceMediaDAO.create(deviceMediaDO);
		Assert.assertTrue(id > 0);
	}

    public void testQueryById() {
        DeviceMediaDO deviceMediaDO = new DeviceMediaDO();
		deviceMediaDO.setMediaId(2);
		deviceMediaDO.setGmtModified(new java.util.Date());
		deviceMediaDO.setGmtCreate(new java.util.Date());
		deviceMediaDO.setPlay(false);
		deviceMediaDO.setDeviceId(2);
        int id = deviceMediaDAO.create(deviceMediaDO);
        Assert.assertTrue(id > 0);
        DeviceMediaDO deviceMediaDO_2 = deviceMediaDAO.queryById(id);
        Assert.assertNotNull(deviceMediaDO_2);
        Map<Integer, DeviceMediaDO> deviceMediaDO_3 = deviceMediaDAO.queryByIds(Arrays.asList(id));
        Assert.assertNotNull(deviceMediaDO_3);
        Assert.assertTrue(!deviceMediaDO_3.isEmpty());
    }

    public void testDelete() {
        DeviceMediaDO deviceMediaDO = new DeviceMediaDO();
		deviceMediaDO.setMediaId(2);
		deviceMediaDO.setGmtModified(new java.util.Date());
		deviceMediaDO.setGmtCreate(new java.util.Date());
		deviceMediaDO.setPlay(false);
		deviceMediaDO.setDeviceId(2);
        int id = deviceMediaDAO.create(deviceMediaDO);
        Assert.assertTrue(id > 0);
        DeviceMediaDO deviceMediaDO_2 = deviceMediaDAO.queryById(id);
        Assert.assertNotNull(deviceMediaDO_2);
        deviceMediaDAO.delete(id);
        deviceMediaDO_2 = deviceMediaDAO.queryById(id);
        Assert.assertNull(deviceMediaDO_2);
    }

    public void testUpdate() {
        DeviceMediaDO deviceMediaDO = new DeviceMediaDO();
		deviceMediaDO.setMediaId(2);
		deviceMediaDO.setGmtModified(new java.util.Date());
		deviceMediaDO.setGmtCreate(new java.util.Date());
		deviceMediaDO.setPlay(false);
		deviceMediaDO.setDeviceId(2);
        int id = deviceMediaDAO.create(deviceMediaDO);
        Assert.assertTrue(id > 0);
        DeviceMediaDO deviceMediaDO_2 = deviceMediaDAO.queryById(id);
        // TODO finish update


        deviceMediaDAO.update(deviceMediaDO_2);
        deviceMediaDO_2 = deviceMediaDAO.queryById(id);
        // TODO finish your asserts

    }


    public void setDeviceMediaDAO(DeviceMediaDAO deviceMediaDAO) {
		this.deviceMediaDAO = deviceMediaDAO;
	}

}
