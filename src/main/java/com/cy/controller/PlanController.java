package com.cy.controller;

import com.cy.common.StringtoDate;
import com.cy.common.utils.BindingResultUtils;
import com.cy.common.utils.MapToListUtils;
import com.cy.common.vo.ResultEnum;
import com.cy.common.vo.ResultVO;
import com.cy.entity.note.Attachment;
import com.cy.entity.plan.Plan;
import com.cy.entity.system.SystemStatusList;
import com.cy.entity.system.SystemTypeList;
import com.cy.entity.user.User;
import com.cy.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/")
public class PlanController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private PlanService planService;

    @Autowired
    private SysTypeListService typeListService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private FileListService fileListService;

    //格式转换
    DefaultConversionService conversionService = new DefaultConversionService();

    @RequestMapping(value = "planview",method = RequestMethod.GET)
    public String planView(Model model, HttpSession session, @RequestParam(value = "page",defaultValue = "0") int page,
                           @RequestParam(value = "baseKey",required = false) String baseKey,
                           @RequestParam(value = "type",required = false) String type,
                           @RequestParam(value = "status",required = false) String status,
                           @RequestParam(value = "time",required = false) String time,
                           @RequestParam(value = "icon",required = false) String icon){
        logger.info("baseKey:"+baseKey);
        sortPaging(model,session,page,baseKey,type,status,time,icon);
        return "plan/planview";
    }

    /**
     * 新增修改，对计划进行编辑
     * @return
     */
    @RequestMapping(value = "planedit",method = RequestMethod.GET)
    public String planEdit(HttpServletRequest request,Model model){
        Long pid = Long.valueOf(request.getParameter("pid"));

        //检验表单数据重新进入编辑页面
        if (!StringUtils.isEmpty(request.getAttribute("errormess"))){
            request.setAttribute("errormess",request.getAttribute("errormess"));
            request.setAttribute("plan",request.getAttribute("plan2"));
        }else if (!StringUtils.isEmpty(request.getAttribute("success"))){
            request.setAttribute("success",request.getAttribute("success"));
            request.setAttribute("plan",request.getAttribute("plan2"));
        }

        //pid为-1表示新建
        if (pid == -1){
            model.addAttribute("plan",null);
            model.addAttribute("pid",pid);
        }else if (pid > 0){
            //表示编辑
            Plan plan = planService.findById(pid);
            model.addAttribute("plan",plan);
            model.addAttribute("pid",pid);
        }
        setTypeAndStatus(model);
        return "plan/planedit";
    }

    @RequestMapping("plansave")
    public String doPlanSave(@RequestParam("file") MultipartFile file, HttpServletRequest request, @Valid Plan plan, BindingResult bindingResult) throws IOException {
        conversionService.addConverter(new StringtoDate());
        // 格式化开始日期和结束日期
        Date start = conversionService.convert(plan.getStartTime(), Date.class);
        Date end = conversionService.convert(plan.getEndTime(), Date.class);
        Attachment att = null;
        Long attId = null;
        Plan plan1 = null;

        HttpSession session = request.getSession();
        //Long.valueOf(session.getAttribute("userId")+"")
        long userId = 1L;
        User user = userService.findUserByUserId(userId);

        //获取到类型和状态Id
        String type = request.getParameter("type");
        String status = request.getParameter("status");
        long typeId = typeListService.findByTypeModelAndTypeName("aoa_plan_list",type).getTypeId();
        long statusId = statusService.findByStatusModelAndStatusName("aoa_plan_list",type).getStatusId();
        long pid = Long.valueOf(request.getParameter("pid")+"");

        //对绑定结果进行校验
        ResultVO resultVO = BindingResultUtils.hasError(bindingResult);
        if (!ResultEnum.SUCCESS.getCode().equals(resultVO.getCode())){
            List<Object> list = new MapToListUtils<Object>().mapToList(resultVO.getData());
            request.setAttribute("errormess",list.get(0).toString());
        }
        else {
            if (!StringUtils.isEmpty(session.getAttribute("getId"))){
                logger.info("验证通过");
            }

            //表示计划为新建状态
            if (pid == -1){
                //附件不为空
                if (!file.isEmpty()){
                    att = (Attachment) fileListService.saveFile(file,user,null,false);
                    attId = att.getAttachmentId();
                }else if (file.isEmpty()){
                    attId = null;
                }
                plan1 = new Plan(typeId,statusId,attId,start,end,new Date(),plan.getTitle(),plan.getLabel(),plan.getPlanContent(),plan.getPlanSummary(),null,user);
                planService.savePlan(plan1);
            }

            //表示修改
            if (pid > 0){
                 plan1 = planService.findById(pid);
                 //附件为空，并且上传了附件
                 if (plan1.getAttachId() == null){
                     if (!file.isEmpty()){
                         att = (Attachment) fileListService.saveFile(file,user,null,false);
                         attId = att.getAttachmentId();
                         plan1.setAttachId(attId);
                         planService.savePlan(plan1);
                     }
                 }
                 //附件不为空，对附件和计划进行修改
                 if (plan1.getAttachId() != null){
                     fileListService.updateAtt(file,user,null,plan.getAttachId());
                     planService.updatePlan(typeId,statusId,start,end,plan.getTitle(),plan.getLabel(),plan.getPlanContent(),plan.getPlanSummary(),pid);
                 }
            }
            request.setAttribute("success","后台验证成功");
        }
        request.setAttribute("plan2",plan);
        return "plan/planedit";
    }

    private void sortPaging(Model model, HttpSession session, int page, String baseKey, String type, String status, String time, String icon) {
        Long userId = 1L;
        //Long.valueOf(session.getAttribute("userId")+"")
        User user = userService.findUserByUserId(userId);
        logger.info("计划分页查询");
        Page<Plan> planPage = planService.doPaging(page,baseKey,userId,type,status,time);
        setTypeAndStatus(model);
        model.addAttribute("plist",planPage.getContent());
        model.addAttribute("page",planPage);
        model.addAttribute("url","planviewtable");
    }

    private void setTypeAndStatus(Model model) {
        logger.info("获取类型和状态信息");
        List<SystemTypeList> typeLists = typeListService.findTypeByTypeModel("aoa_plan_list");
        List<SystemStatusList> statusLists = statusService.findStatusByStatusModel("aoa_plan_list");
        model.addAttribute("typelist",typeLists);
        model.addAttribute("statuslist",statusLists);
    }
}
