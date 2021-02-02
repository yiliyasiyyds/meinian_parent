package com.sht.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sht.dao.MemberDao;
import com.sht.entities.Member;
import com.sht.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberDao memberDao;
    @Override
    public Long findByPhone(String phone) {
        return memberDao.findByPhone(phone);
    }

    @Override
    public void quickAdd(Member member) {
        memberDao.quickAdd(member);
    }

    @Override
    public Long findMemberCountByMonth(String date) {
        return memberDao.findMemberCountByMonth(date);
    }
}
