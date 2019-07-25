package com.carl.demo.controller;

import com.alibaba.fastjson.JSON;
import com.carl.demo.entity.TaskInfo;
import com.carl.demo.service.IOperatorsInfoService;
import com.carl.demo.service.ITaskInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
public class OperatorInfoController {
    @Autowired
    private IOperatorsInfoService operatorsInfoService;

    @Autowired
    private ITaskInfoService mtaskInfoService;

    @RequestMapping(value="/qr", method={RequestMethod.GET})
    @ResponseBody
    public String  getOperatorList(HttpServletRequest request) {
        List<String> th = operatorsInfoService.getOperatorList("TH");
        HashSet<String> strings = new HashSet<>();
        List<String> result = new ArrayList<>();
        strings.addAll(th);
        result.addAll(strings);
        String s1 = JSON.toJSONString(result);
        String s2 = JSON.toJSONString(th);
        System.out.println("之前的结果为"+s2);
        System.out.println("转化的结果"+s1);
        return s1;
    }

    @RequestMapping(value="/up", method={RequestMethod.GET})
    @ResponseBody
    private String updateTaskInfoFinishedCount() {
        TaskInfo task = new TaskInfo();
        task.setTaskDoneCount(22);
        mtaskInfoService.recordTaskDoneCount("345345435435", task.getTaskDoneCount());
        return null;
    }
}
