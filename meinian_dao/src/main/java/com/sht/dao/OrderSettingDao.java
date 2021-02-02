package com.sht.dao;

import com.sht.entities.OrderSetting;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {
    Long findCountByOrderDate(Date orderDate);

    void update(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(String start, String end);

    OrderSetting getOrderSettingByDate(Date orderDate);

    void updateReservationsByDate(Date orderDate);
}
