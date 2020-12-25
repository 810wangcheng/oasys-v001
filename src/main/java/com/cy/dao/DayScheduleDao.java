package com.cy.dao;

import com.cy.entity.schedule.ScheduleList;
import com.cy.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Administrator
 */
public interface DayScheduleDao extends JpaRepository<ScheduleList,Long> {

    /**
     * 根据用户查询日程
     * @param user
     * @return
     */
    List<ScheduleList> findByUser(User user);

    /**
     * 查询多个用户的日程
     * @param users
     * @return
     */
    List<ScheduleList> findByUsers(List<User> users);
}
