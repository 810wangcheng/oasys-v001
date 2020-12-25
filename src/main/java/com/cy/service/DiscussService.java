package com.cy.service;

import com.cy.dao.DiscussDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class DiscussService {

    @Autowired
    private DiscussDao discussDao;

    public Long getDiscussCount(){
        return discussDao.count();
    }
}
