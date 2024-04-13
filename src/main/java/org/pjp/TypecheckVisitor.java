package org.pjp;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.pjp.generated.ProjectGrammarBaseVisitor;
import org.pjp.generated.ProjectGrammarParser;

import java.util.List;

public class TypecheckVisitor extends ProjectGrammarBaseVisitor<Type> {

    private final SymbolTable symbolTable = new SymbolTable();
    private final ParseTreeProperty<Type> types = new ParseTreeProperty<Type>();

    public ParseTreeProperty<Type> getTypes() {
        return types;
    }

    @Override
    public Type visitProg(ProjectGrammarParser.ProgContext ctx) {
        for (ProjectGrammarParser.StatementContext statement : ctx.statement()) {
            visit(statement);
        }
        return null;
    }

    @Override
    public Type visitIdentifier(ProjectGrammarParser.IdentifierContext ctx) {
        Type type = symbolTable.Get(ctx.ID().getSymbol());
        types.put(ctx, type);
        return type;
    }

    @Override
    public Type visitParentheses(ProjectGrammarParser.ParenthesesContext ctx) {
        Type type = visit(ctx.expr());
        types.put(ctx, type);
        return type;
    }

    @Override
    public Type visitLogicAndOr(ProjectGrammarParser.LogicAndOrContext ctx) {
        Type left = visit(ctx.expr(0));
        Type right = visit(ctx.expr(1));

        if (left == Type.ERROR || right == Type.ERROR) {
            return Type.ERROR;
        }

        if (left != Type.BOOL || right != Type.BOOL) {
            Errors.addError(ctx.op, "Type mismatch in logical AND: '" + left + "' and '" + right + "', expected bool.");
            return Type.ERROR;
        }
        types.put(ctx, Type.BOOL);
        return Type.BOOL;
    }

    @Override
    public Type visitComparison(ProjectGrammarParser.ComparisonContext ctx) {
        Type left = visit(ctx.expr(0));
        Type right = visit(ctx.expr(1));

        types.put(ctx.expr(0), left);
        types.put(ctx.expr(1), right);

        if (left == Type.ERROR || right == Type.ERROR) {
            return Type.ERROR;
        }

        if (left != right) {
            if (left == Type.INT && right == Type.FLOAT) {
                types.put(ctx.expr(0), Type.INT);
                left = Type.FLOAT;
            }
            else if (left == Type.FLOAT && right == Type.INT) {
                types.put(ctx.expr(1), Type.INT);
                right = Type.FLOAT;
            }
            else {
                Errors.addError(ctx.op, "Type mismatch in comparison: '" + left + "' and '" + right + "'.");
                return Type.ERROR;
            }
        }

        if (left != Type.INT && left != Type.FLOAT) {
            Errors.addError(ctx.op, "Invalid type for comparison: '" + left + "', expected int or float..");
            return Type.ERROR;
        }
        types.put(ctx, Type.BOOL);
        return Type.BOOL;
    }

    @Override
    public Type visitAssignment(ProjectGrammarParser.AssignmentContext ctx) {
        Type left = symbolTable.Get(ctx.ID().getSymbol());
        Type right = visit(ctx.expr());

        if (left == Type.ERROR || right == Type.ERROR) {
            return Type.ERROR;
        }

        if (left != right) {
            if (left == Type.FLOAT && right == Type.INT) {
                types.put(ctx.expr(), Type.INT);
                right = Type.FLOAT;
            }
            else {
                Errors.addError(ctx.op, "Type mismatch in assignment: '" + left + "' and '" + right + "'.");
                return Type.ERROR;
            }
        }
        types.put(ctx, left);
        return left;
    }

    @Override
    public Type visitUnaryMinus(ProjectGrammarParser.UnaryMinusContext ctx) {
        Type type = visit(ctx.expr());
        if (type == Type.ERROR) {
            return Type.ERROR;
        }

        if (type != Type.INT && type != Type.FLOAT) {
            Errors.addError(ctx.expr().start, "Invalid type for unary minus: '" + type + "', expected int or float.");
            return Type.ERROR;
        }
        types.put(ctx, type);
        return type;
    }

