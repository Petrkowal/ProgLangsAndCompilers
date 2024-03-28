package org.pjp;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.Tool;

import java.io.FileInputStream;
import java.io.InputStream;

import org.pjp.antlr4.*;



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


            // check for syntax errors
            if(parser.getNumberOfSyntaxErrors() > 0){
                System.out.println("Syntax errors found. Exiting program.");
                return;
            }
            else {
                System.out.println("No syntax errors found.");
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }

    }
}