package com.sht.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sht.constant.MessageConstant;
import com.sht.entities.OrderSetting;
import com.sht.entity.Result;
import com.sht.service.OrderSettingService;
import com.sht.util.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.table.TableRowSorter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/Ordersetting")
public class OrderSettingController {
    @Reference
    OrderSettingService orderSettingService;
    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) throws IOException {
        try {
            List<String[]> excels = POIUtils.readExcel(excelFile);
            List<OrderSetting> list = new ArrayList();
            for (String[] excel : excels) {
                list.add(new OrderSetting(new Date(excel[0]),Integer.parseInt(excel[1])));
            }
            orderSettingService.add(list);
            return new Result(true, MessageConstant.UPLOAD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.UPLOAD_FAIL);
        }
    }
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        String start = date;
        String end = date.substring(0,8)+"31";
        try {
            List list = orderSettingService.getOrderSettingByMonth(start, end);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }
    @RequestMapping("/updateNumberByDate")
    public Result updateNumberByDate(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.updateNumberByDate(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
