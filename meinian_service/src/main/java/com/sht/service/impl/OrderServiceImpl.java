package com.sht.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sht.constant.MessageConstant;
import com.sht.dao.MemberDao;
import com.sht.dao.OrderDao;
import com.sht.dao.OrderSettingDao;
import com.sht.dao.SetmealDao;
import com.sht.entities.Member;
import com.sht.entities.Order;
import com.sht.entities.Setmeal;
import com.sht.entity.Result;
import com.sht.service.OrderService;
import com.sht.service.OrderSettingService;
import com.sht.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    MemberDao memberDao;
    @Autowired
    OrderSettingService orderSettingDao;
    @Autowired
    SetmealDao setmealDao;
    @Override
    public Order findOrder(Order order) {
        return orderDao.findOrder(order);
    }

    @Override
    public void add(Order order) {
        orderDao.add(order);
    }

    @Override
    public Result save(Map map) {
        Order order = new Order();
        Date orderDate = null;
        try {
            orderDate = DateUtils.parseString2Date((String)map.get("orderDate"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(orderSettingDao.getOrderSettingByDate(orderDate)==null){
            return new Result(false,MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        Member member = new Member();

        //查询用户是否存在
        if(memberDao.findByPhone((String) map.get("telephone"))==0){
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setIdCard((String) map.get("idCard"));
            member.setPhoneNumber((String) map.get("telephone"));
            member.setRegTime(new Date(System.currentTimeMillis()));
            memberDao.quickAdd(member);
        }

        //查询预约是否已满
        order.setOrderDate(orderDate);
        order.setMemberId(member.getId());
        order.setSetmealId(Integer.valueOf((String)map.get("setmealId")));
        if(orderSettingDao.isFull(orderDate)){
            return new Result(false, MessageConstant.ORDER_FULL);
        }

            //查询用户是否重复下单
            if(findOrder(order)!=null){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        order.setOrderType(Order.ORDERTYPE_WEIXIN);
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        try {
            add(order);
            orderSettingDao.updateReservationsByDate(orderDate);
            return new Result(true,MessageConstant.ORDER_SUCCESS,order);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.ORDER_FAIL);
        }

    }

    @Override
    public Map findById(Integer id) {
        Map map = orderDao.findById(id);
        map.put("member",map.get("member"));
        map.put("setmeal",map.get("setmeal"));
        map.put("orderDate",map.get("orderDate"));
        map.put("orderType",map.get("orderType"));
        return map;
    }
}
