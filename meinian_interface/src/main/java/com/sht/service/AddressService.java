package com.sht.service;

import com.sht.entities.Address;
import com.sht.entity.PageResult;
import com.sht.entity.QueryPageBean;

import java.util.List;
import java.util.Map;

public interface AddressService {
    PageResult findPage(QueryPageBean queryPageBean);

    void add(Address address);

    void delete(Integer id);

    List<Address> findAllMaps();
}
