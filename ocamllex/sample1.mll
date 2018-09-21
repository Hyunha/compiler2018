{
  (* first sample *)
  open Printf
}

rule is_verb = parse
  | [' ' '\t']+            { is_verb lexbuf }
  | ("is" | "am" | "are" | "were" | "was" |
     "be" | "being" | "been" |
     "do" | "does" | "did" |
     "will" | "would" | "should" |
     "can" | "could" |
     "has" | "have" | "had" | "go") as verb {
      printf "'%s': is a verb.\n" verb; 
      is_verb lexbuf
    }
  | ['a'-'z' 'A'-'Z']+ as non_verb {
      printf "'%s': is not a verb.\n" non_verb;
      is_verb lexbuf
    }
  | _ {
      is_verb lexbuf
    }
  | eof { () }

{
  let main () =
    let lexbuf = Lexing.from_channel stdin in
    is_verb lexbuf

  let _ = Printexc.print main ()
}

(*
ocamllex sample1.mll
ocamlc sample1.ml
./a.out
 *)