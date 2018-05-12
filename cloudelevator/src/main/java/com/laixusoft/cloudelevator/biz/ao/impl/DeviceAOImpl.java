package com.laixusoft.cloudelevator.biz.ao.impl;

import com.laixusoft.cloudelevator.biz.ao.DeviceAO;
import com.laixusoft.cloudelevator.biz.dal.dao.DeviceDAO;
import com.laixusoft.cloudelevator.biz.dal.dao.DeviceMediaDAO;
import com.laixusoft.cloudelevator.biz.dal.dao.MediaDAO;
import com.laixusoft.cloudelevator.biz.dal.domain.DeviceDO;
import com.laixusoft.cloudelevator.biz.dal.domain.DeviceMediaDO;
import com.laixusoft.cloudelevator.biz.dal.domain.MediaDO;
import com.laixusoft.cloudelevator.biz.dto.DeviceDto;
import com.laixusoft.cloudelevator.biz.dto.DeviceMediaDto;
import com.laixusoft.cloudelevator.biz.dto.MediaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wint.help.biz.query.BaseQuery;
import wint.help.biz.result.Result;
import wint.help.biz.result.ResultSupport;
import wint.help.biz.result.StringResultCode;
import wint.lang.utils.StringUtil;
import wint.mvc.flow.FlowData;

import java.util.ArrayList;
import java.util.List;


/**
 * this file is auto generate.
 */
public class DeviceAOImpl implements DeviceAO {

    private static final Logger log = LoggerFactory.getLogger(DeviceAOImpl.class);

    private DeviceDAO deviceDAO;

    private MediaDAO mediaDAO;

    private DeviceMediaDAO deviceMediaDAO;

    @Override
    public Result updateDevice(DeviceDO deviceDO) {
        Result result = new ResultSupport(false);
        try {
            // TODO add your biz code here

            int id = deviceDO.getId();
            DeviceDO deviceIndb = deviceDAO.queryById(id);
            if (deviceIndb == null) {
                // here replace your result code like:
                // result.setResultCode(DeviceDOResultCodes.DEVICE_NOT_EXIST);
                result.setResultCode(new StringResultCode("device not exist"));
                return result;
            }
            deviceIndb.setAndroidClientId(deviceDO.getAndroidClientId());
            deviceIndb.setDeviceName(deviceDO.getDeviceName());
            deviceIndb.setAllStorage(deviceDO.getAllStorage());
            deviceIndb.setCurStorage(deviceDO.getCurStorage());
            deviceIndb.setDeviceNumber(deviceDO.getDeviceNumber());

            deviceDAO.update(deviceIndb);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("createDevice error", e);
        }
        return result;
    }

    @Override
    public Result createDevice(DeviceDto dto) {
        Result result = new ResultSupport(false);
        try {
            if (StringUtil.isEmpty(dto.getDeviceName())) {
                result.setResultCode(new StringResultCode("设备名称不能为空"));
                return result;
            }

            if (StringUtil.isEmpty(dto.getDeviceNumber())) {
                result.setResultCode(new StringResultCode("设备编号不能为空"));
                return result;
            }

            DeviceDO deviceDO = new DeviceDO();
            deviceDO.setDeviceNumber(dto.getDeviceNumber());
            deviceDO.setDeviceName(dto.getDeviceName());
            int id = deviceDAO.create(deviceDO);
            result.getModels().put("id", id);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("createDevice error", e);
        }
        return result;
    }

    @Override
    public Result viewDevice(int id) {
        Result result = new ResultSupport(false);
        try {
            DeviceDO deviceDO = deviceDAO.queryById(id);
            if (deviceDO == null) {
                result.setResultCode(new StringResultCode("device not exist"));
                return result;
            }

            List<DeviceMediaDO> deviceMediaDOs = deviceMediaDAO.queryByDeviceId(id);

            List<DeviceMediaDto> medias = new ArrayList<DeviceMediaDto>();
            for (DeviceMediaDO deviceMediaDO : deviceMediaDOs) {
                MediaDO mediaDO = mediaDAO.queryById(deviceMediaDO.getMediaId());
                DeviceMediaDto deviceMediaDto=new DeviceMediaDto();
                deviceMediaDto.setId(deviceMediaDO.getId());
                deviceMediaDto.setMediaId(deviceMediaDO.getMediaId());
                deviceMediaDto.setDeviceId(deviceMediaDO.getDeviceId());
                deviceMediaDto.setTitle(mediaDO.getTitle());
                deviceMediaDto.setUrl(mediaDO.getUrl());
                deviceMediaDto.setFileSize(com.laixusoft.cloudelevator.biz.common.utils.StringUtil.convertFileSize(mediaDO.getFileSize()));
                medias.add(deviceMediaDto);
            }

            result.getModels().put("device", deviceDO);
            result.getModels().put("medias", medias);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("viewDevice error", e);
        }
        return result;
    }

    @Override
    public Result viewDeviceForEdit(int id) {
        Result result = new ResultSupport(false);
        try {
            DeviceDO deviceDO = deviceDAO.queryById(id);
            result.getModels().put("device", deviceDO);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("viewDeviceForEdit error", e);
        }
        return result;
    }

    @Override
    public Result viewList(BaseQuery query) {
        Result result = new ResultSupport(false);
        try {
            // TODO add your biz code here

            List<DeviceDO> devices = deviceDAO.queryForPage(query);

            result.getModels().put("devices", devices);
            result.getModels().put("query", query);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("viewList error", e);
        }
        return result;
    }

    @Override
    public Result deleteDevice(int id) {
        Result result = new ResultSupport(false);
        try {
            deviceDAO.delete(id);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("deleteDevice error", e);
        }
        return result;
    }

    @Override
    public Result deleteDeviceMedia(int id) {
        Result result = new ResultSupport(false);
        try {
            deviceMediaDAO.delete(id);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("deleteDeviceMedia error", e);
        }
        return result;
    }

    @Override
    public Result saveDeviceMedia(FlowData flowData){
        Result result = new ResultSupport(false);
        try {
            String medias=flowData.getParameters().getString("medias");
            int id=flowData.getParameters().getInt("id");

            DeviceDO deviceDO = deviceDAO.queryById(id);
            if (deviceDO == null) {
                result.setResultCode(new StringResultCode("device not exist"));
                return result;
            }

            List<DeviceMediaDO> deviceMediaDOs=deviceMediaDAO.queryByDeviceId(id);
            for(DeviceMediaDO deviceMediaDO:deviceMediaDOs){
                deviceMediaDAO.delete(deviceMediaDO.getId());
            }

            List<String> ids=StringUtil.splitTrim(medias,",");
            for(String mediaId:ids){
                DeviceMediaDO deviceMediaDO=new DeviceMediaDO();
                deviceMediaDO.setDeviceId(id);
                deviceMediaDO.setMediaId(Integer.parseInt(mediaId));
                deviceMediaDO.setPlay(true);
                deviceMediaDAO.create(deviceMediaDO);
            }

            result.setSuccess(true);
        } catch (Exception e) {
            log.error("saveDeviceMedia error", e);
        }
        return result;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    public void setMediaDAO(MediaDAO mediaDAO) {
        this.mediaDAO = mediaDAO;
    }

    public void setDeviceMediaDAO(DeviceMediaDAO deviceMediaDAO) {
        this.deviceMediaDAO = deviceMediaDAO;
    }
}