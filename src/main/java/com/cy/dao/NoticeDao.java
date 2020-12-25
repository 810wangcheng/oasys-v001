package com.cy.dao;

import com.cy.entity.notice.NoticesList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 */
public interface NoticeDao extends JpaRepository<NoticesList,Long> {
}
