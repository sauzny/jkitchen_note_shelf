package com.sauzny.sparkbaby.demolog;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

import lombok.Data;

@Data
public class LogEntity implements Serializable{

    private static final long serialVersionUID = 1L; 
    
    public LogEntity(){}
    
    public LogEntity(String line){
        
        String q = line.split(" - ")[0].replace("  ", " ");
        String[] qs = q.split(" ");
        this.date = Date.valueOf(LocalDate.parse(qs[0]));
        this.localTime = qs[1];
        //this.zone = qs[2];
        this.threadName = qs[3];
        this.level = qs[4];
        //this.className = qs[5];
        this.message = line.split(" - ")[1];
        
        Student student = Student.fromJson(this.message);

        this.studentHometown = student.getHometown();
        this.studentName = student.getName();
        this.studentGender = student.getGender();
        this.studentAge = student.getAge();
        this.studentBalance = student.getBalance();
        this.studentEmail = student.getEmail();
        
        if (this.studentAge < 20) {
            this.studentAgeRange = "15~20";
        }else if(this.studentAge>=20 && this.studentAge < 30) {
            this.studentAgeRange = "20~30";
        }else if(this.studentAge>=30 && this.studentAge < 40) {
            this.studentAgeRange = "30~40";
        }else if(this.studentAge>=40 && this.studentAge < 50) {
            this.studentAgeRange = "40~50";
        }else if(this.studentAge>=50) {
            this.studentAgeRange = "50~55";
        }
    }
    
    // 日期
    private Date date;
    // 时间
    private String localTime;
    // 时区
    //private String zone;
    // 线程名
    private String threadName;
    // 日志级别
    private String level;
    // 类名
    //private String className;
    // 日志内容
    private String message;
    //
    private String studentName;
    //
    private String studentGender;
    //
    private int studentAge;
    //
    private String studentAgeRange;
    //
    private double studentBalance;
    //
    private String studentEmail;
    //
    private String studentHometown;
    
}
