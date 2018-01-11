package com.sauzny.lombok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

// 构造函数
// 无参数
//@NoArgsConstructor
@RequiredArgsConstructor(staticName="of")
@AllArgsConstructor
// private static final log
@Slf4j
@Data
public class Student {

    private int id;
    private String name;
    @NonNull private String description;  
    
    public void foo01(){
        log.info(description);
    }
}
