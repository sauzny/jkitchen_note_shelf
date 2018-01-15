package com.sauzny.tablesaw;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import tech.tablesaw.aggregate.CrossTab;
import tech.tablesaw.api.CategoryColumn;
import tech.tablesaw.api.DateColumn;
import tech.tablesaw.api.ShortColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.mapping.DateMapUtils;

public class Three {

    public static void main(String[] args) {

        String userDir = System.getProperty("user.dir");
        Path demo2 = Paths.get(userDir+"/demo2.csv");
        Path bushApproval = Paths.get(userDir+"/BushApproval.csv");
        
        try {

            Table t1 = Table.read().csv(demo2.toString());
            Table t2 = Table.read().csv(bushApproval.toString());

            DateColumn date1 = t1.dateColumn("riqi");
            DateColumn date2 = t2.dateColumn("date");
            
            /*
            Table table02 = Table.create("table02", 
                    table01.shortColumn("id"),
                    table01.dateColumn("riqi"),
                    table01.shortColumn("l"));
            
            ShortColumn lan = table02.shortColumn("l");
            
            DateColumn date = table02.dateColumn("riqi");
            CategoryColumn month = date.month();
            table02.addColumn(month);
            
            Table xtab = CrossTab.xTabCount(table02, month, lan);
            
            out(xtab.print());

            out(CrossTab.tablePercents(xtab).print());
            */
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private static void out(Object str) {
        System.out.println(String.valueOf(str));
        System.out.println("");
    }
}
