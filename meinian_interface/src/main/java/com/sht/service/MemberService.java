package com.sht.service;

import com.sht.entities.Member;

public interface MemberService {
    Long findByPhone(String phone);

    void quickAdd(Member member);

    Long findMemberCountByMonth(String date);
}
