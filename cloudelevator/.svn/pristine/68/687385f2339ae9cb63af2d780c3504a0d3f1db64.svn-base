package com.laixusoft.cloudelevator.biz.ao.impl;

import com.laixusoft.cloudelevator.biz.ao.MediaAO;
import com.laixusoft.cloudelevator.biz.common.utils.FileUtil;
import com.laixusoft.cloudelevator.biz.dal.dao.MediaDAO;
import com.laixusoft.cloudelevator.biz.dal.domain.MediaDO;
import com.laixusoft.cloudelevator.biz.dto.MediaDto;
import com.laixusoft.cloudelevator.biz.query.MediaQuery;
import com.laixusoft.cloudelevator.biz.store.file.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wint.help.biz.query.BaseQuery;
import wint.help.biz.result.Result;
import wint.help.biz.result.ResultSupport;
import wint.help.biz.result.StringResultCode;
import wint.lang.utils.StringUtil;
import wint.mvc.flow.FlowData;
import wint.mvc.form.fileupload.UploadFile;

import java.util.ArrayList;
import java.util.List;


/**
 * this file is auto generate.
 */
public class MediaAOImpl implements MediaAO {

    private static final Logger log = LoggerFactory.getLogger(MediaAOImpl.class);

    private MediaDAO mediaDAO;

    private FileService fileService;

    @Override
    public Result createMedia(FlowData flowData) {
        Result result = new ResultSupport(false);
        try {

            String title = flowData.getParameters().getString("title");
            if (StringUtil.isEmpty(title)) {
                result.setResultCode(new StringResultCode("请填写视频名称!"));
                return result;
            }

            UploadFile uploadFile = flowData.getParameters().getUploadFile("fileUrl");
            if (uploadFile == null || StringUtil.isEmpty(uploadFile.getName())) {
                result.setResultCode(new StringResultCode("请选择上传的文件!"));
                return result;
            }

            String visitorUrl = fileService.createFile(uploadFile.getName(), FileUtil.inputStreamToByte(uploadFile.getInputStream()));

            if (StringUtil.isEmpty(visitorUrl)) {
                result.setResultCode(new StringResultCode("上传失败!"));
                return result;
            }

            MediaDO mediaDO = new MediaDO();
            mediaDO.setTitle(title);
            mediaDO.setUrl(visitorUrl);
            mediaDO.setFileSize((int) uploadFile.getSize());

            mediaDAO.create(mediaDO);

            result.setSuccess(true);
        } catch (Exception e) {
            log.error("createMedia error", e);
        }
        return result;
    }

    @Override
    public Result viewMedia(int id) {
        Result result = new ResultSupport(false);
        try {
            // TODO add your biz code here

            MediaDO mediaDO = mediaDAO.queryById(id);
            if (mediaDO == null) {
                // here replace your result code like:
                // result.setResultCode(MediaDOResultCodes.MEDIA_NOT_EXIST);
                result.setResultCode(new StringResultCode("media not exist"));
                return result;
            }

            result.getModels().put("media", mediaDO);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("viewMedia error", e);
        }
        return result;
    }

    @Override
    public Result viewMediaForEdit(int id) {
        Result result = new ResultSupport(false);
        try {

            if (id > 0) {
                MediaDO mediaDO = mediaDAO.queryById(id);
                result.getModels().put("media", mediaDO);
            }

            result.setSuccess(true);
        } catch (Exception e) {
            log.error("viewMediaForEdit error", e);
        }
        return result;
    }

    @Override
    public Result viewList(MediaQuery query) {
        Result result = new ResultSupport(false);
        try {
            List<MediaDO> medias = mediaDAO.queryForPage(query);

            List<MediaDto> mediaDtos=new ArrayList<MediaDto>();
            for(MediaDO mediaDO:medias){
                MediaDto mediaDto=new MediaDto();
                mediaDto.setId(mediaDO.getId());
                mediaDto.setTitle(mediaDO.getTitle());
                mediaDto.setUrl(mediaDO.getUrl());
                mediaDto.setFileSize(com.laixusoft.cloudelevator.biz.common.utils.StringUtil.convertFileSize(mediaDO.getFileSize()));
                mediaDtos.add(mediaDto);
            }

            result.getModels().put("medias", mediaDtos);
            result.getModels().put("query", query);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("viewList error", e);
        }
        return result;
    }

    @Override
    public Result deleteMedia(int id) {
        Result result = new ResultSupport(false);
        try {
            mediaDAO.delete(id);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("deleteMedia error", e);
        }
        return result;
    }

    public void setMediaDAO(MediaDAO mediaDAO) {
        this.mediaDAO = mediaDAO;
    }

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }
}