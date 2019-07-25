package com.carl.demo.mapper;

import com.carl.demo.entity.OperatorInfo;


        import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 运营商信息表
 * @author GHB
 * @version 1.0
 * @date 2019.1.15
 */
@Mapper
public interface IOperatorsInfoMapper {

    /**
     * 获取所在国家运营商列表
     * @param country
     * @return
     */
    public List<String> getOperatorList(String country);


}
