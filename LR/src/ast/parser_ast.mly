%{

%}

%token EOF
%token SEMI EQ PRINT PLUS
%token<string> VAR
%token<int> NUM

%type<Ast.program> p
%start p

%%

p:
  | s         { $1 }

s:
  | s SEMI s  { Seq ($1, $3) }
  | VAR EQ e  { Assign ($1, $3) }
  | PRINT e   { Print $2 }
  ;

e:
  | e PLUS e  { Plus ($1, $3) }
  | VAR       { Var $1 }
  | NUM       { Num $1 }
  ;
