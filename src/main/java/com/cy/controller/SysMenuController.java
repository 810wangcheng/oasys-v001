package com.cy.controller;

import com.cy.common.utils.BindingResultUtils;
import com.cy.common.utils.MapToListUtils;
import com.cy.common.vo.ResultEnum;
import com.cy.common.vo.ResultVO;
import com.cy.entity.role.Role;
import com.cy.entity.role.Rolepowerlist;
import com.cy.entity.system.SystemMenu;
import com.cy.service.SysMenuService;
import com.cy.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * 菜单管理
 * @author Administrator
 */
@Controller
@RequestMapping("/")
public class SysMenuController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private SysRoleService roleService;

    @RequestMapping("sysmenu")
    public String findAllMenu(HttpServletRequest request){
        menuService.findSysMenu(request);
        return "systemcontrol/menumanage";
    }

    @RequestMapping("menuedit")
    public String editMenu(HttpServletRequest request){
        //页面点击保存返回编辑页面
        if (!StringUtils.isEmpty(request.getParameter("errormess"))){
            request.setAttribute("errormess",request.getParameter("errormess"));
        }
        if (!StringUtils.isEmpty(request.getParameter("success"))){
            request.setAttribute("success",request.getParameter("success"));
        }

        //展示父级菜单
        List<SystemMenu> parentMenus = menuService.findParentMenus(0L);
        request.setAttribute("parentList",parentMenus);
        HttpSession session = request.getSession();
        session.removeAttribute("getId");

        //id参数不为空表示修改，否则属于新增
        if (!StringUtils.isEmpty(request.getParameter("id"))){
            long menuId = Long.parseLong(request.getParameter("id"));
            SystemMenu menu = menuService.findSysMenuById(menuId);
            //有add参数表示二级菜单添加
            if (!StringUtils.isEmpty(request.getParameter("add"))){
                Long meId = menu.getMenuId();
                String menuName = menu.getMenuName();
                request.setAttribute("getAdd",meId);
                request.setAttribute("getName",menuName);
                log.info("getAdd:{}"+meId);
            }

            session.setAttribute("getId",menuId);
            log.info("getId:{}"+menuId);
            request.setAttribute("menuObj",menu);
        }
        return "systemcontrol/menuedit";
    }

    /**
     * 对菜单表单中的数据进行校验
     * @param request
     * @param menu
     * @param result
     * @return
     */
    @RequestMapping("test111")
    public String saveSysMenu(HttpServletRequest request, @Valid SystemMenu menu, BindingResult result){
        HttpSession session = request.getSession();
        request.setAttribute("menuObj",menu);
        Long menuId = null;

        //对校验数据的绑定结果进行校验
        ResultVO resultVO = BindingResultUtils.hasError(result);
        //如果校验出错
        if (ResultEnum.ERROR.getCode().equals(resultVO.getCode())){
            List<Object> list = new MapToListUtils().mapToList(resultVO.getData());
            request.setAttribute("errormess",list.get(0).toString());
            log.info("resultVo:{data:"+resultVO.getData()+",code:"+resultVO.getCode()+",msg:"+resultVO.getMsg()+"}");
        }
        //校验通过，进行相关业务处理
        else{
            //表示修改页面提交数据
            if (!StringUtils.isEmpty(session.getAttribute("getId"))){
                menuId = (long) session.getAttribute("getId");
                menu.setMenuId(menuId);
                request.removeAttribute("getId");
                log.info("修改页面传入id:"+menuId);
                menuService.saveSysMenu(menu);
            }
            //菜单第一次提交
            else {
                menuService.saveSysMenu(menu);
                //将菜单分配给所有角色
                List<Role> roleList = roleService.findAllRole();
                for (Role role : roleList) {
                    //超级管理员所有菜单权限
                    if (role.getRoleId() == 1){
                        roleService.saveRolePowerList(new Rolepowerlist(role,menu,true));
                    }
                    else {
                        roleService.saveRolePowerList(new Rolepowerlist(role,menu,false));
                    }
                }

            }
            request.setAttribute("success","保存菜单，后台校验成功");
        }
        log.info("保存菜单为："+menu);
        return "forward:/menuedit";
    }

    @RequestMapping("menutable")
    public String doFindMenuByName(HttpServletRequest request){
        if (!StringUtils.isEmpty(request.getParameter("name"))){
            String name = request.getParameter("name");
            log.info("菜单页面查询输入的名称为："+name);
            SystemMenu menu = menuService.findSysMenuByName(name);
            request.setAttribute("oneMenuAll",menu);
        }else{
            menuService.findSysMenu(request);
        }
        return "systemcontrol/menutable";
    }

    @RequestMapping("deletethis")
    public String deleteSysMenu(HttpServletRequest request){
        long menuId = Long.parseLong(request.getParameter("id"));
        menuService.deleteSysMenuById(menuId);
        log.info("delete menu OK");
        return "forward:/sysmenu";
    }
}
