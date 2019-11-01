// Generated from SimpleJson.g4 by ANTLR 4.4

	package com.sauzny.antlr.json.expr;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SimpleJsonParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SimpleJsonVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code blank}
	 * labeled alternative in {@link SimpleJsonParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlank(@NotNull SimpleJsonParser.BlankContext ctx);
	/**
	 * Visit a parse tree produced by the {@code printKeyAndValue}
	 * labeled alternative in {@link SimpleJsonParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintKeyAndValue(@NotNull SimpleJsonParser.PrintKeyAndValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleJsonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(@NotNull SimpleJsonParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleJsonParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(@NotNull SimpleJsonParser.ProgContext ctx);
}