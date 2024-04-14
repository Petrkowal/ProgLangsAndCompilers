package org.pjp;

import java.util.HashMap;

import org.antlr.v4.runtime.Token;

public class SymbolTable {

    private final HashMap<String, VarEntry> symbolTable;

    public SymbolTable() {
        symbolTable = new HashMap<String, VarEntry>();
    }

    public boolean Add(Token token, Type type) {
        if (symbolTable.containsKey(token.getText())) {
            Errors.addError(token, "Variable " + token.getText() + " already declared.");
            return false;
        } else {
            symbolTable.put(token.getText(), new VarEntry(type, null));
            return true;
        }
    }

    public Type Get(Token token) {
        if (symbolTable.containsKey(token.getText())) {
            return symbolTable.get(token.getText()).getType();
        } else {
            Errors.addError(token, "Variable " + token.getText() + " not declared.");
        }
        return Type.ERROR;
    }

    public void Set(String string, Type type, Object value) {
        if (symbolTable.containsKey(string)) {
            symbolTable.get(string).set(type, value);
        } else {
            symbolTable.put(string, new VarEntry(type, value));
        }
    }

    public VarEntry Get(String string) {
        return symbolTable.getOrDefault(string, null);
    }

    public class VarEntry {
        private Type type;
        private Object value;

        public VarEntry(Type type, Object value) {
            this.type = type;
            this.value = value;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public Object getValue() {
            return value;
        }

        public void set(Type type, Object value) {
            this.type = type;
            this.value = value;
        }
    }


}
