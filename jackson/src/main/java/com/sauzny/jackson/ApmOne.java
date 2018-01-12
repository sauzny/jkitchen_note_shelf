package com.sauzny.jackson;

import java.time.LocalDateTime;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ApmOne {

    // 执行次数
    private long count;
    // 最小耗时
    private long minDuration = Long.MAX_VALUE;
    // 最大耗时
    private long maxDuration = Long.MIN_VALUE;
    // 最小耗时出现时间
    private LocalDateTime minDurationDateTime;
    // 最大耗时出现时间
    private LocalDateTime maxDurationDateTime;
    
    private List<String> names;
    
    private java.util.Date dateUtil;
    
    private java.sql.Date dateSql;
    
    private String name;
    
    private int age;
    
    /**
        jackson 转换时间类型问题：
        
     */
    
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    public java.util.Date getDateUtil() {
        return dateUtil;
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    public java.sql.Date getDateSql() {
        return dateSql;
    }
    
    // 现在 mapper 中 时间类型 默认为  yyyy-MM-dd'T'HH:mm:ss.SSSZ 
    // 如果有需求，此处还需要自己指定 @JsonFormat
    
    public LocalDateTime getMinDurationDateTime() {
        return minDurationDateTime;
    }
    
    public LocalDateTime getMaxDurationDateTime() {
        return maxDurationDateTime;
    }
  
    
}
