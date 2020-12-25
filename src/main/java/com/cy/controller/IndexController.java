package com.cy.controller;

import com.cy.entity.attendce.Attends;
import com.cy.entity.mail.Mailreciver;
import com.cy.entity.notice.NoticeUserRelation;
import com.cy.entity.notice.NoticesList;
import com.cy.entity.plan.Plan;
import com.cy.entity.process.Notepaper;
import com.cy.entity.process.ProcessList;
import com.cy.entity.schedule.ScheduleList;
import com.cy.entity.system.SystemStatusList;
import com.cy.entity.system.SystemTypeList;
import com.cy.entity.task.Taskuser;
import com.cy.entity.user.User;
import com.cy.entity.user.UserLog;
import com.cy.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/")
public class IndexController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private DayScheduleService dayScheduleService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeUserRelationService noticeUserRelationService;

    @Autowired
    private MailService mailService;

    @Autowired
    private FileListService fileListService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private DiscussService discussService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private SysTypeListService sysTypeListService;

    @Autowired
    private AttendsService attendsService;

    @Autowired
    private PlanService planService;

    @Autowired
    private ProcessService processService;

    @RequestMapping("index")
    public String doIndex(HttpServletRequest request, Model model){
        Long userId = 1L;
        User user = userService.findUserByUserId(userId);
        //根据用户和角色获取菜单，在这里只是根据角色设置菜单
        sysMenuService.findMenuSys(request,user);

        List<ScheduleList> scheduleList = dayScheduleService.findScheduleList(userId);
        for (ScheduleList schedule : scheduleList) {
            if (schedule.getIsreminded() != null && !schedule.getIsreminded()){
                logger.debug(schedule.getStartTime()+"");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                String start = dateFormat.format(schedule.getStartTime());
                String now = dateFormat.format(new Date());
                try {
                    long startMills = dateFormat.parse(start).getTime();
                    long nowMills = dateFormat.parse(now).getTime();
                    long differMills = nowMills - startMills;
                    if (differMills > 0 && differMills < 86400000){
                        //创建通知对象
                        NoticesList noticesList = new NoticesList();
                        noticesList.setTypeId(11L);
                        noticesList.setStatusId(15L);
                        noticesList.setTitle("您有一个日程即将开始");
                        noticesList.setUrl("/daycalendar");
                        noticesList.setUserId(userId);
                        noticesList.setNoticeTime(new Date());
                        //保存通知
                        NoticesList notice = noticeService.saveNotice(noticesList);
                        //保存通知用户关系
                        noticeUserRelationService.saveNoticeUserRe(new NoticeUserRelation(notice,user,false));
                        schedule.setIsreminded(true);
                        //保存日程
                        dayScheduleService.saveSchedule(schedule);
                    }
                } catch (ParseException e) {
                    logger.debug("获取用户日程出错");
                    e.printStackTrace();
                }
            }
        }
        List<NoticeUserRelation> noticeUserRelations = noticeUserRelationService.findNoticeByReadAndUserId(false,user);
        List<Mailreciver> mailReceiverList = mailService.findMailReciverByReadAndDelAndReciverId(false,false,user);
        List<Taskuser> taskUserList = userService.findTaskUserByUserIdAndStatusId(user,3);
        model.addAttribute("notice",noticeUserRelations.size());
        model.addAttribute("mail",mailReceiverList.size());
        model.addAttribute("task",taskUserList.size());
        model.addAttribute("user",user);
        List<UserLog> userLogList = userService.findUserLogByUserId(user);
        request.setAttribute("userLogList",userLogList);
        return  "index/index";
    }

    @RequestMapping("test2")
    public String test2(HttpSession session,Model model,HttpServletRequest request){
        Long userId = 1L;
        User user = userService.findUserByUserId(userId);
        request.setAttribute("user",user);
        request.setAttribute("filenum",fileListService.getCount());
        request.setAttribute("directornum",commonService.getDirectorCount());
        request.setAttribute("discussnum",discussService.getDiscussCount());

        List<Map<String,Object>> list = noticeService.findMyNoticeLimit(userId);
        model.addAttribute("user",user);
        for (Map<String,Object> map : list){
            map.put("status",statusService.findStatusById((Long) map.get("status_id")).getStatusName());
            map.put("type", sysTypeListService.findTypeById((Long)map.get("type_id")).getTypeName());
            map.put("statusColor",statusService.findStatusById((Long) map.get("status_id")).getStatusColor());
            map.put("userName",userService.findUserByUserId((Long)map.get("user_id")).getUserName());
            map.put("deptName",userService.findUserByUserId((Long)map.get("user_id")).getDept().getDeptName());
        }
        showList(model,userId);
        logger.info("通知"+list);
        model.addAttribute("noticeList",list);

        //列举计划
        List<Plan> planList = planService.findPlanByUserIdLimit(userId);
        model.addAttribute("planList",planList);
        List<SystemTypeList> typeList = sysTypeListService.findTypeByTypeModel("aoa_plan_list");
        List<SystemStatusList> statusList = statusService.findStatusByStatusModel("aoa_plan_list");
        model.addAttribute("ptypelist",typeList);
        model.addAttribute("pstatuslist",statusList);

        //列举便签
        List<Notepaper> notePaperList = processService.findNotePaperListByUserId(userId);
        model.addAttribute("notepaperList",notePaperList);

        //列举几个流程
        List<ProcessList> processLists = processService.findLastThreeProcessList(userId);
        model.addAttribute("processlist",processLists);
        List<SystemStatusList> processStatus = statusService.findStatusByStatusModel("aoa_process_list");
        model.addAttribute("prostatuslist",processStatus);

        return "systemcontrol/control";
    }

    private void showList(Model model, Long userId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = dateFormat.format(new Date());
        Attends attends = attendsService.findLastAttends(nowDate,userId);
        if (attends != null){
            String type = sysTypeListService.findTypeNameById(attends.getAttendsId());
            model.addAttribute("type",type);
        }
        model.addAttribute("alist",attends);
    }
}
