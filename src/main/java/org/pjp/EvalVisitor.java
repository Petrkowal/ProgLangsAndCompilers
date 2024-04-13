package org.pjp;


import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.tool.ast.TerminalAST;
import org.pjp.generated.ProjectGrammarBaseVisitor;
import org.pjp.generated.ProjectGrammarParser;

import java.util.ArrayList;
import java.util.List;


public class EvalVisitor extends ProjectGrammarBaseVisitor<List<Instruction>> {

    public void setTypes(ParseTreeProperty<Type> types) {
        this.types = types;
    }

    private ParseTreeProperty<Type> types;
    private int labelCounter = 0;

    @Override
    public List<Instruction> visitProg(ProjectGrammarParser.ProgContext ctx) {
        ArrayList<Instruction> result = new ArrayList<>();
        for (ProjectGrammarParser.StatementContext statement : ctx.statement()) {
            result.addAll(visit(statement));
        }
        return result;
    }

    @Override
    public List<Instruction> visitIdentifier(ProjectGrammarParser.IdentifierContext ctx) {
        ArrayList<Instruction> result = new ArrayList<>();
        result.add(new Load(ctx.ID().getText(), true));  // TODO Remove space end
        return result;
    }

    @Override
    public List<Instruction> visitParentheses(ProjectGrammarParser.ParenthesesContext ctx) {
        ArrayList<Instruction> result = new ArrayList<>(visit(ctx.expr()));
        return result;
    }

    @Override
    public List<Instruction> visitComparison(ProjectGrammarParser.ComparisonContext ctx) {
        ArrayList<Instruction> result = new ArrayList<>();
        List<Instruction> left = visit(ctx.expr(0));
        List<Instruction> right = visit(ctx.expr(1));
        Type leftType = types.get(ctx.expr(0));
        Type rightType = types.get(ctx.expr(1));
        if (leftType != rightType) {

            result.addAll(left);
            if (leftType == Type.INT){
                result.add(new Itof());
            }
            result.addAll(right);
            if (rightType == Type.INT){
                result.add(new Itof());
            }
        }
        else{
            result.addAll(left);
            result.addAll(right);
        }

        switch (ctx.op.getText()) {
            case "<":
                result.add(new Lt());
                break;
            case ">":
                result.add(new Gt());
                break;
        }
        return result;
    }

    @Override
    public List<Instruction> visitAssignment(ProjectGrammarParser.AssignmentContext ctx) {
        ArrayList<Instruction> result = new ArrayList<>(visit(ctx.expr()));
        Type type = types.get(ctx);
        Type rightType = types.get(ctx.expr());
        if (type == Type.FLOAT && rightType == Type.INT){
            result.add(new Itof());
        }
        result.add(new Save(ctx.ID().getText()));
        result.add(new Load(ctx.ID().getText(), false));  // TODO: Remoe space end
        return result;
    }

    @Override
    public List<Instruction> visitUnaryMinus(ProjectGrammarParser.UnaryMinusContext ctx) {
        ArrayList<Instruction> result = new ArrayList<>(visit(ctx.expr()));
        result.add(new Uminus());
        return result;
    }

    @Override
    public List<Instruction> visitArithmetic(ProjectGrammarParser.ArithmeticContext ctx) {
        ArrayList<Instruction> result = new ArrayList<>();
        List<Instruction> left = visit(ctx.expr(0));
        List<Instruction> right = visit(ctx.expr(1));
        Type type = types.get(ctx);
        if (type == Type.FLOAT){
            Type leftType = types.get(ctx.expr(0));
            Type rightType = types.get(ctx.expr(1));

            result.addAll(left);
            if (leftType == Type.INT){
                result.add(new Itof());
            }
            result.addAll(right);
            if (rightType == Type.INT){
                result.add(new Itof());
            }
        }
        else{
            result.addAll(left);
            result.addAll(right);
        }

        switch (ctx.op.getText()) {
            case "+":
                result.add(new Add());
                break;
            case "-":
                result.add(new Sub());
                break;
            case "*":
                result.add(new Mul());
                break;
            case "/":
                result.add(new Div());
                break;
            case "%":
                result.add(new Mod());
                break;
            case ".":
                result.add(new Concat());
                break;
        }
        return result;
    }

