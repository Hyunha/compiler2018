lexer grammar LexerRule;

NEWLINE : [\r\n ]+ -> skip;
INT     : [0-9]+ ;
ID      : [a-z][a-zA-Z0-9_]* ;
SEMI    : ';' ;
MULT    : '*' ;
PLUS    : '+' ;
DIV     : '/' ;
MINUS   : '-' ;
