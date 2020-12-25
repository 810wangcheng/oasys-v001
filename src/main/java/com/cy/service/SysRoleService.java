package com.cy.service;

import com.cy.dao.RoleDao;
import com.cy.dao.RolePowerListDao;
import com.cy.entity.role.Role;
import com.cy.entity.role.Rolepowerlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class SysRoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RolePowerListDao rolePowerListDao;

    public List<Role> findAllRole() {
        return roleDao.findAll();
    }

    public void saveRolePowerList(Rolepowerlist rolepowerlist) {
        rolePowerListDao.save(rolepowerlist);
    }
}
