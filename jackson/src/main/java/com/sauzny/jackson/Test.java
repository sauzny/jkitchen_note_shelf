package com.sauzny.jackson;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.google.common.collect.Lists;

public class Test {

    public static void main(String[] args) {
        
        ApmOne apmOne = new ApmOne();
        apmOne.setCount(89L);
        apmOne.setMaxDuration(3L);
        apmOne.setMaxDurationDateTime(LocalDateTime.now());
        apmOne.setNames(Lists.newArrayList("a","b"));
        apmOne.setDateSql(java.sql.Date.valueOf(LocalDate.now()));
        apmOne.setDateUtil(java.util.Date.from(Instant.ofEpochMilli(1471337924226L)));
        
        String json = JsonMapper.nonEmptyMapper().toJson(apmOne);
        
        System.out.println(json);
    }
}

