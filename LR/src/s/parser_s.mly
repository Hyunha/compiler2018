%{

%}

%token LP RP A EOF


%type<unit> s
%start s

%%

s:
  | LP s RP { }
  | A       { }
  ;

