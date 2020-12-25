package com.cy.dao;

import com.cy.entity.task.Taskuser;
import com.cy.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Administrator
 */
public interface TaskUserDao extends JpaRepository<Taskuser,Long> {

    /**
     * 根据用户id和状态id查询任务用户
     * @param userId
     * @param i
     * @return
     */
    List<Taskuser> findByUserIdAndStatusId(User userId, int i);
}
