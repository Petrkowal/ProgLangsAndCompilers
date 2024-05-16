// Generated from /home/petr/IdeaProjects/pjp/src/main/antlr4/ProjectGrammar.g4 by ANTLR 4.13.1
package org.pjp.generated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ProjectGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ProjectGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ProjectGrammarParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(ProjectGrammarParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identifier}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(ProjectGrammarParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parentheses}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParentheses(ProjectGrammarParser.ParenthesesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code comparison}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(ProjectGrammarParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(ProjectGrammarParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code literals}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiterals(ProjectGrammarParser.LiteralsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryMinus}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryMinus(ProjectGrammarParser.UnaryMinusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arithmetic}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmetic(ProjectGrammarParser.ArithmeticContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicAndOr}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicAndOr(ProjectGrammarParser.LogicAndOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equality}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquality(ProjectGrammarParser.EqualityContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ternary}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTernary(ProjectGrammarParser.TernaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicNot}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicNot(ProjectGrammarParser.LogicNotContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGrammarParser#declarationStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationStatement(ProjectGrammarParser.DeclarationStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGrammarParser#emptyStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyStatement(ProjectGrammarParser.EmptyStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGrammarParser#exprStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStatement(ProjectGrammarParser.ExprStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGrammarParser#readStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadStatement(ProjectGrammarParser.ReadStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGrammarParser#writeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteStatement(ProjectGrammarParser.WriteStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGrammarParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(ProjectGrammarParser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGrammarParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(ProjectGrammarParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGrammarParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(ProjectGrammarParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGrammarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(ProjectGrammarParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGrammarParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(ProjectGrammarParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGrammarParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(ProjectGrammarParser.TypeContext ctx);
}