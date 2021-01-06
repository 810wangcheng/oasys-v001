package com.cy.controller;

import com.cy.common.utils.BindingResultUtils;
import com.cy.common.utils.MapToListUtils;
import com.cy.common.vo.ResultEnum;
import com.cy.common.vo.ResultVO;
import com.cy.entity.system.SystemTypeList;
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
import java.util.List;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/")
public class SysTypeController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysTypeListService typeService;

    /**
     * 查询出所有类型数据
     * @param request
     * @return
     */
    @RequestMapping("systype")
    public String findAllTypeList(HttpServletRequest request){
        List<SystemTypeList> typeLists = typeService.findAllType();
        request.setAttribute("typeList",typeLists);
        return "systemcontrol/typemanage";
    }

    /**
     * 对其中一条类型数据进行修改
     * @param request
     * @return
     */
    //http://localhost:8080/typeedit?typeid=1
    @RequestMapping("typeedit")
    public String editTypeList(HttpServletRequest request){
        //获取请求中的参数
        if (!StringUtils.isEmpty(request.getParameter("typeid"))){
            //通过请求参数查询类型列表数据
            long typeid = Long.parseLong(request.getParameter("typeid"));
            SystemTypeList type = typeService.findTypeListByTypeId(typeid);
            //将查询到的数据封装到请求语中
            request.setAttribute("typeObj",type);
            HttpSession session = request.getSession();
            //将typeId封装到session中
            session.setAttribute("typeid",typeid);
        }
        return "systemcontrol/typeedit";
    }

    /**
     * 系统编辑页面，保存取消按钮对表单数据进行校验
     * @param request 请求
     * @param typeList @Valid注解表示需要对此对象进行校验
     * @param result BindingResult 是springboot里面对传入数据进行校验 对需要校验的数据添加@Valid，此对象为校验结果
     * @return
     */
    @RequestMapping("typecheck")
    public String typeListCheck(HttpServletRequest request, @Valid SystemTypeList typeList, BindingResult result){
        HttpSession session = request.getSession();
        Long typeId = null;
        request.setAttribute("menuObj",typeList);

        ResultVO resultVO = BindingResultUtils.hasError(result);
        if (ResultEnum.ERROR.getCode().equals(resultVO.getCode())){
            List<Object> list = new MapToListUtils<>().mapToList(resultVO.getData());
            //封装错误信息
            request.setAttribute("errormess",list.get(0).toString());
            log.info("页面传入数据："+typeList);
            log.info("错误信息："+list);
            log.info("错误第一条信息："+list.get(0).toString());
            log.info("BindingResult.data:"+resultVO.getData());
            log.info("BindingResult.code:"+resultVO.getCode());
            log.info("BindingResult.message:"+resultVO.getMsg());
        }
        else {
            //利用typeid判断是是否是修改
            if (!StringUtils.isEmpty(session.getAttribute("typeid"))){
                typeId = (Long) session.getAttribute("typeid");
                typeList.setTypeId(typeId);
                log.info("typeId:"+typeId);
                session.removeAttribute("typeid");
            }
            typeService.saveTypeList(typeList);
            request.setAttribute("success","后台验证成功");
        }
        return "systemcontrol/typeedit";
    }

    @RequestMapping("deletetype")
    public String deleteTypeList(HttpServletRequest request){
        String typeListId = request.getParameter("id");
        long typeId = Long.parseLong(typeListId);
        typeService.deleteTypeListByTypeId(typeId);
        return "forward:systype";
    }

    @RequestMapping("typetable")
    public String findTypeListByNameOrModel(HttpServletRequest request){
        List<SystemTypeList> typeLists = null;
        //根据类型名称和类型模块进行查询
        if (!StringUtils.isEmpty(request.getParameter("name"))){
            String nameOrModel = "%"+request.getParameter("name")+"%";
            typeLists = typeService.findTypeListByNameOrModel(nameOrModel);
            request.setAttribute("typeList",typeLists);
        }
        //没有输入信息，查询全部
        else {
            typeLists = typeService.findAllType();
            request.setAttribute("typeList",typeLists);
        }
        return "systemcontrol/typetable";
    }
}
