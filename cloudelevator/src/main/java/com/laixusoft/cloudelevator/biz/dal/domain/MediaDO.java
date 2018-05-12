package com.laixusoft.cloudelevator.biz.dal.domain;

/**
 * 版权所有: 杭州睐旭信息科技有限公司
 * Created by User: apple
 * Date: 15/5/19
 * Time: 下午12:44
 */
public class MediaDO extends BaseDO {

    private String title;//媒体名称
    private String url;//媒体路径
    private long fileSize;//文件大小

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}
