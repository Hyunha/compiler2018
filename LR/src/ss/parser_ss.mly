%{

%}

%token LP RP EOF


%type<unit> s
%start s

%%

s:
  | LP s RP s { }
  |           { }
  ;

