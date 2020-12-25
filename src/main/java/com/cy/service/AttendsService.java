package com.cy.service;

import com.cy.dao.AttendsDao;
import com.cy.entity.attendce.Attends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class AttendsService {

    @Autowired
    private AttendsDao attendsDao;

    public Attends findLastAttends(String nowDate, Long userId) {
        return attendsDao.findLastAttends(nowDate,userId);
    }
}
