package com.cy.dao;

import com.cy.entity.discuss.Discuss;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 */
public interface DiscussDao extends JpaRepository<Discuss,Long> {
}
