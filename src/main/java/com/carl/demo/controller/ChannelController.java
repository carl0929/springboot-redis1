package com.carl.demo.controller;

import com.carl.demo.entity.ChannelInfo;
import com.carl.demo.service.IChannelInfoService;
import com.carl.demo.utils.RediskeyConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ChannelController {

    @Autowired
    private IChannelInfoService mChanInfoService;

    @Autowired
    private RedisTemplate redisTemplate;
 /*   @Autowired
    RedisClientTemplate redisClientTemplate;*/

    @RequestMapping(value = "/chan/getkey", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String onRSAPubkeyRequest(HttpServletRequest request) {
        String cid = request.getParameter("cid");
        if(StringUtils.isEmpty(cid)) {
            System.out.println("参数中没有cid，无法查找RSA公钥！");
            return "";
        }
        ChannelInfo info = mChanInfoService.getChanInfoByChanNo(cid);
        if(null == info) {
            System.out.println("渠道号【{0}】没有对应的渠道信息,无法查找RSA公钥！");
            return "";
        }
        System.out.println("拿到了信息！");
        return info.getRsaPubKeyB64Str();
    }

    @GetMapping(value = "/setkey")
    @ResponseBody
    public String setkey(HttpServletRequest request){
        String cid = request.getParameter("cid");
        redisTemplate.opsForValue().set(cid,"this is a test");
        String o = (String) redisTemplate.opsForValue().get(cid);
        return o;
    }

    @GetMapping(value = "/test")
    @ResponseBody
    public String testSet(HttpServletRequest request){
        String cid = request.getParameter("cid");
        redisTemplate.delete(cid);
        String channelInfoStr = (String) redisTemplate.opsForValue().get(cid);
        System.out.println("test里缓存的数据为"+channelInfoStr);
        /*redisClientTemplate.setToRedis("CHANNELINFOKEY20190311230823","11111");
        System.out.println("C"+redisClientTemplate.getRedis("CHANNELINFOKEY20190311230823"));
        System.out.println("f"+redisClientTemplate.getRedis("Frank111"));*/


        return channelInfoStr;
       }
}
