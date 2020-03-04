package com.sauzny.jackson;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
public class Fy2020 {

    @Test
    public void foo01() {
        //log.info("test001");

        String fy2020Json = "";

        try {
            fy2020Json = Files.readString(Paths.get("fy2020.json"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<FyData> fyDataList = JacksonTools.nonNull().fromJson(fy2020Json, ArrayList.class, FyData.class);

        Collections.reverse(fyDataList);

        //log.info("fyDataList = {}", fyDataList);

        log.info("疑似病例增长率\t{}", Fy2020.print(Fy2020.r1(fyDataList)));

        log.info("确诊病例增长率\t{}", Fy2020.print(Fy2020.r2(fyDataList)));
    }

    // 疑似病例增长率
    private static List<Double> r1(List<FyData> fyDataList){

        List<Double> result = Lists.newArrayList();

        for (int i = 0; i < fyDataList.size(); i++) {

            FyData data1 = fyDataList.get(i);

            if (i > 0) {
                FyData data0 = fyDataList.get(i - 1);
                BigDecimal d1 = new BigDecimal(data1.getCn_susNum());
                BigDecimal d0 = new BigDecimal(data0.getCn_susNum());
                if(data0.getCn_susNum() == 0){
                    result.add(0D);
                }else{
                    BigDecimal r = d1.divide(d0, 4, RoundingMode.HALF_UP)
                            .subtract(BigDecimal.valueOf(1))
                            .multiply(BigDecimal.valueOf(100));
                    result.add(r.doubleValue());
                }
            } else {
                result.add(0D);
            }
        }

        return result;
    }

    // 确诊病例增长率
    private static List<Double> r2(List<FyData> fyDataList){

        List<Double> result = Lists.newArrayList();

        for (int i = 0; i < fyDataList.size(); i++) {

            FyData data1 = fyDataList.get(i);

            if (i > 0) {
                FyData data0 = fyDataList.get(i - 1);
                BigDecimal d1 = new BigDecimal(data1.getCn_conNum());
                BigDecimal d0 = new BigDecimal(data0.getCn_conNum());
                if(data0.getCn_susNum() == 0){
                    result.add(0D);
                }else{
                    BigDecimal r = d1.divide(d0, 4, RoundingMode.HALF_UP)
                            .subtract(BigDecimal.valueOf(1))
                            .multiply(BigDecimal.valueOf(100));
                    result.add(r.doubleValue());
                }
            } else {
                result.add(0D);
            }
        }

        return result;
    }

    public static String print(List<Double> result){
        StringJoiner sj = new StringJoiner("\t");
        result.forEach(r -> sj.add(String.valueOf(r)));
        return sj.toString();
    }

}