    @Override
    public Type visitArithmetic(ProjectGrammarParser.ArithmeticContext ctx) {
        Type left = visit(ctx.expr(0));
        Type right = visit(ctx.expr(1));

        if (left == Type.ERROR || right == Type.ERROR) {
            return Type.ERROR;
        }

        if (left != right){
            if (left == Type.INT && right == Type.FLOAT) {
                types.put(ctx.expr(0), Type.INT);
                left = Type.FLOAT;
            }
            else if (left == Type.FLOAT && right == Type.INT) {
                types.put(ctx.expr(1), Type.INT);
                right = Type.FLOAT;
            }
            else {
                Errors.addError(ctx.op, "Type mismatch in arithmetic operation '" + ctx.op.getText() + "': '" + left + "' and '" + right + "'.");
                return Type.ERROR;
            }
            types.put(ctx, Type.FLOAT);
            return Type.FLOAT;
        }

        if (ctx.op.getText().equals("%")) {
            if (left != Type.INT) {
                Errors.addError(ctx.op, "Invalid type for modulo operation: '" + left + "', expected int.");
                return Type.ERROR;
            }
            types.put(ctx, Type.INT);
            return Type.INT;
        }

        if (ctx.op.getText().equals(".")) {
            if (left != Type.STRING) {
                Errors.addError(ctx.op, "Invalid type for string concatenation operation: '" + left + "', expected string.");
                return Type.ERROR;
            }
            types.put(ctx, Type.STRING);
            return Type.STRING;
        }

        else {
            if (left == Type.INT || left == Type.FLOAT) {
                types.put(ctx, left);
                return left;
            }
            Errors.addError(ctx.op, "Invalid type for arithmetic operation '" + ctx.op.getText() + "': '" + left + "', expected int or float.");
            return Type.ERROR;
        }
    }

    @Override
    public Type visitEquality(ProjectGrammarParser.EqualityContext ctx) {
        Type left = visit(ctx.expr(0));
        Type right = visit(ctx.expr(1));

        if (left == Type.ERROR || right == Type.ERROR) {
            return Type.ERROR;
        }

        if (left != right) {
            if (left == Type.FLOAT && right == Type.INT) {
                types.put(ctx.expr(1), Type.INT);
                right = Type.FLOAT;
            }
            else if (left == Type.INT && right == Type.FLOAT) {
                types.put(ctx.expr(0), Type.INT);
                left = Type.FLOAT;
            }
            else {
                Errors.addError(ctx.op, "Type mismatch in equality comparison: '" + left + "' and '" + right + "'.");
                return Type.ERROR;
            }
        }

        if (left != Type.INT && left != Type.FLOAT && left != Type.STRING) {
            Errors.addError(ctx.op, "Invalid type for equality comparison: '" + left + "', expected int, float or string.");
            return Type.ERROR;
        }
        types.put(ctx, Type.BOOL);
        return Type.BOOL;
    }

    @Override
    public Type visitLogicNot(ProjectGrammarParser.LogicNotContext ctx) {
        Type type = visit(ctx.expr());
        if (type != Type.BOOL) {
            Errors.addError(ctx.expr().start, "Invalid type for logical NOT: '" + type + "', expected bool.");
            return Type.ERROR;
        }
        types.put(ctx, Type.BOOL);
        return Type.BOOL;
    }

    @Override
    public Type visitTernary(ProjectGrammarParser.TernaryContext ctx) {
        Type condition = visit(ctx.expr(0));
        Type left = visit(ctx.expr(1));
        Type right = visit(ctx.expr(2));

        if (condition == Type.ERROR || left == Type.ERROR || right == Type.ERROR) {
            return Type.ERROR;
        }

        if (condition != Type.BOOL) {
            Errors.addError(ctx.expr(0).start, "Invalid type for ternary condition: '" + condition + "', expected bool.");
            return Type.ERROR;
        }

        if (left != right) {
            if (left == Type.FLOAT && right == Type.INT) {
                types.put(ctx.expr(2), Type.INT);
                right = Type.FLOAT;
            }
            else if (left == Type.INT && right == Type.FLOAT) {
                types.put(ctx.expr(1), Type.INT);
                left = Type.FLOAT;
            }
            else {
                Errors.addError(ctx.expr(2).start, "Type mismatch in ternary operation: '" + left + "' and '" + right + "'.");
                return Type.ERROR;
            }
        }
        types.put(ctx, left);
        return left;
    }

