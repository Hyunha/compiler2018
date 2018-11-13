/*
 * Simple HTML rendering test.
   - sample html cases : https://en.wikipedia.org/wiki/Help:Wikitext
 */

grammar Rendering;
/*
 * Parser.
 */
document : (block NEWLINE)* ;

block : SHARPs TEXT # SHARP
      | STARs TEXT  # STAR
      | TEXT        # NORMAL
      ;

/*
 * Lexer.
 */

NEWLINE : [\r\n]+ ;
SHARPs : SHARP+ ; 
STARs : STAR+ ;
TEXT : ~[\r\n#*]+ ;

/*
 * Fragments.
 */
fragment
SHARP : '#' ;
STAR  : '*' ;
