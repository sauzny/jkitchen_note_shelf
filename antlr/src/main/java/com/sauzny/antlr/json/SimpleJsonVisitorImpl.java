package com.sauzny.antlr.json;

import com.sauzny.jkitchen_note.antlr.json.expr.SimpleJsonBaseVisitor;
import com.sauzny.jkitchen_note.antlr.json.expr.SimpleJsonParser.PrintKeyAndValueContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;
import java.util.Map;

public class SimpleJsonVisitorImpl extends SimpleJsonBaseVisitor<String>{
    
    Map<String, String> memory = new HashMap<String, String>();
    
    @Override
    public String visitPrintKeyAndValue(PrintKeyAndValueContext ctx) {
        // TODO Auto-generated method stub
        String key = ctx.expr().ABC(0).getText();
        String value = ctx.expr().ABC(1).getText();
        memory.put(key, value);
        return super.visitPrintKeyAndValue(ctx);
    }
    
    @Override
    public String visit(ParseTree tree) {
        // TODO Auto-generated method stub
        return super.visit(tree);
    }
}
