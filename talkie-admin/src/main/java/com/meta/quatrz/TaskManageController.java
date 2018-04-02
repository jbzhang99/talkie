package com.meta.quatrz;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.feignclient.quatrz.TaskClient;

import com.meta.model.task.TaskInfo;
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
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "任务调度器",description = "系统计时任务计划")
public class TaskManageController extends BaseControllerUtil{

    @Autowired
    private TaskClient taskClient;

    @GetMapping(value = ServiceUrls.TaskManage.TASK)
    @ApiOperation(value = "获取全部定时任务列表")
    public Result getTaskAll(){
      return taskClient.getTaskAll();
    }

    @PostMapping(value = ServiceUrls.TaskManage.TASK)
    @ApiOperation(value = "新增定时任务")
    public Result addJob(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody
                    TaskInfo info){
        try {
             return taskClient.addJob(info);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            return error("添加失败");
        }
    }




    @DeleteMapping(value = ServiceUrls.TaskManage.TASK)
    @ApiOperation(value = "删除定时任务")
    public Result deleteJob(
            @RequestParam(value = "jobName", required = false)
            String jobName,
            @RequestParam(value ="jobGroup", required = false)
            String jobGroup){


      return  taskClient.deleteJob(jobName,jobGroup);

    }

    @PostMapping(value = ServiceUrls.TaskManage.TASK+"/pause")
    @ApiOperation(value = "暂停任务")
    public Result pause( @RequestParam(value = "jobName", required = false)
                                     String jobName,
                         @RequestParam(value ="jobGroup", required = false)
                                     String jobGroup){
        System.err.println("jobName"+jobName);
        return  taskClient.pause(jobName,jobGroup);
    }

    @PostMapping(value = ServiceUrls.TaskManage.TASK+"/resume")
    @ApiOperation(value = "重启任务")
    public Result resume( @RequestParam(value = "jobName", required = false)
                                      String jobName,
                          @RequestParam(value ="jobGroup", required = false)
                                      String jobGroup){
      return taskClient.resume(jobName,jobGroup);
    }
}
