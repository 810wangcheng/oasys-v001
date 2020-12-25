package com.cy.service;

import com.cy.dao.NoticeUserRelationDao;
import com.cy.entity.notice.NoticeUserRelation;
import com.cy.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 维护通知用户关系表服务
 * @author Administrator
 */
@Service
public class NoticeUserRelationService {

    @Autowired
    private NoticeUserRelationDao noticeUserRelationDao;

    /**
     * 保存通知用户关系对象
     * @param noticeUserRelation
     * @return
     */
    public NoticeUserRelation saveNoticeUserRe(NoticeUserRelation noticeUserRelation){
        return noticeUserRelationDao.save(noticeUserRelation);
    }

    public List<NoticeUserRelation> findNoticeByReadAndUserId(Boolean read, User user) {
        return noticeUserRelationDao.findByReadAndUserId(read,user);
    }
}
