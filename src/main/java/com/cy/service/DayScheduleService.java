package com.cy.service;

import com.cy.dao.DayScheduleDao;
import com.cy.dao.UserDao;
import com.cy.entity.schedule.ScheduleList;
import com.cy.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class DayScheduleService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DayScheduleDao dayScheduleDao;

    /**
     * 保存日程
     * @param scheduleList
     * @return
     */
    public ScheduleList saveSchedule(ScheduleList scheduleList){
        return dayScheduleDao.save(scheduleList);
    }

    public List<ScheduleList> findScheduleList(Long userId){
        User user = userDao.findOne(userId);
        List<User> users = new ArrayList<>();
        users.add(user);

        //根据用户查询日程
        List<ScheduleList> allDayScheduleList = dayScheduleDao.findByUsers(users);
       /* List<ScheduleList> oneDayScheduleList = dayScheduleDao.findByUser(user);
        List<ScheduleList> otherDayScheduleList = dayScheduleDao.findByUsers(users);

        for (ScheduleList scheduleList : oneDayScheduleList) {
            allDayScheduleList.add(scheduleList);
        }

        for (ScheduleList scheduleList : otherDayScheduleList) {
            allDayScheduleList.add(scheduleList);
        }*/

        for (ScheduleList scheduleList : allDayScheduleList) {
            User scheduleListUser = scheduleList.getUser();
            scheduleList.setUsername(scheduleListUser.getRealName());
        }
        return  allDayScheduleList;
    }
}
