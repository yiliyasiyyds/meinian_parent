package com.sht.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sht.constant.MessageConstant;
import com.sht.constant.RedisMessageConstant;
import com.sht.entity.Result;
import com.sht.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/Order")
public class OrderController {
    @Reference
    OrderService orderService;

    @Autowired
    JedisPool jedisPool;
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) throws Exception {
        Jedis jedis = jedisPool.getResource();
        String key = RedisMessageConstant.SENDTYPE_ORDER + (String) map.get("telephone");
        String code = jedis.get(key);

        //判断验证码
        if(code==null){
            return new Result(false, MessageConstant.VALIDATECODE_TIMEOUT_ERROR);
        }
        if(!code.equals((String)map.get("validateCode"))){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        return orderService.save(map);
    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
       Map map = orderService.findById(id);
       return new Result(true,"",map);
    }
}
