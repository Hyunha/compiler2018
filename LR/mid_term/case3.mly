%{
  (* try below commands...
    > menhir --canonical case3.mly
    > menhir --lalr case3.mly
    > menhir --lalr --follow-construction case3.mly
   *)
%}

%token A B C D

%type<unit> s
%start s

%%

s:
  | a A     { }
  | B a C   { }
  | b C     { }
  | B b A   { }
  ;
a:
  | D       { }
  ;
b:
  | D       { }
  ;