    @Override
    public Type visitDeclarationStatement(ProjectGrammarParser.DeclarationStatementContext ctx) {
        if (visit(ctx.type()) == Type.ERROR) {
            Errors.addError(ctx.type().start, "Invalid type.");
            return Type.ERROR;
        }

        for (TerminalNode terminalNode : ctx.ID()) {
            Token token = terminalNode.getSymbol();
            Type type = visit(ctx.type());
            if (!symbolTable.Add(token, type)) {
                return Type.ERROR;
            }
            types.put(terminalNode, type);
        }
        return null;
    }

    @Override
    public Type visitIfStatement(ProjectGrammarParser.IfStatementContext ctx) {
        if (visit(ctx.expr()) != Type.BOOL) {
            return Type.ERROR;
        }
        visit(ctx.statement(0));
        if (ctx.statement().size() > 1) {
            visit(ctx.statement(1));
        }
        return null;
    }

    @Override
    public Type visitReadStatement(ProjectGrammarParser.ReadStatementContext ctx) {
        List<TerminalNode> identifiers = ctx.ID();
        for (TerminalNode identifier : identifiers) {
            if (symbolTable.Get(identifier.getSymbol()) == Type.ERROR) {
                return Type.ERROR;
            }
            types.put(identifier, symbolTable.Get(identifier.getSymbol()));
        }
        return null;
    }

    @Override
    public Type visitWriteStatement(ProjectGrammarParser.WriteStatementContext ctx) {
        for (ProjectGrammarParser.ExprContext expr : ctx.expr()) {
            if (visit(expr) == Type.ERROR) {
                return Type.ERROR;
            }
        }
        return null;
    }

    @Override
    public Type visitWhileStatement(ProjectGrammarParser.WhileStatementContext ctx) {
        if (visit(ctx.expr()) != Type.BOOL) {
            return Type.ERROR;
        }
        visit(ctx.statement());
        return null;
    }

    @Override
    public Type visitLiteral(ProjectGrammarParser.LiteralContext ctx) {
        return switch (ctx.getStart().getType()) {
            case ProjectGrammarParser.INT -> {
                types.put(ctx, Type.INT);
                yield Type.INT;
            }
            case ProjectGrammarParser.FLOAT -> {
                types.put(ctx, Type.FLOAT);
                yield Type.FLOAT;
            }
            case ProjectGrammarParser.BOOL -> {
                types.put(ctx, Type.BOOL);
                yield Type.BOOL;
            }
            case ProjectGrammarParser.STRING -> {
                types.put(ctx, Type.STRING);
                yield Type.STRING;
            }
            default -> {
                Errors.addError(ctx.start, "Invalid literal '" + ctx.getText() + "'.");
                yield Type.ERROR;
            }
        };
    }

    @Override
    public Type visitType(ProjectGrammarParser.TypeContext ctx) {
        return switch (ctx.getText()) {
            case "int" -> {
                types.put(ctx, Type.INT);
                yield Type.INT;
            }
            case "float" -> {
                types.put(ctx, Type.FLOAT);
                yield Type.FLOAT;
            }
            case "bool" -> {
                types.put(ctx, Type.BOOL);
                yield Type.BOOL;
            }
            case "string" -> {
                types.put(ctx, Type.STRING);
                yield Type.STRING;
            }
            default -> {
                Errors.addError(ctx.start, "Invalid type '" + ctx.getText() + "'.");
                yield Type.ERROR;
            }
        };
    }



}
