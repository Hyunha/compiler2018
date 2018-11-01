{
  (* count number of chars, words, and lines. *)
  open Printf
  open Parser_ss
}

let ignore = [' ' '\n' '\t']

rule token = parse
  | ignore    { token lexbuf }
  | "("       { LP }
  | ")"       { RP }
  | _ as c    { printf "lexing error %c@." c; exit 1 }
  | eof       { EOF }

{
  (* *)
}

(*

 *)