package com.cy.dao;

import com.cy.entity.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Administrator
 */
public interface PlanDao extends JpaRepository<Plan,Long> {

    @Query(nativeQuery = true,value = "SELECT * FROM aoa_plan_list p WHERE p.plan_user_id=?1 ORDER BY p.create_time DESC,p.end_time DESC LIMIT 0,5")
    List<Plan> findByUserLimit(Long userId);
}
