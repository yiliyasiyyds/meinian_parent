package com.sht.service;

import com.sht.entities.OrderSetting;

import java.util.Date;
import java.util.List;

public interface OrderSettingService {
    void add(List<OrderSetting> list);

    List getOrderSettingByMonth(String start, String end);

    void updateNumberByDate(OrderSetting orderSetting);

    boolean isFull(Date orderDate);

    void updateReservationsByDate(Date orderDate);

    OrderSetting getOrderSettingByDate(Date orderDate);
}
