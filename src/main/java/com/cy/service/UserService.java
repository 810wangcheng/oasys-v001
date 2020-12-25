package com.cy.service;

import com.cy.dao.TaskUserDao;
import com.cy.dao.UserDao;
import com.cy.dao.UserLogDao;
import com.cy.entity.task.Taskuser;
import com.cy.entity.user.User;
import com.cy.entity.user.UserLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TaskUserDao taskUserDao;

    @Autowired
    private UserLogDao userLogDao;

    public User findUserByUserId(Long userId){
        return userDao.findOne(userId);
    }

    public List<Taskuser> findTaskUserByUserIdAndStatusId(User user, int i) {
        return taskUserDao.findByUserIdAndStatusId(user,i);
    }

    public List<UserLog> findUserLogByUserId(User userId) {
        return userLogDao.findUserLogByUserId(userId);
    }
}
