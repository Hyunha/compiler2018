grammar While;

import LexerRule;

prog
  : var_decs SEMI stmts ;
var_decs
  : var_decs SEMI var_dec   # VarDecs
  | var_dec                 # VarDec
  ;
var_dec
  : ID ':' type_exp ;
type_exp
  : 'int'                             # IntType
  | 'bool'                            # BoolType
  | 'array' '[' INT ']' 'of' type_exp # ArrType
  ;
stmts
  : stmts SEMI stmt         # Stmt_list
  | stmt                    # Stmt_single
  ;
stmt
  : ID '=' aexp              # Assign
 // (1) | ID '#' bexp              # BAssign
 // (3) | ID '=' expr              # Assign
  | 'print' aexp             # Print
  | 'if' bexp 'then' stmt    # If
  ;

// (3) expr : aexp | bexp ;
aexp
  : aexp aop aexp            # BinaryOp
  | INT                      # Int
  | ID                       # Var
  | '(' aexp ')'             # Parens
// (2)  | bexp                # BexpInAexp
  ;

bexp
  : 'true'                   # BETrue
  | 'false'                  # BEFalse
  | '!' bexp                 # BENot
  | aexp rop aexp            # BERop
  | bexp bop bexp            # BEBop
  ;

rop
  : GT    # RopGT
  | LT    # RopLT
  ;

bop
  : AND   # BopAND
  | OR    # BopOR
  ;

aop
  : PLUS                     # Plus
  | MINUS                    # Minus
  | MULT                     # Mult
  | DIV                      # Div
  ;
