
open Format

let filename = ref ""

let verbose = ref false

let today = "20181101"
let tag = "master"
let major = 0
let minor = 1
let patch = 0
let misc  = 0
let devel = false

let print_version () =
  fprintf std_formatter "[%s] %s-%d.%d.%d.%d@."
    tag today major minor patch misc;
  exit 0

type mode = AST | SS | S
let mode = ref S
let execute_ast = ref false

let set_mode_s () =
  mode := S
let set_mode_ss () =
  mode := SS
let set_mode_ast () =
  mode := AST

let options = [
  ("-version",    Arg.Unit print_version,       "print version");
  ("-v",          Arg.Set verbose,              "set verbose option");
  ("-s",          Arg.Unit set_mode_s,          "switch parser : S (default)");
  ("-ss",         Arg.Unit set_mode_ss,         "switch parser : SS");
  ("-ast",        Arg.Unit set_mode_ast,        "switch parser : AST");
  ("-run",        Arg.Set execute_ast,          "execute ast program after parsing");
]

let usage_msg = 
  "main.byte -{s|ss|ast} -run? <filename>\n"

let anon_fun fn =
  filename := fn

let _ =
  let _ = Arg.parse options anon_fun usage_msg in
  if not (Sys.file_exists !filename) then
    begin
      fprintf err_formatter "file not founded : %s" !filename;
      exit 1
    end
  else
    begin
      try
        let chnl = open_in !filename in
        let lexbuf = Lexing.from_channel chnl in
        let _ =
          match !mode with
          | S ->
            fprintf std_formatter "try to parse S@.";
            let _ = Parser_s.s Lexer_s.token lexbuf in
            fprintf std_formatter "PARSE OK@."
          | SS ->
            fprintf std_formatter "try to parse SS@.";
            let _ = Parser_ss.s Lexer_ss.token lexbuf in
            fprintf std_formatter "PARSE OK@."
          | AST ->
            fprintf std_formatter "try to parse AST@.";
            let ast = Parser_ast.p Lexer_ast.token lexbuf in
            fprintf std_formatter "PARSE OK@.";
            fprintf std_formatter "%a@." Ast.print ast;
            if !execute_ast then
              begin
                fprintf std_formatter "EXECUTE PROGRAM@.";
                Ast.run ast;
                fprintf std_formatter "@."
              end
        in
        close_in chnl
      with
      | _ -> failwith "Unknown"
    end
