package com.sht.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sht.dao.TravelItemDao;
import com.sht.entities.TravelItem;
import com.sht.entity.PageResult;
import com.sht.entity.QueryPageBean;
import com.sht.entity.Result;
import com.sht.service.TravelItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass= TravelItemService.class)
@Transactional
public class TravelItemServiceImpl implements TravelItemService{
    @Autowired
    private TravelItemDao travelItemDao;

    @Override
    public void add(TravelItem travelItem) {
        travelItemDao.add(travelItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Page<Object> page = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List<TravelItem> list = travelItemDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),list);
    }

    @Override
    public void delete(Integer id) {
        travelItemDao.delete(id);
    }

    @Override
    public void update(TravelItem travelItem) {
        travelItemDao.update(travelItem);
    }

    @Override
    public TravelItem getTravelItem(Integer id) {
        return travelItemDao.getTravelItem(id);
    }

    @Override
    public List<TravelItem> findAll() {
        return travelItemDao.findAll();
    }
}
