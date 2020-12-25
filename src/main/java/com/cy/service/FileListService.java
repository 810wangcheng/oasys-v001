package com.cy.service;

import com.cy.dao.FileListDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class FileListService {

    @Autowired
    private FileListDao fileListDao;

    public Long getCount() {
        return fileListDao.count();
    }
}
