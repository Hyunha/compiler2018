grammar While;

import LexerRule;

prog
  : stmts ;
stmts
  : stmts SEMI stmt         # Stmt_list
  | stmt                    # Stmt_single
  ;
stmt
  : ID ':=' expr             # Assign
  | 'print' expr             # Print
  ;

expr
  : expr bop expr            # BinaryOp
  | INT                      # Int
  | ID                       # Var
  | '(' expr ')'             # Parens
  ;

bop
  : PLUS                     # Plus
  | MINUS                    # Minus
  | MULT                     # Mult
  | DIV                      # Div
  ;
