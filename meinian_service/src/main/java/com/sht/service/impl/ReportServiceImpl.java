package com.sht.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sht.dao.MemberDao;
import com.sht.dao.OrderDao;
import com.sht.service.ReportService;
import com.sht.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    MemberDao memberDao;
    @Autowired
    OrderDao orderDao;
    @Override
    public Map<String, Object> getBusinessReport() throws Exception {
        Map map = new HashMap();
        Date today1 = DateUtils.getToday();
        String today =DateUtils.parseDate2String(today1);
        String monday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        String firstDay4month = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        map.put("reportDate", today);
        map.put("todayNewMember", memberDao.todayNewMember(today));
        map.put("totalMember", memberDao.findTotalMember());
        map.put("thisWeekNewMember", memberDao.findNewMember(monday));
        map.put("thisMonthNewMember", memberDao.findNewMember(firstDay4month));
        map.put("todayOrderNumber", orderDao.getTodayOrderNumber(today));
        map.put("todayVisitsNumber", orderDao.getTodayVisitsNumber(today));
        map.put("thisWeekOrderNumber", orderDao.getThisWeekAndMonthOrderNumber(monday));
        map.put("thisWeekVisitsNumber", orderDao.getThisWeekAndMonthVisitsNumber(monday));
        map.put("thisMonthOrderNumber",orderDao.getThisWeekAndMonthOrderNumber(firstDay4month));
        map.put("thisMonthVisitsNumber", orderDao.getThisWeekAndMonthVisitsNumber(firstDay4month));
        map.put("hotSetmeal", orderDao.findHotSetmeal());
        return map;
    }

    @Override
    public Map<String, Object> getBusinessReportData() {
        try {
            return getBusinessReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
