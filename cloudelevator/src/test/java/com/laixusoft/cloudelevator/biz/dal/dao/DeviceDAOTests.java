package com.laixusoft.cloudelevator.biz.dal.dao;

import java.util.Arrays;
import java.util.Map;
import com.laixusoft.cloudelevator.biz.dal.domain.DeviceDO;
import com.laixusoft.cloudelevator.BaseTest;
import junit.framework.Assert;

/**
* this file is auto generate.
*/
public class DeviceDAOTests extends BaseTest {

	private DeviceDAO deviceDAO;

    public void testCreate() {
        DeviceDO deviceDO = new DeviceDO();
		deviceDO.setAndroidClientId("a");
		deviceDO.setGmtModified(new java.util.Date());
		deviceDO.setDeviceName("a");
		deviceDO.setGmtCreate(new java.util.Date());
		deviceDO.setAllStorage("a");
		deviceDO.setCurStorage("a");
		deviceDO.setDeviceNumber("a");
		int id = deviceDAO.create(deviceDO);
		Assert.assertTrue(id > 0);
	}

    public void testQueryById() {
        DeviceDO deviceDO = new DeviceDO();
		deviceDO.setAndroidClientId("a");
		deviceDO.setGmtModified(new java.util.Date());
		deviceDO.setDeviceName("a");
		deviceDO.setGmtCreate(new java.util.Date());
		deviceDO.setAllStorage("a");
		deviceDO.setCurStorage("a");
		deviceDO.setDeviceNumber("a");
        int id = deviceDAO.create(deviceDO);
        Assert.assertTrue(id > 0);
        DeviceDO deviceDO_2 = deviceDAO.queryById(id);
        Assert.assertNotNull(deviceDO_2);
        Map<Integer, DeviceDO> deviceDO_3 = deviceDAO.queryByIds(Arrays.asList(id));
        Assert.assertNotNull(deviceDO_3);
        Assert.assertTrue(!deviceDO_3.isEmpty());
    }

    public void testDelete() {
        DeviceDO deviceDO = new DeviceDO();
		deviceDO.setAndroidClientId("a");
		deviceDO.setGmtModified(new java.util.Date());
		deviceDO.setDeviceName("a");
		deviceDO.setGmtCreate(new java.util.Date());
		deviceDO.setAllStorage("a");
		deviceDO.setCurStorage("a");
		deviceDO.setDeviceNumber("a");
        int id = deviceDAO.create(deviceDO);
        Assert.assertTrue(id > 0);
        DeviceDO deviceDO_2 = deviceDAO.queryById(id);
        Assert.assertNotNull(deviceDO_2);
        deviceDAO.delete(id);
        deviceDO_2 = deviceDAO.queryById(id);
        Assert.assertNull(deviceDO_2);
    }

    public void testUpdate() {
        DeviceDO deviceDO = new DeviceDO();
		deviceDO.setAndroidClientId("a");
		deviceDO.setGmtModified(new java.util.Date());
		deviceDO.setDeviceName("a");
		deviceDO.setGmtCreate(new java.util.Date());
		deviceDO.setAllStorage("a");
		deviceDO.setCurStorage("a");
		deviceDO.setDeviceNumber("a");
        int id = deviceDAO.create(deviceDO);
        Assert.assertTrue(id > 0);
        DeviceDO deviceDO_2 = deviceDAO.queryById(id);
        // TODO finish update


        deviceDAO.update(deviceDO_2);
        deviceDO_2 = deviceDAO.queryById(id);
        // TODO finish your asserts

    }


    public void setDeviceDAO(DeviceDAO deviceDAO) {
		this.deviceDAO = deviceDAO;
	}

}
