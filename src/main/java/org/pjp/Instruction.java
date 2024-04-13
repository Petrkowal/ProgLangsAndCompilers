package org.pjp;

public interface Instruction {

}


record Add() implements Instruction {
    @Override
    public String toString() {
        return "add";
    }
}

record Sub() implements Instruction {
    @Override
    public String toString() {
        return "sub";
    }
}

record Mul() implements Instruction {
    @Override
    public String toString() {
        return "mul";
    }
}

record Div() implements Instruction {
    @Override
    public String toString() {
        return "div";
    }
}

record Mod() implements Instruction {
    @Override
    public String toString() {
        return "mod";
    }
}

record Uminus() implements Instruction {
    @Override
    public String toString() {
        return "uminus";
    }
}

record Concat() implements Instruction {
    @Override
    public String toString() {
        return "concat";
    }
}

record And() implements Instruction {
    @Override
    public String toString() {
        return "and";
    }
}

record Or() implements Instruction {
    @Override
    public String toString() {
        return "or";
    }
}

record Gt() implements Instruction {
    @Override
    public String toString() {
        return "gt";

    }
}

record Lt() implements Instruction {
    @Override
    public String toString() {
        return "lt";
    }
}

record Eq() implements Instruction {
    @Override
    public String toString() {
        return "eq";
    }
}

record Not() implements Instruction {
    @Override
    public String toString() {
        return "not";
    }
}

record Itof() implements Instruction {
    @Override
    public String toString() {
        return "itof";
    }
}

record Push(Type t, Object val) implements Instruction {
    @Override
    public String toString() {
        String type = switch (t) {
            case INT -> "I";
            case FLOAT -> "F";
            case BOOL -> "B";
            case STRING -> "S";
            default -> "error";
        };
        return "push " + type + " " + val;
    }
}

record Pop() implements Instruction {
    @Override
    public String toString() {
        return "pop";
    }
}

record Load(String id, boolean space_end) implements Instruction {
    @Override
    public String toString() {  // TODO: Remove space end
        return "load " + id + (space_end ? " " : "");
    }
}

record Save(String id) implements Instruction {
    @Override
    public String toString() {
        return "save " + id;
    }
}

record Label(int n) implements Instruction {
    @Override
    public String toString() {
        return "label " + n;
    }
}

record Jmp(int n) implements Instruction {
    @Override
    public String toString() {
        return "jmp " + n;
    }
}

record Fjmp(int n) implements Instruction {
    @Override
    public String toString() {
        return "fjmp " + n;
    }
}

record Print(int n) implements Instruction {
    @Override
    public String toString() {
        return "print " + n;
    }
}

record Read(Type type) implements Instruction {
    @Override
    public String toString() {
        String t = switch (type) {
            case INT -> "I";
            case FLOAT -> "F";
            case BOOL -> "B";
            case STRING -> "S";
            default -> "error";
        };
        return "read " + t;
    }
}
