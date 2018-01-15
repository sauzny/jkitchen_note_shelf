package com.sauzny.tablesaw;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import tech.tablesaw.aggregate.CrossTab;
import tech.tablesaw.api.CategoryColumn;
import tech.tablesaw.api.DateColumn;
import tech.tablesaw.api.Table;

public class Two {

    public static void main(String[] args) {
        String userDir = System.getProperty("user.dir");
        Path demo1 = Paths.get(userDir+"/demo1.csv");
        Path bushApproval = Paths.get(userDir+"/BushApproval.csv");
        
        // create our table from a flat file:
        try {
            
            Table table1 = Table.read().csv(bushApproval.toString());

            out(table1.print());
            
            DateColumn date = table1.dateColumn("date");
            CategoryColumn month = date.month();
            table1.addColumn(month);
            
            CategoryColumn who = table1.categoryColumn("who");
            
            Table xtab = CrossTab.xTabCount(table1, month, who);
            out(xtab.print());

            out(CrossTab.tablePercents(xtab).print());
            
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
