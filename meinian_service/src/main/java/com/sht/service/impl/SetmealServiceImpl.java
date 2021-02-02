package com.sht.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sht.constant.RedisConstant;
import com.sht.dao.SetmealDao;
import com.sht.entities.Setmeal;
import com.sht.entity.PageResult;
import com.sht.entity.QueryPageBean;
import com.sht.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;
    @Override
    public void add(Integer[] id, Setmeal setmeal) {
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
        setmealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        setmealDao.setSetmealAndTravelGroup(id,setmealId);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Page<Object> page = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List list = setmealDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),list);
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public List findTravelGroupIdBySetmealId(Integer id) {
        return setmealDao.findTravelGroupIdBySetmealId(id);
    }

    @Override
    public void delete(Integer id) {
        setmealDao.deleteSetmealAndTravelGroupBySetmealId(id);
        setmealDao.delete(id);
    }

    @Override
    public void update(Integer[] id, Setmeal setmeal) {
        Integer setmealId = setmeal.getId();
        setmealDao.deleteSetmealAndTravelGroupBySetmealId(setmealId);
        setmealDao.setSetmealAndTravelGroup(id,setmealId);
        setmealDao.update(setmeal);
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findById2(Integer id) {
        return setmealDao.findById2(id);
    }

    @Override
    public Long findSetmealCountById(Integer id) {
        return setmealDao.findSetmealCountById(id);
    }
}
