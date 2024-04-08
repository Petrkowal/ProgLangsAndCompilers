package org.pjp;

import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

public class Errors {
    private static final List<String> errorsData = new ArrayList<String>();

    public static void addError(Token token, String message) {
        errorsData.add("Error at line " + token.getLine() + ", column " + token.getCharPositionInLine() + ": " + message);
    }
    public static int getNumberOfErrors() {
        return errorsData.size();
    }
    public static void printAndClearErrors() {
        for (String error : errorsData) {
            System.out.println(error);
        }
        errorsData.clear();
    }
}
