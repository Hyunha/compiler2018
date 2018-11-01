{
  (* count number of chars, words, and lines. *)
  open Printf
  open Parser_ast

  let check_var s =
    match s with
    | "print" -> PRINT
    | _ -> VAR s
}

let ignore = [' ' '\n' '\t']
let eol = '\n'
let id = ['a'-'z' 'A'-'Z']['a'-'z' 'A'-'Z' '0'-'9' '_']*
let num = ['1'-'9']['0'-'9']*

rule token = parse
  | ignore    { token lexbuf }
  | ";"       { SEMI }
  | "="       { EQ }
  | "+"       { PLUS }
  | id as id  { check_var id }
  | num as n  { NUM (int_of_string n) }
  | _ as c    { printf "lexing error %c@." c; exit 1 }
  | eof       { EOF }

{
  (* *)
}

(*

 *)