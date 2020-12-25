package com.cy.dao;

import com.cy.entity.user.User;
import com.cy.entity.user.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Administrator
 */
public interface UserLogDao extends JpaRepository<UserLog,Long> {

    /**
     * 根据用户id查询用户日志表中数据
     * @param userId
     * @return
     */
    @Query(nativeQuery = true,value=("select * from aoa_user_log where aoa_user_log.user_id=?1 ORDER BY aoa_user_log.log_time DESC LIMIT 0,13"))
    List<UserLog> findUserLogByUserId(User userId);
}
