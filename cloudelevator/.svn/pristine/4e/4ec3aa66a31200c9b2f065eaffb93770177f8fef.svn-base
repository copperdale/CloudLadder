/**
 * 
 */
package com.laixusoft.cloudelevator.biz.store;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import magick.PixelPacket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.*;
import java.util.Date;
import java.util.UUID;

public class ImageUtils {

    private static final Log logger = LogFactory.getLog(ImageUtils.class);

//    static{
//        //不能漏掉这个，不然jmagick.jar的路径找不到
//        System.setProperty("jmagick.systemclassloader","no");
//    }

    public static String getImageType(byte[] bytes) {
        if (isJPEG(bytes)) {
            return "image/jpeg";
        }
        if (isGIF(bytes)) {
            return "image/gif";
        }
        if (isPNG(bytes)) {
            return "image/png";
        }
        if (isBMP(bytes)) {
            return "application/x-bmp";
        }
        return null;
    }

    private static boolean isJPEG(byte[] b) {
        if (b.length < 2) {
            return false;
        }
        return (b[0] == (byte) 0xFF) && (b[1] == (byte) 0xD8);
    }

    private static boolean isGIF(byte[] b) {
        if (b.length < 6) {
            return false;
        }
        return b[0] == 'G' && b[1] == 'I' && b[2] == 'F' && b[3] == '8'
                && (b[4] == '7' || b[4] == '9') && b[5] == 'a';
    }

    private static boolean isPNG(byte[] b) {
        if (b.length < 8) {
            return false;
        }
        return (b[0] == (byte) 137 && b[1] == (byte) 80 && b[2] == (byte) 78
                && b[3] == (byte) 71 && b[4] == (byte) 13 && b[5] == (byte) 10
                && b[6] == (byte) 26 && b[7] == (byte) 10);
    }

    private static boolean isBMP(byte[] b) {
        if (b.length < 2) {
            return false;
        }
        return (b[0] == 0x42) && (b[1] == 0x4d);
    }


    /**
     * 计算平均亮度
     * 
     * @param mi
     * @return
     */
    public static double getAvgLuminance(MagickImage mi) {
        try {
            Dimension dimension = mi.getDimension();
            int width = (int) dimension.getWidth();
            int height = (int) dimension.getHeight();
            double totalLumiance = 0;

            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    PixelPacket onePixel = mi.getOnePixel(w, h);
                    int red = onePixel.getRed() / 255;
                    int green = onePixel.getGreen() / 255;
                    int blue = onePixel.getBlue() / 255;

                    double luminance = 0.299 * red + 0.587 * green + 0.114 * blue;

                    totalLumiance += luminance;
                }
            }
            return totalLumiance / (width * height);
        } catch (Exception e) {
            logger.error("Ops.", e);
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @param bucket
     * @param imageData
     * @return
     */
    public static Image createImage(String bucket, byte[] imageData) {
        MagickImage magickImage = null;
        int width = 0;
        int height = 0;
        String format = null;
        try {
            magickImage = new MagickImage(new ImageInfo(), imageData);
            Dimension dimension = magickImage.getDimension();
            format = magickImage.getImageFormat().toLowerCase();
            width = (int) dimension.getWidth();
            height = (int) dimension.getHeight();
        } catch (MagickException e1) {
            throw new RuntimeException("error", e1);
        } finally {
            if (magickImage != null) {
                magickImage.destroyImages();
            }
        }

        Image image = new Image();
        image.setFid(UUID.randomUUID().toString());
        image.setFormat(format);
        image.setWidth(width);
        image.setHeight(height);
        image.setSize(imageData.length);
        image.setCreateTime(new Date());
        image.setStatus(0);
        image.setBucket(bucket);
        return image;
    }
}
