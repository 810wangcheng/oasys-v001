package com.cy.dao;

import com.cy.entity.mail.Mailreciver;
import com.cy.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Administrator
 */
public interface MailReciverDao extends JpaRepository<Mailreciver,Long> {
    /**
     * 根据是否读取删除以及接受者id获取邮件接受者
     * @param b
     * @param b1
     * @param userId
     * @return
     */
    List<Mailreciver> findByReadAndDelAndReciverId(boolean b, boolean b1, User userId);
}
