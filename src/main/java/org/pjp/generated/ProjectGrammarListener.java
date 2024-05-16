// Generated from /home/petr/IdeaProjects/pjp/src/main/antlr4/ProjectGrammar.g4 by ANTLR 4.13.1
package org.pjp.generated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ProjectGrammarParser}.
 */
public interface ProjectGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ProjectGrammarParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(ProjectGrammarParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGrammarParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(ProjectGrammarParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifier}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(ProjectGrammarParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifier}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(ProjectGrammarParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parentheses}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParentheses(ProjectGrammarParser.ParenthesesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parentheses}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParentheses(ProjectGrammarParser.ParenthesesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code comparison}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterComparison(ProjectGrammarParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by the {@code comparison}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitComparison(ProjectGrammarParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(ProjectGrammarParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(ProjectGrammarParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code literals}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLiterals(ProjectGrammarParser.LiteralsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code literals}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLiterals(ProjectGrammarParser.LiteralsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryMinus}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinus(ProjectGrammarParser.UnaryMinusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryMinus}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinus(ProjectGrammarParser.UnaryMinusContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arithmetic}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic(ProjectGrammarParser.ArithmeticContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arithmetic}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic(ProjectGrammarParser.ArithmeticContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicAndOr}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLogicAndOr(ProjectGrammarParser.LogicAndOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicAndOr}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLogicAndOr(ProjectGrammarParser.LogicAndOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equality}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEquality(ProjectGrammarParser.EqualityContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equality}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEquality(ProjectGrammarParser.EqualityContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ternary}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterTernary(ProjectGrammarParser.TernaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ternary}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitTernary(ProjectGrammarParser.TernaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicNot}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLogicNot(ProjectGrammarParser.LogicNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicNot}
	 * labeled alternative in {@link ProjectGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLogicNot(ProjectGrammarParser.LogicNotContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGrammarParser#declarationStatement}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationStatement(ProjectGrammarParser.DeclarationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGrammarParser#declarationStatement}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationStatement(ProjectGrammarParser.DeclarationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGrammarParser#emptyStatement}.
	 * @param ctx the parse tree
	 */
	void enterEmptyStatement(ProjectGrammarParser.EmptyStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGrammarParser#emptyStatement}.
	 * @param ctx the parse tree
	 */
	void exitEmptyStatement(ProjectGrammarParser.EmptyStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGrammarParser#exprStatement}.
	 * @param ctx the parse tree
	 */
	void enterExprStatement(ProjectGrammarParser.ExprStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGrammarParser#exprStatement}.
	 * @param ctx the parse tree
	 */
	void exitExprStatement(ProjectGrammarParser.ExprStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGrammarParser#readStatement}.
	 * @param ctx the parse tree
	 */
	void enterReadStatement(ProjectGrammarParser.ReadStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGrammarParser#readStatement}.
	 * @param ctx the parse tree
	 */
	void exitReadStatement(ProjectGrammarParser.ReadStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGrammarParser#writeStatement}.
	 * @param ctx the parse tree
	 */
	void enterWriteStatement(ProjectGrammarParser.WriteStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGrammarParser#writeStatement}.
	 * @param ctx the parse tree
	 */
	void exitWriteStatement(ProjectGrammarParser.WriteStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGrammarParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(ProjectGrammarParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGrammarParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(ProjectGrammarParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGrammarParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(ProjectGrammarParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGrammarParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(ProjectGrammarParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGrammarParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(ProjectGrammarParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGrammarParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(ProjectGrammarParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ProjectGrammarParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ProjectGrammarParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGrammarParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(ProjectGrammarParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGrammarParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(ProjectGrammarParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(ProjectGrammarParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(ProjectGrammarParser.TypeContext ctx);
}