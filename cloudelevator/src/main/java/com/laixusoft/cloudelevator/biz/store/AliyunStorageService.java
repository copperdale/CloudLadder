/**
 *
 */
package com.laixusoft.cloudelevator.biz.store;


import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.GetObjectRequest;
import com.aliyun.openservices.oss.model.OSSObject;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PutObjectResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author damon
 */
public class AliyunStorageService implements StorageService {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private OSSClient ossClient;

    public void init() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    @Override
    public void storeImage(Image image, byte[] imageBytes) throws IOException {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(imageBytes.length);
        meta.setContentType(ImageUtils.getImageType(imageBytes));
        String filename = image.getFid() + "." + image.getFormat();
        InputStream inputStream = new ByteArrayInputStream(imageBytes);
        PutObjectResult putObject = ossClient.putObject(image.getBucket(), filename,
                inputStream, meta);
    }

    @Override
    public void deleteImage(Image image) throws IOException {
        String filename = image.getFid() + "." + image.getFormat();
        ossClient.deleteObject(image.getBucket(), filename);

    }

    @Override
    public void storeFile(String bucket, String fileName, byte[] fileBytes) throws IOException {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(fileBytes.length);
        InputStream inputStream = new ByteArrayInputStream(fileBytes);
        PutObjectResult putObject = ossClient.putObject(bucket, fileName,
                inputStream, meta);
    }

    @Override
    public InputStream downloadFile(String bucket, String fileName) throws IOException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, fileName);
        OSSObject ossObject = ossClient.getObject(getObjectRequest);
        if (ossObject != null) {
            return ossObject.getObjectContent();
        } else {
            return null;
        }
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
}
