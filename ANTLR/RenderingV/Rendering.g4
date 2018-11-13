/*
 * Simple HTML rendering test.
   - sample html cases : https://en.wikipedia.org/wiki/Help:Wikitext
 */

grammar Rendering;
/*
 * Parser.
 */
document : blocks ;

blocks : (block NEWLINE)* ;

block : SHARPs TEXT   # SHARP
      | STARs TEXT    # STAR
      | TEXT          # PLAINTEXT
      | EQs TEXT EQs  # HEADER
      ;

/*
 * Lexer.
 */

NEWLINE : [\r\n]+ ;
SHARPs : SHARP+ ; 
STARs : STAR+ ;
EQs : EQ+ ;
TEXT : ~[\r\n#*=]+ ;

/*
 * Fragments.
 */
fragment
SHARP   : '#' ;
STAR    : '*' ;
EQ      : '=' ;
