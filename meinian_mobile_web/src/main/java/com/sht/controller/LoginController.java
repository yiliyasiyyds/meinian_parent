package com.sht.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sht.constant.MessageConstant;
import com.sht.constant.RedisMessageConstant;
import com.sht.entities.Member;
import com.sht.entity.Result;
import com.sht.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Reference
    MemberService memberService;
    @Autowired
    JedisPool jedisPool;
    @RequestMapping("check")
    public Result check(Map map, HttpServletResponse response){
        Jedis jedis = jedisPool.getResource();
        String key = RedisMessageConstant.SENDTYPE_ORDER + (String) map.get("telephone");
        String code = jedis.get(key);
        if(code==null){
            return new Result(false, MessageConstant.VALIDATECODE_TIMEOUT_ERROR);
        }
        if(!code.equals((String)map.get("validateCode"))){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        Member member = new Member();

        //查询用户是否存在
        if(memberService.findByPhone((String) map.get("telephone"))==0){
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setIdCard((String) map.get("idCard"));
            member.setPhoneNumber((String) map.get("telephone"));
            member.setRegTime(new Date(System.currentTimeMillis()));
            memberService.quickAdd(member);
        }
        Cookie cookie = new Cookie("login_member_telephone", (String) map.get("telephone"));
        cookie.setPath("/");//路径
        cookie.setMaxAge(60*60*24*30);//有效期30天（单位秒）
        response.addCookie(cookie);
        return new Result(true,MessageConstant.LOGIN_SUCCESS);

    }
}
