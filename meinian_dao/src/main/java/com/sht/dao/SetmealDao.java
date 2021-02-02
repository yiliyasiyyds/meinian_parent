package com.sht.dao;

import com.sht.entities.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealDao {
    void add(Setmeal setmeal);

    void setSetmealAndTravelGroup(@Param("array") Integer[] id,@Param("setmealId") Integer setmealId);

    List findPage(String queryString);

    Setmeal findById(Integer id);

    List findTravelGroupIdBySetmealId(Integer id);

    void delete(Integer id);
    void deleteSetmealAndTravelGroupBySetmealId(Integer id);

    void update(Setmeal setmeal);

    List<Setmeal> findAll();

    Setmeal findById2(Integer id);

    Long findSetmealCountById(Integer id);
}
