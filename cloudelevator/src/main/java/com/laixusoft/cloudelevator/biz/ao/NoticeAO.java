package com.laixusoft.cloudelevator.biz.ao;

import com.laixusoft.cloudelevator.biz.dto.NoticeDto;
import wint.help.biz.query.BaseQuery;
import wint.help.biz.result.Result;
import com.laixusoft.cloudelevator.biz.dal.domain.NoticeDO;

/**
* this file is auto generate.
*/
public interface NoticeAO {

    Result createNotice(NoticeDto dto);

    Result updateNotice(NoticeDO noticeDO);

    Result viewNotice(int id);

    Result viewNoticeForEdit(int id);

    Result viewList(BaseQuery query);

    Result deleteNotice(int id);

}