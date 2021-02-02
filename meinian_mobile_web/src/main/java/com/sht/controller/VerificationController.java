package com.sht.controller;

import com.sht.constant.MessageConstant;
import com.sht.constant.RedisMessageConstant;
import com.sht.entity.Result;
import com.sht.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("VerificationCode")
public class VerificationController {
    @Autowired
    JedisPool jedisPool;
    @RequestMapping("getCode")
    public Result getCode(String phoneNumber){
        try {
            String key = RedisMessageConstant.SENDTYPE_ORDER+phoneNumber;
            Integer value = ValidateCodeUtils.generateValidateCode(4);
            Jedis jedis = jedisPool.getResource();
            jedis.setex(key,5*60, String.valueOf(value));
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS,value);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }
    }
}
