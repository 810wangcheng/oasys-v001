package com.cy.dao;

import com.cy.entity.system.SystemStatusList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Administrator
 */
public interface StatusDao extends JpaRepository<SystemStatusList,Long> {

    List<SystemStatusList> findByStatusModel(String statusModel);
}
