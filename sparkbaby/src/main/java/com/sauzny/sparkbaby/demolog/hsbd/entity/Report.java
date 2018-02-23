package com.sauzny.sparkbaby.demolog.hsbd.entity;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.apache.commons.lang3.RandomUtils;

public class Report {

    private int id;
    
    private LocalDate localdate;
    
    private double sales;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getLocaldate() {
        return localdate;
    }

    public void setLocaldate(LocalDate localdate) {
        this.localdate = localdate;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }
    
    public static void main(String[] args) {
        
        for(int i=0;i<24;i++){
            
            LocalDate localdate = LocalDate.parse("2011-01-01").plusMonths(i).with(TemporalAdjusters.lastDayOfMonth());
            
            double sale = RandomUtils.nextDouble(1000, 3000);
            
            sale = Double.parseDouble(new DecimalFormat("#.00").format(sale));
            
            System.out.println(localdate + "\t" + sale);
        }
    }
}
