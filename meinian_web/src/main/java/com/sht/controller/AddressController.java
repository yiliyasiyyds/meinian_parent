package com.sht.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sht.constant.MessageConstant;
import com.sht.entities.Address;
import com.sht.entity.PageResult;
import com.sht.entity.QueryPageBean;
import com.sht.entity.Result;
import com.sht.service.AddressService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/Address")
@RestController
public class AddressController {

    @Reference
    AddressService addressService;

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = addressService.findPage(queryPageBean);
        return pageResult;
    }
    @RequestMapping("/addAddress")
    public Result add(@RequestBody Address address){
        try {
            addressService.add(address);
            return new Result(true, MessageConstant.ADD_ADDRESS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_ADDRESS_FAIL);
        }
    }
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        try {
            addressService.delete(id);
            return new Result(true, MessageConstant.DELETE_ADDRESS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_ADDRESS_FAIL);
        }
    }
    @RequestMapping("/findAllMaps")
    public Map findAllMaps(){
        Map map = new HashMap();
        List<Address> list = addressService.findAllMaps();
        List<Map> gridnMaps = new ArrayList(); //标记地址的经纬度
        List<Map> nameMaps = new ArrayList<>(); //标记地址名称
        for (Address address : list) {
            String addressName = address.getAddressName();
            Map<String,String> mapName = new HashMap<>();
            mapName.put("addressName",addressName);
            nameMaps.add(mapName);

            Map<String,String> gridnMap = new HashMap<>();
            gridnMap.put("lng",address.getLng());
            gridnMap.put("lat",address.getLat());
            gridnMaps.add(gridnMap);
        }
        map.put("gridnMaps",gridnMaps);
        map.put("nameMaps",nameMaps);
        return map ;
    }
}
