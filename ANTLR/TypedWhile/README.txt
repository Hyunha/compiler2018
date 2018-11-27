HOW TO RUN
------------------------------------------------

$ antlr4 -visitor -no-listener While.g4
$ javac While*.java Type*.java Env.java ExecVisitor.java Run.java
$ java Run test.txt
