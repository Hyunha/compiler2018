grammar LExpr;
prog: expr NEWLINE ;
expr: expr MULT expr    # Mult
    | expr PLUS expr    # Plus
    | expr DIV expr     # Div
    | expr MINUS expr   # Minus
    | INT               # Int
    | '(' expr ')'      # Parens
    ;
NEWLINE : [\r\n]+ ;
INT     : [0-9]+ ;
MULT    : '*' ;
PLUS    : '+' ;
DIV     : '/' ;
MINUS   : '-' ;