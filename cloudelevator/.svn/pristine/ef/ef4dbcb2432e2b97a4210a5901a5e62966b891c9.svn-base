/**
 * 
 */
package com.laixusoft.cloudelevator.biz.store;


import java.io.IOException;
import java.io.InputStream;

/**
 * @author damon
 *
 */
public interface StorageService {

    void storeImage(Image image, byte[] imageBytes) throws IOException;

    void deleteImage(Image image) throws IOException;

    void storeFile(String bucket, String fileName, byte[] fileBytes) throws IOException;

    InputStream downloadFile(String bucket, String fileName) throws IOException;

}
