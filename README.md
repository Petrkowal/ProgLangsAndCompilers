# Programming Languages and Compilers project

This is a project for VSB-TUO plc course.\
The project is a simple compiler for a C-like language in Java.\
The lexer, parser and visitors / listeners are generated using ANTLR4 from the grammar file.

1. Written the grammar file for the language.
2. Generated the lexer and parser using ANTLR4.
3. Implemented type checking visitor.
4. Implemented compilation visitor.
5. Implemented the virtual machine for the compiled code.

## Language description

The following section is rewritten / copied from
the [project assignment](http://behalek.cs.vsb.cz/wiki/index.php/PLC_Project) version 2024 (last modified 2024-03-28)

### Formatting

The program consists of a sequence of commands. Commands are written with free formatting. Comments, spaces, tabs, and
line breaks serve only as delimiters and do not affect the meaning of the program. Comments are bounded by two slashes
and the end of the line. Keywords are reserved. Identifiers and keywords are case-sensitive.

### Literals

- integers `int`
- floating point numbers `float`
- booleans `bool`
- strings `string`

### Variables

Variable's identifiers are composed of letters and digits, and it must start with a letter. Each variable must be
declared before it is used. Repeated declaration of a variable with the same name is an error. Variables must have one
of the following types: `int`, `float`, `bool` or `string`. After the variables are declared, they have initial
values: `0`, `0.0`, `""` respectively `false`.

### Statements

- `;` &ndash; empty statement
- `type variable, variable, ...;` &ndash; declaration of variables, all these variables have the same type `type`. It
  can be one of: `int`, `float`, `bool`, `String`
- `expression;` &ndash; expression statement
- `read variable, variable, ...;` &ndash; read values from the standard input and assign them to the variables. Each
  variable is on a separate line and it is verified that the entered value is of the correct type (not needed to check
  in the project)
- `write expression, expression, ...;` &ndash; write values to the standard output. Each expression is on a separate
  line
- `{ statement statement ... }` &ndash; block of statements
- `if (condition) statement [else statement]` &ndash; conditional statement, condition is an expression of type `bool`,
  the else part is optional
- `while (condition) statement` &ndash; loop statement, condition is an expression of type `bool`

### Expressions

Lists in expressions trees are literals or variables. Types of operands must preserve the type of the operator. If
necessary, int values are automatically cast to `float`. In other word, the type of `5 + 5.5` is `float`, and the
number `5` is automatically converted to `float`. There is **no** conversion from `float` to `int`.

| Description                  | Operator           | Operator's signature                                |
|------------------------------|--------------------|-----------------------------------------------------|
| unary minus                  | `-`                | `I -> I` &#8744; `F -> F`                           |
| binary arithmetic operations | `+`, `-`, `*`, `/` | `I x I -> I` &#8744; `F x F -> F`                   |
| modulo                       | `%`                | `I x I -> I`                                        |
| concatenation                | `.`                | `S x S -> S`                                        |
| relational operations        | `<`, `>`           | `n x n -> B`, where `n` is `I` or `F`               |
| comparison                   | `==`, `!=`         | `n x n -> B`, where `n` is `I`, `F` or `S`          |
| logic and, or                | `&&`, `\|\|`       | `B` x `B` -> `B`                                    |
| logic not                    | `!`                | `B` -> `B`                                          |
| assignment                   | `=`                | `x` x `x` -> `x`, where `x` is `I`, `F`, `B` or `S` |

In the assignment, left operand is strictly a variable and the right operand is expression. The type of the variable is
the type of the left operand. A side effect is storing the value on the right side into the variable. The automatic
conversion cannot change the type of the variable, i.e., it is impossible to store 'float' value in 'int' value.

We can use parentheses in expressions.

All operations (except `=`) have left associativity (`=` has right associativity), and their priority is from the lowest
to the highest:

1. `=`
2. `||`
3. `&&`
4. `==`, `!=`
5. `<`, `>`
6. `+`, `-`, `.` (concatenation)
7. `*`, `/`, `%`
8. `!`
9. `-` (unary minus)

### Instruction set

All instructions are stack based. The main memory is a stack and while evaluating the instructions, the input data are
taken from stack and the results are put also in stack.

| Instruction | Description                                                   |
|-------------|---------------------------------------------------------------|
| `add`       | binary `+`                                                    |
| `sub`       | binary `-`                                                    |
| `mul`       | binary `*`                                                    |
| `div`       | binary `/`                                                    |
| `mod`       | binary `%`                                                    |
| `uminus`    | unary `-`                                                     |
| `concat`    | concatenation  of strings                                     |
| `and`       | binary `&&`                                                   |
| `or`        | binary `\|\|`                                                 |
| `gt`        | binary `>`                                                    |
| `lt`        | binary `<`                                                    |
| `eq`        | binary `==`                                                   |
| `not`       | unary `!`                                                     |
| `itof`      | converts `int` from stack to `float` and puts it back         |
| `push T x`  | pushes the value `x` of type `T` to the stack                 |
| `pop`       | takes the value from the stack and discards it                |
| `load id`   | loads the value of the variable `id` to the stack             |
| `save id`   | saves the value from the stack to the variable `id`           |
| `label n`   | marks the position in the code with unique number `n`         |
| `jmp n`     | jumps to the position marked by the label `n`                 |
| `fjmp n`    | takes bool from the stack and jumps if it is `false`          |
| `print n`   | prints `n` values from the stack                              |
| `read T`    | reads value of type `T` from stdin and stores it on the stack |




















