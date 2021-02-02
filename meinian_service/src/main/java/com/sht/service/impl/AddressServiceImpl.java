package com.sht.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sht.dao.AddressDao;
import com.sht.entities.Address;
import com.sht.entity.PageResult;
import com.sht.entity.QueryPageBean;
import com.sht.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressDao addressDao;
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Page<Object> page = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List list = addressDao.findPage(queryPageBean.getQueryString());
        PageResult pageResult = new PageResult(page.getTotal(),list);
        return pageResult;
    }

    @Override
    public void add(Address address) {
        addressDao.add(address);
    }

    @Override
    public void delete(Integer id) {
        addressDao.delete(id);
    }

    @Override
    public List<Address> findAllMaps() {
        return addressDao.findAllMaps();
    }
}
