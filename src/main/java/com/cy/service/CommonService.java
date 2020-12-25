package com.cy.service;

import com.cy.dao.DirectorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 收录简单操作dao
 * @author Administrator
 */
@Service
public class CommonService {

    @Autowired
    private DirectorDao directorDao;

    public Long getDirectorCount() {
        return directorDao.count();
    }
}
