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

public class Demo02 {

    // HSSF类，只支持2007以前的excel（文件扩展名为xls） ---pom引入poi
    // 而XSSF支持07以后的                             ---pom引入poi-ooxml

    /**
     * 将demo01的sheet2转换成sql
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
                // 循环列Cell
                // 0序号 1组名 2key 3en 4zh
                // 这里多读了一列 不知什么情况
                String[] temps = new String[4];
                for (int cellNum = 0; cellNum <= xssfRow.getLastCellNum()-1; cellNum++) {
                    if (cellNum == 0) {
                    } else {
                        temps[cellNum-1] = xssfRow.getCell(cellNum).getStringCellValue();
                    }
                }

                StringJoiner sj = new StringJoiner("', '");
                sj.add(temps[1]);
                sj.add(temps[3]);
                sj.add(temps[0]);
                sj.add(temps[2].replaceAll("\n","\\\\n"));
                sj.add(temps[3]);
                // insert into aaabbb values ();
                String sql = "insert into aaabbb values ("+rowNum+", '"+sj.toString()+"', '', '', now());";
                list.add(sql);
            }

            // 输出到新的文件
            // open选项，如果不存在就创建。会覆盖原有的内容
            Files.write(Paths.get("demo02.sql"), list, charsets, StandardOpenOption.CREATE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Demo02.rw();
    }
}
