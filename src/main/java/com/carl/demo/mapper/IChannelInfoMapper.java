package com.carl.demo.mapper;


import com.carl.demo.entity.ChannelInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface IChannelInfoMapper {


    /**
     * 根据渠道号查询渠道信息
     * @param tabName 表名称
     * @param chanNo 渠道号
     * @return 可能为null
     */
    public ChannelInfo getChanInfoByChanNo(@Param("tableName") String tabName, @Param("chanNo")String chanNo);

}
