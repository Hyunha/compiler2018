%{
  /*  */
  int strike = 0;
  int ball = 0;
  int out_count = 0;
  int inning = 0;
  int team = 0;

  int points[3][2] = {{0,0},{0,0},{0,0}};
  int end_game = 0;

  void print_points() {
    int points_of_A = points[0][0] + points[1][0] + points[2][0];
    int points_of_B = points[0][1] + points[1][1] + points[2][1];
    printf("/===================\\\n");
    printf("|   | 1 | 2 | 3 |sum|\n");
    printf("+---+---+---+---+---+\n");
    printf("| A |%3d|%3d|%3d|%3d|\n", points[0][0], points[1][0], points[2][0], points_of_A);
    printf("| B |%3d|%3d|%3d|%3d|\n", points[0][1], points[1][1], points[2][1], points_of_B);
    printf("\\===================/\n");
    if (end_game) {
      if (points_of_A == points_of_B) {
        printf("draw game\n");
      } else if(points_of_A > points_of_B) {
        printf("team A victory!\n");
      } else {
        printf("team B victory!\n");
      }
    }
  }

  void reset_inning() {
    strike = 0;
    ball = 0;
  }

  void get_points() {
    points[inning][team]++;
    reset_inning();
  }

  void get_outcount() {
    reset_inning();    
    out_count++;
    if (out_count == 3) {
      out_count = 0;
      if (team == 0) {
        team = 1;
        printf("[%d] team B ---------\n", inning + 1);
      } else {
        inning++;
        team = 0;
        if (inning == 3) {
          printf("game end!\n");
          end_game = 1;
        } else {
          printf("[%d] team A ---------\n", inning + 1);
        }
      }
    }
  }
%}

%%

[Ss]    {
  if (!end_game) {
    printf("strike!\n");
    strike++;
    if (strike == 3) {
      printf("strike out! [%d/3]\n", out_count + 1);
      get_outcount();
    }
  }
}
[Bb]    {
  if (!end_game) {
    printf("ball!\n");
    ball++;
    if (ball == 4) {
      printf("4 ball!\n");
      get_points();
    }
  }
}
[Hh] 	  {
  if (!end_game) {
    printf("Hit!\n");
    get_points();
    reset_inning();
    print_points();
  }
}

.|\n    { }
%%

int main(int argc, char** argv) {
  /* input from files */
  FILE *file;

  file = fopen(argv[1], "r");
  if (!file) {
    printf("no file.\n");
    exit(1);
  }
  yyin = file; // setting yyin as the input file.
  printf("[%d] team A ---------\n", inning);
  yylex();
  print_points();
  if (!end_game) {
    printf(" game is not ended.\n");
  }
  return 0;
}