package org.example.sauzny.service;

public class ReportService {

    public String getReport(int y, int m) {
        return String.format("%s年%s月", y, m);
    }
}
