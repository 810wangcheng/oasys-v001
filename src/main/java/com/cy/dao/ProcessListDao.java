package com.cy.dao;

import com.cy.entity.process.ProcessList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author Administrator
 */
public interface ProcessListDao extends PagingAndSortingRepository<ProcessList,Long> {

    /**
     * 根据用户Id查找最后三条进程
     * @param userId
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT *  FROM aoa_process_list  p WHERE p.process_user_id = ?1 ORDER BY p.apply_time DESC LIMIT 0,3 ")
    List<ProcessList> findLastThreeProcessByUserId(Long userId);
}
