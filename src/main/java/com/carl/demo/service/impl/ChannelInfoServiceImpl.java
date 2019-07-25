package com.carl.demo.service.impl;

import com.carl.demo.entity.ChannelInfo;
import com.carl.demo.mapper.IChannelInfoMapper;
import com.carl.demo.service.IChannelInfoService;
import com.carl.demo.utils.Objects;
import com.carl.demo.utils.RediskeyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service
public class ChannelInfoServiceImpl implements IChannelInfoService {


    @Autowired
    private IChannelInfoMapper mChannelInfoMapper;

    @Autowired
    private RedisTemplate redisTemplate;


   /* @Autowired
    private ValueOperations<String,Object> valueOperations;*/
  /*  @Autowired
    RedisClientTemplate redisClientTemplate;*/

    /*@Autowired
    private JedisClusterService jedisClusterService;*/

  /*  public static String keyvalue;

    private String getValue(String chanNo){
        keyvalue = RediskeyConstants.CHANNEL_INFO_KEY + chanNo;
        return keyvalue;
    }*/

    /**
     * 根据渠道号获取渠道信息
     *
     * @param chanNo 渠道号
     * @return 可能为null
     */
    @Override
    public ChannelInfo getChanInfoByChanNo(String chanNo) {
        ChannelInfo channelInfoStr = null;
        System.out.println("进入了impl");
        try {
			String key = RediskeyConstants.CHANNEL_INFO_KEY + chanNo;

            channelInfoStr = (ChannelInfo) redisTemplate.opsForValue().get(key);
            System.out.println("从缓存中拿出的数据"+channelInfoStr);
            //缓存中获取不到，查询数据库，并保存在缓存中
			if(Objects.isEmpty(channelInfoStr)) {
                channelInfoStr = mChannelInfoMapper.getChanInfoByChanNo(M_TAB_NAME, chanNo);
                System.out.println("从数据库拿出的数据为："+channelInfoStr);
            if(Objects.isEmpty(channelInfoStr)) {
                System.out.println("查询渠道数据，无数据");
                return null;
            }
            //缓存60分钟
            redisTemplate.opsForValue().set(key, channelInfoStr,600, TimeUnit.SECONDS);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        //return mChannelInfoMapper.getChanInfoByChanNo(M_TAB_NAME, chanNo);
        return channelInfoStr;
    }


    private static final String M_TAB_NAME = "channel_info";

    public String getTableName() {
        return M_TAB_NAME;
    }

    private static final String M_SQL_TAB_CREATE = "_id bigint(20) NOT NULL AUTO_INCREMENT, " +
            "chan_no varchar(255) NOT NULL, " +
            "chan_desc varchar(255) NOT NULL, " +
            "task_switch int(5), " +
            "s_user_max_tasks int(5), " +
            "postback varchar(255), " +
            "rsa_pub_key_b64 varchar(1024) NOT NULL, " +
            "rsa_priv_key_b64 varchar(2048) NOT NULL, " +
            "create_time timestamp NOT NULL, " +
            "PRIMARY KEY (_id)";


    public String getCreateTableSql() {
        return M_SQL_TAB_CREATE;
    }
}
