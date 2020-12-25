package com.cy.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Mapper
public interface NoticeMapper {
    /**
     * 根据用户id查询通知信息
     * @param userId
     * @return
     */
    List<Map<String, Object>> findMyNoticeLimit(@Param("userId") Long userId);
}
