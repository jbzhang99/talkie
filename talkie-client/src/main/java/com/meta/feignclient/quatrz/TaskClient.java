package com.meta.feignclient.quatrz;

import com.meta.Result;
import com.meta.ServiceNames;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.model.task.TaskInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Created by y747718944 on 2018/2/5
 */
@FeignClient(name = ServiceNames.TALKIE_CORE)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface TaskClient {


    @GetMapping(value = ServiceUrls.TaskManage.TASK)
    @ApiOperation(value = "获取全部定时任务列表")
    public Result getTaskAll();

    @PostMapping(value = ServiceUrls.TaskManage.TASK)
    @ApiOperation(value = "新增定时任务")
    public Result addJob(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody
                    TaskInfo info)throws Exception;





    @DeleteMapping(value = ServiceUrls.TaskManage.TASK)
    @ApiOperation(value = "删除定时任务")
    public Result deleteJob(
            @RequestParam(value = "jobName", required = false)
                    String jobName,
            @RequestParam(value ="jobGroup", required = false)
                    String jobGroup);

    @PostMapping(value = ServiceUrls.TaskManage.TASK+"/pause")
    @ApiOperation(value = "暂停任务")
    public Result pause( @RequestParam(value = "jobName", required = false)
                                 String jobName,
                         @RequestParam(value ="jobGroup", required = false)
                                 String jobGroup);

    @PostMapping(value = ServiceUrls.TaskManage.TASK+"/resume")
    @ApiOperation(value = "重启任务")
    public Result resume( @RequestParam(value = "jobName", required = false)
                                  String jobName,
                          @RequestParam(value ="jobGroup", required = false)
                                  String jobGroup);
}
