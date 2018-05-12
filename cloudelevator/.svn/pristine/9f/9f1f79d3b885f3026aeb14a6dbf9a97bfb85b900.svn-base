package com.laixusoft.cloudelevator.biz.store.image.impl;


import com.laixusoft.cloudelevator.biz.store.Image;
import com.laixusoft.cloudelevator.biz.store.ImageUtils;
import com.laixusoft.cloudelevator.biz.store.StorageService;
import com.laixusoft.cloudelevator.biz.store.image.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * Created by apple on 14/11/25 下午8:23.
 */
public class ImageServiceImpl implements ImageService {

    private static final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

    private String bucket;

    private String vistorUrl;

    private StorageService storageService;

    @Override
    public Image createImage(byte[] imageData) {
        Image image = ImageUtils.createImage(this.bucket, imageData);
        try {
            storageService.storeImage(image, imageData);
            StringBuilder sb = new StringBuilder(this.getVistorUrl()).append(image.getFid()).append(".").append(image.getFormat());
            image.setUrl(sb.toString());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    @Override
    public void deleteImage(Image image) {
        try {
            storageService.deleteImage(image);
        } catch (Throwable e) {
            log.error("delete image:", e);
        }
    }

    @Override
    public InputStream downloadImage(Image image) {
        try {
            String filename = image.getFid() + "." + image.getFormat();
            return storageService.downloadFile(image.getBucket(), filename);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public String getVistorUrl() {
        return vistorUrl;
    }

    public void setVistorUrl(String vistorUrl) {
        this.vistorUrl = vistorUrl;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }
}
