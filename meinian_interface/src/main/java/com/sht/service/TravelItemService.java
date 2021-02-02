package com.sht.service;

import com.sht.entities.TravelItem;
import com.sht.entity.PageResult;
import com.sht.entity.QueryPageBean;
import com.sht.entity.Result;

import java.util.List;

public interface TravelItemService {
    void add(TravelItem travelItem);

    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id);

    void update(TravelItem travelItem);

    TravelItem getTravelItem(Integer id);

    List<TravelItem> findAll();
}
