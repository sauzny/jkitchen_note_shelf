package com.sauzny.poi;


import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.StringJoiner;

public class Demo01 {

    // HSSF类，只支持2007以前的excel（文件扩展名为xls） ---pom引入poi
    // 而XSSF支持07以后的                             ---pom引入poi-ooxml

    /**
     * 将demo01的sheet2做ETL输出到新的文件中
     * 主要目的是去掉en列单元格中的换行，使用base64的方式将单元格内容编码
     */
    public static void rw() {
        Path path = Paths.get("demo01.xlsx");
        Charset charsets = StandardCharsets.UTF_8;

        List<String> list = new ArrayList<>();

        try {
            //HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(path.toString()));
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(path.toString()));
            // 工作表从0开始，最大值 hssfWorkbook.getNumberOfSheets()
            //HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(1);
            XSSFSheet xssfSheet = wb.getSheetAt(1);

            // 循环行，跳过第0行
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                //HSSFRow hssfRow = xssfSheet.getRow(rowNum);
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow == null) continue;

                StringJoiner sj = new StringJoiner("\t");
                // 循环列Cell
                // 0序号 1组名 2key 3en 4zh
                // 这里多读了一列 不知什么情况
                for (int cellNum = 0; cellNum <= xssfRow.getLastCellNum()-1; cellNum++) {
                    if (cellNum == 0) {
                        sj.add(String.valueOf(xssfRow.getCell(cellNum).getNumericCellValue()));
                    } else if (cellNum != 3) {
                        sj.add(xssfRow.getCell(cellNum).getStringCellValue());
                    } else {
                        String en = xssfRow.getCell(cellNum).getStringCellValue();
                        byte[] bytes = Base64.getEncoder().encode(en.getBytes(charsets));
                        sj.add(new String(bytes, charsets));
                    }
                }

                list.add(sj.toString());
            }

            // 输出到新的文件
            // open选项，如果不存在就创建。会覆盖原有的内容
            Files.write(Paths.get("demo01.txt"), list, charsets, StandardOpenOption.CREATE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void base64(){
        Charset charsets = StandardCharsets.UTF_8;
        String en = "abc";
        System.out.println(en);
        byte[] bytes = Base64.getEncoder().encode(en.getBytes(charsets));
        System.out.println(new String(bytes, charsets));

    }

    public static void main(String[] args) {
        Demo01.rw();
    }
}
