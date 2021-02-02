package com.sht.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sht.constant.MessageConstant;
import com.sht.entities.TravelItem;
import com.sht.entity.PageResult;
import com.sht.entity.QueryPageBean;
import com.sht.entity.Result;
import com.sht.service.TravelItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/TravelItem")
public class TravelItemController {
    @Reference
    TravelItemService travelItemService;
    @RequestMapping("/add")
    public Result add(@RequestBody TravelItem travelItem){
        System.out.println(travelItem.getAge());
        try {
            travelItemService.add(travelItem);
            return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TRAVELITEM_FAIL);
        }
    }
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult page = travelItemService.findPage(queryPageBean);
        return page;
    }
    @RequestMapping("/findAll")
    public Result findAll(){
        List<TravelItem> all = null;
        try {
            all = travelItemService.findAll();
            return new Result(true, MessageConstant.QUERY_TRAVELITEM_SUCCESS,all);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TRAVELITEM_FAIL,all);
        }
    }
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            travelItemService.delete(id);
            return new Result(true, MessageConstant.DELETE_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_TRAVELITEM_FAIL);
        }
    }
    @RequestMapping("/update")
    public Result update(@RequestBody TravelItem travelItem){
        try {
            travelItemService.update(travelItem);
            return new Result(true, MessageConstant.EDIT_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_TRAVELITEM_FAIL);
        }
    }
    @RequestMapping("/getTravelItem")
    public Result getTravelItem(Integer id){
        TravelItem travelItem = new TravelItem();
        try {
            travelItem = travelItemService.getTravelItem(id);
            return new Result(true,"",travelItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"",travelItem);
        }
    }
}
