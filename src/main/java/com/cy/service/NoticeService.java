package com.cy.service;

import com.cy.dao.NoticeDao;
import com.cy.dao.NoticeMapper;
import com.cy.entity.notice.NoticesList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service
public class NoticeService {
    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private NoticeMapper noticeMapper;

    public NoticesList saveNotice(NoticesList noticesList){
        return noticeDao.save(noticesList);
    }

    public List<Map<String, Object>> findMyNoticeLimit(Long userId) {
        return noticeMapper.findMyNoticeLimit(userId);
    }
}