    @Override
    public List<Instruction> visitLogicAndOr(ProjectGrammarParser.LogicAndOrContext ctx) {
        ArrayList<Instruction> result = new ArrayList<>(visit(ctx.expr(0)));
        result.addAll(visit(ctx.expr(1)));
        switch (ctx.op.getText()) {
            case "&&":
                result.add(new And());
                break;
            case "||":
                result.add(new Or());
                break;
        }
        return result;
    }

    @Override
    public List<Instruction> visitEquality(ProjectGrammarParser.EqualityContext ctx) {
        ArrayList<Instruction> result = new ArrayList<>();
        List<Instruction> left = visit(ctx.expr(0));
        List<Instruction> right = visit(ctx.expr(1));
        Type type = types.get(ctx);
        if (type == Type.FLOAT){
            Type leftType = types.get(ctx.expr(0));
            Type rightType = types.get(ctx.expr(1));

            result.addAll(left);
            if (leftType == Type.INT){
                result.add(new Itof());
            }
            result.addAll(right);
            if (rightType == Type.INT){
                result.add(new Itof());
            }
        }
        else{
            result.addAll(left);
            result.addAll(right);
        }

        switch (ctx.op.getText()) {
            case "==":
                result.add(new Eq());
                break;
            case "!=":
                result.add(new Eq());
                result.add(new Not());
                break;
        }
        return result;
    }

    @Override
    public List<Instruction> visitTernary(ProjectGrammarParser.TernaryContext ctx) {
        List<Instruction> condition = visit(ctx.expr(0));
        List<Instruction> trueBranch = visit(ctx.expr(1));
        List<Instruction> falseBranch = visit(ctx.expr(2));
        int falseLabel = labelCounter++;
        int endLabel = labelCounter++;
        ArrayList<Instruction> result = new ArrayList<>(condition);
        result.add(new Fjmp(falseLabel));
        result.addAll(trueBranch);
        result.add(new Jmp(endLabel));
        result.add(new Label(falseLabel));
        result.addAll(falseBranch);
        result.add(new Label(endLabel));
        return result;
    }

    @Override
    public List<Instruction> visitLogicNot(ProjectGrammarParser.LogicNotContext ctx) {
        ArrayList<Instruction> result = new ArrayList<>(visit(ctx.expr()));
        result.add(new Not());
        return result;
    }

    @Override
    public List<Instruction> visitDeclarationStatement(ProjectGrammarParser.DeclarationStatementContext ctx) {
        ArrayList<Instruction> result = new ArrayList<>();
        Object defaultValue = null;
        Type type = switch (ctx.type().getText()) {
            case "int" -> {
                defaultValue = 0;
                yield Type.INT;
            }
            case "float" -> {
                defaultValue = 0.0;
                yield Type.FLOAT;
            }
            case "bool" -> {
                defaultValue = true; // TODO: Change to false
                yield Type.BOOL;
            }
            case "string" -> {
                defaultValue = "\"\"";
                yield Type.STRING;
            }
            default -> null;
        };
        assert type != null;

        for (TerminalNode id : ctx.ID()) {
            result.add(new Push(type, defaultValue));
            result.add(new Save(id.getText()));
        }

        return result;
    }

    @Override
    public List<Instruction> visitEmptyStatement(ProjectGrammarParser.EmptyStatementContext ctx) {
        return new ArrayList<>();
    }

    @Override
    public List<Instruction> visitExprStatement(ProjectGrammarParser.ExprStatementContext ctx) {
        ArrayList<Instruction> result = new ArrayList<>(visit(ctx.expr()));
        result.add(new Pop());
        return result;
    }

    @Override
    public List<Instruction> visitReadStatement(ProjectGrammarParser.ReadStatementContext ctx) {
        ArrayList<Instruction> result = new ArrayList<>();
        for (TerminalNode id : ctx.ID()) {
            result.add(new Read(types.get(id)));
            result.add(new Save(id.getText()));
        }
        return result;
    }

