package com.carl.demo.service;

import com.carl.demo.entity.OperatorInfo;

import java.util.List;


/**
 * 运营商信息服务接口
 * @author GHB
 * @version 1.0
 * @date 2019.1.15
 */
public interface IOperatorsInfoService  {

    /**
     * 获取所在国家运营商列表
     * @param country
     * @return
     */
    public List<String> getOperatorList(String country);


    /**
     * 国家运营商上报信息
     * @return
     *//*
	public String getOperator(String uid,String app_name,String fid,String carrier);*/
}
