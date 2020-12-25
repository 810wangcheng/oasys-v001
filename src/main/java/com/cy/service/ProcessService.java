package com.cy.service;

import com.cy.dao.NotePaperDao;
import com.cy.dao.ProcessListDao;
import com.cy.entity.process.Notepaper;
import com.cy.entity.process.ProcessList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class ProcessService {

    @Autowired
    private NotePaperDao notePaperDao;

    @Autowired
    private ProcessListDao processListDao;

    public List<Notepaper> findNotePaperListByUserId(Long userId){
       return notePaperDao.findByUserIdOrderByCreateTimeDesc(userId);
    }

    public List<ProcessList> findLastThreeProcessList(Long userId) {
        return processListDao.findLastThreeProcessByUserId(userId);
    }
}
