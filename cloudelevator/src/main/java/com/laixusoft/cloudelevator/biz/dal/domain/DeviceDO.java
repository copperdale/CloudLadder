package com.laixusoft.cloudelevator.biz.dal.domain;

/**
 * 版权所有: 杭州睐旭信息科技有限公司
 * Created by User: apple
 * Date: 15/5/19
 * Time: 下午12:46
 */
public class DeviceDO extends BaseDO {

    private String deviceName;//设备名称
    private String deviceNumber;//设备编号
    private String androidClientId;//客户端id
    private String allStorage;//总存储空间
    private String curStorage;//可用存储空间
    private boolean online;//是否在线正常
    private String medias;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getAndroidClientId() {
        return androidClientId;
    }

    public void setAndroidClientId(String androidClientId) {
        this.androidClientId = androidClientId;
    }

    public String getAllStorage() {
        return allStorage;
    }

    public void setAllStorage(String allStorage) {
        this.allStorage = allStorage;
    }

    public String getCurStorage() {
        return curStorage;
    }

    public void setCurStorage(String curStorage) {
        this.curStorage = curStorage;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getMedias() {
        return medias;
    }

    public void setMedias(String medias) {
        this.medias = medias;
    }
}
