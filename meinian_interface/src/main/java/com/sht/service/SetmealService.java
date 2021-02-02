package com.sht.service;

import com.sht.entities.Setmeal;
import com.sht.entity.PageResult;
import com.sht.entity.QueryPageBean;

import java.util.List;

public interface SetmealService {
    void add(Integer[] id, Setmeal setmeal);

    PageResult findPage(QueryPageBean queryPageBean);

    Setmeal findById(Integer id);

    List findTravelGroupIdBySetmealId(Integer id);

    void delete(Integer id);

    void update(Integer[] id, Setmeal setmeal);

    List<Setmeal> findAll();
    //客户端获取带详情的套餐
    Setmeal findById2(Integer id);

    Long findSetmealCountById(Integer id);
}
