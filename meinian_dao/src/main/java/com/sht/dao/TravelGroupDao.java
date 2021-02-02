package com.sht.dao;

import com.sht.entities.TravelGroup;

import java.util.List;
import java.util.Map;

public interface TravelGroupDao {
    void add(TravelGroup travelGroup);

    void setTravelGroupAndTravelItem(Map<String, Integer> map);

    List findPage(String queryString);

    TravelGroup findById(Integer id);

    TravelGroup findById2(Integer id);

    List findTravelItemIdByTravelGroupId(Integer id);

    void deleteTravelGroupAndTravelItemByTravelGroupId(Integer travelGroupId);

    void update(TravelGroup travelGroup);

    void delete(Integer id);

    List findAll();
}
