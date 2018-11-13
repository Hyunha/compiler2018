grammar LExpr;

import LexerRule;

prog: expr NEWLINE ;
expr: expr PLUS expr          # Plus
    | expr MINUS expr         # Minus
    | expr op=('*'|'/') expr  # MulDiv
    | INT                     # Int
    | '(' expr ')'            # Parens
    ;
