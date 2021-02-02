package com.sht.service;

import java.util.Map;

public interface ReportService {
    public Map<String,Object> getBusinessReport() throws Exception;

    Map<String, Object> getBusinessReportData();
}
