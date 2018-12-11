grammar While;

import LexerRule;

prog
  : var_decls SEMI stmts ;
var_decls
  : var_decls SEMI var_decl # VarDecs
  | var_decl                # VarDec
  ;

var_decl
  : ID ':' type_exp;

type_exp
  : 'int'                                     # IntType
  | 'bool'                                    # BoolType
  ;

stmts
  : stmts SEMI stmt         # Stmt_list
  | stmt                    # Stmt_single
  ;

stmt
  : ID '=' expr                       # Assign
  | 'print' expr                      # Print
  | 'if' expr 'then' stmt             # If0
  | 'if' expr 'then' stmt 'else' stmt # If
  | 'skip'                            # Skip
  | 'while' expr 'do' stmts 'od'      # While
  | '{' stmts '}'                     # Block
  ;

expr
  : expr bop expr            # E_Bop
  | INT                      # E_Int
  | ID                       # E_Var
  | '(' expr ')'             # E_Paren
  | 'true'                   # E_True
  | 'false'                  # E_False
  | '!' expr                 # E_Not
  ;

exprs
  : exprs ',' expr           # Expr_list
  | expr                     # Expr_single
  ;

bop
  : AND                      # BOP_AND
  | OR                       # BOP_OR
  | GT                       # ROP_GT
  | LT                       # ROP_LT
  | PLUS                     # AOP_PLUS
  | MINUS                    # AOP_MINUS
  | MULT                     # AOP_MULT
  | DIV                      # AOP_DIV
  ;
