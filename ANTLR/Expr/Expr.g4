grammar Expr;
prog: (expr NEWLINE)* ;
expr: expr MULT expr
    | expr PLUS expr
    | expr DIV expr
    | expr MINUS expr
    | INT
    | '(' expr ')'
    ;
NEWLINE : [\r\n]+ ;
INT     : [0-9]+ ;
MULT    : '*' ;
PLUS    : '+' ;
DIV     : '/' ;
MINUS   : '-' ;