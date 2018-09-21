{
  (* count number of chars and lines. *)
  open Printf

  let num_lines = ref 0
  let num_chars = ref 0
}

rule count = parse
  | '\n'  { incr num_lines; incr num_chars; count lexbuf }
  | _     { incr num_chars; count lexbuf }
  | eof   { () }

{
  let main () =
    let lexbuf = Lexing.from_channel stdin in
    count lexbuf;
    printf "# of chars : %d\n" !num_chars;
    printf "# of lines : %d\n" !num_lines

  let _ = Printexc.print main ()
}

(*
  ocamllex sample2.mll
  ocamlc sample2.ml
  ./a.out
 *)