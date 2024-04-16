package org.pjp;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class VirtualMachine {
    private final Stack<Object> stack = new Stack<Object>();
    private final List<Instruction> instructions;
    private int instructionPointer = 0;
    private final SymbolTable symbolTable = new SymbolTable();
    private final HashMap<Integer, Integer> labels = new HashMap<Integer, Integer>();

    public VirtualMachine(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public void run() {
        for (int i = 0; i < instructions.size(); i++) {
            if (instructions.get(i) instanceof Label) {
                execLabel((Label) instructions.get(i), i);
            }
        }
        while (instructionPointer < instructions.size()) {
            Instruction instruction = instructions.get(instructionPointer);

            if (instruction instanceof Add) {
                execAdd();
            } else if (instruction instanceof Sub) {
                execSub();
            } else if (instruction instanceof Mul) {
                execMul();
            } else if (instruction instanceof Div) {
                execDiv();
            } else if (instruction instanceof Mod) {
                execMod();
            } else if (instruction instanceof Uminus) {
                execUminus();
            } else if (instruction instanceof Concat) {
                execConcat();
            } else if (instruction instanceof And) {
                execAnd();
            } else if (instruction instanceof Or) {
                execOr();
            } else if (instruction instanceof Gt) {
                execGt();
            } else if (instruction instanceof Lt) {
                execLt();
            } else if (instruction instanceof Eq) {
                execEq();
            } else if (instruction instanceof Not) {
                execNot();
            } else if (instruction instanceof Itof) {
                execItof();
            } else if (instruction instanceof Push) {
                execPush((Push) instruction);
            } else if (instruction instanceof Pop) {
                execPop();
            } else if (instruction instanceof Load) {
                execLoad((Load) instruction);
            } else if (instruction instanceof Save) {
                execSave((Save) instruction);
            } else if (instruction instanceof Jmp) {
                execJmp((Jmp) instruction);
            } else if (instruction instanceof Fjmp) {
                execFjmp((Fjmp) instruction);
            } else if (instruction instanceof Tjmp) {
                execTjmp((Tjmp) instruction);
            } else if (instruction instanceof Print) {
                execPrint((Print) instruction);
            } else if (instruction instanceof Read) {
                execRead((Read) instruction);
            }
            instructionPointer++;
        }
    }

    private void execAdd() {
        Object b = stack.pop();
        Object a = stack.pop();
        if (a instanceof Integer){
            stack.push((Integer) a + (Integer) b);
        }
        else if (a instanceof Float){
            stack.push((Float) a + (Float) b);
        }
        else if (a instanceof Double)
            stack.push((((Double) a).floatValue() + ((Double) b).floatValue()));
    }

    private void execSub() {
        Object b = stack.pop();
        Object a = stack.pop();
        if (a instanceof Integer){
            stack.push((Integer) a - (Integer) b);
        }
        else if (a instanceof Float){
            stack.push((Float) a - (Float) b);
        }
    }

    private void execMul() {
        Object b = stack.pop();
        Object a = stack.pop();
        if (a instanceof Integer){
            stack.push((Integer) a * (Integer) b);
        }
        else if (a instanceof Float){
            stack.push((Float) a * (Float) b);
        }
    }

    private void execDiv() {
        Object b = stack.pop();
        Object a = stack.pop();

        if (a instanceof Integer){
            stack.push((Integer) a / (Integer) b);
        }
        else if (a instanceof Float){
            stack.push((Float) a / (Float) b);
        }
    }

    private void execMod() {
        int b = (int) stack.pop();
        int a = (int) stack.pop();
        stack.push(a % b);
    }

    private void execUminus() {
        Object a = stack.pop();
        if (a instanceof Integer){
            stack.push(-(Integer) a);
        }
        else if (a instanceof Float){
            stack.push(-(Float) a);
        }
    }

    private void execConcat() {
        String a = (String) stack.pop();
        String b = (String) stack.pop();
        stack.push(b + a);
    }

    private void execAnd() {
        boolean b = (boolean) stack.pop();
        boolean a = (boolean) stack.pop();
        stack.push(a && b);
    }

    private void execOr() {
        boolean b = (boolean) stack.pop();
        boolean a = (boolean) stack.pop();
        stack.push(a || b);
    }

    private void execGt() {
        Object b = stack.pop();
        Object a = stack.pop();
        if (a instanceof Integer){
            stack.push((Integer) a > (Integer) b);
        }
        else if (a instanceof Float){
            stack.push((Float) a > (Float) b);
        }
    }

    private void execLt() {
        Object b = stack.pop();
        Object a = stack.pop();
        if (a instanceof Integer){
            stack.push((Integer) a < (Integer) b);
        }
        else if (a instanceof Float){
            stack.push((Float) a < (Float) b);
        }
    }

    private void execEq() {
        Object b = stack.pop();
        Object a = stack.pop();
        if (a instanceof Integer){
            stack.push(a.equals(b));
        }
        else if (a instanceof Float){
            stack.push(a.equals(b));
        }
    }

    private void execNot() {
        boolean a = (boolean) stack.pop();
        stack.push(!a);
    }

    private void execItof() {
        int a = (int) stack.pop();
        stack.push((float) a);
    }

    private void execPush(Push push) {
        switch (push.t()) {
            case INT -> {
                if (push.val() instanceof Integer)
                    stack.push(push.val());
                else
                    stack.push(Integer.parseInt((String) push.val()));
            }
            case FLOAT -> {
                if (push.val() instanceof Float)
                    stack.push(push.val());
                else if (push.val() instanceof Double)
                    stack.push(((Double) push.val()).floatValue());
                else
                    stack.push(Float.parseFloat((String) push.val()));
            }
            case BOOL -> {
                if (push.val() instanceof Boolean)
                    stack.push(push.val());
                else
                    stack.push(Boolean.parseBoolean((String) push.val()));
            }
            case STRING -> {
                stack.push(push.val());
            }
        }
    }

    private void execPop() {
        stack.pop();
    }

    private void execLoad(Load load) {
        SymbolTable.VarEntry var = symbolTable.Get(load.id());
        if (var == null)
            throw new RuntimeException("Variable " + load.id() + " not declared.");
        stack.push(var.getValue());
    }

    private void execSave(Save save) {
        Object o = stack.pop();
        if (o instanceof Integer)
            symbolTable.Set(save.id(), Type.INT, o);
        else if (o instanceof Float)
            symbolTable.Set(save.id(), Type.FLOAT, o);
        else if (o instanceof Double)
            symbolTable.Set(save.id(), Type.FLOAT, ((Double) o).floatValue());
        else if (o instanceof Boolean)
            symbolTable.Set(save.id(), Type.BOOL, o);
        else if (o instanceof String)
            symbolTable.Set(save.id(), Type.STRING, o);
        else
            throw new RuntimeException("Invalid type");
    }

    private void execLabel(Label label, int instruction) {
        labels.put(label.n(), instruction);
    }

    private void execJmp(Jmp jmp) {
        instructionPointer = labels.get(jmp.n()) - 1; // TOD: Maybe without the -1 will be ok too
    }

    private void execFjmp(Fjmp fjmp) {
        boolean a = (boolean) stack.pop();
        if (!a)
            instructionPointer = labels.get(fjmp.n()) - 1;
    }

    private void execTjmp(Tjmp tjmp) {
        boolean a = (boolean) stack.pop();
        if (a)
            instructionPointer = labels.get(tjmp.n()) - 1;
    }

    private void execPrint(Print print) {
        int n = print.n();
        for (int i = 0; i < n; i++)
            System.out.println(stack.pop());
    }

    private void execRead(Read read) {
        boolean ok = false;
        while (!ok){
            try{

                System.out.print("Input: ");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                switch (read.type()) {
                    case INT -> stack.push(Integer.parseInt(input));
                    case FLOAT -> stack.push(Float.parseFloat(input));
                    case BOOL -> stack.push(Boolean.parseBoolean(input));
                    case STRING -> stack.push(input);
                }
                ok = true;
            }
            catch (Exception e){
                System.out.println("Invalid input. Try again.");
            }
        }
    }

}
