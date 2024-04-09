package org.pjp;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.pjp.generated.ProjectGrammarBaseVisitor;
import org.pjp.generated.ProjectGrammarParser;

import java.util.List;

public class TypecheckVisitor extends ProjectGrammarBaseVisitor<Type> {

    private final SymbolTable symbolTable = new SymbolTable();

    @Override
    public Type visitProg(ProjectGrammarParser.ProgContext ctx) {
        for (ProjectGrammarParser.StatementContext statement : ctx.statement()) {
            visit(statement);
        }
        return null;
    }

    @Override
    public Type visitIdentifier(ProjectGrammarParser.IdentifierContext ctx) {
        return symbolTable.Get(ctx.ID().getSymbol());
    }

    @Override
    public Type visitParentheses(ProjectGrammarParser.ParenthesesContext ctx) {
        return (Type) visit(ctx.expr());
    }

    @Override
    public Type visitLogicAndOr(ProjectGrammarParser.LogicAndOrContext ctx) {
        Type left = (Type) visit(ctx.expr(0));
        Type right = (Type) visit(ctx.expr(1));

        if (left == Type.ERROR || right == Type.ERROR) {
            return Type.ERROR;
        }

        if (left != Type.BOOL || right != Type.BOOL) {
            Errors.addError(ctx.op, "Type mismatch in logical AND: '" + left + "' and '" + right + "', expected bool.");
            return Type.ERROR;
        }

        return Type.BOOL;
    }

    @Override
    public Type visitComparison(ProjectGrammarParser.ComparisonContext ctx) {
        Type left = (Type) visit(ctx.expr(0));
        Type right = (Type) visit(ctx.expr(1));

        if (left == Type.ERROR || right == Type.ERROR) {
            return Type.ERROR;
        }

        if (left != right) {
            if (left == Type.INT && right == Type.FLOAT) {
                left = Type.FLOAT;
            }
            else if (left == Type.FLOAT && right == Type.INT) {
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
        return Type.BOOL;
    }

    @Override
    public Type visitAssignment(ProjectGrammarParser.AssignmentContext ctx) {
        Type left = symbolTable.Get(ctx.ID().getSymbol());
        Type right = (Type) visit(ctx.expr());

        if (left == Type.ERROR || right == Type.ERROR) {
            return Type.ERROR;
        }

        if (left != right) {
            if (left == Type.FLOAT && right == Type.INT) {
                right = Type.FLOAT;
            }
            else {
                Errors.addError(ctx.op, "Type mismatch in assignment: '" + left + "' and '" + right + "'.");
                return Type.ERROR;
            }
        }
        return left;
    }

    @Override
    public Type visitUnaryMinus(ProjectGrammarParser.UnaryMinusContext ctx) {
        Type type = (Type) visit(ctx.expr());
        if (type == Type.ERROR) {
            return Type.ERROR;
        }

        if (type != Type.INT && type != Type.FLOAT) {
            Errors.addError(ctx.expr().start, "Invalid type for unary minus: '" + type + "', expected int or float.");
            return Type.ERROR;
        }
        return type;
    }

    @Override
    public Type visitArithmetic(ProjectGrammarParser.ArithmeticContext ctx) {
        Type left = (Type) visit(ctx.expr(0));
        Type right = (Type) visit(ctx.expr(1));

        if (left == Type.ERROR || right == Type.ERROR) {
            return Type.ERROR;
        }

        if (left != right){
            if (left == Type.INT && right == Type.FLOAT) {
                left = Type.FLOAT;
            }
            else if (left == Type.FLOAT && right == Type.INT) {
                right = Type.FLOAT;
            }
            else {
                Errors.addError(ctx.op, "Type mismatch in arithmetic operation '" + ctx.op.getText() + "': '" + left + "' and '" + right + "'.");
                return Type.ERROR;
            }
        }

        if (ctx.op.getText().equals("%")) {
            if (left != Type.INT) {
                Errors.addError(ctx.op, "Invalid type for modulo operation: '" + left + "', expected int.");
                return Type.ERROR;
            }
            return Type.INT;
        }

        if (ctx.op.getText().equals(".")) {
            if (left != Type.STRING) {
                Errors.addError(ctx.op, "Invalid type for string concatenation operation: '" + left + "', expected string.");
                return Type.ERROR;
            }
            return Type.STRING;
        }

        else {
            if (left == Type.INT || left == Type.FLOAT) {
                return left;
            }
            Errors.addError(ctx.op, "Invalid type for arithmetic operation '" + ctx.op.getText() + "': '" + left + "', expected int or float.");
            return Type.ERROR;

        }

    }

    @Override
    public Type visitEquality(ProjectGrammarParser.EqualityContext ctx) {
        Type left = (Type) visit(ctx.expr(0));
        Type right = (Type) visit(ctx.expr(1));

        if (left == Type.ERROR || right == Type.ERROR) {
            return Type.ERROR;
        }

        if (left != right) {
            if (left == Type.FLOAT && right == Type.INT) {
                right = Type.FLOAT;
            }
            else if (left == Type.INT && right == Type.FLOAT) {
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
        return Type.BOOL;
    }

    @Override
    public Type visitLogicNot(ProjectGrammarParser.LogicNotContext ctx) {
        Type type = (Type) visit(ctx.expr());
        if (type != Type.BOOL) {
            Errors.addError(ctx.expr().start, "Invalid type for logical NOT: '" + type + "', expected bool.");
            return Type.ERROR;
        }
        return Type.BOOL;
    }

    @Override
    public Type visitDeclarationStatement(ProjectGrammarParser.DeclarationStatementContext ctx) {
        if (visit(ctx.type()) == Type.ERROR) {
            Errors.addError(ctx.type().start, "Invalid type.");
            return Type.ERROR;
        }

        for (TerminalNode terminalNode : ctx.ID()) {
            Token token = terminalNode.getSymbol();
            if (!symbolTable.Add(token, (Type) visit(ctx.type()))) {
                return Type.ERROR;
            }
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
            case ProjectGrammarParser.INT -> Type.INT;
            case ProjectGrammarParser.FLOAT -> Type.FLOAT;
            case ProjectGrammarParser.BOOL -> Type.BOOL;
            case ProjectGrammarParser.STRING -> Type.STRING;
            default -> {
                Errors.addError(ctx.start, "Invalid literal '" + ctx.getText() + "'.");
                yield Type.ERROR;
            }
        };
    }

    @Override
    public Type visitType(ProjectGrammarParser.TypeContext ctx) {
        return switch (ctx.getText()) {
            case "int" -> Type.INT;
            case "float" -> Type.FLOAT;
            case "bool" -> Type.BOOL;
            case "string" -> Type.STRING;
            default -> {
                Errors.addError(ctx.start, "Invalid type '" + ctx.getText() + "'.");
                yield Type.ERROR;
            }
        };
    }

}
