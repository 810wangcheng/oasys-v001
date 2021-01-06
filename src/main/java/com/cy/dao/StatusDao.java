package com.cy.dao;

import com.cy.entity.system.SystemStatusList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Administrator
 */
public interface StatusDao extends JpaRepository<SystemStatusList,Long> {

    List<SystemStatusList> findByStatusModel(String statusModel);

    /**
     * 根据状态名称或者状态模型进行查询
     * @param name
     * @param name1
     * @return
     */
    List<SystemStatusList> findByStatusNameLikeOrStatusModelLike(String name, String name1);

    /**
     * 根据状态模型和状态名称确定唯一状态
     * @param aoa_plan_list
     * @param type
     * @return
     */
    SystemStatusList findByStatusModelAndStatusName(String aoa_plan_list, String type);
}
