package com.sht.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sht.dao.TravelGroupDao;
import com.sht.entities.TravelGroup;
import com.sht.entity.PageResult;
import com.sht.entity.QueryPageBean;
import com.sht.service.TravelGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass= TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService{
    @Autowired
    TravelGroupDao travelGroupDao;

    @Override
    public void add(TravelGroup travelGroup,Integer[] travelItemIds) {
        travelGroupDao.add(travelGroup);
        Integer travelGroupId = travelGroup.getId();
        for (Integer travelItemId : travelItemIds) {
            Map<String, Integer> map = new HashMap<>();
            map.put("travelGroup",travelGroupId);
            map.put("travelItem",travelItemId);
            travelGroupDao.setTravelGroupAndTravelItem(map);
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Page<Object> page = PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        List list=travelGroupDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),list);
    }

    @Override
    public TravelGroup findById(Integer id) {
        return travelGroupDao.findById(id);
    }

    @Override
    public List findTravelItemIdByTravelGroupId(Integer id) {
        return travelGroupDao.findTravelItemIdByTravelGroupId(id);
    }

    @Override
    public void update(TravelGroup travelGroup, Integer[] travelItemIds) {
        travelGroupDao.update(travelGroup);
        Integer travelGroupId = travelGroup.getId();
        travelGroupDao.deleteTravelGroupAndTravelItemByTravelGroupId(travelGroupId);
        for (Integer travelItemId : travelItemIds) {
            Map<String, Integer> map = new HashMap<>();
            map.put("travelGroup",travelGroupId);
            map.put("travelItem",travelItemId);
            travelGroupDao.setTravelGroupAndTravelItem(map);
        }
    }

    @Override
    public void delete(Integer id) {
        travelGroupDao.deleteTravelGroupAndTravelItemByTravelGroupId(id);
        travelGroupDao.delete(id);
    }

    @Override
    public List findAll() {
        return travelGroupDao.findAll();
    }
}
