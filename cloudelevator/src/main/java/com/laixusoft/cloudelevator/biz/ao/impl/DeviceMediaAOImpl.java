package com.laixusoft.cloudelevator.biz.ao.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wint.help.biz.query.BaseQuery;
import wint.help.biz.result.Result;
import wint.help.biz.result.ResultSupport;
import wint.help.biz.result.StringResultCode;
import com.laixusoft.cloudelevator.biz.dal.domain.DeviceMediaDO;
import com.laixusoft.cloudelevator.biz.ao.DeviceMediaAO;
import com.laixusoft.cloudelevator.biz.dal.dao.DeviceMediaDAO;


/**
* this file is auto generate.
*/
public class DeviceMediaAOImpl implements DeviceMediaAO {

    private static final Logger log = LoggerFactory.getLogger(DeviceMediaAOImpl.class);

    private DeviceMediaDAO deviceMediaDAO;

    @Override
    public Result updateDeviceMedia(DeviceMediaDO deviceMediaDO) {
        Result result = new ResultSupport(false);
        try {
            // TODO add your biz code here

            int id = deviceMediaDO.getId();
            DeviceMediaDO deviceMediaIndb = deviceMediaDAO.queryById(id);
            if (deviceMediaIndb == null) {
                // here replace your result code like:
                // result.setResultCode(DeviceMediaDOResultCodes.DEVICEMEDIA_NOT_EXIST);
                result.setResultCode(new StringResultCode("deviceMedia not exist"));
                return result;
            }
            deviceMediaIndb.setMediaId(deviceMediaDO.getMediaId());
            deviceMediaIndb.setPlay(deviceMediaDO.isPlay());
            deviceMediaIndb.setDeviceId(deviceMediaDO.getDeviceId());
            
            deviceMediaDAO.update(deviceMediaIndb);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("createDeviceMedia error", e);
        }
        return result;
    }

    @Override
    public Result createDeviceMedia(DeviceMediaDO deviceMediaDO) {
        Result result = new ResultSupport(false);
        try {
            // TODO add your biz code here

            int id = deviceMediaDAO.create(deviceMediaDO);
            result.getModels().put("id", id);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("createDeviceMedia error", e);
        }
        return result;
    }

    @Override
    public Result viewDeviceMedia(int id) {
        Result result = new ResultSupport(false);
        try {
            // TODO add your biz code here

            DeviceMediaDO deviceMediaDO = deviceMediaDAO.queryById(id);
            if (deviceMediaDO == null) {
                // here replace your result code like:
                // result.setResultCode(DeviceMediaDOResultCodes.DEVICEMEDIA_NOT_EXIST);
                result.setResultCode(new StringResultCode("deviceMedia not exist"));
                return result;
            }

            result.getModels().put("deviceMedia", deviceMediaDO);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("viewDeviceMedia error", e);
        }
        return result;
    }

    @Override
    public Result viewDeviceMediaForEdit(int id) {
        Result result = new ResultSupport(false);
        try {
            // TODO add your biz code here

            DeviceMediaDO deviceMediaDO = deviceMediaDAO.queryById(id);
            if (deviceMediaDO == null) {
                // here replace your result code like:
                // result.setResultCode(DeviceMediaDOResultCodes.DEVICEMEDIA_NOT_EXIST);
                result.setResultCode(new StringResultCode("deviceMedia not exist"));
                return result;
            }

            result.getModels().put("deviceMedia", deviceMediaDO);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("viewDeviceMediaForEdit error", e);
        }
        return result;
    }

    @Override
    public Result viewList(BaseQuery query) {
        Result result = new ResultSupport(false);
        try {
            // TODO add your biz code here

            List<DeviceMediaDO> deviceMedias = deviceMediaDAO.queryForPage(query);

            result.getModels().put("deviceMedias", deviceMedias);
            result.getModels().put("query", query);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("viewList error", e);
        }
        return result;
    }

    @Override
    public Result deleteDeviceMedia(int id) {
        Result result = new ResultSupport(false);
        try {
            // TODO add your biz code here

            deviceMediaDAO.delete(id);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("deleteDeviceMedia error", e);
        }
        return result;
    }

    public void setDeviceMediaDAO(DeviceMediaDAO deviceMediaDAO) {
        this.deviceMediaDAO = deviceMediaDAO;
    }

}