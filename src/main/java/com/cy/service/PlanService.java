package com.cy.service;

import com.cy.dao.PlanDao;
import com.cy.entity.plan.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
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

    /**
     * 分页查询计划
     * @param page
     * @param baseKey
     * @param userId
     * @param type
     * @param status
     * @param time
     * @return
     */
    public Page<Plan> doPaging(int page, String baseKey, Long userId, String type, String status, String time) {
        Pageable pageRequest = new PageRequest(page, 10);
        if (!StringUtils.isEmpty(baseKey)){
            return planDao.findByBaseKey(baseKey,userId,pageRequest);
        }
        //类型
        if (!StringUtils.isEmpty(type)){
            if (type.toString().equals("0")){
                return planDao.findByUserOrderByTypeIdDesc(userId,pageRequest);
            }else {
                return planDao.findByUserOrderByTypeIdAsc(userId,pageRequest);
            }
        }
        //状态
        if (!StringUtils.isEmpty(status)){
            if (status.toString().equals("0")){
                return planDao.findByUserOrderByStatusIdDesc(userId,pageRequest);
            }else {
                return planDao.findByUserOrderByStatusIdAsc(userId,pageRequest);
            }
        }
        //时间
        if (!StringUtils.isEmpty(time)){
            if (time.toString().equals("0")){
                return planDao.findByUserOrderByCreateTimeDesc(userId,pageRequest);
            }else{
                return planDao.findByUserOrderByCreateTimeAsc(userId,pageRequest);
            }
        }

        return planDao.findByUserOrderByCreateTimeDesc(userId,pageRequest);
    }

    public Plan findById(Long pid) {
        return planDao.findOne(pid);
    }

    public void savePlan(Plan plan) {
        planDao.save(plan);
    }

    public Integer updatePlan(long typeId, long statusId, Date start, Date end, String title, String label, String planContent, String planSummary, long pid) {
        return planDao.updatePlan(typeId,statusId,start,end,title,label,planContent,planSummary,pid);
    }
}
