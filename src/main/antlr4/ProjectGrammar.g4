
grammar ProjectGrammar;

prog: statement+ EOF;


// A -> DO WHILE
/*
do statement while (condition);
*/

// B -> FOR
/*
for (expr ; contition ; expr) statement
*/

expr:
      op='-' expr                   # unaryMinus
    | op='!' expr                   # logicNot
    | expr op=('*'|'/'|'%') expr    # arithmetic
    | expr op=('+'|'-'|'.') expr    # arithmetic
    | expr op=('>'|'<') expr        # comparison
    | expr op=('=='|'!=') expr      # equality
    | expr op='&&' expr             # logicAndOr
    | expr op='||' expr             # logicAndOr
    | <assoc=right> ID op='=' expr  # assignment
    | literal                       # literals
    | ID                            # identifier
    | '(' expr ')'                  # parentheses
    ;

declarationStatement: type ID (',' ID)* ';';
emptyStatement: ';';
exprStatement: expr ';';
readStatement: 'read' (ID ',')* ID ';';
writeStatement: 'write' (expr ',')* expr ';';
blockStatement: '{' statement* '}';
ifStatement: 'if' '(' expr ')' statement ('else' statement)?;
whileStatement: 'while' '(' expr ')' statement;
//doWhileStatement: 'do' statement 'while' '(' expr ')' ';';
//forStatement: 'for' '(' expr ';' expr ';' expr ')' statement;


statement:
     ifStatement
    | blockStatement
    | declarationStatement
    | emptyStatement
    | readStatement
    | writeStatement
    | whileStatement
    | exprStatement
//    | doWhileStatement
//    | forStatement
    ;

literal: INT | FLOAT | BOOL | STRING;

type: 'int'
    | 'float'
    | 'bool'
    | 'string'
    ;

INT : '0'
    | [1-9][0-9]* ;

FLOAT: [0-9]+ '.' [0-9]+ ;

BOOL : 'true' | 'false' ;

STRING : '"' (~["\\\r\n] | EscapeSequence)* '"' ;

ID : [a-zA-Z]+ ;

WS : [ \t\r\n\u000C]+ -> skip ;
COMMENT : '//' ~[\r\n]* -> skip ;

fragment EscapeSequence:
    '\\' 'u005c'? [btnfr"'\\]
    | '\\' 'u005c'? ([0-3]? [0-7])? [0-7]
    | '\\' 'u'+ HexDigit HexDigit HexDigit HexDigit
;

HexDigit: [0-9a-fA-F] ;
