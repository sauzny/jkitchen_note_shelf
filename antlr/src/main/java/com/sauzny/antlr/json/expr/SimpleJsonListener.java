// Generated from SimpleJson.g4 by ANTLR 4.4

	package com.sauzny.antlr.json.expr;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SimpleJsonParser}.
 */
public interface SimpleJsonListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code blank}
	 * labeled alternative in {@link SimpleJsonParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterBlank(@NotNull SimpleJsonParser.BlankContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blank}
	 * labeled alternative in {@link SimpleJsonParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitBlank(@NotNull SimpleJsonParser.BlankContext ctx);
	/**
	 * Enter a parse tree produced by the {@code printKeyAndValue}
	 * labeled alternative in {@link SimpleJsonParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterPrintKeyAndValue(@NotNull SimpleJsonParser.PrintKeyAndValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code printKeyAndValue}
	 * labeled alternative in {@link SimpleJsonParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitPrintKeyAndValue(@NotNull SimpleJsonParser.PrintKeyAndValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleJsonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(@NotNull SimpleJsonParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleJsonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(@NotNull SimpleJsonParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleJsonParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(@NotNull SimpleJsonParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleJsonParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(@NotNull SimpleJsonParser.ProgContext ctx);
}