HOW TO RUN
------------------------------------------------

$ antlr4 -visitor -no-listener While.g4
$ javac While*.java Env.java ExecVisitor.java Run.java
$ java Run test.txt
