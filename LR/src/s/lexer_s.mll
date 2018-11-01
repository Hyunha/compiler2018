{
  (* count number of chars, words, and lines. *)
  open Printf
  open Parser_s
}

let ignore = [' ' '\n' '\t']

rule token = parse
  | ignore    { token lexbuf }
  | "("       { LP }
  | ")"       { RP }
  | "A"       { A }
  | _ as c    { printf "lexing error %c@." c; exit 1 }
  | eof       { EOF }

{
  (* *)
}

(*

 *)