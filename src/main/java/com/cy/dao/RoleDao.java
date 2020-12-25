package com.cy.dao;

import com.cy.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 */
public interface RoleDao extends JpaRepository<Role,Long> {
}
