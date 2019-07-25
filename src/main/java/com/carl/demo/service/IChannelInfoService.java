package com.carl.demo.service;

import com.carl.demo.entity.ChannelInfo;

public interface IChannelInfoService {
    /**
     * 根据渠道号获取渠道信息
     * @param chanNo 渠道号
     * @return 可能为null
     */
    public ChannelInfo getChanInfoByChanNo(String chanNo);
}
