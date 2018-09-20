%{
  int num_lines = 0;
  int num_chars = 0;
%}
%%

\n        { num_lines++; num_chars++; }
.         { num_chars++; }

%%

int main() {
  yylex();
  printf("# of chars : %d\n", num_chars);
  printf("# of lines : %d\n", num_lines);
  return 0;
}