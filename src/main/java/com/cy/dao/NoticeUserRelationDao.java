package com.cy.dao;

import com.cy.entity.notice.NoticeUserRelation;
import com.cy.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Administrator
 */
public interface NoticeUserRelationDao extends JpaRepository<NoticeUserRelation,Long> {
    /**
     * 根据是否读取通知以及用户id获取通知用户关系表中数据
     * @param read
     * @param userId
     * @return
     */
    List<NoticeUserRelation> findByReadAndUserId(Boolean read, User userId);
}
