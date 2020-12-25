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
}
