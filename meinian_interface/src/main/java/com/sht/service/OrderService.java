package com.sht.service;

import com.sht.entities.Order;
import com.sht.entity.Result;

import java.util.Map;

public interface OrderService {
    Order findOrder(Order order);

    void add(Order order);

    Result save(Map map);

    Map findById(Integer id);
}
