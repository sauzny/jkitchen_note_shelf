package com.sauzny.jackson;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FyData {
    // 日期
    private String date;
    // 疑似
    private int cn_susNum;
    // 确诊
    private int cn_conNum;
    // 武汉确诊
    private int wuhan_conNum;
}
