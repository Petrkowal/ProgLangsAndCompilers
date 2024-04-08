package org.pjp;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

import org.antlr.v4.runtime.Token;
import org.pjp.generated.*;

public class SymbolTable {
    // dictionary for storing the symbol table -> string and data type (Type)
    private final HashMap<String, Type> symbolTable;

    public SymbolTable() {
        symbolTable = new HashMap<String, Type>();
    }

    public boolean Add(Token token, Type type) {
        if (symbolTable.containsKey(token.getText())) {
            Errors.addError(token, "Variable " + token.getText() + " already declared.");
            return false;
        } else {
            symbolTable.put(token.getText(), type);
            return true;
        }
    }

    public Type Get(Token token) {
        if (symbolTable.containsKey(token.getText())) {
            return symbolTable.get(token.getText());
        } else {
            Errors.addError(token, "Variable " + token.getText() + " not declared.");
        }
        return Type.ERROR;
    }

//    public void SetValue(Token token, ) {
//        symbolTable.put(token.getText(), type);
//    }


}
