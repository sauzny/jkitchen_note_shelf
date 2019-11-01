package com.sauzny.antlr.labeled;

import com.sauzny.antlr.labeled.expr.LabeledExprLexer;
import com.sauzny.antlr.labeled.expr.LabeledExprParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {  
    public static void main(String[] args) throws Exception {  
       // create a CharStream thatreads from standard input  
       String inputFile = System.getProperty("user.dir") + "/data.txt";  
       InputStream is = System.in;  
        
       if ( inputFile!=null ) is = new FileInputStream(inputFile);  
       ANTLRInputStream input = new ANTLRInputStream(is);  
        
       LabeledExprLexer lexer = new LabeledExprLexer(input);  
       CommonTokenStream tokens = new CommonTokenStream(lexer);  
       LabeledExprParser parser = new LabeledExprParser(tokens);  
       ParseTree tree = parser.prog(); // parse  
       EvalVisitor eval = new EvalVisitor();  
       eval.visit(tree);
       eval.visit
    }  
}  
