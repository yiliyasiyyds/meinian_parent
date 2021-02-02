package com.sht.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sht.dao.OrderSettingDao;
import com.sht.entities.OrderSetting;
import com.sht.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Override
    public void add(List<OrderSetting> list) {
        for (OrderSetting orderSetting : list) {
            if(orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate())>0){
                orderSettingDao.update(orderSetting);
            }else {
                orderSettingDao.add(orderSetting);
            }
        }
    }

    @Override
    public List getOrderSettingByMonth(String start, String end) {
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(start, end);
        List<Map> leftobj = new ArrayList();
        for (OrderSetting orderSetting : list) {
            Map map = new HashMap();
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());
            leftobj.add(map);
        }
        return leftobj;
    }

    @Override
    public void updateNumberByDate(OrderSetting orderSetting) {
        if(orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate())>0){
            orderSettingDao.update(orderSetting);
        }else {
            orderSettingDao.add(orderSetting);
        }
    }

    @Override
    public boolean isFull(Date orderDate) {
       OrderSetting orderSetting = orderSettingDao.getOrderSettingByDate(orderDate);
       if(orderSetting.getNumber()>orderSetting.getReservations()){
           return false;
       }
        return true;
    }

    @Override
    public void updateReservationsByDate(Date orderDate) {
        orderSettingDao.updateReservationsByDate(orderDate);
    }

    @Override
    public OrderSetting getOrderSettingByDate(Date orderDate) {
        return orderSettingDao.getOrderSettingByDate(orderDate);
    }
}
