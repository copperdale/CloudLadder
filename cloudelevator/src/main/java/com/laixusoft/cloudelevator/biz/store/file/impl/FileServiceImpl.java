package com.laixusoft.cloudelevator.biz.store.file.impl;


import com.laixusoft.cloudelevator.biz.store.StorageService;
import com.laixusoft.cloudelevator.biz.store.file.FileService;

import java.io.InputStream;

/**
 * Created by apple on 14/11/25 下午8:22.
 */
public class FileServiceImpl implements FileService {

    private String bucket;

    private String vistorUrl;

    private StorageService storageService;

    @Override
    public String createFile(String fileName, byte[] fileByte) {
        try {
            storageService.storeFile(bucket, fileName, fileByte);
            return vistorUrl + fileName;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public InputStream downloadFile(String fileName) {
        try {
            return storageService.downloadFile(bucket, fileName);
        } catch (Exception e) {
            return null;
        }
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getVistorUrl() {
        return vistorUrl;
    }

    public void setVistorUrl(String vistorUrl) {
        this.vistorUrl = vistorUrl;
    }

    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }
}
