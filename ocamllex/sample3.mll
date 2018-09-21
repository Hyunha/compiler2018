{
  (* count number of chars, words, and lines. *)
  open Printf

  let num_lines = ref 0
  let num_chars = ref 0
  let num_words = ref 0
}

rule count = parse
  | [^' ' '\n' '\t']+ as word {
      incr num_words;
      num_chars := String.length word + !num_chars;
      count lexbuf
    }
  | '\n'  { incr num_chars; incr num_lines; count lexbuf }
  | _     { count lexbuf }
  | eof   { () }

{
  let main () =
    let lexbuf = Lexing.from_channel stdin in
    count lexbuf;
    printf "# of chars : %d\n" !num_chars;
    printf "# of words : %d\n" !num_words;
    printf "# of lines : %d\n" !num_lines

  let _ = Printexc.print main ()
}

(*
  ocamllex sample3.mll
  ocamlc sample3.ml
  ./a.out
 *)