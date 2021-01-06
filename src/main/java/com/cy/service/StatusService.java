package com.cy.service;

import com.cy.dao.StatusDao;
import com.cy.entity.system.SystemStatusList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class StatusService {

    @Autowired
    private StatusDao statusDao;

    public SystemStatusList findStatusById(Long statusId) {
        return statusDao.findOne(statusId);
    }

    public List<SystemStatusList> findStatusByStatusModel(String statusModel) {
        return statusDao.findByStatusModel(statusModel);
    }

    public List<SystemStatusList> findAllSysStatus() {
        return statusDao.findAll();
    }

    public void deleteSysStatusById(Long statusId) {
        statusDao.delete(statusId);
    }

    public SystemStatusList saveSysStatus(SystemStatusList statusList) {
        return statusDao.save(statusList);
    }

    public List<SystemStatusList> findByStatusNameLikeOrStatusModelLike(String name, String name1) {
        return statusDao.findByStatusNameLikeOrStatusModelLike(name,name1);
    }

    public SystemStatusList findByStatusModelAndStatusName(String aoa_plan_list, String type) {
        return statusDao.findByStatusModelAndStatusName(aoa_plan_list,type);
    }
}
