package com.cy.service;

import com.cy.dao.RolePowerListDao;
import com.cy.dao.SysMenuDao;
import com.cy.entity.role.Rolemenu;
import com.cy.entity.system.SystemMenu;
import com.cy.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class SysMenuService {

    @Autowired
    private RolePowerListDao rolePowerListDao;

    @Autowired
    private SysMenuDao sysMenuDao;

    public void findMenuSys(HttpServletRequest request, User user){
        List<Rolemenu> oneMenuAll = rolePowerListDao.findParentMenu(0L, user.getRole().getRoleId(), true, true);
        List<Rolemenu> twoMenuAll = rolePowerListDao.findChildMenu(0L,user.getRole().getRoleId(),true,true);
        request.setAttribute("oneMenuAll",oneMenuAll);
        request.setAttribute("twoMenuAll",twoMenuAll);
    }

    public void findSysMenu(HttpServletRequest request) {
        //一级菜单
        List<SystemMenu> parentMenuList = this.findParentMenus(0L);
        //二级菜单
        List<SystemMenu> childMenuList = sysMenuDao.findByParentIdNotOrderBySortId(0L);
        request.setAttribute("oneMenuAll",parentMenuList);
        request.setAttribute("towMenuAll",childMenuList);
    }


    public SystemMenu findSysMenuById(long menuId) {
        return sysMenuDao.findOne(menuId);
    }

    /**
     * 查询一级菜单
     * @param l
     * @return
     */
    public List<SystemMenu> findParentMenus(long l) {
        return sysMenuDao.findByParentIdOrderBySortId(l);
    }

    /**
     * 保存菜单信息
     * @param menu
     */
    public void saveSysMenu(SystemMenu menu) {
        sysMenuDao.save(menu);
    }

    public void deleteSysMenuById(long menuId) {
        sysMenuDao.delete(menuId);
    }
}
