HOW TO RUN
------------------------------------------------

$ antlr4 -no-listener -visitor Rendering.g4
$ javac Rendering*.java Translator*.java
$ java Translator test.txt > test.html