package com.laixusoft.cloudelevator.biz.ao.impl;

import java.util.List;

import com.laixusoft.cloudelevator.biz.dto.NoticeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wint.help.biz.query.BaseQuery;
import wint.help.biz.result.Result;
import wint.help.biz.result.ResultSupport;
import wint.help.biz.result.StringResultCode;
import com.laixusoft.cloudelevator.biz.dal.domain.NoticeDO;
import com.laixusoft.cloudelevator.biz.ao.NoticeAO;
import com.laixusoft.cloudelevator.biz.dal.dao.NoticeDAO;
import wint.lang.utils.StringUtil;


/**
* this file is auto generate.
*/
public class NoticeAOImpl implements NoticeAO {

    private static final Logger log = LoggerFactory.getLogger(NoticeAOImpl.class);

    private NoticeDAO noticeDAO;

    @Override
    public Result updateNotice(NoticeDO noticeDO) {
        Result result = new ResultSupport(false);
        try {
            // TODO add your biz code here

            int id = noticeDO.getId();
            NoticeDO noticeIndb = noticeDAO.queryById(id);
            if (noticeIndb == null) {
                // here replace your result code like:
                // result.setResultCode(NoticeDOResultCodes.NOTICE_NOT_EXIST);
                result.setResultCode(new StringResultCode("notice not exist"));
                return result;
            }
            noticeIndb.setContent(noticeDO.getContent());
            
            noticeDAO.update(noticeIndb);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("createNotice error", e);
        }
        return result;
    }

    @Override
    public Result createNotice(NoticeDto dto) {
        Result result = new ResultSupport(false);
        try {

            if(StringUtil.isEmpty(dto.getContent())){
                result.setResultCode(new StringResultCode("通知内容不能为空"));
                return result;
            }

            NoticeDO noticeDO=new NoticeDO();
            noticeDO.setContent(dto.getContent());
            int id = noticeDAO.create(noticeDO);
            result.getModels().put("id", id);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("createNotice error", e);
        }
        return result;
    }

    @Override
    public Result viewNotice(int id) {
        Result result = new ResultSupport(false);
        try {
            // TODO add your biz code here

            NoticeDO noticeDO = noticeDAO.queryById(id);
            if (noticeDO == null) {
                // here replace your result code like:
                // result.setResultCode(NoticeDOResultCodes.NOTICE_NOT_EXIST);
                result.setResultCode(new StringResultCode("notice not exist"));
                return result;
            }

            result.getModels().put("notice", noticeDO);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("viewNotice error", e);
        }
        return result;
    }

    @Override
    public Result viewNoticeForEdit(int id) {
        Result result = new ResultSupport(false);
        try {
            NoticeDO noticeDO = noticeDAO.queryById(id);

            result.getModels().put("notice", noticeDO);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("viewNoticeForEdit error", e);
        }
        return result;
    }

    @Override
    public Result viewList(BaseQuery query) {
        Result result = new ResultSupport(false);
        try {
            // TODO add your biz code here

            List<NoticeDO> notices = noticeDAO.queryForPage(query);

            result.getModels().put("notices", notices);
            result.getModels().put("query", query);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("viewList error", e);
        }
        return result;
    }

    @Override
    public Result deleteNotice(int id) {
        Result result = new ResultSupport(false);
        try {
            // TODO add your biz code here

            noticeDAO.delete(id);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("deleteNotice error", e);
        }
        return result;
    }

    public void setNoticeDAO(NoticeDAO noticeDAO) {
        this.noticeDAO = noticeDAO;
    }

}