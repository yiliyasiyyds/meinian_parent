package com.sht.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sht.constant.MessageConstant;
import com.sht.constant.RedisConstant;
import com.sht.entities.Setmeal;
import com.sht.entity.PageResult;
import com.sht.entity.QueryPageBean;
import com.sht.entity.Result;
import com.sht.service.SetmealService;
import com.sht.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/Setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile){
        String imgName = imgFile.getOriginalFilename();
        int last = imgName.lastIndexOf(".");
        String fileName = UUID.randomUUID().toString()+imgName.substring(last);
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL,fileName);
        }
    }
    @RequestMapping("/add")
    public Result add(Integer[] id, @RequestBody Setmeal setmeal){
        try {
            setmealService.add(id,setmeal);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        }  catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult page=setmealService.findPage(queryPageBean);
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
        Setmeal setmeal = null;
        try {
            setmeal = setmealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
    @RequestMapping("/findTravelGroupIdBySetmealId")
    public Result findTravelGroupIdBySetmealId(Integer id){
        List list = setmealService.findTravelGroupIdBySetmealId(id);
        return new Result(true,"",list);
    }
    @RequestMapping("delete")
    public Result delete(Integer id){
        try {
            setmealService.delete(id);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }
    @RequestMapping("update")
    public Result update(Integer[] id,@RequestBody Setmeal setmeal){
        try {
            setmealService.update(id,setmeal);
            return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
        }  catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }
}
