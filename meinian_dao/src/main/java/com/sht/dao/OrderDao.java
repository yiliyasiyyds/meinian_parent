package com.sht.dao;

import com.sht.entities.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    Order findOrder(Order order);
    Map findById(Integer id);

    void add(Order order);

    Long getTodayOrderNumber(String today);

    Long getTodayVisitsNumber(String today);

    Long getThisWeekAndMonthOrderNumber(String monday);

    Long getThisWeekAndMonthVisitsNumber(String monday);

    List<Map<String,Object>> findHotSetmeal();
}
