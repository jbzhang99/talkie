package com.meta.controller.quartz;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.quartz.ServiceException;
import com.meta.quartz.domain.TaskInfo;
import com.meta.quartz.servlet.TaskServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by y747718944 on 2018/2/5
 * 任务调度器控制
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "talkie_manage", description = "系统计时任务计划")
public class TaskManageController extends BaseControllerUtil {

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @GetMapping(value = ServiceUrls.TaskManage.TASK)
    @ApiOperation(value = "获取全部定时任务列表")
    public Result getTaskAll() {
        List<TaskInfo> infos = taskServiceImpl.list();
        return getResultList(infos);
    }

    @PostMapping(value = ServiceUrls.TaskManage.TASK)
    @ApiOperation(value = "新增定时任务")
    public Result addJob(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody
                    TaskInfo info) throws Exception {
        if (info.getId() == 0) {
            taskServiceImpl.addJob(info);
        } else {
            taskServiceImpl.edit(info);
        }
        return success("新增成功");
    }


    @DeleteMapping(value = ServiceUrls.TaskManage.TASK)
    @ApiOperation(value = "删除定时任务")
    public Result deleteJob(
            @RequestParam(value = "jobName", required = false)
                    String jobName,
            @RequestParam(value = "jobGroup", required = false)
                    String jobGroup) {
        try {
            taskServiceImpl.delete(jobName, jobGroup);
        } catch (ServiceException e) {
            return error(e.getMessage());
        }
        return success("删除成功!");

    }

    @PostMapping(value = ServiceUrls.TaskManage.TASK + "/pause")
    @ApiOperation(value = "暂停任务")
    public Result pause(@RequestParam(value = "jobName", required = false)
                                String jobName,
                        @RequestParam(value = "jobGroup", required = false)
                                String jobGroup) {
        try {
            taskServiceImpl.pause(jobName, jobGroup);
        } catch (ServiceException e) {
            return error(e.getMessage());
        }
        return success("暂停成功!");
    }

    @PostMapping(value = ServiceUrls.TaskManage.TASK + "/resume")
    @ApiOperation(value = "重启任务")
    public Result resume(
            @RequestParam(value = "jobName", required = false) String jobName,
            @RequestParam(value = "jobGroup", required = false) String jobGroup) {
        try {
            taskServiceImpl.resume(jobName, jobGroup);
        } catch (ServiceException e) {
            return error(e.getMessage());
        }
        return success("重启成功!");
    }
}
