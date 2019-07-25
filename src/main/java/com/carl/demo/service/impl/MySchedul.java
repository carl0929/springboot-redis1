package com.carl.demo.service.impl;


import com.carl.demo.entity.TaskInfo;
import com.carl.demo.mapper.ITaskInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
@Configuration
public class MySchedul {

  /*   @Autowired
    private ITaskInfoMapper taskInfoMapper;

   @Scheduled(cron = "0/10 * * * * ?")
    public void test(){
        List<TaskInfo> tasks_info = taskInfoMapper.getAllRecords("tasks_info");
        for (TaskInfo taskInfo : tasks_info) {
            System.out.println(taskInfo.getInd()+"改前-------------------"+taskInfo.getTaskDoneCount());
            taskInfo.setTaskDoneCount(0);
            taskInfoMapper.updateRecordByInd("tasks_info",taskInfo.getInd(),taskInfo);
            System.out.println(taskInfo.getInd()+"之后"+taskInfo.getTaskDoneCount());
        }
    }*/
}
