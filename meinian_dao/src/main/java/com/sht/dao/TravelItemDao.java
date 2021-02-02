package com.sht.dao;

import com.sht.entities.TravelItem;

import java.util.List;

public interface TravelItemDao {
    void add(TravelItem travelItem);
   List<TravelItem> findPage(String str);

    void delete(Integer id);

    void update(TravelItem travelItem);

    TravelItem getTravelItem(Integer id);

    List<TravelItem> findAll();
    List<TravelItem> findById();
}
