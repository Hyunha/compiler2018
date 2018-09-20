%{
  int num_lines = 1;
  int num_chars = 0;
  int num_words = 0;
%}

word	[^ \n\t]+
eol		\n
%%

{word} 	  { num_words++; num_chars += yyleng; }
{eol}     { num_lines++; num_chars++; }
.         { num_chars++; }

%%

int main(int argc, char** argv) {
  FILE *file;

  file = fopen(argv[1], "r");
  if (!file) {
    printf("no file.\n");
    exit(1);
  }
  yyin = file;
  yylex();
  printf("# of chars : %d\n", num_chars);
  printf("# of words : %d\n", num_words);
  printf("# of lines : %d\n", num_lines);
  return 0;
}