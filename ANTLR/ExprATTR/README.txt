HOW TO RUN
------------------------------------------------

$ antlr4 -no-listener -visitor LExpr.g4
$ javac LExpr*.java EvalVisitor.java Calc.java
$ java Calc test.txt
