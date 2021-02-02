package com.sht.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sht.entities.Setmeal;
import com.sht.entity.Result;
import com.sht.service.MemberService;
import com.sht.service.ReportService;
import com.sht.service.SetmealService;
import com.sht.util.DateUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/Report")
public class ReportController {
    @Reference
    private MemberService memberService;
    @Reference
    private SetmealService setmealService;
    @Reference
    private ReportService reportService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);
        List month = new ArrayList();
        List members = new ArrayList();
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1);
            String date = new SimpleDateFormat("yyyy-MM").format(calendar.getTime());
            month.add(date);
            members.add(memberService.findMemberCountByMonth(date));
        }
        Map map = new HashMap();
        map.put("months",month);
        map.put("memberCount",members);
        return new Result(true,"",map);
    }
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        List<Setmeal> setmeals=setmealService.findAll();
        List setmealNames = new ArrayList();
        List setmealCount = new ArrayList();
        for (Setmeal setmeal : setmeals) {
            setmealNames.add(setmeal.getName());
            Map map = new HashMap();
            map.put("name",setmeal.getName());
            map.put("value",setmealService.findSetmealCountById(setmeal.getId()));
            setmealCount.add(map);
        }
        Map map = new HashMap();
        map.put("setmealNames",setmealNames);
        map.put("setmealCount",setmealCount);
        return new Result(true,"",map);
    }
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() throws Exception {
        Map map = reportService.getBusinessReport();
        return new Result(true,"",map);
    }
    @RequestMapping("/exportBusinessReport")
    public void exportBusinessReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> result = reportService.getBusinessReportData();
        String path = request.getSession().getServletContext().getRealPath("template");
        String templatePath = path + File.separator + "report_template.xlsx";

        String reportDate = (String) result.get("reportDate");
        Integer todayNewMember = ((Long)result.get("todayNewMember")).intValue();
        Integer totalMember = ((Long)result.get("totalMember")).intValue();
        Integer thisWeekNewMember =  ((Long)result.get("thisWeekNewMember")).intValue();
        Integer thisMonthNewMember =  ((Long)result.get("thisMonthNewMember")).intValue();
        Integer todayOrderNumber =  ((Long)result.get("todayOrderNumber")).intValue();
        Integer thisWeekOrderNumber =  ((Long)result.get("thisWeekOrderNumber")).intValue();
        Integer thisMonthOrderNumber =  ((Long)result.get("thisMonthOrderNumber")).intValue();
        Integer todayVisitsNumber =  ((Long)result.get("todayVisitsNumber")).intValue();
        Integer thisWeekVisitsNumber =  ((Long)result.get("thisWeekVisitsNumber")).intValue();
        Integer thisMonthVisitsNumber =  ((Long)result.get("thisMonthVisitsNumber")).intValue();
        List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");


        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(templatePath)));
        XSSFSheet sheet = workbook.getSheetAt(0);

        XSSFRow row = sheet.getRow(2);
        row.getCell(5).setCellValue(reportDate);//日期

        row = sheet.getRow(4);
        row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
        row.getCell(7).setCellValue(totalMember);//总会员数

        row = sheet.getRow(5);
        row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
        row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

        row = sheet.getRow(7);
        row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
        row.getCell(7).setCellValue(todayVisitsNumber);//今日出游数

        row = sheet.getRow(8);
        row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
        row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周出游数

        row = sheet.getRow(9);
        row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
        row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月出游数

        int rowNum = 12;
        for(Map map : hotSetmeal){//热门套餐
            String name = (String) map.get("name");
            Long setmeal_count = (Long) map.get("setmeal_count");
            BigDecimal proportion = (BigDecimal) map.get("proportion");
            row = sheet.getRow(rowNum ++);
            row.getCell(4).setCellValue(name);//套餐名称
            row.getCell(5).setCellValue(setmeal_count);//预约数量
            row.getCell(6).setCellValue(proportion.doubleValue());//占比
        }
        ServletOutputStream out = response.getOutputStream();
        // 下载的数据类型（excel类型）
        response.setContentType("application/vnd.ms-excel");
        // 设置下载形式(通过附件的形式下载)
        response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
        workbook.write(out);

        out.flush();
        out.close();
        workbook.close();
    }
}
