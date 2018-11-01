open Printf
open Format

let fpf = fprintf

type var = string

type s =
  | Assign of var * e
  | Seq of s * s
  | Print of e
and e =
  | Plus of e * e
  | Minus of e * e
  | Var of var
  | Num of int
and program = s

let rec print fmt p =
    print_s fmt p
and print_s fmt s =
  match s with
  | Assign (v, e) ->
    fpf fmt "%s := %a" v print_e e
  | Seq (s1, s2) ->
    fpf fmt "%a;@.%a" print_s s1 print_s s2
  | Print e ->
    fpf fmt "print %a" print_e e
and print_e fmt e =
  match e with
  | Plus (e1, e2) ->
    fpf fmt "%a + %a" print_e e1 print_e e2
  | Minus (e1, e2) ->
    fpf fmt "%a - %a" print_e e1 print_e e2
  | Var var -> 
    fpf fmt "%s" var
  | Num n ->
    fpf fmt "%d" n

module Env = Map.Make(String)

let rec run p =
    ignore (run_s Env.empty p)
and run_s env s =
  match s with
  | Assign (v, e) ->
    Env.add v (eval_e env e) env
  | Seq (s1, s2) ->
    run_s (run_s env s1) s2
  | Print e ->
    fpf std_formatter "%d" (eval_e env e);
    env
and eval_e env e =
  match e with
  | Plus (e1, e2) ->
    (eval_e env e1) + (eval_e env e2)
  | Minus (e1, e2) ->
    (eval_e env e1) - (eval_e env e2)
  | Var var -> 
    Env.find var env
  | Num n ->
    n

