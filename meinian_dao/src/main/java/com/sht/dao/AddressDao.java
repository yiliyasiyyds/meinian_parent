package com.sht.dao;

import com.sht.entities.Address;

import java.util.List;

public interface AddressDao {
    List findPage(String str);

    void add(Address address);

    void delete(Integer id);

    List<Address> findAllMaps();
}