    @Override
    public List<Instruction> visitWriteStatement(ProjectGrammarParser.WriteStatementContext ctx) {
        ArrayList<Instruction> result = new ArrayList<>();
        for (ProjectGrammarParser.ExprContext expr : ctx.expr()) {
            result.addAll(visit(expr));
        }
        result.add(new Print(ctx.expr().size()));
//        result.add(new Push(Type.STRING, "\n"));
//        result.add(new Print(1));
        return result;
    }

    @Override
    public List<Instruction> visitBlockStatement(ProjectGrammarParser.BlockStatementContext ctx) {
        ArrayList<Instruction> result = new ArrayList<>();
        for (ProjectGrammarParser.StatementContext statement : ctx.statement()) {
            result.addAll(visit(statement));
        }
        return result;
    }

    @Override
    public List<Instruction> visitIfStatement(ProjectGrammarParser.IfStatementContext ctx) {
        List<Instruction> condition = visit(ctx.expr());
        ArrayList<Instruction> result = new ArrayList<>(condition);
        int elseLabel = labelCounter++;
        int endLabel = labelCounter++;
        result.add(new Fjmp(elseLabel));
        List<Instruction> then = visit(ctx.statement(0));
        result.addAll(then);
        result.add(new Jmp(endLabel));
        result.add(new Label(elseLabel));
        if (ctx.statement().size() > 1) {
            List<Instruction> elseCode = visit(ctx.statement(1));
            result.addAll(elseCode);
        }
        result.add(new Label(endLabel));
        return result;
    }

    @Override
    public List<Instruction> visitWhileStatement(ProjectGrammarParser.WhileStatementContext ctx) {
        int startLabel = labelCounter++;
        int endLabel = labelCounter++;
        ArrayList<Instruction> result = new ArrayList<>();
        result.add(new Label(startLabel));
        result.addAll(visit(ctx.expr()));
        result.add(new Fjmp(endLabel));
        result.addAll(visit(ctx.statement()));
        result.add(new Jmp(startLabel));
        result.add(new Label(endLabel));
        return result;
    }

    @Override
    public List<Instruction> visitStatement(ProjectGrammarParser.StatementContext ctx) {
        ArrayList<Instruction> result = new ArrayList<>();
        if (ctx.blockStatement() != null) {
            result.addAll(visit(ctx.blockStatement()));
        }
        if (ctx.declarationStatement() != null) {
            result.addAll(visit(ctx.declarationStatement()));
        }
        if (ctx.emptyStatement() != null) {
            result.addAll(visit(ctx.emptyStatement()));
        }
        if (ctx.exprStatement() != null) {
            result.addAll(visit(ctx.exprStatement()));
        }
        if (ctx.ifStatement() != null) {
            result.addAll(visit(ctx.ifStatement()));
        }
        if (ctx.readStatement() != null) {
            result.addAll(visit(ctx.readStatement()));
        }
        if (ctx.writeStatement() != null) {
            result.addAll(visit(ctx.writeStatement()));
        }
        if (ctx.whileStatement() != null) {
            result.addAll(visit(ctx.whileStatement()));
        }
        return result;
    }

    @Override
    public List<Instruction> visitLiteral(ProjectGrammarParser.LiteralContext ctx) {
        ArrayList<Instruction> result = new ArrayList<>();
        switch (ctx.getStart().getType()) {
            case ProjectGrammarParser.INT:
                result.add(new Push(Type.INT, ctx.INT().getText()));
                break;
            case ProjectGrammarParser.FLOAT:
                result.add(new Push(Type.FLOAT, ctx.FLOAT().getText()));
                break;
            case ProjectGrammarParser.BOOL:
                result.add(new Push(Type.BOOL, ctx.BOOL().getText()));
                break;
            case ProjectGrammarParser.STRING:
                result.add(new Push(Type.STRING, ctx.STRING().getText()));
                break;
        }
        return result;
    }

}
