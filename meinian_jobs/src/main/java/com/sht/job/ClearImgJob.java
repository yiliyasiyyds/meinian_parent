package com.sht.job;

import com.sht.constant.RedisConstant;
import com.sht.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    public void clearImg(){
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        for (String s : set) {
            QiniuUtils.deleteFileFromQiniu(s);
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,s);
            System.out.println("图片缓存已清理");
        }
    }
}
