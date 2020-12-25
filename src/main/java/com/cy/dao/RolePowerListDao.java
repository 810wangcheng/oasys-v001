package com.cy.dao;

import com.cy.entity.role.Rolemenu;
import com.cy.entity.role.Rolepowerlist;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Administrator
 */
public interface RolePowerListDao extends JpaRepository<Rolepowerlist,Long> {

    /**
     *查询所有父菜单
     * @param l 菜单id
     * @param roleId 角色id
     * @param b 菜单是否显示
     * @param b1 角色是否显示
     * @return
     */
    @Query("select new com.cy.entity.role.Rolemenu(menu.menuId,menu.menuName,menu.menuUrl,menu.show,role.check,menu.parentId,menu.menuIcon,menu.sortId,menu.menuGrade)" +
            "from Rolepowerlist as role,SystemMenu as menu where role.menuId.menuId=menu.menuId and menu.parentId=?1 and role.roleId.roleId=?2 and menu.show=?3 and role.check=?4 order by menu.sortId")
    List<Rolemenu> findParentMenu(Long l, Long roleId, boolean b, boolean b1);

    /**
     * 查询所有子菜单
     * @param l 菜单id
     * @param roleId 角色id
     * @param b 菜单是否显示
     * @param b1 角色是否显示
     * @return
     */
    @Query("select new com.cy.entity.role.Rolemenu(menu.menuId,menu.menuName,menu.menuUrl,menu.show,role.check,menu.parentId,menu.menuIcon,menu.sortId,menu.menuGrade) " +
            "from Rolepowerlist as role,SystemMenu as menu where role.menuId.menuId=menu.menuId and menu.parentId != ?1 and role.roleId.roleId=?2 and menu.show=?3 and role.check=?4 order by menu.sortId")
    List<Rolemenu> findChildMenu(long l, Long roleId, boolean b, boolean b1);
}
