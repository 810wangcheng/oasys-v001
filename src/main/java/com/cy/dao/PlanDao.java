package com.cy.dao;

import com.cy.entity.plan.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
public interface PlanDao extends JpaRepository<Plan,Long> {

    @Query(nativeQuery = true,value = "SELECT * FROM aoa_plan_list p WHERE p.plan_user_id=?1 ORDER BY p.create_time DESC,p.end_time DESC LIMIT 0,5")
    List<Plan> findByUserLimit(Long userId);

    @Query("FROM Plan p WHERE (p.label LIKE %?1% OR p.title LIKE %?1% OR DATE_format(p.createTime,'%Y-%m-%d') LIKE %?1% or " +
            "p.typeId IN (SELECT t.typeId FROM SystemTypeList t WHERE t.typeName like %?1%) or " +
            "p.statusId IN (SELECT s.statusId FROM SystemStatusList s WHERE s.statusName like %?1%)) AND p.user.userId=?2")
    Page<Plan> findByBaseKey(String baseKey, Long userId, Pageable page);

    /**
     * 根据类型降序
     * @param userId
     * @param page
     * @return
     */
    @Query("FROM Plan p WHERE p.user.userId=?1 ORDER BY p.typeId DESC")
    Page<Plan> findByUserOrderByTypeIdDesc(Long userId, Pageable page);

    /**
     * 根据类型升序
     * @param userId
     * @param page
     * @return
     */
    @Query("FROM Plan p WHERE p.user.userId=?1 ORDER BY p.typeId ASC ")
    Page<Plan> findByUserOrderByTypeIdAsc(Long userId, Pageable page);

    /**
     * 根据状态降序
     * @param userId
     * @param page
     * @return
     */
    @Query("FROM Plan p WHERE p.user.userId=?1 ORDER BY  p.statusId DESC")
    Page<Plan> findByUserOrderByStatusIdDesc(Long userId, Pageable page);

    /**
     * 根据状态升序
     * @param userId
     * @param page
     * @return
     */
    @Query("FROM Plan p WHERE p.user.userId=?1 ORDER BY  p.statusId ASC")
    Page<Plan> findByUserOrderByStatusIdAsc(Long userId, Pageable page);

    /**
     * 根据创建时间降序
     * @param userId
     * @param pageRequest
     * @return
     */
    @Query("FROM Plan p WHERE p.user.userId=?1 ORDER BY p.createTime DESC")
    Page<Plan> findByUserOrderByCreateTimeDesc(Long userId, Pageable pageRequest);

    /**
     * 根据创建时间升序
     * @param userId
     * @param pageRequest
     * @return
     */
    @Query("FROM Plan p WHERE p.user.userId=?1 ORDER BY p.createTime ASC ")
    Page<Plan> findByUserOrderByCreateTimeAsc(Long userId, Pageable pageRequest);

    /**
     * 根据计划id对计划进行修改
     * @param typeId
     * @param statusId
     * @param start
     * @param end
     * @param title
     * @param label
     * @param planContent
     * @param planSummary
     * @param pid
     * @return
     */
    @Query("UPDATE Plan p SET p.typeId=?1,p.statusId=?2,p.startTime=?3,p.endTime=?4,p.title=?5,p.label=?6,p.planContent=?7,p.planSummary=?8 WHERE p.planId=?9")
    @Modifying
    Integer updatePlan(long typeId, long statusId, Date start, Date end, String title, String label, String planContent, String planSummary, long pid);
}
