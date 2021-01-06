package com.cy.controller;

import com.cy.common.utils.BindingResultUtils;
import com.cy.common.utils.MapToListUtils;
import com.cy.common.vo.ResultEnum;
import com.cy.common.vo.ResultVO;
import com.cy.entity.system.SystemStatusList;
import com.cy.service.StatusService;
import com.cy.service.SysTypeListService;
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
import java.awt.*;
import java.util.List;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/")
public class SysStatusController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StatusService statusService;

    @RequestMapping("sysstatus")
    public String showSysStatus(HttpServletRequest request){
        List<SystemStatusList> systemStatusLists =  statusService.findAllSysStatus();
        request.setAttribute("statusList",systemStatusLists);
        return "systemcontrol/statusmanage";
    }

    @RequestMapping("statusedit")
    public String sysStatusEdit(HttpServletRequest request){
        if (!StringUtils.isEmpty(request.getParameter("statusid"))){
            Long statusId = Long.parseLong(request.getParameter("statusid"));
            SystemStatusList statusList = statusService.findStatusById(statusId);
            request.setAttribute("status",statusList);
            HttpSession session = request.getSession();
            session.setAttribute("statusid",statusId);
        }
        return "systemcontrol/statusedit";
    }

    @RequestMapping("statuscheck")
    public String testSysStatus(HttpServletRequest request, @Valid SystemStatusList statusList, BindingResult br){
        request.setAttribute("menuObj",statusList);
        logger.info(statusList.toString());
        ResultVO resultVO = BindingResultUtils.hasError(br);
        if(!ResultEnum.SUCCESS.getCode().equals(resultVO.getCode())){
            List<Object> list = new MapToListUtils<Object>().mapToList(resultVO.getData());
            request.setAttribute("errormess",list.get(0).toString());
            logger.info("getData:"+resultVO.getData());
            logger.info("getCode:"+resultVO.getCode());
            logger.info("getMes:"+resultVO.getMsg());
        }else{
            HttpSession session = request.getSession();
            if (!StringUtils.isEmpty(session.getAttribute("statusid"))){
                Long statusId = (Long) session.getAttribute("statusid");
                statusList.setStatusId(statusId);
                session.removeAttribute("statusid");
            }
            statusService.saveSysStatus(statusList);
            request.setAttribute("success","验证成功");
        }
        return "systemcontrol/statusedit";
    }

    @RequestMapping("statustable")
    public String searchStatus(HttpServletRequest request){
        if (!StringUtils.isEmpty(request.getParameter("name"))){
            logger.info("状态管理页面查询输入："+request.getParameter("name"));
            String name = "%"+request.getParameter("name")+"%";
            List<SystemStatusList> statusLists = statusService.findByStatusNameLikeOrStatusModelLike(name,name);
            request.setAttribute("statusList",statusLists);
        }else {
            List<SystemStatusList> allSysStatus = statusService.findAllSysStatus();
            request.setAttribute("statusList",allSysStatus);
        }
        return "systemcontrol/statustable";
    }
    @RequestMapping("deletestatus")
    public String deleteSysStatus(HttpServletRequest request){
        Long statusId = Long.parseLong(request.getParameter("id"));
        statusService.deleteSysStatusById(statusId);
        return "forward:/sysstatus";
    }
}
