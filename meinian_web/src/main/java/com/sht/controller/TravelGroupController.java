package com.sht.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sht.constant.MessageConstant;
import com.sht.entities.TravelGroup;
import com.sht.entity.PageResult;
import com.sht.entity.QueryPageBean;
import com.sht.entity.Result;
import com.sht.service.TravelGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/TravelGroup")
public class TravelGroupController {
    @Reference
    TravelGroupService travelGroupService;
    @RequestMapping("/add")
    public Result add(@RequestBody TravelGroup travelGroup, @RequestParam Integer[] travelItemIds){
        try {
            travelGroupService.add(travelGroup,travelItemIds);
            return new Result(true,MessageConstant.ADD_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_TRAVELGROUP_FAIL);
        }
    }
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult page= travelGroupService.findPage(queryPageBean);
        return page;
    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
        TravelGroup travelGroup;
        try {
            travelGroup = travelGroupService.findById(id);
            return new Result(true,"",travelGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }
    }
    @RequestMapping("/findTravelItemIdByTravelGroupId")
    public Result findTravelItemIdByTravelGroupId(Integer id){
        List list = travelGroupService.findTravelItemIdByTravelGroupId(id);
        return new Result(true,"",list);
    }
    @RequestMapping("/update")
    public Result update(@RequestBody TravelGroup travelGroup, @RequestParam Integer[] travelItemIds){
        try {
            travelGroupService.update(travelGroup,travelItemIds);
            return new Result(true,MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_TRAVELGROUP_FAIL);
        }
    }
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            travelGroupService.delete(id);
            return new Result(true,MessageConstant.DELETE_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_TRAVELGROUP_FAIL);
        }
    }
    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List list=travelGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }
    }
}
