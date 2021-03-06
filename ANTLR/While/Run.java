
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Run {
  public static void main( String[] args) throws Exception {
    File file = new File(args[0]);
    FileInputStream fis = null;
    try {
        fis = new FileInputStream(file);
        CharStream inp = CharStreams.fromStream(fis);
        WhileLexer lexer = new WhileLexer(inp);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        WhileParser parser = new WhileParser(tokens);
        ParseTree tree = parser.prog();
        ExecVisitor exec = new ExecVisitor();
        exec.visit(tree);
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
          if (fis != null) {
            fis.close();
          }
        } catch (IOException ex) {
          ex.printStackTrace();
        }
    }
  }
}