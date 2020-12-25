package com.cy.dao;

import com.cy.entity.process.Notepaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Administrator
 */
public interface NotePaperDao extends JpaRepository<Notepaper,Long> {

    /**
     * 根据用户id查询便签，每页显示五条
     * @param userId
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT * FROM aoa_notepaper n WHERE n.notepaper_user_id=?1 ORDER BY n.create_time DESC  LIMIT 0,5")
    List<Notepaper> findByUserIdOrderByCreateTimeDesc(long userId);
}
