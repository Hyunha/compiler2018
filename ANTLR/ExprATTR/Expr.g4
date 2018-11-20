grammar Expr;
prog
  : expr EOF                { System.out.println($expr.val); }
  ;
expr returns [int val]
  : le=expr MULT re=expr    { $val = $le.val * $re.val; }
  | le=expr DIV re=expr     { $val = $le.val / $re.val; }
  | le=expr PLUS re=expr    { $val = $le.val + $re.val; }
  | le=expr MINUS re=expr   { $val = $le.val - $re.val; }
  | INT                     { $val = Integer.parseInt($INT.text); }
  | '(' expr ')'            { $val = $expr.val; }
  ;

NEWLINE : [\r\n ]+ -> skip;
INT     : [0-9]+ ;
MULT    : '*' ;
PLUS    : '+' ;
DIV     : '/' ;
MINUS   : '-' ;
