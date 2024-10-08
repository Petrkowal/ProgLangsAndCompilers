package org.pjp;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.FileInputStream;
import java.io.InputStream;

import org.pjp.generated.*;


public class Main {
    public static void main(String[] args) {

        try{

            String inputFile = "src/main/resources/input.txt";
            // parse
            InputStream is = new FileInputStream(inputFile);
            CharStream input = CharStreams.fromStream(is);
            ProjectGrammarLexer lexer = new ProjectGrammarLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ProjectGrammarParser parser = new ProjectGrammarParser(tokens);
            ParseTree tree = parser.prog();

            TypecheckVisitor typecheckVisitor = new TypecheckVisitor();
            CompileVisitor compileVisitor = new CompileVisitor();
            compileVisitor.setTypes(typecheckVisitor.getTypes());

            // check for syntax errors
            if(parser.getNumberOfSyntaxErrors() > 0){
                System.out.println("Syntax errors found. Exiting program.");
                return;
            }
            else {
                System.out.println("No syntax errors found.");
                typecheckVisitor.visit(tree);
                if(Errors.getNumberOfErrors() > 0){
                    Errors.printAndClearErrors();
                    System.out.println("Type errors found. Exiting program.");
                    return;
                }
                else {
                    System.out.println("No type errors found.");
                    java.util.List<Instruction> instructionList = compileVisitor.visit(tree);
                    // print the result
                    for (Instruction instruction : instructionList) {
                        System.out.println(instruction);
                    }

                    VirtualMachine vm = new VirtualMachine(instructionList);
                    vm.run();
                }
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

    }
}