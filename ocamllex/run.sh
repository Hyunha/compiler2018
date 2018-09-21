#!/bin/bash
ocamllex sample$1.mll; ocamlc sample$1.ml; ./a.out $2