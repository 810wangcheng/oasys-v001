package com.cy.service;

import com.cy.dao.PlanDao;
import com.cy.entity.plan.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class PlanService {

    @Autowired
    private PlanDao planDao;

    public List<Plan> findPlanByUserIdLimit(Long userId) {
        return planDao.findByUserLimit(userId);
    }
}
