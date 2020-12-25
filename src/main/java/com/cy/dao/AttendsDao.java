package com.cy.dao;

import com.cy.entity.attendce.Attends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Administrator
 */
public interface AttendsDao extends JpaRepository<Attends,Long> {
    /**
     * 根据当前日期以及用户id查询考勤表中数据
     * @param nowDate
     * @param userId
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT * FROM aoa_attends_list a WHERE DATE_format(a.attends_time,'%Y-%m-%d') like %?1% and a.attends_user_id=?2 ORDER BY a.attends_time DESC LIMIT 1")
    Attends findLastAttends(String nowDate, Long userId);
}
