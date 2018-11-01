%{
  (* try below commands...
    > menhir --canonical case2.mly
    > menhir --lalr case2.mly
   *)
%}

%token A B C D

%type<unit> s
%start s

%%

s:
  | a A     { }
  | B a C   { }
  | D C     { }
  | B D A   { }
  ;
a:
  | D       { }
  ;
