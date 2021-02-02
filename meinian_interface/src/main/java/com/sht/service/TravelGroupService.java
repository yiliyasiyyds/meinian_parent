package com.sht.service;

import com.sht.entities.TravelGroup;
import com.sht.entity.PageResult;
import com.sht.entity.QueryPageBean;

import java.util.List;

public interface TravelGroupService {
    void add(TravelGroup travelGroup,Integer[] travelItemIds);

    PageResult findPage(QueryPageBean queryPageBean);

    TravelGroup findById(Integer id);

    List findTravelItemIdByTravelGroupId(Integer id);

    void update(TravelGroup travelGroup, Integer[] travelItemIds);

    void delete(Integer id);

    List findAll();
}
