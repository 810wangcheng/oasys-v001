package com.cy.dao;

import com.cy.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 */
public interface UserDao extends JpaRepository<User,Long> {

}
