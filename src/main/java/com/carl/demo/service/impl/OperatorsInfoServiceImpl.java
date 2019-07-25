package com.carl.demo.service.impl;

import com.carl.demo.mapper.IOperatorsInfoMapper;
import com.carl.demo.service.IOperatorsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OperatorsInfoServiceImpl implements IOperatorsInfoService {


    @Autowired
    private IOperatorsInfoMapper mOperatorsInfoMapper;

    @Override
    public List<String> getOperatorList(String country) {
        return mOperatorsInfoMapper.getOperatorList(country);
    }

}
