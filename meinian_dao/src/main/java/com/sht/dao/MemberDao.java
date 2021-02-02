package com.sht.dao;

import com.sht.entities.Member;

public interface MemberDao {
    Long findByPhone(String phone);

    void quickAdd(Member member);

    Member findNameById(Integer id);

    Long findMemberCountByMonth(String date);

    Long todayNewMember(String today);

    Long findTotalMember();

    Long findNewMember(String parseDate2String);
}
