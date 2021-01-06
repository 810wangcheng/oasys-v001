package com.cy.dao;

import com.cy.entity.system.SystemMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Administrator
 */
public interface SysMenuDao extends JpaRepository<SystemMenu,Long> {

    /**
     * 根据菜单id查询一级菜单,并根据sortId进行排序
     * @param l
     * @return
     */
    List<SystemMenu> findByParentIdOrderBySortId(long l);

    /**
     * 查询二级菜单
     * @param l
     * @return
     */
    List<SystemMenu> findByParentIdNotOrderBySortId(long l);

    /**
     * 根据名称进行查询
     * @param name
     * @return
     */
    SystemMenu findByMenuName(String name);
}
