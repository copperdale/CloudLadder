package com.laixusoft.cloudelevator.biz.store.image;


import com.laixusoft.cloudelevator.biz.store.Image;

import java.io.InputStream;

/**
 * Created by apple on 14/11/25 下午8:21.
 */
public interface ImageService {

    Image createImage(byte[] imageData);

    void deleteImage(Image image);

    InputStream downloadImage(Image image);
}
