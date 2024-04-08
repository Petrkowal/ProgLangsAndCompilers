package org.pjp;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.FileInputStream;
import java.io.InputStream;

import org.pjp.generated.*;


public class Main {
    public static void main(String[] args) {
//        String[] antlrArgs = {"-no-listener", "-visitor", "-Dlanguage=Java", "-o", "src/main/java/org/pjp/", "src/main/antlr4/ProjectGrammar.g4"};
//        Tool antlr = new Tool(antlrArgs);
//        antlr.processGrammarsOnCommandLine();

        try{

            String inputFile = "src/main/resources/input.txt";
            // parse
            InputStream is = new FileInputStream(inputFile);
            CharStream input = CharStreams.fromStream(is);
            ProjectGrammarLexer lexer = new ProjectGrammarLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ProjectGrammarParser parser = new ProjectGrammarParser(tokens);
            ParseTree tree = parser.prog(); // parse the input stream
            TypecheckVisitor typecheckVisitor = new TypecheckVisitor();

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
                }
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

    }
}